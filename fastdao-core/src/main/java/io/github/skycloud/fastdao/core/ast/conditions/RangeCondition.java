/**
 * @(#)RangeCondition.java, 10月 20, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.conditions;


import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.visitor.Visitor;
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


}