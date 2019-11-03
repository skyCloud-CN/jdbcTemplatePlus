/**
 * @(#)BindOrCondition.java, 11月 01, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.conditions.bind;

import io.github.skycloud.fastdao.core.ast.conditions.Condition;
import io.github.skycloud.fastdao.core.ast.conditions.OrCondition;
import io.github.skycloud.fastdao.core.ast.request.ConditionalRequest;

/**
 * @author yuntian
 */
public
interface BindOrCondition<P extends ConditionalRequest<P>> extends OrCondition<BindOrCondition<P>> {

    @Override
    BindOrCondition<P> or(Condition condition);

    @Override
    BindOrCondition<P> orOptional(Condition condition);

    @Override
    BindOrCondition<P> orIf(Condition condition, boolean preCondition);

    @Override
    BindOrCondition<P> allowEmpty();

    /**
     * return to Bind ConditionalRequest
     *
     * @return
     */
    P endCondition();

}