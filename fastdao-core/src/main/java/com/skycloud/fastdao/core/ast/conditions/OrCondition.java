/**
 * @(#)OrCondition.java, 9月 09, 2019.
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuntian
 */
@Getter
public class OrCondition extends Condition {

    private List<Condition> subConditions = Lists.newArrayList();

    public OrCondition or(Condition condition) {
        subConditions.add(condition);
        return this;
    }

    public OrCondition orIgnoreIllegal(Condition condition) {
        if (condition.isLegal()) {
            subConditions.add(condition);
        }
        return this;
    }

    @Override
    public boolean isLegal() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return subConditions.isEmpty() || subConditions.stream().anyMatch(Condition::isEmpty);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        List<Condition> copySubConditions = new ArrayList<>(subConditions.size());
        for (Condition condition : subConditions) {
            copySubConditions.add((Condition) condition.copy());
        }
        OrCondition orCondition = new OrCondition();
        orCondition.subConditions = copySubConditions;
        return orCondition;
    }

}