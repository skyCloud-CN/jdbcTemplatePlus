/**
 * @(#)ColumnMapColumnMappingHandler.java, 10月 13, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.plugins.columnmap;

import com.skycloud.fastdao.core.mapping.ColumnMapping;
import com.skycloud.fastdao.core.mapping.TypeHandler;
import com.skycloud.fastdao.core.mapping.TypeHandlerResolver;
import com.skycloud.fastdao.core.plugins.PluggableHandler;
import com.skycloud.fastdao.core.reflection.MetaClass;
import com.skycloud.fastdao.core.reflection.MetaField;
import org.apache.commons.lang3.StringUtils;

import java.sql.JDBCType;
import java.util.Optional;

/**
 * @author yuntian
 */
public class ColumnMapColumnMappingHandler implements PluggableHandler<ColumnMapping> {

    @Override
    public ColumnMapping handle(ColumnMapping pluggable, Class clazz) {
        MetaClass metaClass = MetaClass.of(clazz);
        MetaField field = metaClass.getMetaField(pluggable.getFieldName());
        ColumnMap annotation = field.getAnnotation(ColumnMap.class);
        if (annotation == null) {
            return pluggable;
        }
        if (StringUtils.isNotBlank(annotation.column())) {
            pluggable.setColumnName(annotation.column());
        }

        if (annotation.jdbcType() != JDBCType.NULL) {
            pluggable.setJdbcType(annotation.jdbcType());
            Optional.ofNullable(TypeHandlerResolver.getTypeHandler(pluggable.getJavaType(), pluggable.getJdbcType()))
                    .ifPresent(pluggable::setHandler);
        }
        if (annotation.handler() != TypeHandler.class) {
            try {
                pluggable.setHandler(annotation.handler().newInstance());
            } catch (Exception e) {
                throw new RuntimeException("create Handler fail");
            }
        }
        return pluggable;
    }
}