/**
 * @(#)IQueryRequest.java, 10月 20, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.request;

import com.google.common.collect.Lists;
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

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @author yuntian
 */
public interface QueryRequest extends Sortable<QueryRequest>, ConditionalRequest<QueryRequest> {

    QueryRequest distinct();

    QueryRequest addSelectFields(Column... fields);

    QueryRequest addSelectFields(Collection<Column> fields);

    @Override
    QueryRequest limit(int limit);

    @Override
    QueryRequest offset(int offset);

    @Override
    QueryRequest addSort(Column column, OrderEnum order);

    @Override
    QueryRequest addSort(String field, OrderEnum order);

    /**
     * @author yuntian
     */
    @Getter
    class QueryRequestAst implements QueryRequest, SqlAst, Sortable<QueryRequest> {

        private Condition condition;

        private boolean distinct;

        private List<String> selectFields = Lists.newArrayList();

        private SortLimitClause sortLimitClause = new SortLimitClause();

        private Function<IllegalConditionException,?> onSyntaxError;
        @Override
        public QueryRequest addSelectFields(Column... fields) {
            for (Column field : fields) {
                addSelectField(field.toString());
            }
            return this;
        }

        @Override
        public QueryRequest addSelectFields(Collection<Column> fields) {
            for (Column field : fields) {
                addSelectField(field.toString());
            }
            return this;
        }

        @Override
        public QueryRequest setCondition(Condition condition) {
            this.condition = condition;
            return this;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SqlAst copy() {
            QueryRequestAst request = new QueryRequestAst();
            request.selectFields = Lists.newArrayList(selectFields);
            if (condition != null) {
                request.condition = (Condition) ((SqlAst) condition).copy();
            }
            request.distinct = distinct;
            request.sortLimitClause = (SortLimitClause) sortLimitClause.copy();
            request.onSyntaxError=onSyntaxError;
            return request;
        }

        private void addSelectField(String field) {
            selectFields.add(field);
        }

        @Override
        public QueryRequestAst distinct() {
            distinct = true;
            return this;
        }

        @Override
        public QueryRequest limit(int limit) {
            sortLimitClause.setLimit(limit);
            return this;
        }

        @Override
        public QueryRequest offset(int offset) {
            sortLimitClause.setOffset(offset);
            return this;
        }

        @Override
        public QueryRequest addSort(Column column, OrderEnum order) {
            sortLimitClause.addSort(column.getName(), order);
            return this;
        }

        @Override
        public QueryRequest addSort(String field, OrderEnum order) {
            sortLimitClause.addSort(field, order);
            return this;
        }

        @Override
        public Condition getCondition() {
            return condition;
        }


        @Override
        public <T extends Request> T onSyntaxError(Function<IllegalConditionException, ?> action) {
            this.onSyntaxError=action;
            return (T)this;
        }

        @Override
        public Function<IllegalConditionException, ?> getOnSyntaxError() {
            return onSyntaxError;
        }
    }
}