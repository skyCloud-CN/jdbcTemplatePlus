/**
 * @(#)SqlVisitor.java, 10月 01, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.visitor;

import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.ValueParser;
import io.github.skycloud.fastdao.core.ast.conditions.AndConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.Condition;
import io.github.skycloud.fastdao.core.ast.conditions.EqualConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.IsNullConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.LikeConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.OrConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.RangeConditionAst;
import io.github.skycloud.fastdao.core.ast.constants.SQLConstant;
import io.github.skycloud.fastdao.core.ast.model.Sort;
import io.github.skycloud.fastdao.core.ast.model.SortLimitClause;
import io.github.skycloud.fastdao.core.ast.model.SqlFunction;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequestAst;
import io.github.skycloud.fastdao.core.ast.request.InsertRequestAst;
import io.github.skycloud.fastdao.core.ast.request.QueryRequestAst;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequestAst;
import io.github.skycloud.fastdao.core.exceptions.IllegalConditionException;
import io.github.skycloud.fastdao.core.exceptions.IllegalRequestException;
import io.github.skycloud.fastdao.core.models.Column;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import static io.github.skycloud.fastdao.core.ast.constants.SQLConstant.SPACE;
import static io.github.skycloud.fastdao.core.ast.constants.SQLConstant.UNQUOTE;

/**
 * @author yuntian
 */
public class MysqlVisitor extends SqlVisitor {


    public MysqlVisitor(String tableName, ValueParser valueParser) {
        super(tableName, valueParser);
    }

    @Override
    public void visit(QueryRequestAst request) {
        sb.append(SQLConstant.SELECT);
        if (request.isDistinct()) {
            sb.append(SQLConstant.DISTINCT);
        }
        if (CollectionUtils.isEmpty(request.getSelectFields())) {
            sb.append(SQLConstant.ALL_FIELD);
        } else {
            boolean single = request.getSelectFields().size() == 1;
            visitCollection(request.getSelectFields(), field -> {
                if (field instanceof String) {
                    visitField((String) field);
                } else if (field instanceof SqlFunction) {
                    visitFunction((SqlFunction) field, single);
                }
            }, SQLConstant.COMMA);

        }
        sb.append(SQLConstant.FROM);
        visitField(tableName);
        visitWhereClause(request.getCondition());
        visitGroupBy(request.getGroupByFields());
        visitHaving(request.getHavingCondition());
        visit(request.getSortLimitClause());
    }

    private void visitHaving(Condition condition) {

        // if no condition exist then return all data
        if (condition == null) {
            return;
        }
        if (!condition.isLegal()) {
            throw new IllegalConditionException("condition has null value or is empty");
        }
        if (condition.isEmpty()) {
            return;
        }
        sb.append(SQLConstant.HAVING);
        ((SqlAst) condition).accept(this);
    }

    private void visitGroupBy(List<Object> groupByField) {
        if (CollectionUtils.isEmpty(groupByField)) {
            return;
        }
        sb.append(SQLConstant.GROUP_BY);
        visitCollection(groupByField, field -> {
            if (field instanceof String) {
                visitField((String) field);
            } else if (field instanceof SqlFunction) {
                visitFunction((SqlFunction) field, true);
            }
        }, SQLConstant.COMMA);
    }

    private void visitWhereClause(Condition condition) {
        // if no condition exist then return all data
        if (condition == null) {
            return;
        }
        if (!condition.isLegal()) {
            throw new IllegalConditionException("condition has null value or is empty");
        }
        if (condition.isEmpty()) {
            return;
        }
        sb.append(SQLConstant.WHERE);
        ((SqlAst) condition).accept(this);
    }

    @Override
    public void visit(SortLimitClause sortLimitClause) {
        if (sortLimitClause == null) {
            return;
        }
        if (CollectionUtils.isNotEmpty(sortLimitClause.getSorts())) {
            sb.append(SQLConstant.ORDER_BY);
            List<Sort> sorts = sortLimitClause.getSorts();
            visitCollection(sorts, this::visit, SQLConstant.COMMA);
        }

        // visit limit part
        Integer limit = sortLimitClause.getLimit();
        Integer offset = sortLimitClause.getOffset();
        if (limit != null) {
            sb.append(SQLConstant.LIMIT);
            if (offset != null) {
                sb.append(offset);
                sb.append(SQLConstant.COMMA);
            }
            sb.append(limit);
        } else {
            if (offset != null) {
                throw new IllegalRequestException("offset is defined but limit is not defined");
            }
        }
    }

    private void visitFieldSetClause(Map<String, Object> setFields) {
        visitCollection(setFields.entrySet(), (entry) -> {
            visitField(entry.getKey());
            sb.append(SQLConstant.EQUAL);
            visitValue(entry.getKey(), entry.getValue());
        }, SQLConstant.COMMA);
    }

    private void visitValueInsertClause(Map<String, Object> setFields) {
        sb.append(SQLConstant.LB);
        visitCollection(setFields.entrySet(), entry -> visitField(entry.getKey()), SQLConstant.COMMA);
        sb.append(SQLConstant.RB);
        sb.append(SQLConstant.VALUES);
        sb.append(SQLConstant.LB);
        visitCollection(setFields.entrySet(), entry -> visitValue(entry.getKey(), entry.getValue()), SQLConstant.COMMA);
        sb.append(SQLConstant.RB);
    }

