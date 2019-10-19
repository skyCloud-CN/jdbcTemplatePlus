/**
 * @(#)RowMapperBuilder.java, 9月 26, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.jdbctemplate.plus;

import com.google.common.collect.Maps;

import io.github.skycloud.fastdao.core.util.SingletonCache;

import java.util.Map;


/**
 * @author yuntian
 */
public class RowMappers {

    private static Map<Class, RowMapperWrapper> rowMappings = new SingletonCache<>(Maps.newConcurrentMap(), RowMappers::newInstance);

    public static <T> RowMapperWrapper<T> of(Class<T> clazz) {
        return rowMappings.get(clazz);
    }

    private static <T> RowMapperWrapper<T> newInstance(Class<T> clazz) {
        return buildRowMapper(clazz);
    }

    public synchronized static <T> RowMapperWrapper<T> buildRowMapper(final Class<T> clazz) {
        return new RowMapperWrapper<>(clazz);
    }
}