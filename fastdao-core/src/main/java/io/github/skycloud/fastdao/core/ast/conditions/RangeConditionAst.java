/**
 * @(#)RangeConditionAst.java, 11月 01, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.conditions;

import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.visitor.Visitor;
import lombok.Getter;

/**
 * @author yuntian
 */
@Getter
public
class RangeConditionAst implements RangeCondition, SqlAst {

    private String field;

    private Object gt;

    private Object lt;

    private boolean egt = false;

    private boolean elt = false;

    public RangeConditionAst(String field) {
        this.field = field;
    }

    @Override
    public io.github.skycloud.fastdao.core.ast.conditions.RangeConditionAst gt(Object value) {
        this.gt = value;
        this.egt = false;
        return this;
    }

    @Override
    public io.github.skycloud.fastdao.core.ast.conditions.RangeConditionAst gte(Object value) {
        this.gt = value;
        this.egt = true;
        return this;
    }

    @Override
    public io.github.skycloud.fastdao.core.ast.conditions.RangeConditionAst lt(Object value) {
        this.lt = value;
        this.elt = false;
        return this;
    }

    @Override
    public io.github.skycloud.fastdao.core.ast.conditions.RangeConditionAst lte(Object value) {
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
        io.github.skycloud.fastdao.core.ast.conditions.RangeConditionAst condition = new io.github.skycloud.fastdao.core.ast.conditions.RangeConditionAst(field);
        condition.gt = gt;
        condition.lt = lt;
        condition.egt = egt;
        condition.elt = elt;
        return condition;
    }


}