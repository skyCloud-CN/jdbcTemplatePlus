/**
 * @(#)RangeCondition.java, 10月 20, 2019.
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
public interface RangeCondition extends Condition {

    /**
     * make column > value
     *
     * @param value
     * @return
     */
    RangeCondition gt(Object value);

    /**
     * make column >= value
     *
     * @param value
     * @return
     */
    RangeCondition gte(Object value);

    /**
     * make column < value
     *
     * @param value
     * @return
     */
    RangeCondition lt(Object value);

    /**
     * make column <= value
     *
     * @param value
     * @return
     */
    RangeCondition lte(Object value);


    @Getter
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
        public RangeConditionAst gt(Object value) {
            this.gt = value;
            this.egt = false;
            return this;
        }

        @Override
        public RangeConditionAst gte(Object value) {
            this.gt = value;
            this.egt = true;
            return this;
        }

        @Override
        public RangeConditionAst lt(Object value) {
            this.lt = value;
            this.elt = false;
            return this;
        }

        @Override
        public RangeConditionAst lte(Object value) {
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
            RangeConditionAst condition = new RangeConditionAst(field);
            condition.gt = gt;
            condition.lt = lt;
            condition.egt = egt;
            condition.elt = elt;
            return condition;
        }


    }
}