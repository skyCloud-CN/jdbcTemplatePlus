/**
 * @(#)LikeConditionAst.java, 11月 01, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.conditions;

import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.visitor.Visitor;
import lombok.Getter;

/**
 * @author yuntian
 */
@Getter
public
class LikeConditionAst implements LikeCondition, SqlAst {

    private String field;

    private Object value;

    private boolean matchLeft = false;

    private boolean matchRight = false;

    public LikeConditionAst(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public boolean isLegal() {
        return value != null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public LikeConditionAst matchLeft() {
        matchLeft = true;
        return this;
    }

    @Override
    public LikeConditionAst matchRight() {
        matchRight = true;
        return this;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        LikeConditionAst condition = new io.github.skycloud.fastdao.core.ast.conditions.LikeConditionAst(field, value);
        condition.matchLeft = this.matchLeft;
        condition.matchRight = this.matchRight;
        return condition;
    }


}