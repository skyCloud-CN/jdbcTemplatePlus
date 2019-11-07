/**
 * @(#)ColumnMapValueParser.java, 10月 13, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.plugins.columnmap;

import io.github.skycloud.fastdao.core.ast.ValueParser;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.mapping.TypeHandler;

import java.util.Map;

/**
 * @author yuntian
 */
public class ColumnMapValueParser implements ValueParser {

    private ValueParser delegate;

    private RowMapping rowMapping;

    public ColumnMapValueParser(ValueParser delegate, RowMapping rowMapping) {
        this.delegate = delegate;
        this.rowMapping = rowMapping;
    }
    /**
     * convert java type to jdbcType
     */
    @Override
    public String parseValue(String field, Object value) {
        TypeHandler handler = rowMapping.getColumnMappingByColumnName(field).getHandler();
        value = handler.parseParam(value);
        return delegate.parseValue(field, value);
    }

    @Override
    public Map<String, Object> getParamMap() {
        return delegate.getParamMap();
    }
}