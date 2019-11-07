/**
 * @(#)ColumnMappingRequestHandler.java, 10月 13, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.plugins.columnmap;

import io.github.skycloud.fastdao.core.ast.ValueParser;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.plugins.PluggableHandler;

/**
 * @author yuntian
 */
public class ColumnMappingValueParserHandler implements PluggableHandler<ValueParser> {

    @Override
    public ValueParser handle(ValueParser pluggable, Class clazz) {
        return new ColumnMapValueParser(pluggable, RowMapping.of(clazz));
    }
}