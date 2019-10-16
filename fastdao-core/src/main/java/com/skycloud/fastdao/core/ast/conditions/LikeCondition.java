/**
 * @(#)LikeCondition.java, 9月 30, 2019.
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
public class LikeCondition extends Condition {

    private String field;

    private Object value;

    private boolean matchLeft = false;

    private boolean matchRight = false;

    public LikeCondition(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public boolean isLegal() {
        return value != null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public LikeCondition matchLeft() {
        matchLeft = true;
        return this;
    }

    public LikeCondition matchRight() {
        matchRight = true;
        return this;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        return new LikeCondition(field, value);
    }


}