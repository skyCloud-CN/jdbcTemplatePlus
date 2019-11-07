/**
 * @(#)UpdateRequestAst.java, 11月 01, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.request;

import com.google.common.collect.Maps;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.conditions.Condition;
import io.github.skycloud.fastdao.core.ast.enums.OrderEnum;
import io.github.skycloud.fastdao.core.ast.model.SortLimitClause;
import io.github.skycloud.fastdao.core.ast.visitor.Visitor;
import io.github.skycloud.fastdao.core.exceptions.IllegalConditionException;
import io.github.skycloud.fastdao.core.models.Column;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;

/**
 * @author yuntian
 */
@Getter
public
class UpdateRequestAst implements UpdateRequest, SqlAst {

    private Map<String, Object> updateFields = Maps.newLinkedHashMap();

    private Condition condition;

    private SortLimitClause sortLimitClause = new SortLimitClause();

    private Function<IllegalConditionException, ?> onSyntaxError;

    private boolean reuse = true;

    @Override
    public UpdateRequest addUpdateField(String field, Object value) {
        updateFields.put(field, value);
        return this;
    }

    @Override
    public UpdateRequest addUpdateField(Column field, Object value) {
        updateFields.put(field.getName(), value);
        return this;
    }

    @Override
    public UpdateRequest setCondition(Condition condition) {
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
        reuse = false;
        return (T) this;
    }

    @Override
    public boolean isReuse() {
        return reuse;
    }

    @Override
    public UpdateRequest limit(int limit) {
        sortLimitClause.setLimit(limit);
        return this;
    }

    @Override
    public UpdateRequest offset(int offset) {
        sortLimitClause.setOffset(offset);
        return this;
    }

    @Override
    public UpdateRequest addSort(Column column, OrderEnum order) {
        sortLimitClause.addSort(column.getName(), order);
        return this;
    }

    @Override
    public UpdateRequest addSort(String field, OrderEnum order) {
        sortLimitClause.addSort(field, order);
        return this;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        io.github.skycloud.fastdao.core.ast.request.UpdateRequestAst request = new io.github.skycloud.fastdao.core.ast.request.UpdateRequestAst();
        request.updateFields = Maps.newLinkedHashMap(updateFields);
        if(condition!=null) {
            request.condition = (Condition) ((SqlAst) condition).copy();
        }
        request.sortLimitClause = (SortLimitClause) sortLimitClause.copy();
        request.onSyntaxError = onSyntaxError;
        return request;
    }

}