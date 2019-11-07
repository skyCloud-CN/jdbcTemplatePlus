/**
 * @(#)BindAndConditionAst.java, 11月 01, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.conditions.bind;

import io.github.skycloud.fastdao.core.ast.conditions.AndConditionAst;
import io.github.skycloud.fastdao.core.ast.request.ConditionalRequest;

/**
 * @author yuntian
 */
public class BindAndConditionAst<P extends ConditionalRequest<P>> extends AndConditionAst<BindAndCondition<P>> implements BindAndCondition<P> {

    private P parent;

    public BindAndConditionAst(P parent) {
        this.parent = parent;
    }

    @Override
    public P endCondition() {
        return parent;
    }
}