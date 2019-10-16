/**
 * @(#)ColumnMappingRequestHandler.java, 10月 13, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.plugins.columnmap;

import com.skycloud.fastdao.core.ast.ValueParser;
import com.skycloud.fastdao.core.mapping.RowMapping;
import com.skycloud.fastdao.core.plugins.PluggableHandler;

/**
 * @author yuntian
 */
public class ColumnMappingValueParserHandler implements PluggableHandler<ValueParser> {

    @Override
    public ValueParser handle(ValueParser pluggable, Class clazz) {
        return new ColumnMapValueParser(pluggable, RowMapping.of(clazz));
    }
}