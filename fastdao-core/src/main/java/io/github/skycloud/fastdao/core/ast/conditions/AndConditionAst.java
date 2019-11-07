/**
 * @(#)AndConditionAst.java, 11月 01, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.conditions;

import com.google.common.collect.Lists;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.visitor.Visitor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuntian
 */
@Getter
public class AndConditionAst<T extends AndCondition> implements AndCondition<T>, SqlAst {

    private List<Condition> subConditions = Lists.newArrayList();

    private boolean allowEmpty = false;

    @Override
    public T and(Condition condition) {
        subConditions.add(condition);
        return (T) this;
    }

    @Override
    public T andOptional(Condition condition) {
        if (condition.isLegal()) {
            subConditions.add(condition);
        }
        return (T) this;
    }

    @Override
    public T andIf(Condition condition, boolean preCondition) {
        if (preCondition) {
            subConditions.add(condition);
        }
        return (T) this;
    }


    @Override
    public T allowEmpty() {
        allowEmpty = true;
        return (T) this;
    }

    @Override
    public boolean isLegal() {
        // all subCondition must be legal
        for (Condition subCondition : subConditions) {
            if (!subCondition.isLegal()) {
                return false;
            }
        }
        return allowEmpty || !isEmpty();
    }

    @Override
    public boolean isEmpty() {
        return subConditions.isEmpty() || subConditions.stream().allMatch(Condition::isEmpty);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        io.github.skycloud.fastdao.core.ast.conditions.AndConditionAst andCondition = new io.github.skycloud.fastdao.core.ast.conditions.AndConditionAst();
        ArrayList<Condition> arrayList = new ArrayList<>(subConditions.size());
        for (Condition condition : subConditions) {
            arrayList.add((Condition) ((SqlAst) condition).copy());
        }
        andCondition.subConditions = arrayList;
        andCondition.allowEmpty = allowEmpty;
        return andCondition;
    }


}