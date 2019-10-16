/**
 * @(#)ColumnMapValueParser.java, 10月 13, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.plugins.columnmap;

import com.skycloud.fastdao.core.ast.ValueParser;
import com.skycloud.fastdao.core.mapping.RowMapping;
import com.skycloud.fastdao.core.mapping.TypeHandler;

import java.util.Map;

/**
 * @author yuntian
 */
public class ColumnMapValueParser implements ValueParser {

    ValueParser delegate;

    RowMapping rowMapping;

    public ColumnMapValueParser(ValueParser delegate, RowMapping rowMapping) {
        this.delegate = delegate;
        this.rowMapping = rowMapping;
    }

    @Override
    public String parseField(String field) {
        return delegate.parseField(field);
    }

    @Override
    public String parseValue(String field, Object value) {
        TypeHandler handler = rowMapping.getColumnMappingByColumnName(field).getHandler();
        try {
            value = handler.parseParam(value);
        } catch (Exception e) {

        }
        return delegate.parseValue(field, value);
    }

    @Override
    public Map<String, Object> getParamMap() {
        return delegate.getParamMap();
    }
}