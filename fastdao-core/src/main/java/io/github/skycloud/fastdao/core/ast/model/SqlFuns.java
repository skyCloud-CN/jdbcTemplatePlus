/**
 * @(#)SqlFuns.java, 10月 29, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.model;

import io.github.skycloud.fastdao.core.table.Column;

import static io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum.AVG;
import static io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum.COUNT;
import static io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum.MAX;
import static io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum.MIN;
import static io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum.SUM;

/**
 * @author yuntian
 */
public interface SqlFuns {

    static SqlFun max(Column column) {
        return new SqlFun(MAX, column);
    }

    static SqlFun min(Column column) {
        return new SqlFun(MIN, column);
    }

    static SqlFun avg(Column column) {
        return new SqlFun(AVG, column);
    }

    static SqlFun sum(Column column) {
        return new SqlFun(SUM, column);
    }

    static SqlFun count(Column column) {
        return new SqlFun(COUNT, column);
    }


}