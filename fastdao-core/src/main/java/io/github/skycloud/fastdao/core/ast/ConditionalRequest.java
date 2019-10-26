/**
 * @(#)ConditionalRequest.java, 10月 19, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast;


import io.github.skycloud.fastdao.core.ast.conditions.AndCondition;
import io.github.skycloud.fastdao.core.ast.conditions.AndCondition.AndConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.OrCondition;
import io.github.skycloud.fastdao.core.ast.conditions.OrCondition.OrConditionAst;

/**
 * @author yuntian
 */
public interface ConditionalRequest<T extends ConditionalRequest<T>> extends Request {

    /**
     * to support stream api, equals to setCondition(Condition.and()...)
     *
     * @return
     */
    default BindAndCondition<T> beginAndCondition() {
        BindAndConditionAst<T> condition = new BindAndConditionAst(this);
        setCondition(condition);
        return condition;
    }

    /**
     * to support stream api, equals to setCondition(Condition.or()...)
     *
     * @return
     */
    default BindOrCondition<T> beginOrCondition() {
        BindOrConditionAst<T> condition = new BindOrConditionAst(this);
        setCondition(condition);
        return condition;
    }

    T setCondition(Condition condition);

    Condition getCondition();

    interface BindAndCondition<P extends ConditionalRequest<P>> extends AndCondition<BindAndCondition<P>> {

        @Override
        BindAndCondition<P> and(Condition condition);

        @Override
        BindAndCondition<P> andOptional(Condition condition);

        @Override
        BindAndCondition<P> allowEmpty();

        /**
         * return to Bind ConditionalRequest
         *
         * @return
         */
        P endCondition();

    }

    interface BindOrCondition<P extends ConditionalRequest<P>> extends OrCondition<BindOrCondition<P>> {

        @Override
        BindOrCondition<P> or(Condition condition);

        @Override
        BindOrCondition<P> orOptional(Condition condition);

        @Override
        BindOrCondition<P> allowEmpty();
        /**
         * return to Bind ConditionalRequest
         *
         * @return
         */
        P endCondition();

    }

    class BindAndConditionAst<P extends ConditionalRequest<P>> extends AndConditionAst<BindAndCondition<P>> implements BindAndCondition<P> {

        private P parent;

        private BindAndConditionAst(P parent) {
            this.parent = parent;
        }

        @Override
        public P endCondition() {
            return parent;
        }
    }

    class BindOrConditionAst<P extends ConditionalRequest<P>> extends OrConditionAst<BindOrCondition<P>> implements BindOrCondition<P> {

        private P parent;

        private BindOrConditionAst(P parent) {
            this.parent = parent;
        }

        @Override
        public P endCondition() {
            return parent;
        }
    }
}