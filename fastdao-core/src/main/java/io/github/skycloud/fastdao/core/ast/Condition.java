/**
 * @(#)Condition.java, 9月 10, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast;

import io.github.skycloud.fastdao.core.ast.conditions.AndCondition;
import io.github.skycloud.fastdao.core.ast.conditions.AndCondition.DefaultAndCondition;
import io.github.skycloud.fastdao.core.ast.conditions.OrCondition.DefaultOrCondition;
import io.github.skycloud.fastdao.core.ast.conditions.OrCondition;

/**
 * @author yuntian
 */
public interface Condition{

    boolean isLegal();

    boolean isEmpty();

    static AndCondition and() {
        return new DefaultAndCondition();
    }

    static OrCondition or() {
        return new DefaultOrCondition();
    }

}