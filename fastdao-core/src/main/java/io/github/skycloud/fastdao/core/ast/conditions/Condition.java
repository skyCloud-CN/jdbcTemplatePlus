/**
 * @(#)Condition.java, 9月 10, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.conditions;

import java.util.Collection;

/**
 * @author yuntian
 * all Condition can be get from this class, but only or/and method is recommaned
 * other condition is recommend to create by define a static {@link io.github.skycloud.fastdao.core.models.Column}
 * <p>
 * all Condition class is provided to user by an interface, this is just to hide some unsafe method
 */
public interface Condition {

    boolean isLegal();

    boolean isEmpty();

    static AndCondition and() {
        return new AndConditionAst();
    }

    static AndCondition and(Condition condition) {
        return new AndConditionAst().and(condition);
    }

    static OrCondition or() {
        return new OrConditionAst();
    }

    static OrCondition or(Condition condition) {
        return new OrConditionAst().or(condition);
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