/**
 * @(#)SqlVisitor.java, 10月 12, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.visitor;

import io.github.skycloud.fastdao.core.ast.ValueParser;

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