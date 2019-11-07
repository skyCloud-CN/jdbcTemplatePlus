/**
 * @(#)IntegerHandler.java, 10月 03, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.mapping.handlers;


import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yuntian
 */
public class IntegerTypeHandler extends BaseTypeHandler<Integer> {

    @Override
    public Integer getNullableValue(ResultSet rs, String columnName) throws SQLException {
        return rs.getInt(columnName);
    }
}