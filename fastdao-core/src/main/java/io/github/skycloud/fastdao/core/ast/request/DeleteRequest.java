/**
 * @(#)IDeleteRequest.java, 10月 20, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.request;


import io.github.skycloud.fastdao.core.ast.Condition;
import io.github.skycloud.fastdao.core.ast.ConditionalRequest;
import io.github.skycloud.fastdao.core.ast.Request;
import io.github.skycloud.fastdao.core.ast.Sortable;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.Visitor;
import io.github.skycloud.fastdao.core.ast.enums.OrderEnum;
import io.github.skycloud.fastdao.core.ast.model.SortLimitClause;
import io.github.skycloud.fastdao.core.exceptions.IllegalConditionException;
import io.github.skycloud.fastdao.core.table.Column;
import lombok.Getter;

import java.util.function.Function;

/**
 * @author yuntian
 */
public interface DeleteRequest extends Sortable<DeleteRequest>, ConditionalRequest<DeleteRequest> {

    /**
     * override from {@link Sortable}
     */
    @Override
    DeleteRequest limit(int limit);

    /**
     * override from {@link Sortable}
     */
    @Override
    DeleteRequest offset(int offset);

    /**
     * override from {@link Sortable}
     */
    @Override
    DeleteRequest addSort(Column column, OrderEnum order);

    /**
     * override from {@link Sortable}
     */
    @Override
    DeleteRequest addSort(String field, OrderEnum order);

    /**
     * @author yuntian
     */
    @Getter
    class DeleteRequestAst implements DeleteRequest, SqlAst {

        private Condition condition;

        private SortLimitClause sortLimitClause = new SortLimitClause();

        private Function<IllegalConditionException, ?> onSyntaxError;

        private boolean reuse = true;

        @Override
        public DeleteRequestAst limit(int limit) {
            sortLimitClause.setLimit(limit);
            return this;
        }

        @Override
        public DeleteRequestAst offset(int offset) {
            sortLimitClause.setOffset(offset);
            return this;
        }

        @Override
        public DeleteRequestAst addSort(Column column, OrderEnum order) {
            sortLimitClause.addSort(column.getName(), order);
            return this;
        }

        @Override
        public DeleteRequest addSort(String field, OrderEnum order) {
            sortLimitClause.addSort(field, order);
            return this;
        }


        @Override
        public DeleteRequestAst setCondition(Condition condition) {
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
            DeleteRequestAst request = new DeleteRequestAst();
            request.condition = (Condition) ((SqlAst) condition).copy();
            request.sortLimitClause = (SortLimitClause) sortLimitClause.copy();
            request.onSyntaxError = onSyntaxError;
            return request;
        }
    }
}