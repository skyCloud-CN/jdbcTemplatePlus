/**
 * @(#)IInsertRequest.java, 10月 20, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.request;

import com.google.common.collect.Maps;
import io.github.skycloud.fastdao.core.ast.FieldUpdateRequest;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.Visitor;
import io.github.skycloud.fastdao.core.table.Column;

import java.util.Map;

/**
 * @author yuntian
 */
public interface InsertRequest extends FieldUpdateRequest<InsertRequest> {

    @Override
    InsertRequest addUpdateField(Column field, Object value);

    @Override
    InsertRequest addUpdateField(String field, Object value);

    /**
     * @author yuntian
     */

    class InsertRequestAst implements InsertRequest, SqlAst {

        private Map<String, Object> updateFields = Maps.newLinkedHashMap();

        @Override
        public InsertRequest addUpdateField(Column field, Object value) {
            updateFields.put(field.getName(), value);
            return this;
        }

        @Override
        public InsertRequest addUpdateField(String field, Object value) {
            updateFields.put(field, value);
            return this;
        }


        @Override
        public Map<String, Object> getUpdateFields() {
            return updateFields;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SqlAst copy() {
            InsertRequestAst request = new InsertRequestAst();
            request.updateFields = Maps.newLinkedHashMap(updateFields);
            return request;
        }
    }
}