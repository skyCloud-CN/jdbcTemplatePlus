/**
 * @(#)SqlVisitor.java, 10月 01, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.ast;

import com.skycloud.fastdao.core.SqlVisitor;
import com.skycloud.fastdao.core.ast.conditions.AndCondition;
import com.skycloud.fastdao.core.ast.conditions.EqualCondition;
import com.skycloud.fastdao.core.ast.conditions.LikeCondition;
import com.skycloud.fastdao.core.ast.conditions.OrCondition;
import com.skycloud.fastdao.core.ast.conditions.RangeCondition;
import com.skycloud.fastdao.core.ast.constants.SQLConstant;
import com.skycloud.fastdao.core.ast.model.Sort;
import com.skycloud.fastdao.core.ast.model.SortLimitClause;
import com.skycloud.fastdao.core.ast.request.CountRequest;
import com.skycloud.fastdao.core.ast.request.DeleteRequest;
import com.skycloud.fastdao.core.ast.request.InsertRequest;
import com.skycloud.fastdao.core.ast.request.QueryRequest;
import com.skycloud.fastdao.core.ast.request.UpdateRequest;
import com.skycloud.fastdao.core.exceptions.IllegalConditionException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author yuntian
 */
public class MysqlVisitor extends SqlVisitor {


    public MysqlVisitor(String tableName, ValueParser valueParser) {
        super(tableName, valueParser);
    }

    @Override
    public void visit(QueryRequest request) {
        sb.append(SQLConstant.SELECT);
        if (request.isDistinct()) {
            sb.append(SQLConstant.DISTINCT);
        }
        if (CollectionUtils.isEmpty(request.getSelectFields())) {
            sb.append(SQLConstant.ALL_FIELD);
        } else {
            visitCollection(request.getSelectFields(), this::visitField, SQLConstant.COMMA);
        }
        sb.append(SQLConstant.FROM);
        visitField(tableName);
        visitWhereClause(request.getCondition());
        visit(request.getSortLimitClause());
    }

    @Override
    public void visit(CountRequest request) {
        sb.append(SQLConstant.SELECT);
        sb.append(SQLConstant.COUNT);
        sb.append(SQLConstant.LB);
        if (request.isDistinct()) {
            sb.append(SQLConstant.DISTINCT);
        }
        if (StringUtils.isNotBlank(request.getCountField())) {
            visitField(request.getCountField());
        }
        sb.append(SQLConstant.ALL_FIELD);
        sb.append(SQLConstant.RB);
        sb.append(SQLConstant.FROM);
        visitField(tableName);
        visitWhereClause(request.getCondition());
    }

    private void visitWhereClause(Condition condition) {
        if (condition == null || condition.isEmpty()) {
            return;
        }
        sb.append(SQLConstant.WHERE);
        try {
            condition.accept(this);
        } catch (IllegalConditionException e) {
            isLegal = false;
        }
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
            sb.append(limit);
            if (offset == null) {
                return;
            }
            sb.append(SQLConstant.COMMA);
            sb.append(offset);
        }
    }

    public void visitFieldSetClause(Map<String, Object> setFields) {
        sb.append(SQLConstant.SET);
        visitCollection(setFields.entrySet(), (entry) -> {
            visitField(entry.getKey());
            sb.append(SQLConstant.EQUAL);
            visitValue(entry.getKey(), entry.getValue());
        }, SQLConstant.COMMA);
    }


    @Override
    public void visit(UpdateRequest request) {
        sb.append(SQLConstant.UPDATE);
        visitField(tableName);
        visitFieldSetClause(request.getUpdateFields());
        visitWhereClause(request.getCondition());
        visit(request.getSortLimitClause());
    }

    @Override
    public void visit(DeleteRequest request) {
        sb.append(SQLConstant.DELETE);
        sb.append(SQLConstant.FROM);
        visitField(tableName);
        visitWhereClause(request.getCondition());
        visit(request.getSortLimitClause());
    }

    @Override
    public void visit(InsertRequest request) {
        sb.append(SQLConstant.INSERT_INTO);
        visitField(tableName);
        sb.append(SQLConstant.SET);
        visitCollection(request.getInsertFields().entrySet(), (entry) -> {
            visitField(entry.getKey());
            sb.append(SQLConstant.EQUAL);
            visitValue(entry.getKey(), entry.getValue());
        }, SQLConstant.COMMA);
    }


    @Override
    public void visit(AndCondition condition) {
        List<Condition> subConditions = condition.getSubConditions();
        visitConditions(subConditions, cond -> cond.accept(this), SQLConstant.AND);
    }


    @Override
    public void visit(OrCondition condition) {
        sb.append(SQLConstant.LB);
        visitConditions(condition.getSubConditions(), cond -> cond.accept(this), SQLConstant.OR);
        sb.append(SQLConstant.RB);
    }


    @Override
    public void visit(EqualCondition condition) {
        if (!condition.isLegal()) {
            throw new IllegalConditionException();
        }
        visitField(condition.getField());
        if (condition.getValue() instanceof Collection && ((Collection) condition.getValue()).size() > 1) {
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
    public void visit(RangeCondition condition) {
        if (!condition.isLegal()) {
            throw new IllegalConditionException();
        }
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
    public void visit(LikeCondition condition) {
        if (!condition.isLegal()) {
            throw new IllegalConditionException();
        }
        visitField(condition.getField());
        sb.append(SQLConstant.LIKE);
        String value =
                (condition.isMatchLeft() ? "%" : "") + condition.getValue() + (condition.isMatchRight() ? "%" : "");
        visitValue(condition.getField(), value);
    }


    private void visit(Sort sort) {
        visitField(sort.getField());
        sb.append(sort.getOrder().name());
    }

    protected void visitValue(String field, Object value) {
        sb.append(valueParser.parseValue(field, value));
    }

    protected void visitField(String field) {
        sb.append(valueParser.parseField(field));
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