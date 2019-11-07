/**
 * @(#)QueryRequestAst.java, 11月 01, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.request;

import com.google.common.collect.Lists;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.conditions.Condition;
import io.github.skycloud.fastdao.core.ast.enums.OrderEnum;
import io.github.skycloud.fastdao.core.ast.model.SortLimitClause;
import io.github.skycloud.fastdao.core.ast.model.SqlFunction;
import io.github.skycloud.fastdao.core.ast.visitor.Visitor;
import io.github.skycloud.fastdao.core.exceptions.IllegalConditionException;
import io.github.skycloud.fastdao.core.exceptions.IllegalRequestException;
import io.github.skycloud.fastdao.core.models.Column;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @author yuntian
 */
@Getter
public
class QueryRequestAst implements QueryRequest, SqlAst, Sortable<QueryRequest> {

    private Condition condition;

    private boolean distinct;

    // this field is String or SqlFun.class
    private List<Object> selectFields = Lists.newArrayList();

    private List<Object> groupByFields = Lists.newArrayList();

    private Condition havingCondition;

    private SortLimitClause sortLimitClause = new SortLimitClause();

    private Function<IllegalConditionException, ?> onSyntaxError;

    private boolean reuse = true;

    @Override
    public QueryRequest addSelectFields(Column... fields) {
        for (Column field : fields) {
            addSelectField(field.getName());
        }
        return this;
    }

    @Override
    public QueryRequest addSelectFields(SqlFunction... functions) {
        for (SqlFunction fun : functions) {
            addSelectField(fun);
        }
        return this;
    }

    @Override
    public QueryRequest addSelectFields(String... fields) {
        for (String field : fields) {
            addSelectField(field);
        }
        return this;
    }

    @Override
    public QueryRequest addSelectFields(Collection fields) {
        for (Object field : fields) {
            if (field instanceof Column) {
                addSelectField(((Column) field).getName());
            } else if (field instanceof String) {
                addSelectField(field);
            } else if (field instanceof SqlFunction) {
                addSelectField(field);
            } else {
                throw new IllegalRequestException("only Column ,SqlFun ,String is allowed as fields type");
            }
        }
        return this;
    }

    private void addSelectField(Object field) {
        selectFields.add(field);
    }

    @Override
    public QueryRequest setCondition(Condition condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public io.github.skycloud.fastdao.core.ast.request.QueryRequestAst distinct() {
        distinct = true;
        return this;
    }

    @Override
    public QueryRequest groupBy(Column... columns) {
        for(Column column:columns){
            groupByFields.add(column.getName());
        }
        return this;
    }

    @Override
    public QueryRequest having(Condition condition) {
        havingCondition = condition;
        return this;
    }

    @Override
    public QueryRequest limit(int limit) {
        sortLimitClause.setLimit(limit);
        return this;
    }

    @Override
    public QueryRequest offset(int offset) {
        sortLimitClause.setOffset(offset);
        return this;
    }

    @Override
    public QueryRequest addSort(Column column, OrderEnum order) {
        sortLimitClause.addSort(column.getName(), order);
        return this;
    }

    @Override
    public QueryRequest addSort(String field, OrderEnum order) {
        sortLimitClause.addSort(field, order);
        return this;
    }

    @Override
    public Condition getCondition() {
        return condition;
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

    /**
     * override from {@link SqlAst}
     */
    @Override
    public SqlAst copy() {
        io.github.skycloud.fastdao.core.ast.request.QueryRequestAst request = new io.github.skycloud.fastdao.core.ast.request.QueryRequestAst();
        request.selectFields = Lists.newArrayList(selectFields);
        if (condition != null) {
            request.condition = (Condition) ((SqlAst) condition).copy();
        }
        request.distinct = distinct;
        request.sortLimitClause = (SortLimitClause) sortLimitClause.copy();
        request.onSyntaxError = onSyntaxError;
        request.havingCondition = havingCondition;
        request.groupByFields = groupByFields;
        return request;
    }

    /**
     * override from {@link SqlAst}
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}