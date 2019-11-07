/**
 * @(#)JdbcVisitor.java, 9月 10, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.jdbctemplate.plus;

import com.google.common.collect.Maps;
import io.github.skycloud.fastdao.core.ast.ValueParser;

import java.util.Map;

import static io.github.skycloud.fastdao.core.ast.constants.SQLConstant.SPACE;

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
    public String parseValue(String field, Object value) {
        String counterStr = String.valueOf(counter++);
        paramMap.put(counterStr, value);
        return ":" + counterStr + SPACE;
    }
}