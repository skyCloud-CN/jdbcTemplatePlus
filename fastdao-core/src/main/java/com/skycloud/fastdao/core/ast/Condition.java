/**
 * @(#)Condition.java, 9月 10, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.ast;

import com.skycloud.fastdao.core.ast.conditions.AndCondition;
import com.skycloud.fastdao.core.ast.conditions.OrCondition;

/**
 * @author yuntian
 */
public abstract class Condition implements SqlAst {

    public abstract boolean isLegal();

    public abstract boolean isEmpty();

    public static AndCondition andCondition() {
        return new AndCondition();
    }

    public static OrCondition orCondition() {
        return new OrCondition();
    }

}