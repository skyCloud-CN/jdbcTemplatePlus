/**
 * @(#)FieldMapper.java, 9月 28, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.mapping;

import com.skycloud.fastdao.core.plugins.Pluggable;

import java.sql.JDBCType;

/**
 * @author yuntian
 */
public class ColumnMapping implements Pluggable {

    private String fieldName;

    private String columnName;

    private Class javaType;

    private JDBCType jdbcType;

    private TypeHandler handler;

    private boolean primary;

    private RowMapping rowMapping;

    public String getFieldName() {
        return fieldName;
    }

    public ColumnMapping setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public String getColumnName() {
        return columnName;
    }

    public ColumnMapping setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public Class getJavaType() {
        return javaType;
    }

    public ColumnMapping setJavaType(Class javaType) {
        this.javaType = javaType;
        return this;
    }

    public JDBCType getJdbcType() {
        return jdbcType;
    }

    public ColumnMapping setJdbcType(JDBCType jdbcType) {
        this.jdbcType = jdbcType;
        return this;
    }

    public TypeHandler getHandler() {
        return handler;
    }

    public ColumnMapping setHandler(TypeHandler handler) {
        this.handler = handler;
        return this;
    }

    public boolean isPrimary() {
        return primary;
    }

    public ColumnMapping setPrimary(boolean primary) {
        this.primary = primary;
        return this;
    }

    public RowMapping getRowMapping() {
        return rowMapping;
    }

    public ColumnMapping setRowMapping(RowMapping rowMapping) {
        this.rowMapping = rowMapping;
        return this;
    }
}