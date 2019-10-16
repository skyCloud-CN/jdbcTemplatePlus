/**
 * @(#)RowMapperBuilder.java, 9月 26, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.springjdbc.plus;

import com.google.common.collect.Maps;
import com.skycloud.fastdao.core.mapping.ColumnMapping;
import com.skycloud.fastdao.core.mapping.RowMapping;
import com.skycloud.fastdao.core.mapping.TypeHandler;
import com.skycloud.fastdao.core.reflection.MetaClass;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


/**
 * @author yuntian
 */
public class RowMappers {

    private static Map<Class, RowMapper> rowMappings = Maps.newConcurrentMap();

    public static <T> RowMapper<T> of(Class<T> clazz) {
        RowMapper mapper = rowMappings.get(clazz);
        if (mapper == null) {
            mapper = buildRowMapper(clazz);
            rowMappings.put(clazz, mapper);
        }
        return mapper;
    }

    public synchronized static <T> RowMapper<T> buildRowMapper(final Class<T> clazz) {
        RowMapper mapper = rowMappings.get(clazz);
        if (mapper != null) {
            return mapper;
        }
        mapper = new RowMapper<T>() {
            @Override
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                try {
                    T instance = clazz.newInstance();
                    MetaClass metaClass = MetaClass.of(clazz);
                    RowMapping rowMapping = RowMapping.of(clazz);
                    for (ColumnMapping cm : rowMapping.getColumnMapping()) {
                        TypeHandler handler = cm.getHandler();
                        Object value = handler.getResult(rs, cm.getColumnName());
                        metaClass.invokeSetter(instance, cm.getFieldName(), value);
                    }
                    return instance;
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
        };
        return mapper;
    }
}