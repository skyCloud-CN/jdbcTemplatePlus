/**
 * @(#)CountRequestAst.java, 11月 01, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.request;

import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.conditions.Condition;
import io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum;
import io.github.skycloud.fastdao.core.ast.model.SqlFunction;
import io.github.skycloud.fastdao.core.ast.visitor.Visitor;
import io.github.skycloud.fastdao.core.exceptions.IllegalConditionException;
import io.github.skycloud.fastdao.core.models.Column;
import lombok.Getter;

import java.util.function.Function;

/**
 * @author yuntian
 */
@Getter
public
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
        this.countField = field.getName();
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
        if (condition != null) {
            request.setCondition((Condition) ((SqlAst) condition).copy());
        }
        SqlFunction fun = new SqlFunction(SqlFunEnum.COUNT, countField);
        if (distinct) {
            fun.distinct();
        }
        request.notReuse();
        request.addSelectFields(fun);
        request.onSyntaxError(onSyntaxError);
        return request;
    }

}