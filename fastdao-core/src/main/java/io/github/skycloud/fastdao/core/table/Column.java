/**
 * @(#)Column.java, 9月 30, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.table;

import io.github.skycloud.fastdao.core.ast.conditions.EqualCondition;
import io.github.skycloud.fastdao.core.ast.conditions.EqualCondition.EqualConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.IsNullCondition;
import io.github.skycloud.fastdao.core.ast.conditions.IsNullCondition.IsNullConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.LikeCondition;
import io.github.skycloud.fastdao.core.ast.conditions.LikeCondition.LikeConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.RangeCondition;
import io.github.skycloud.fastdao.core.ast.conditions.RangeCondition.RangeConditionAst;
import lombok.Getter;

import java.util.Collection;

/**
 * @author yuntian
 */
@Getter
public class Column {

    public Column(String name) {
        this.name = name;
    }

    private final String name;

    public EqualCondition equal(Object... value) {
        return new EqualConditionAst(this.name, value);
    }

    public EqualCondition equal(Collection value) {
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

    @Override
    public String toString() {
        return this.name;
    }
}