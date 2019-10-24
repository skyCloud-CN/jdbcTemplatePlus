/**
 * @(#)ConditionalRequest.java, 10月 19, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast;


import io.github.skycloud.fastdao.core.ast.conditions.AndCondition;
import io.github.skycloud.fastdao.core.ast.conditions.AndCondition.DefaultAndCondition;
import io.github.skycloud.fastdao.core.ast.conditions.OrCondition;
import io.github.skycloud.fastdao.core.ast.conditions.OrCondition.DefaultOrCondition;

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
        DefaultBindAndCondition<T> condition = new DefaultBindAndCondition(this);
        setCondition(condition);
        return condition;
    }

    /**
     * to support stream api, equals to setCondition(Condition.or()...)
     *
     * @return
     */
    default BindOrCondition<T> beginOrCondition() {
        DefaultBindOrCondition<T> condition = new DefaultBindOrCondition(this);
        setCondition(condition);
        return condition;
    }

    T setCondition(Condition condition);

    Condition getCondition();

    interface BindAndCondition<P extends ConditionalRequest<P>> extends AndCondition<BindAndCondition<P>> {

        @Override
        BindAndCondition<P> and(Condition condition);

        @Override
        BindAndCondition<P> andIgnoreIllegal(Condition condition);

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
        BindOrCondition<P> orIgnoreIllegal(Condition condition);

        @Override
        BindOrCondition<P> allowEmpty();
        /**
         * return to Bind ConditionalRequest
         *
         * @return
         */
        P endCondition();

    }

    class DefaultBindAndCondition<P extends ConditionalRequest<P>> extends DefaultAndCondition<BindAndCondition<P>> implements BindAndCondition<P> {

        private P parent;

        private DefaultBindAndCondition(P parent) {
            this.parent = parent;
        }

        @Override
        public P endCondition() {
            return parent;
        }
    }

    class DefaultBindOrCondition<P extends ConditionalRequest<P>> extends DefaultOrCondition<BindOrCondition<P>> implements BindOrCondition<P> {

        private P parent;

        private DefaultBindOrCondition(P parent) {
            this.parent = parent;
        }

        @Override
        public P endCondition() {
            return parent;
        }
    }
}