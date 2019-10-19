/**
 * @(#)IsNullCondition.java, 10月 21, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.conditions;


import io.github.skycloud.fastdao.core.ast.Condition;
import io.github.skycloud.fastdao.core.ast.SqlAst;

import io.github.skycloud.fastdao.core.ast.Visitor;
import lombok.Getter;

/**
 * @author yuntian
 */
public interface IsNullCondition extends Condition {

    @Getter
    class DefaultIsNullCondition implements IsNullCondition, SqlAst {

        private String field;

        public DefaultIsNullCondition(String field) {
            this.field = field;
        }

        @Override
        public boolean isLegal() {
            return true;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        @Override
        public SqlAst copy() {
            return new DefaultIsNullCondition(field);
        }
    }
}