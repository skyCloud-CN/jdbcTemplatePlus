/**
 * @(#)DeleteRequestAst.java, 11月 01, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.request;

import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.conditions.Condition;
import io.github.skycloud.fastdao.core.ast.enums.OrderEnum;
import io.github.skycloud.fastdao.core.ast.model.SortLimitClause;
import io.github.skycloud.fastdao.core.ast.visitor.Visitor;
import io.github.skycloud.fastdao.core.exceptions.IllegalConditionException;
import io.github.skycloud.fastdao.core.models.Column;
import lombok.Getter;

import java.util.function.Function;

/**
 * @author yuntian
 */
@Getter
public
class DeleteRequestAst implements DeleteRequest, SqlAst {

    private Condition condition;

    private SortLimitClause sortLimitClause = new SortLimitClause();

    private Function<IllegalConditionException, ?> onSyntaxError;

    private boolean reuse = true;

    @Override
    public io.github.skycloud.fastdao.core.ast.request.DeleteRequestAst limit(int limit) {
        sortLimitClause.setLimit(limit);
        return this;
    }

    @Override
    public io.github.skycloud.fastdao.core.ast.request.DeleteRequestAst offset(int offset) {
        sortLimitClause.setOffset(offset);
        return this;
    }

    @Override
    public io.github.skycloud.fastdao.core.ast.request.DeleteRequestAst addSort(Column column, OrderEnum order) {
        sortLimitClause.addSort(column.getName(), order);
        return this;
    }

    @Override
    public DeleteRequest addSort(String field, OrderEnum order) {
        sortLimitClause.addSort(field, order);
        return this;
    }


    @Override
    public io.github.skycloud.fastdao.core.ast.request.DeleteRequestAst setCondition(Condition condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public <T extends Request> T onSyntaxError(Function<IllegalConditionException, ?> action) {
        this.onSyntaxError = action;
        return (T) this;
    }

    @Override
    public Function<IllegalConditionException, ?> getOnSyntaxError() {
        return onSyntaxError;
    }

    @Override
    public <T extends Request> T notReuse() {
        reuse = false;
        return (T) this;
    }

    @Override
    public boolean isReuse() {
        return reuse;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        io.github.skycloud.fastdao.core.ast.request.DeleteRequestAst request = new io.github.skycloud.fastdao.core.ast.request.DeleteRequestAst();
        if(condition!=null) {
            request.condition = (Condition) ((SqlAst) condition).copy();
        }
        request.sortLimitClause = (SortLimitClause) sortLimitClause.copy();
        request.onSyntaxError = onSyntaxError;
        return request;
    }
}