/**
 * @(#)Plugins.java, 10月 07, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.reflect.TypeToken;

import io.github.skycloud.fastdao.core.plugins.autofill.AutoFillPlugin;
import io.github.skycloud.fastdao.core.plugins.columnmap.ColumnMapAnnotationPlugin;
import io.github.skycloud.fastdao.core.plugins.exclude.ExcludeAnnotationPlugin;
import io.github.skycloud.fastdao.core.plugins.logicdelete.LogicDeleteHandler;
import io.github.skycloud.fastdao.core.plugins.logicdelete.LogicDeletePlugin;
import io.github.skycloud.fastdao.core.plugins.shard.ShardPlugin;
import io.github.skycloud.fastdao.core.util.SingletonCache;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author yuntian
 */
public class PluginManager {

    private final static List<Plugin> plugins = Lists.newArrayList();

    private final static Multimap<Class, Ordered<PluggableHandler>> handlers = ArrayListMultimap.create();

    private final static SingletonCache<Class, List<PluggableHandler>> runtimeHandlers = new SingletonCache<>(new ConcurrentHashMap<>(), PluginManager::registerRuntime);

    private static AtomicInteger handlerNumber = new AtomicInteger(0);

    private static PluggableHandler sentinel = (pluggable, clazz) -> pluggable;

    private static AtomicBoolean installed = new AtomicBoolean(false);

    static {
        register(new ColumnMapAnnotationPlugin());
        register(new ExcludeAnnotationPlugin());
        register(new ShardPlugin());
        register(new AutoFillPlugin());
        register(new LogicDeletePlugin());
    }

    public static void register(Plugin plugin) {
        plugins.add(plugin);
    }

    public static synchronized void installAllPlugin() {
        if (installed.get()) {
            return;
        }
        for (Plugin plugin : plugins) {
            install(plugin);
        }
        installed.getAndSet(true);
    }

    private static void install(Plugin plugin) {
        if (plugin == null) {
            return;
        }
        List<PluggableHandler> handlerList = plugin.getHandlers();
        if (!CollectionUtils.isEmpty(handlerList)) {
            handlerList.forEach(PluginManager::register);
        }
    }

    private static void register(PluggableHandler handler) {
        Class pluggableClass = (Class) TypeToken.of(handler.getClass()).resolveType(PluggableHandler.class.getTypeParameters()[0]).getType();
        int order = handlerNumber.incrementAndGet();
        handlers.put(pluggableClass, new Ordered<>(handler, order));
    }

    public static <T extends Pluggable> T plugin(T pluggable, Class clazz) {
        if (!installed.get()) {
            installAllPlugin();
        }
        Collection<PluggableHandler<T>> collection = (Collection) runtimeHandlers.get(pluggable.getClass());
        for (PluggableHandler<T> handler : collection) {
            pluggable = handler.handle(pluggable, clazz);
        }
        return pluggable;
    }

    private static List<PluggableHandler> registerRuntime(Class clazz) {
        Collection<Collection<Ordered<PluggableHandler>>> allHandler = Lists.newArrayList();
        for (Class key : handlers.keys()) {
            if (key.isAssignableFrom(clazz)) {
                Collection<Ordered<PluggableHandler>> ordered = handlers.get(key);
                if (CollectionUtils.isNotEmpty(ordered)) {
                    allHandler.add(ordered);
                }
            }
        }
        List<PluggableHandler> handlers = allHandler.stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Ordered::getOrder))
                .map(Ordered::getDelegate)
                .collect(Collectors.toList());
        handlers.add(sentinel);
        return handlers;
    }

    public static void registerBefore(Class<? extends Plugin> indicatePlugin, Plugin installPlugin) {
        for (int i = 0; i < plugins.size(); i++) {
            if (plugins.get(i).getClass() == indicatePlugin) {
                plugins.add(i, installPlugin);
            }
        }
    }

    public static void registerAfter(Class<? extends Plugin> indicatePlugin, Plugin installPlugin) {
        for (int i = 0; i < plugins.size(); i++) {
            if (plugins.get(i).getClass() == indicatePlugin) {
                plugins.add(i + 1, installPlugin);
            }
        }
    }

    private static class Ordered<T> {

        private T delegate;

        private int order;

        public Ordered(T delegate, int order) {
            this.delegate = delegate;
            this.order = order;
        }

        public T getDelegate() {
            return delegate;
        }

        public int getOrder() {
            return order;
        }
    }
}