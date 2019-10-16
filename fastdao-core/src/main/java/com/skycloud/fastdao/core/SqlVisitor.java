/**
 * @(#)SqlVisitor.java, 10月 12, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core;

import com.skycloud.fastdao.core.ast.ValueParser;
import com.skycloud.fastdao.core.ast.Visitor;

/**
 * @author yuntian
 */
public abstract class SqlVisitor implements Visitor {

    protected StringBuilder sb;

    protected String tableName;

    protected ValueParser valueParser;

    protected boolean isLegal = true;

    public SqlVisitor(String tableName, ValueParser valueParser) {
        this.sb = new StringBuilder();
        this.tableName = tableName;
        this.valueParser = valueParser;
    }

    public ValueParser getValueParser() {
        return valueParser;
    }

    public SqlVisitor setValueParser(ValueParser valueParser) {
        this.valueParser = valueParser;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public SqlVisitor setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public boolean isLegal() {
        return isLegal;
    }

    public String getSql() {
        return sb.toString();
    }
}