/**
 * @(#)TimestampToLongTypeHandler.java, 10月 09, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.mapping.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author yuntian
 */
public class Timestamp2LongTypeHandler extends BaseTypeHandler<Long> {

    @Override
    public Long getNullableValue(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        return timestamp == null ? null : timestamp.getTime();
    }

    @Override
    public Object parseParam(Long param) {
        return param == null ? null : new Timestamp(param);
    }
}