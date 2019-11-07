/**
 * @(#)LogicDeleteVisitor.java, 10月 24, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.plugins.logicdelete;


import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.conditions.AndConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.Condition;
import io.github.skycloud.fastdao.core.ast.conditions.EqualConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.IsNullConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.LikeConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.OrConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.RangeConditionAst;
import io.github.skycloud.fastdao.core.ast.model.SortLimitClause;
import io.github.skycloud.fastdao.core.ast.request.ConditionalRequest;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequestAst;
import io.github.skycloud.fastdao.core.ast.request.InsertRequestAst;
import io.github.skycloud.fastdao.core.ast.request.QueryRequestAst;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequestAst;
import io.github.skycloud.fastdao.core.ast.visitor.Visitor;

import java.util.List;

/**
 * @author yuntian
 * this visitor is to find whether request has a condition related with LogicDelete field
 * if condition related with LogicDelete field is detected, then do nothing
 * or else add a condition LogicDelele=DefaultUnDeletedValue
 */
public class LogicDeleteVisitor implements Visitor {

    private String logicDeleteColumn;

    private Object logicDeleteDefaultValue;

    private boolean hasLogicDeleteField = false;

    LogicDeleteVisitor(String logicDeleteColumn, Object logicDeleteDefaultValue) {
        this.logicDeleteColumn = logicDeleteColumn;
        this.logicDeleteDefaultValue = logicDeleteDefaultValue;
    }

    @Override
    public void visit(QueryRequestAst request) {
        visit((ConditionalRequest) request);
    }

    @Override
    public void visit(UpdateRequestAst request) {
        visit((ConditionalRequest) request);
    }

    @Override
    public void visit(DeleteRequestAst request) {
        // do nothing
    }
    // if a insertRequest d
    @Override
    public void visit(InsertRequestAst request) {
        if (request.getUpdateFields().containsKey(logicDeleteColumn)) {
            return;
        }
        request.addUpdateField(logicDeleteColumn, logicDeleteDefaultValue);
    }

    @Override
    public void visit(SortLimitClause sortLimitClause) {

    }

    private void visit(ConditionalRequest request) {
        if (request.getCondition() == null) {
            request.setCondition(new EqualConditionAst(logicDeleteColumn, logicDeleteDefaultValue));
            return;
        }
        ((SqlAst) request.getCondition()).accept(this);
        if (!hasLogicDeleteField) {
            addLogicDeleteCondition(request);
        }
    }

    private void addLogicDeleteCondition(ConditionalRequest request) {
        Condition condition = Condition.and()
                .and(request.getCondition())
                .and(new EqualConditionAst(logicDeleteColumn, logicDeleteDefaultValue));
        request.setCondition(condition);
    }

    @Override
    public void visit(AndConditionAst condition) {
        List<Condition> subConditions = condition.getSubConditions();
        for (Condition subCondition : subConditions) {
            ((SqlAst) subCondition).accept(this);
        }
    }

    @Override
    public void visit(OrConditionAst condition) {
        List<Condition> subConditions = condition.getSubConditions();
        for (Condition subCondition : subConditions) {
            ((SqlAst) subCondition).accept(this);
        }
    }

    @Override
    public void visit(EqualConditionAst condition) {
        hasLogicDeleteField |= logicDeleteColumn.equals(condition.getField());
    }

    @Override
    public void visit(RangeConditionAst condition) {
        hasLogicDeleteField |= logicDeleteColumn.equals(condition.getField());
    }

    @Override
    public void visit(LikeConditionAst condition) {
        hasLogicDeleteField |= logicDeleteColumn.equals(condition.getField());
    }

    @Override
    public void visit(IsNullConditionAst condition) {
        hasLogicDeleteField |= logicDeleteColumn.equals(condition.getField());
    }

}