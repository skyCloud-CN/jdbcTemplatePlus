/**
 * @(#)BindOrConditionAst.java, 11月 01, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.conditions.bind;

import io.github.skycloud.fastdao.core.ast.conditions.OrConditionAst;
import io.github.skycloud.fastdao.core.ast.request.ConditionalRequest;

/**
 * @author yuntian
 */
public class BindOrConditionAst<P extends ConditionalRequest<P>> extends OrConditionAst<BindOrCondition<P>> implements BindOrCondition<P> {

    private P parent;

    public BindOrConditionAst(P parent) {
        this.parent = parent;
    }

    @Override
    public P endCondition() {
        return parent;
    }
}