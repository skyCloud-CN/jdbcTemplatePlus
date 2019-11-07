/**
 * @(#)StringHandler.java, 9月 28, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.mapping.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yuntian
 */
public class StringTypeHandler extends BaseTypeHandler<String> {

    @Override
    public String getNullableValue(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
    }
}