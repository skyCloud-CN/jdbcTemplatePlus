/**
 * @(#)Long2DateTypeHandler.java, 10月 09, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.mapping.handlers;

import com.skycloud.fastdao.core.mapping.TypeHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author yuntian
 */
public class Bigint2DateTypeHandler implements TypeHandler<Date> {

    @Override
    public Object parseParam(Date param) {
        return param == null ? null : param.getTime();
    }

    @Override
    public Date getResult(ResultSet rs, String columnName) throws SQLException {
        return new Date(rs.getLong(columnName));
    }

}