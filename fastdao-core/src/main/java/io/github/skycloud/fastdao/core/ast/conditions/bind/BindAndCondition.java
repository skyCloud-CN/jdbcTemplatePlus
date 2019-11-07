/**
 * @(#)BindAndCondition.java, 11月 01, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.conditions.bind;

import io.github.skycloud.fastdao.core.ast.conditions.AndCondition;
import io.github.skycloud.fastdao.core.ast.conditions.Condition;
import io.github.skycloud.fastdao.core.ast.request.ConditionalRequest;

/**
 * @author yuntian
 */
public
interface BindAndCondition<P extends ConditionalRequest<P>> extends AndCondition<BindAndCondition<P>> {

    @Override
    BindAndCondition<P> and(Condition condition);

    @Override
    BindAndCondition<P> andOptional(Condition condition);

    @Override
    BindAndCondition<P> andIf(Condition condition, boolean preCondition);

    @Override
    BindAndCondition<P> allowEmpty();

    /**
     * return to Bind ConditionalRequest
     *
     * @return
     */
    P endCondition();

}