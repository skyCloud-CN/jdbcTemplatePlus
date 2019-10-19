/**
 * @(#)Column.java, 9月 30, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.table;

import io.github.skycloud.fastdao.core.ast.conditions.EqualCondition;
import io.github.skycloud.fastdao.core.ast.conditions.EqualCondition.DefaultEqualCondition;
import io.github.skycloud.fastdao.core.ast.conditions.IsNullCondition;
import io.github.skycloud.fastdao.core.ast.conditions.IsNullCondition.DefaultIsNullCondition;
import io.github.skycloud.fastdao.core.ast.conditions.LikeCondition;
import io.github.skycloud.fastdao.core.ast.conditions.LikeCondition.DefaultLikeCondition;
import io.github.skycloud.fastdao.core.ast.conditions.RangeCondition;
import io.github.skycloud.fastdao.core.ast.conditions.RangeCondition.DefaultRangeCondition;
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

    private String name;

    public EqualCondition equal(Object... value) {
        return new DefaultEqualCondition(this.name, value);
    }

    public EqualCondition equal(Collection value) {
        return new DefaultEqualCondition(this.name, value);
    }

    public RangeCondition gt(Object value) {
        return new DefaultRangeCondition(name).gt(value);
    }

    public RangeCondition lt(Object value) {
        return new DefaultRangeCondition(name).lt(value);
    }

    public RangeCondition gte(Object value) {
        return new DefaultRangeCondition(name).gte(value);
    }

    public RangeCondition lte(Object value) {
        return new DefaultRangeCondition(name).lte(value);
    }

    public LikeCondition like(Object value) {
        return new DefaultLikeCondition(name, value);
    }

    public IsNullCondition isNull() {
        return new DefaultIsNullCondition(name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}