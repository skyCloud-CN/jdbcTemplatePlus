/**
 * @(#)SingletonMap.java, 10月 17, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author yuntian
 */
public class SingletonCache<K, V> implements Map<K, V> {

    private Map<K, V> delegate;

    private OnLoad<K, V> onLoad;

    public SingletonCache(Map<K, V> map, OnLoad<K, V> onLoad) {
        this.delegate = map;
        this.onLoad = onLoad;
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return delegate.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = delegate.get(key);
        K k = (K) key;
        if (value == null) {
            return put(k, onLoad.load(k));
        } else {
            return value;
        }
    }

    @Override
    public synchronized V put(K key, V value) {
        delegate.putIfAbsent(key,value);
        return delegate.get(key);
    }

    @Override
    public synchronized V remove(Object key) {
        return delegate.remove(key);
    }

    @Override
    public synchronized void putAll(Map<? extends K, ? extends V> m) {
        m.forEach((k, v) -> delegate.put(k, v));
    }

    @Override
    public synchronized void clear() {
        delegate.clear();
    }

    @Override
    public Set<K> keySet() {
        return delegate.keySet();
    }

    @Override
    public Collection<V> values() {
        return delegate.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return delegate.entrySet();
    }
}