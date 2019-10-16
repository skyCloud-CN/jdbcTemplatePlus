/**
 * @(#)CountRequest.java, 9月 09, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.ast.request;

import com.skycloud.fastdao.core.ast.Condition;
import com.skycloud.fastdao.core.ast.Request;
import com.skycloud.fastdao.core.ast.SqlAst;
import com.skycloud.fastdao.core.ast.Visitor;
import com.skycloud.fastdao.core.table.Column;

/**
 * @author yuntian
 */
public class CountRequest extends Request {

    private boolean distinct;

    private String countField;

    private Condition condition;


    public CountRequest setCondition(Condition condition) {
        this.condition = condition;
        return this;
    }

    public CountRequest setCountField(String countField) {
        this.countField = countField;
        return this;
    }

    public CountRequest setCountField(Column column) {
        this.countField = column.toString();
        return this;
    }

    public CountRequest distinct() {
        distinct = true;
        return this;
    }

    public Condition getCondition() {
        return condition;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public String getCountField() {
        return countField;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        CountRequest request = new CountRequest();
        request.distinct = distinct;
        request.countField = countField;
        request.condition = (Condition) condition.copy();
        return request;
    }

}