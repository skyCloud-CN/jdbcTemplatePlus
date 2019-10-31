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
import io.github.skycloud.fastdao.core.ast.model.SqlFun;
import lombok.Getter;

import java.util.Collection;

import static io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum.AVG;
import static io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum.COUNT;
import static io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum.MAX;
import static io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum.MIN;
import static io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum.SUM;

/**
 * @author yuntian
 */
@Getter
public class Column {

    public Column(String name) {
        this.name = name;
    }

    private final String name;

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


    public SqlFun MAX() {
        return new SqlFun(MAX, this);
    }

    public SqlFun MIN() {
        return new SqlFun(MIN, this);
    }

    public SqlFun AVG() {
        return new SqlFun(AVG, this);
    }

    public SqlFun SUM() {
        return new SqlFun(SUM, this);
    }

    public SqlFun COUNT() {
        return new SqlFun(COUNT, this);
    }

    @Override
    public String toString() {
        return this.name;
    }
}