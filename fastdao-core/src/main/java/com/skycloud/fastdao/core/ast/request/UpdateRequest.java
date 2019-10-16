/**
 * @(#)UpdateRequest.java, 9月 09, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.ast.request;

import com.google.common.collect.Maps;
import com.skycloud.fastdao.core.ast.Condition;
import com.skycloud.fastdao.core.ast.Request;
import com.skycloud.fastdao.core.ast.SqlAst;
import com.skycloud.fastdao.core.ast.Visitor;
import com.skycloud.fastdao.core.ast.model.SortLimitClause;
import com.skycloud.fastdao.core.table.Column;

import java.util.Map;

/**
 * @author yuntian
 */

public class UpdateRequest extends Request {

    private Map<String, Object> updateFields = Maps.newLinkedHashMap();

    private Condition condition;

    private SortLimitClause sortLimitClause = new SortLimitClause();

    public UpdateRequest addUpdateField(String field, Object value) {
        updateFields.put(field, value);
        return this;
    }

    public UpdateRequest addUpdateField(Column field, Object value) {
        updateFields.put(field.toString(), value);
        return this;
    }

    public UpdateRequest setCondition(Condition condition) {
        this.condition = condition;
        return this;
    }

    public UpdateRequest limit(int limit) {
        sortLimitClause.setLimit(limit);
        return this;
    }

    public UpdateRequest offset(int offset) {
        sortLimitClause.setOffset(offset);
        return this;
    }

    public Map<String, Object> getUpdateFields() {
        return updateFields;
    }

    public Condition getCondition() {
        return condition;
    }


    public SortLimitClause getSortLimitClause() {
        return sortLimitClause;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        UpdateRequest request = new UpdateRequest();
        request.updateFields = Maps.newLinkedHashMap(updateFields);
        request.condition = (Condition) condition.copy();
        request.sortLimitClause = (SortLimitClause) sortLimitClause.copy();
        return request;
    }
}