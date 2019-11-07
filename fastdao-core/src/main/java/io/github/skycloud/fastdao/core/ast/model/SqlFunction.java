/**
 * @(#)SqlFunction.java, 10月 27, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.model;

import io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum;
import io.github.skycloud.fastdao.core.models.Column;
import lombok.Getter;

/**
 * @author yuntian
 */
@Getter
public class SqlFunction {

    private SqlFunEnum type;

    private String field;

    private boolean distinct = false;

    public SqlFunction(SqlFunEnum function, Column field) {
        this(function, field.getName());
    }

    public SqlFunction(SqlFunEnum function, String field) {
        this.type = function;
        this.field = field;
    }

    public SqlFunction distinct() {
        this.distinct = true;
        return this;
    }

    public String genKey() {
        return type.genKey(field);
    }

}