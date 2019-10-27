/**
 * @(#)SqlFunction.java, 10月 27, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.model;

import io.github.skycloud.fastdao.core.ast.enums.SqlFunctionEnum;
import io.github.skycloud.fastdao.core.table.Column;
import lombok.Getter;

/**
 * @author yuntian
 */
@Getter
public class SqlFunction {

    private SqlFunctionEnum type;

    private String field;

    public SqlFunction(SqlFunctionEnum function, Column field) {
        this(function, field.getName());
    }

    public SqlFunction(SqlFunctionEnum function, String field) {
        this.type = function;
        this.field = field;
    }

}