/**
 * @(#)ClassMapper.java, 9月 28, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.mapping;

import com.skycloud.fastdao.core.FastDaoConfig;
import com.skycloud.fastdao.core.annotation.PrimaryKey;
import com.skycloud.fastdao.core.annotation.Table;
import com.skycloud.fastdao.core.exceptions.FastDAOException;
import com.skycloud.fastdao.core.exceptions.MetaDataException;
import com.skycloud.fastdao.core.plugins.Pluggable;
import com.skycloud.fastdao.core.reflection.MetaClass;
import com.skycloud.fastdao.core.reflection.MetaField;
import com.skycloud.fastdao.core.util.NameUtil;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuntian
 */
public class RowMapping implements Pluggable {

    private final static Map<Class, RowMapping> rowMappings = new ConcurrentHashMap<>(16);

    private Class javaClass;

    private Map<String, ColumnMapping> fieldNameMap = new HashMap<>(16);

    private Map<String, ColumnMapping> columnNameMap = new HashMap<>(16);

    private ColumnMapping primaryKey;

    private String tableName;

    public static RowMapping of(Class clazz) {
        RowMapping rowMapping = rowMappings.get(clazz);
        if (rowMapping == null) {
            rowMapping = addRowMapping(clazz);
        }
        return rowMapping;
    }

    private synchronized static RowMapping addRowMapping(Class clazz) {
        RowMapping rowMapping = rowMappings.get(clazz);
        if (rowMapping == null) {
            rowMapping = new RowMapping(clazz).invokePlugin(clazz);
            rowMappings.put(clazz, rowMapping);
        }
        return rowMapping;
    }

    protected RowMapping(Class clazz) {
        MetaClass metaClass = MetaClass.of(clazz);
        this.javaClass = clazz;
        this.tableName = getTableName(metaClass);
        for (MetaField metaField : metaClass.metaFields()) {
            // TODO 支持驼峰转换
            String columnName = FastDaoConfig.isMapUnderscoreToCamelCase() ?
                    NameUtil.mapUnderscoreToCamelCase(metaField.getFieldName())
                    : metaField.getFieldName();
            ColumnMapping columnMapping = new ColumnMapping()
                    .setColumnName(columnName)
                    .setJavaType(metaField.getFieldType())
                    .setFieldName(metaField.getFieldName())
                    .setHandler(TypeHandlerResolver.getTypeHandler(metaField.getFieldType()))
                    .setPrimary(false)
                    .setRowMapping(this).invokePlugin(clazz);
            this.fieldNameMap.put(columnMapping.getFieldName(), columnMapping);
            this.columnNameMap.put(columnMapping.getColumnName(), columnMapping);
            if (isPrimaryField(metaField)) {
                columnMapping.setPrimary(true);
                setPrimaryKey(columnMapping);
            }
        }
    }

    public Class getJavaClass() {
        return javaClass;
    }

    public ColumnMapping getPrimaryKeyColumn() {
        if (primaryKey == null) {
            throw new FastDAOException("primary key not defined");
        }
        return primaryKey;
    }

    public void setJavaClass(Class javaClass) {
        this.javaClass = javaClass;
    }

    public ColumnMapping getColumnMappingByFieldName(String propName) {
        return fieldNameMap.get(propName);
    }

    public ColumnMapping getColumnMappingByColumnName(String columnName) {
        return columnNameMap.get(columnName);
    }

    private boolean isPrimaryField(MetaField metaField) {
        Annotation annotation = metaField.getAnnotation(PrimaryKey.class);
        return annotation != null;
    }

    private void setPrimaryKey(ColumnMapping columnMapping) {
        if (primaryKey != null) {
            throw new RuntimeException("has multiple @PrimaryKey annotation");
        }
        primaryKey = columnMapping;
    }

    private String getTableName(MetaClass metaClass) {
        Table table = metaClass.getAnnotation(Table.class);
        if (table == null) {
            throw new MetaDataException("class {} doesn't have @Table annotation", metaClass.getJavaClass());
        }
        return table.tableName();
    }

    public String getTableName() {
        return tableName;
    }

    public Collection<ColumnMapping> getColumnMapping() {
        return fieldNameMap.values();
    }

    public Map<String, ColumnMapping> getFieldNameMap() {
        return fieldNameMap;
    }

}