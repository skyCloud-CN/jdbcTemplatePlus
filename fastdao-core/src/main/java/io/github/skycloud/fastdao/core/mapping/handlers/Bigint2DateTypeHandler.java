/**
 * @(#)Long2DateTypeHandler.java, 10月 09, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.mapping.handlers;

import io.github.skycloud.fastdao.core.mapping.TypeHandler;

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