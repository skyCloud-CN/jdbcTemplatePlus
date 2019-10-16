/**
 * @(#)InsertRequest.java, 10月 01, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.ast.request;

import com.google.common.collect.Maps;
import com.skycloud.fastdao.core.ast.Request;
import com.skycloud.fastdao.core.ast.SqlAst;
import com.skycloud.fastdao.core.ast.Visitor;
import com.skycloud.fastdao.core.table.Column;

import java.util.Map;

/**
 * @author yuntian
 */

public class InsertRequest extends Request {

    private Map<String, Object> insertFields = Maps.newLinkedHashMap();

    public InsertRequest addInsertField(String field, Object value) {
        insertFields.put(field, value);
        return this;
    }

    public InsertRequest addInsertField(Column field, Object value) {
        insertFields.put(field.toString(), value);
        return this;
    }

    public Map<String, Object> getInsertFields() {
        return insertFields;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        InsertRequest request = new InsertRequest();
        request.insertFields = Maps.newLinkedHashMap(insertFields);
        return request;
    }
}