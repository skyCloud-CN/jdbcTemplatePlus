/**
 * @(#)SqlVisitor.java, 10月 12, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core;

import io.github.skycloud.fastdao.core.ast.ValueParser;
import io.github.skycloud.fastdao.core.ast.Visitor;

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