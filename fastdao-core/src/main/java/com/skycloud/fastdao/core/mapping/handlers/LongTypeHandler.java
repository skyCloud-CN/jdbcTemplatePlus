/**
 * @(#)LongHandler.java, 10月 03, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.mapping.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yuntian
 */
public class LongTypeHandler extends BaseTypeHandler<Long> {

    @Override
    public Long getNullableValue(ResultSet rs, String columnName) throws SQLException {
        return rs.getLong(columnName);
    }

}