    @Override
    public void visit(UpdateRequestAst request) {
        sb.append(SQLConstant.UPDATE);
        visitField(tableName);
        sb.append(SQLConstant.SET);
        visitFieldSetClause(request.getUpdateFields());
        visitWhereClause(request.getCondition());
        visit(request.getSortLimitClause());
    }

    @Override
    public void visit(DeleteRequestAst request) {
        sb.append(SQLConstant.DELETE);
        sb.append(SQLConstant.FROM);
        visitField(tableName);
        visitWhereClause(request.getCondition());
        visit(request.getSortLimitClause());
    }

    @Override
    public void visit(InsertRequestAst request) {
        sb.append(SQLConstant.INSERT_INTO);
        visitField(tableName);
        visitValueInsertClause(request.getUpdateFields());
        if (CollectionUtils.sizeIsEmpty(request.getOnDuplicateKeyUpdateFields())) {
            return;
        }
        for (Entry entry : request.getOnDuplicateKeyUpdateFields().entrySet()) {
            if (entry.getValue() == null) {
                entry.setValue(request.getUpdateFields().get(entry.getKey()));
            }
        }
        sb.append(SQLConstant.ON_DUPLICATE_KEY_UPDATE);
        visitFieldSetClause(request.getOnDuplicateKeyUpdateFields());
    }


    @Override
    public void visit(AndConditionAst condition) {
        visitConditions(condition.getSubConditions(), cond -> ((SqlAst) cond).accept(this), SQLConstant.AND);
    }


    @Override
    public void visit(OrConditionAst condition) {
        sb.append(SQLConstant.LB);
        visitConditions(condition.getSubConditions(), cond -> ((SqlAst) cond).accept(this), SQLConstant.OR);
        sb.append(SQLConstant.RB);
    }


    @Override
    public void visit(EqualConditionAst condition) {
        visitField(condition.getField());
        if (condition.getValue() instanceof Collection) {
            sb.append(SQLConstant.IN);
            sb.append(SQLConstant.LB);
            visitValue(condition.getField(), condition.getValue());
            sb.append(SQLConstant.RB);
        } else {
            sb.append(SQLConstant.EQUAL);
            visitValue(condition.getField(), condition.getValue());
        }
    }


    @Override
    public void visit(RangeConditionAst condition) {
        if (condition.getGt() != null) {
            visitField(condition.getField());
            sb.append(condition.isEgt() ? SQLConstant.GTE : SQLConstant.GT);
            visitValue(condition.getField(), condition.getGt());
            if (condition.getLt() != null) {
                sb.append(SQLConstant.AND);
            }
        }
        if (condition.getLt() != null) {
            visitField(condition.getField());
            sb.append(condition.isElt() ? SQLConstant.LTE : SQLConstant.LT);
            visitValue(condition.getField(), condition.getLt());
        }
    }


    @Override
    public void visit(LikeConditionAst condition) {
        visitField(condition.getField());
        sb.append(SQLConstant.LIKE);
        String value =
                (condition.isMatchLeft() ? "%" : "") + condition.getValue() + (condition.isMatchRight() ? "%" : "");
        visitValue(condition.getField(), value);
    }

    @Override
    public void visit(IsNullConditionAst condition) {
        visitField(condition.getField());
        sb.append(SQLConstant.IS_NULL);
    }


    private void visit(Sort sort) {
        visitField(sort.getField());
        sb.append(sort.getOrder().name());
        sb.append(SQLConstant.SPACE);
    }

    protected void visitValue(String field, Object value) {
        if (value instanceof SqlFunction) {
            visitFunction((SqlFunction) value, true);
        } else if (value instanceof Column) {
            visitField(((Column) value).getName());
        } else {
            sb.append(valueParser.parseValue(field, value));
        }
    }

    protected void visitFunction(SqlFunction function, boolean isValue) {
        sb.append(function.getType().name());
        sb.append(SQLConstant.LB);
        if (function.isDistinct()) {
            sb.append(SQLConstant.DISTINCT);
        }
        if (function.getField() != null) {
            visitField(function.getField());
        } else {
            sb.append(SQLConstant.ALL_FIELD);
        }
        sb.append(SQLConstant.RB);
        if (!isValue) {
            sb.append(SQLConstant.AS);
            visitField(function.genKey());
        }
    }

    private void visitField(String field) {
        sb.append(UNQUOTE);
        sb.append(field);
        sb.append(UNQUOTE);
        sb.append(SPACE);
    }


    private <T> void visitCollection(Collection<T> collection, Consumer<T> action, String separator) {
        boolean first = true;
        for (T t : collection) {
            if (first) {
                first = false;
            } else {
                sb.append(separator);
            }
            action.accept(t);
        }
    }

    private <T extends Condition> void visitConditions(Collection<T> collection, Consumer<T> action, String separator) {
        boolean first = true;
        for (T t : collection) {
            if (t.isEmpty()) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append(separator);
            }
            action.accept(t);
        }
    }
}