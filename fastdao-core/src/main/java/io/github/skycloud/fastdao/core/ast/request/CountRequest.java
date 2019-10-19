/**
 * @(#)ICountRequest.java, 10月 20, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.request;

import io.github.skycloud.fastdao.core.ast.Condition;
import io.github.skycloud.fastdao.core.ast.ConditionalRequest;

import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.Visitor;
import io.github.skycloud.fastdao.core.table.Column;
import lombok.Getter;

/**
 * @author yuntian
 */
public interface CountRequest extends ConditionalRequest<CountRequest> {

    DefaultCountRequest setCountField(String countField);

    DefaultCountRequest setCountField(Column column);

    /**
     * @author yuntian
     */
    @Getter
    class DefaultCountRequest implements CountRequest, SqlAst {

        private boolean distinct;

        private String countField;

        private Condition condition;

        @Override
        public DefaultCountRequest setCondition(Condition condition) {
            this.condition = condition;
            return this;
        }

        @Override
        public DefaultCountRequest setCountField(String countField) {
            this.countField = countField;
            return this;
        }

        @Override
        public DefaultCountRequest setCountField(Column column) {
            this.countField = column.toString();
            return this;
        }

        public DefaultCountRequest distinct() {
            distinct = true;
            return this;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SqlAst copy() {
            DefaultCountRequest request = new DefaultCountRequest();
            request.distinct = distinct;
            request.countField = countField;
            if(condition!=null) {
                request.condition = (Condition) ((SqlAst) condition).copy();
            }
            return request;
        }

    }
}