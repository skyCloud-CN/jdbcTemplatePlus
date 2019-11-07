/**
 * @(#)Fun.java, 11月 01, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.models;

import io.github.skycloud.fastdao.core.ast.model.SqlFunction;

import static io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum.AVG;
import static io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum.COUNT;
import static io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum.MAX;
import static io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum.MIN;
import static io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum.SUM;

/**
 * @author yuntian
 */
public class SqlFunctions {

    private Column column;

    public SqlFunctions(Column column) {
        this.column = column;
    }

    public SqlFunction MAX() {
        return new SqlFunction(MAX, column);
    }

    public SqlFunction MIN() {
        return new SqlFunction(MIN, column);
    }

    public SqlFunction AVG() {
        return new SqlFunction(AVG, column);
    }

    public SqlFunction SUM() {
        return new SqlFunction(SUM, column);
    }

    public SqlFunction COUNT() {
        return new SqlFunction(COUNT, column);
    }
}