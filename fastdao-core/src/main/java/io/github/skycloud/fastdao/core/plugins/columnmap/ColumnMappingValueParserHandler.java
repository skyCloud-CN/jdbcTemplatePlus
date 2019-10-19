/**
 * @(#)ColumnMappingRequestHandler.java, 10月 13, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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