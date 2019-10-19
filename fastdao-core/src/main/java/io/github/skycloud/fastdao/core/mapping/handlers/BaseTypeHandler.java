/**
 * @(#)BaseTypeHandler.java, 10月 10, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.mapping.handlers;

import io.github.skycloud.fastdao.core.exceptions.ResultSetHandleException;
import io.github.skycloud.fastdao.core.mapping.TypeHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yuntian
 */
public abstract class BaseTypeHandler<T> implements TypeHandler<T> {

    @Override
    public Object parseParam(T param) {
        return param;
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        try {
            T value = getNullableValue(rs, columnName);
            return rs.wasNull() ? null : value;
        } catch (Exception e) {
            throw new ResultSetHandleException(e.getMessage());
        }
    }

    public abstract T getNullableValue(ResultSet rs, String columnName) throws SQLException;
}