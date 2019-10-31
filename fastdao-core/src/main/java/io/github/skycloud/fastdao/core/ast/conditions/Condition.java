/**
 * @(#)Condition.java, 9月 10, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast;

import io.github.skycloud.fastdao.core.ast.conditions.AndCondition;
import io.github.skycloud.fastdao.core.ast.conditions.AndCondition.AndConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.EqualCondition;
import io.github.skycloud.fastdao.core.ast.conditions.EqualCondition.EqualConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.IsNullCondition;
import io.github.skycloud.fastdao.core.ast.conditions.IsNullCondition.IsNullConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.LikeCondition;
import io.github.skycloud.fastdao.core.ast.conditions.LikeCondition.LikeConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.OrCondition;
import io.github.skycloud.fastdao.core.ast.conditions.OrCondition.OrConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.RangeCondition;
import io.github.skycloud.fastdao.core.ast.conditions.RangeCondition.RangeConditionAst;

import java.util.Collection;

/**
 * @author yuntian
 * all Condition can be get from this class, but only or/and method is recommaned
 * other condition is recommend to create by define a static {@link io.github.skycloud.fastdao.core.table.Column}
 *
 * all Condition class is provided to user by an interface, this is just to hide some unsafe method
 */
public interface Condition {

    boolean isLegal();

    boolean isEmpty();

    static AndCondition and() {
        return new AndConditionAst();
    }

    static OrCondition or() {
        return new OrConditionAst();
    }

    static EqualCondition equal(String column, Object... value) {
        return new EqualConditionAst(column, value);
    }

    static EqualCondition equal(String column, Collection value) {
        return new EqualConditionAst(column, value);
    }

    static RangeCondition range(String column) {
        return new RangeConditionAst(column);
    }

    static LikeCondition like(String column, Object value) {
        return new LikeConditionAst(column, value);
    }

    static IsNullCondition isNull(String column) {
        return new IsNullConditionAst(column);
    }

}