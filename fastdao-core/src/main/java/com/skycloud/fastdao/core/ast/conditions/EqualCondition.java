/**
 * @(#)EqualCondition.java, 9月 09, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.ast.conditions;

import com.google.common.collect.Lists;
import com.skycloud.fastdao.core.ast.Condition;
import com.skycloud.fastdao.core.ast.SqlAst;
import com.skycloud.fastdao.core.ast.Visitor;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

/**
 * @author yuntian
 */
@Getter
public class EqualCondition extends Condition implements Cloneable {

    private String field;

    private Object value;

    public EqualCondition(String field, Object... value) {
        this.field = field;
        if (value.length == 1) {
            this.value = value[0];
        } else {
            this.value = Lists.newArrayList(value);
        }
    }

    public EqualCondition(String field, Collection value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public boolean isLegal() {
        if (value instanceof Collection) {
            if (CollectionUtils.isEmpty((Collection) value)) {
                return false;
            }
        } else {
            return value != null;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        return new EqualCondition(field, value);
    }


}