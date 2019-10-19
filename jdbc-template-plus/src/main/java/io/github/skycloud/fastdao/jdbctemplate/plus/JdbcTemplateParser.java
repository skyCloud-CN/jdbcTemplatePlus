/**
 * @(#)JdbcVisitor.java, 9月 10, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.jdbctemplate.plus;

import com.google.common.collect.Maps;
import io.github.skycloud.fastdao.core.ast.ValueParser;

import java.util.Map;

import static io.github.skycloud.fastdao.core.ast.constants.SQLConstant.SPACE;
import static io.github.skycloud.fastdao.core.ast.constants.SQLConstant.UNQUOTE;

/**
 * @author yuntian
 */
public class JdbcTemplateParser implements ValueParser {

    private Map<String, Object> paramMap = Maps.newHashMap();

    private int counter = 0;

    @Override
    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    @Override
    public String parseField(String field) {
        return UNQUOTE + field + UNQUOTE + SPACE;
    }

    @Override
    public String parseValue(String field, Object value) {
        String counterStr = String.valueOf(counter++);
        paramMap.put(counterStr, value);
        return ":" + counterStr + SPACE;
    }
}