/**
 * @(#)ILikeCondition.java, 10月 20, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.conditions;


import io.github.skycloud.fastdao.core.ast.Condition;
import io.github.skycloud.fastdao.core.ast.SqlAst;

import io.github.skycloud.fastdao.core.ast.Visitor;
import lombok.Getter;

/**
 * @author yuntian
 */
public interface LikeCondition extends Condition {

    LikeCondition matchLeft();

    LikeCondition matchRight();


    /**
     * @author yuntian
     */
    @Getter
    class DefaultLikeCondition implements LikeCondition, SqlAst {

        private String field;

        private Object value;

        private boolean matchLeft = false;

        private boolean matchRight = false;

        public DefaultLikeCondition(String field, Object value) {
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

        @Override
        public DefaultLikeCondition matchLeft() {
            matchLeft = true;
            return this;
        }

        @Override
        public DefaultLikeCondition matchRight() {
            matchRight = true;
            return this;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SqlAst copy() {
            DefaultLikeCondition condition=new DefaultLikeCondition(field, value);
            condition.matchLeft=this.matchLeft;
            condition.matchRight=this.matchRight;
            return condition;
        }


    }
}