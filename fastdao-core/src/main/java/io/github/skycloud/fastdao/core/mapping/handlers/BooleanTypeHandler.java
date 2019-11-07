/**
 * @(#)BooleanHandler.java, 10月 03, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.mapping.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yuntian
 */
public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {

    @Override
    public Boolean getNullableValue(ResultSet rs, String columnName) throws SQLException {
        return rs.getBoolean(columnName);
    }

}