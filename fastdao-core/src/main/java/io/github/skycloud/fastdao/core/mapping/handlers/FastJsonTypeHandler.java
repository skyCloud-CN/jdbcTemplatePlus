
package io.github.skycloud.fastdao.core.mapping.handlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.skycloud.fastdao.core.mapping.TypeHandler;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yuntian
 */
public class FastJsonTypeHandler implements TypeHandler<JSONObject> {

    @Override
    public Object parseParam(JSONObject param) {
        return param == null ? null : param.toJSONString();
    }

    @Override
    public JSONObject getResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        if (StringUtils.isEmpty(value)) {
            return new JSONObject();
        } else {
            return JSON.parseObject(value);
        }
    }
}