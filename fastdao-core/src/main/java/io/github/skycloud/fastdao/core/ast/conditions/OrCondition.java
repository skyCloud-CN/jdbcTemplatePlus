/**
 * @(#)OrCondition.java, 10月 20, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.conditions;

import com.google.common.collect.Lists;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuntian
 */
public interface OrCondition<T extends OrCondition> extends Condition {

    T or(Condition condition);

    T orOptional(Condition condition);

    T orIf(Condition condition, boolean preCondition);

    /**
     * this method is for dynamic SQL when there is no subCondition.
     * if this method is executed,request will translate to SQL `SELECT * FROM table`
     * or else ,request will be seen as a illegal request and return nothing
     */
    T allowEmpty();

}