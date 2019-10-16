/**
 * @(#)Column.java, 9月 30, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.table;

import com.skycloud.fastdao.core.ast.conditions.EqualCondition;
import com.skycloud.fastdao.core.ast.conditions.LikeCondition;
import com.skycloud.fastdao.core.ast.conditions.RangeCondition;
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
        return new EqualCondition(this.name, value);
    }

    public EqualCondition equal(Collection value) {
        return new EqualCondition(this.name, value);
    }

    public RangeCondition gt(Object value) {
        return new RangeCondition(name).gt(value);
    }

    public RangeCondition lt(Object value) {
        return new RangeCondition(name).lt(value);
    }

    public RangeCondition gte(Object value) {
        return new RangeCondition(name).gte(value);
    }

    public RangeCondition lte(Object value) {
        return new RangeCondition(name).lte(value);
    }

    public LikeCondition like(Object value) {
        return new LikeCondition(name, value);
    }

    @Override
    public String toString() {
        return this.name;
    }
}