/**
 * @(#)RangeCondition.java, 9月 10, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.ast.conditions;


import com.skycloud.fastdao.core.ast.Condition;
import com.skycloud.fastdao.core.ast.SqlAst;
import com.skycloud.fastdao.core.ast.Visitor;
import lombok.Getter;

/**
 * @author yuntian
 */
@Getter
public class RangeCondition extends Condition {

    private String field;

    private Object gt;

    private Object lt;

    private boolean egt = false;

    private boolean elt = false;

    public RangeCondition(String field) {
        this.field = field;
    }

    public RangeCondition gt(Object value) {
        this.gt = value;
        this.egt = false;
        return this;
    }

    public RangeCondition gte(Object value) {
        this.gt = value;
        this.egt = true;
        return this;
    }

    public RangeCondition lt(Object value) {
        this.lt = value;
        this.elt = false;
        return this;
    }

    public RangeCondition lte(Object value) {
        this.lt = value;
        this.elt = true;
        return this;
    }

    @Override
    public boolean isLegal() {
        return gt != null || lt != null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        RangeCondition condition = new RangeCondition(field);
        condition.gt = gt;
        condition.lt = lt;
        condition.egt = egt;
        condition.elt = elt;
        return condition;
    }


}