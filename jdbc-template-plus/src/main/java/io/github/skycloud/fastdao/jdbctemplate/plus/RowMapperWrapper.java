/**
 * @(#)RowMapperWrapper.java, 10月 19, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.jdbctemplate.plus;

import io.github.skycloud.fastdao.core.mapping.ColumnMapping;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.mapping.TypeHandler;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yuntian
 */
public class RowMapperWrapper<T> {

    private Class<T> clazz;

    public RowMapperWrapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    public RowMapper<T> getMapper(Collection<String> columns) {
        return (rs, rowNum) -> {
            try {
                T instance = clazz.newInstance();
                Collection<ColumnMapping> columnMappings;
                RowMapping rowMapping = RowMapping.of(clazz);
                if (CollectionUtils.isEmpty(columns)) {
                    columnMappings = rowMapping.getColumnMapping();
                } else {
                    columnMappings = columns.stream().map(rowMapping::getColumnMappingByColumnName).collect(Collectors.toList());
                }
                MetaClass metaClass = MetaClass.of(clazz);
                for (ColumnMapping cm : columnMappings) {
                    TypeHandler handler = cm.getHandler();
                    Object value = handler.getResult(rs, cm.getColumnName());
                    metaClass.invokeSetter(instance, cm.getFieldName(), value);
                }
                return instance;
            } catch (Exception e) {
                throw new RuntimeException();
            }
        };
    }
}