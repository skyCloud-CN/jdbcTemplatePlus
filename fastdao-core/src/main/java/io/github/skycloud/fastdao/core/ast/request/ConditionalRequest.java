/**
 * @(#)ConditionalRequest.java, 10月 19, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.request;


import io.github.skycloud.fastdao.core.ast.conditions.Condition;
import io.github.skycloud.fastdao.core.ast.conditions.bind.BindAndCondition;
import io.github.skycloud.fastdao.core.ast.conditions.bind.BindAndConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.bind.BindOrCondition;
import io.github.skycloud.fastdao.core.ast.conditions.bind.BindOrConditionAst;

/**
 * @author yuntian
 */
public interface ConditionalRequest<T extends ConditionalRequest<T>> extends Request {

    Condition getCondition();

    T setCondition(Condition condition);

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

}