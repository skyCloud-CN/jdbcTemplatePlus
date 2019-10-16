/**
 * @(#)DeleteRequest.java, 9月 09, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.ast.request;

import com.skycloud.fastdao.core.ast.Condition;
import com.skycloud.fastdao.core.ast.Request;
import com.skycloud.fastdao.core.ast.SqlAst;
import com.skycloud.fastdao.core.ast.Visitor;
import com.skycloud.fastdao.core.ast.enums.OrderEnum;
import com.skycloud.fastdao.core.ast.model.Sort;
import com.skycloud.fastdao.core.ast.model.SortLimitClause;
import com.skycloud.fastdao.core.table.Column;

/**
 * @author yuntian
 */

public class DeleteRequest extends Request {

    private Condition condition;

    private SortLimitClause sortLimitClause = new SortLimitClause();

    public DeleteRequest limit(int limit) {
        sortLimitClause.setLimit(limit);
        return this;
    }

    public DeleteRequest offset(int offset) {
        sortLimitClause.setOffset(offset);
        return this;
    }

    public DeleteRequest addSort(String field, OrderEnum order) {
        sortLimitClause.getSorts().add(new Sort(field, order));
        return this;
    }

    public DeleteRequest addSort(Column column, OrderEnum order) {
        return addSort(column.toString(), order);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        DeleteRequest request = new DeleteRequest();
        request.condition = (Condition) condition.copy();
        request.sortLimitClause = (SortLimitClause) sortLimitClause.copy();
        return request;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public SortLimitClause getSortLimitClause() {
        return sortLimitClause;
    }

    public void setSortLimitClause(SortLimitClause sortLimitClause) {
        this.sortLimitClause = sortLimitClause;
    }
}