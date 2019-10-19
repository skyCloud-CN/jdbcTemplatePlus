/**
 * @(#)DateHandler.java, 10月 03, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.mapping.handlers;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author yuntian
 */
public class DateTypeHandler extends BaseTypeHandler<Date> {

    @Override
    public Date getNullableValue(ResultSet rs, String columnName) throws SQLException {
        Date timestamp = rs.getTimestamp(columnName);
        return timestamp == null ? null : new Date(timestamp.getTime());
    }
}