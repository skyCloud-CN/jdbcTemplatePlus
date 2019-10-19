/**
 * @(#)String2JsonTypeHandler.java, 10月 11, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
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