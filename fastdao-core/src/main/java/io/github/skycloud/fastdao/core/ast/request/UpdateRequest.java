/**
 * @(#)IUpdateRequest.java, 10月 20, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.request;

import com.google.common.collect.Maps;
import io.github.skycloud.fastdao.core.ast.Condition;
import io.github.skycloud.fastdao.core.ast.ConditionalRequest;
import io.github.skycloud.fastdao.core.ast.Sortable;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.Visitor;
import io.github.skycloud.fastdao.core.ast.enums.OrderEnum;
import io.github.skycloud.fastdao.core.ast.model.SortLimitClause;
import io.github.skycloud.fastdao.core.table.Column;
import lombok.Getter;

import java.util.Map;

/**
 * @author yuntian
 */
public interface UpdateRequest extends Sortable<UpdateRequest>, ConditionalRequest<UpdateRequest> {

    UpdateRequest addUpdateField(String field, Object value);

    UpdateRequest addUpdateField(Column field, Object value);

    @Override
    UpdateRequest limit(int limit);

    @Override
    UpdateRequest offset(int offset);

    @Override
    UpdateRequest addSort(Column column, OrderEnum order);

    @Override
    UpdateRequest addSort(String field, OrderEnum order);

    /**
     * @author yuntian
     */
    @Getter
    class UpdateRequestAst implements UpdateRequest, SqlAst {

        private Map<String, Object> updateFields = Maps.newLinkedHashMap();

        private Condition condition;

        private SortLimitClause sortLimitClause = new SortLimitClause();

        @Override
        public UpdateRequest addUpdateField(String field, Object value) {
            updateFields.put(field, value);
            return this;
        }

        @Override
        public UpdateRequest addUpdateField(Column field, Object value) {
            updateFields.put(field.getName(), value);
            return this;
        }

        @Override
        public UpdateRequest setCondition(Condition condition) {
            this.condition = condition;
            return this;
        }

        @Override
        public UpdateRequest limit(int limit) {
            sortLimitClause.setLimit(limit);
            return this;
        }

        @Override
        public UpdateRequest offset(int offset) {
            sortLimitClause.setOffset(offset);
            return this;
        }

        @Override
        public UpdateRequest addSort(Column column, OrderEnum order) {
            sortLimitClause.addSort(column.getName(), order);
            return this;
        }

        @Override
        public UpdateRequest addSort(String field, OrderEnum order) {
            sortLimitClause.addSort(field, order);
            return this;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SqlAst copy() {
            UpdateRequestAst request = new UpdateRequestAst();
            request.updateFields = Maps.newLinkedHashMap(updateFields);
            request.condition = (Condition) ((SqlAst) condition).copy();
            request.sortLimitClause = (SortLimitClause) sortLimitClause.copy();
            return request;
        }
    }
}