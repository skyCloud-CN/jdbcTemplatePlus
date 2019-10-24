/**
 * @(#)IInsertRequest.java, 10月 20, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.request;

import com.google.common.collect.Maps;
import io.github.skycloud.fastdao.core.ast.Request;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.Visitor;
import io.github.skycloud.fastdao.core.table.Column;

import java.util.Map;

/**
 * @author yuntian
 */
public interface InsertRequest extends Request {

    InsertRequest addInsertField(Column field, Object value);


    /**
     * @author yuntian
     */

    class DefaultInsertRequest implements InsertRequest, SqlAst {

        private Map<String, Object> insertFields = Maps.newLinkedHashMap();

        @Override
        public DefaultInsertRequest addInsertField(Column field, Object value) {
            insertFields.put(field.getName(), value);
            return this;
        }

        public DefaultInsertRequest addInsertField(String field, Object value) {
            insertFields.put(field, value);
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
            DefaultInsertRequest request = new DefaultInsertRequest();
            request.insertFields = Maps.newLinkedHashMap(insertFields);
            return request;
        }
    }
}