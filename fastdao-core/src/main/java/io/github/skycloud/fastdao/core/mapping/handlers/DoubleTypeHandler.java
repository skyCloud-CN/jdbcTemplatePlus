package io.github.skycloud.fastdao.core.mapping.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoubleTypeHandler extends BaseTypeHandler<Double> {

    @Override
    public Double getNullableValue(ResultSet rs, String columnName) throws SQLException {
        return rs.getDouble(columnName);
    }
}
