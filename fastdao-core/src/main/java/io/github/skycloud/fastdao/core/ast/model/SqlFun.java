/**
 * @(#)SqlFunction.java, 10月 27, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.model;

import io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum;
import io.github.skycloud.fastdao.core.table.Column;
import lombok.Getter;

/**
 * @author yuntian
 */
@Getter
public class SqlFun {

    private SqlFunEnum type;

    private String field;

    public SqlFun(SqlFunEnum function, Column field) {
        this(function, field.getName());
    }

    public SqlFun(SqlFunEnum function, String field) {
        this.type = function;
        this.field = field;
    }

    public String genKey() {
        return type.name() + '|' + field;
    }
}