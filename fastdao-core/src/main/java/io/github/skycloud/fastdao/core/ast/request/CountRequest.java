/**
 * @(#)ICountRequest.java, 10月 20, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.request;

import io.github.skycloud.fastdao.core.ast.Condition;
import io.github.skycloud.fastdao.core.ast.ConditionalRequest;
import io.github.skycloud.fastdao.core.ast.Request;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.Visitor;
import io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum;
import io.github.skycloud.fastdao.core.ast.model.SqlFun;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest.QueryRequestAst;
import io.github.skycloud.fastdao.core.exceptions.IllegalConditionException;
import io.github.skycloud.fastdao.core.table.Column;
import lombok.Getter;

import java.util.function.Function;

/**
 * @author yuntian
 */
public interface CountRequest extends ConditionalRequest<CountRequest> {

    /**
     * SELECT DISTINCT COUNT(*) ...
     */
    CountRequest distinct();

    /**
     * SELECT COUNT(field)
     */
    CountRequest setCountField(String field);

    /**
     * SELECT COUNT(field)
     */
    CountRequest setCountField(Column field);

    /**
     * @author yuntian
     */
    @Getter
    class CountRequestAst implements CountRequest, SqlAst {

        private boolean distinct;

        private String countField;

        private Condition condition;

        private Function<IllegalConditionException, ?> onSyntaxError;

        @Override
        public CountRequest setCountField(String field) {
            this.countField = field;
            return this;
        }

        @Override
        public CountRequest setCountField(Column field) {
            this.countField = field.toString();
            return this;
        }

        @Override
        public CountRequest distinct() {
            distinct = true;
            return this;
        }

        @Override
        public CountRequest setCondition(Condition condition) {
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
            return (T) this;
        }

        @Override
        public boolean isReuse() {
            return true;
        }

        /**
         * count request is kind of QueryRequest and no longer need visitor method due to SQL function support
         */
        @Override
        public void accept(Visitor visitor) {
            throw new UnsupportedOperationException();
        }

        /**
         * count request is translate to QueryRequest by copy
         */
        @Override
        public SqlAst copy() {
            QueryRequestAst request = new QueryRequestAst();
            if (distinct) {
                request.distinct();
            }
            if (condition != null) {
                request.setCondition((Condition) ((SqlAst) condition).copy());
            }
            SqlFun fun = new SqlFun(SqlFunEnum.COUNT, countField);
            if (distinct) {
                fun.distinct();
            }
            request.notReuse();
            request.addSelectFields(fun);
            request.onSyntaxError(onSyntaxError);
            return request;
        }

    }
}