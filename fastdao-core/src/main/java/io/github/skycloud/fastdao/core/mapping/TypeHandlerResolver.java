/**
 * @(#)HandlerResolver.java, 10月 03, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.mapping;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import io.github.skycloud.fastdao.core.exceptions.FastDAOException;
import io.github.skycloud.fastdao.core.mapping.handlers.Bigint2DateTypeHandler;
import io.github.skycloud.fastdao.core.mapping.handlers.BooleanTypeHandler;
import io.github.skycloud.fastdao.core.mapping.handlers.DateTypeHandler;
import io.github.skycloud.fastdao.core.mapping.handlers.FastJsonTypeHandler;
import io.github.skycloud.fastdao.core.mapping.handlers.IntegerTypeHandler;
import io.github.skycloud.fastdao.core.mapping.handlers.LongTypeHandler;
import io.github.skycloud.fastdao.core.mapping.handlers.StringTypeHandler;
import io.github.skycloud.fastdao.core.mapping.handlers.Timestamp2LongTypeHandler;

import java.sql.JDBCType;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuntian
 */
public class TypeHandlerResolver {

    private static final Map<Class, TypeHandler> defaultHandlerMap = Maps.newConcurrentMap();

    private static final Map<Class, Map<JDBCType, TypeHandler>> typeHanlderMap = Maps.newConcurrentMap();

    static {
        register(String.class, new StringTypeHandler());
        register(Integer.class, new IntegerTypeHandler());
        register(int.class,new IntegerTypeHandler());
        register(Long.class, new LongTypeHandler());
        register(long.class,new LongTypeHandler());
        register(Date.class, new DateTypeHandler());
        register(Boolean.class, new BooleanTypeHandler());
        register(boolean.class, new BooleanTypeHandler());
        register(JSONObject.class, new FastJsonTypeHandler());
        register(Long.class, JDBCType.TIMESTAMP, new Timestamp2LongTypeHandler());
        register(Long.class, JDBCType.TIME, new Timestamp2LongTypeHandler());
        register(Long.class, JDBCType.DATE, new Timestamp2LongTypeHandler());
        register(Date.class, JDBCType.BIGINT, new Bigint2DateTypeHandler());
    }

    public synchronized static void register(Class clazz, TypeHandler handler) {
        if (defaultHandlerMap.putIfAbsent(clazz, handler) != null) {
            throw new RuntimeException();
        }

    }

    public synchronized static void register(Class clazz, JDBCType jdbcType, TypeHandler handler) {
        typeHanlderMap.putIfAbsent(clazz, new ConcurrentHashMap<>());
        if (typeHanlderMap.get(clazz).putIfAbsent(jdbcType, handler) != null) {
            throw new RuntimeException();
        }
    }

    public static TypeHandler getTypeHandler(Class clazz) {
        try {
            return defaultHandlerMap.get(clazz);
        } catch (Exception e) {
            throw new FastDAOException("handler for class {} not exist", clazz.getName());
        }
    }

    public static TypeHandler getTypeHandler(Class clazz, JDBCType jdbcType) {
        if (jdbcType == null) {
            return getTypeHandler(clazz);
        }
        Map<JDBCType, TypeHandler> handlerMap = typeHanlderMap.get(clazz);
        if (handlerMap == null) {
            return defaultHandlerMap.get(clazz);
        }
        TypeHandler handler = handlerMap.get(jdbcType);
        return handler == null ? defaultHandlerMap.get(clazz) : handler;

    }

}