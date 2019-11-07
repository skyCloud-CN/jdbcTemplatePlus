/**
 * @(#)Column.java, 9月 30, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.models;

import io.github.skycloud.fastdao.core.ast.conditions.EqualCondition;
import io.github.skycloud.fastdao.core.ast.conditions.EqualConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.IsNullCondition;
import io.github.skycloud.fastdao.core.ast.conditions.IsNullConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.LikeCondition;
import io.github.skycloud.fastdao.core.ast.conditions.LikeConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.RangeCondition;
import io.github.skycloud.fastdao.core.ast.conditions.RangeConditionAst;

import java.util.Collection;

/**
 * @author yuntian
 */

public class Column {

    public Column(String name) {
        this.name = name;
        this.sqlFunctions = new SqlFunctions(this);
    }

    private final String name;

    private SqlFunctions sqlFunctions;

    public EqualCondition eq(Object... value) {
        return new EqualConditionAst(this.name, value);
    }

    public EqualCondition eq(Collection value) {
        return new EqualConditionAst(this.name, value);
    }

    public RangeCondition gt(Object value) {
        return new RangeConditionAst(name).gt(value);
    }

    public RangeCondition lt(Object value) {
        return new RangeConditionAst(name).lt(value);
    }

    public RangeCondition gte(Object value) {
        return new RangeConditionAst(name).gte(value);
    }

    public RangeCondition lte(Object value) {
        return new RangeConditionAst(name).lte(value);
    }

    public LikeCondition like(Object value) {
        return new LikeConditionAst(name, value);
    }

    public IsNullCondition isNull() {
        return new IsNullConditionAst(name);
    }

    public String getName() {
        return this.name;
    }

    public SqlFunctions fun() {
        return sqlFunctions;
    }

}