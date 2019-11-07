/**
 * @(#)IsNullConditionAst.java, 11月 01, 2019.
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
class IsNullConditionAst implements IsNullCondition, SqlAst {

    private String field;

    public IsNullConditionAst(String field) {
        this.field = field;
    }

    @Override
    public boolean isLegal() {
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
        return new io.github.skycloud.fastdao.core.ast.conditions.IsNullConditionAst(field);
    }
}