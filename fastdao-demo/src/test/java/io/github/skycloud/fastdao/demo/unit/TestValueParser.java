/**
 * @(#)TestValueParser.java, 11月 02, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.demo.unit;

import io.github.skycloud.fastdao.core.ast.ValueParser;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yuntian
 */
class TestValueParser implements ValueParser {

    @Override
    public String parseValue(String field, Object value) {
        if (value instanceof Collection) {
            List<String> strArr = ((List<String>) ((Collection) value).stream()
                    .map(x -> x.toString() + " ")
                    .collect(Collectors.toList()));

            return StringUtils.join(strArr.toArray(), ",");
        } else {
            return String.valueOf(value) + " ";
        }

    }

    @Override
    public Map<String, Object> getParamMap() {
        return null;
    }
}