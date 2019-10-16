/**
 * @(#)QueryRequest.java, 9月 09, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.ast.request;

import com.google.common.collect.Lists;
import com.skycloud.fastdao.core.ast.Condition;
import com.skycloud.fastdao.core.ast.Request;
import com.skycloud.fastdao.core.ast.SqlAst;
import com.skycloud.fastdao.core.ast.Visitor;
import com.skycloud.fastdao.core.ast.model.SortLimitClause;
import com.skycloud.fastdao.core.table.Column;

import java.util.Collection;
import java.util.List;

/**
 * @author yuntian
 */

public class QueryRequest extends Request {

    private boolean distinct;

    private List<String> selectFields = Lists.newArrayList();

    private Condition condition;

    private SortLimitClause sortLimitClause = new SortLimitClause();

    public QueryRequest addSelectFields(String... fields) {
        for (String field : fields) {
            addSelectField(field);
        }
        return this;
    }

    public QueryRequest addSelectFields(Column... fields) {
        for (Column field : fields) {
            addSelectField(field.toString());
        }
        return this;
    }

    public QueryRequest addSelectFields(Collection<String> fields) {
        for (String field : fields) {
            addSelectField(field);
        }
        return this;
    }

    public QueryRequest setCondition(Condition condition) {
        this.condition = condition;
        return this;
    }

    public QueryRequest limit(int limit) {
        sortLimitClause.setLimit(limit);
        return this;
    }

    public QueryRequest offset(int offset) {
        sortLimitClause.setOffset(offset);
        return this;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        QueryRequest request = new QueryRequest();
        request.selectFields = Lists.newArrayList(selectFields);
        request.condition = (Condition) condition.copy();
        request.distinct = distinct;
        request.sortLimitClause = (SortLimitClause) sortLimitClause.copy();
        return request;
    }

    private void addSelectField(String field) {
        selectFields.add(field);
    }

    public QueryRequest distinct() {
        distinct = true;
        return this;
    }

    public Condition getCondition() {
        return condition;
    }

    public SortLimitClause getSortLimitClause() {
        return sortLimitClause;
    }

    public List<String> getSelectFields() {
        return selectFields;
    }

    public boolean isDistinct() {
        return distinct;
    }
}