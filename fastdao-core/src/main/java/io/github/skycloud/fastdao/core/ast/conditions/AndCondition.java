/**
 * @(#)AndCondition.java, 10月 20, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.conditions;

import com.google.common.collect.Lists;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.visitor.Visitor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuntian
 */
public interface AndCondition<T extends AndCondition> extends Condition {

    /**
     * add a {@link Condition} as a subCondition
     *
     * @param condition
     * @return
     */
    T and(Condition condition);

    /**
     * add a {@link Condition} as subCondition only when the condition is legal,
     * for example,  if a {@link EqualCondition} has value = null, then it is a illegal condition
     * but {@link AndCondition} and {@link OrCondition} is always seen as a legal condition if it allow empty
     *
     * @param condition
     * @return
     */
    T andOptional(Condition condition);

    /**
     * condition is add only when preCondition is true
     */
    T andIf(Condition condition, boolean preCondition);

    /**
     * this method is for dynamic SQL when there is no subCondition.
     * if this method is executed,request will translate to SQL `SELECT * FROM table`
     * or else ,request will be seen as a illegal request and return nothing
     */
    T allowEmpty();


}