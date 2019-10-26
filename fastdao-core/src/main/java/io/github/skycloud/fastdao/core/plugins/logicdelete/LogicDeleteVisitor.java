/**
 * @(#)LogicDeleteVisitor.java, 10月 24, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins.logicdelete;


import io.github.skycloud.fastdao.core.ast.Condition;
import io.github.skycloud.fastdao.core.ast.ConditionalRequest;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.Visitor;
import io.github.skycloud.fastdao.core.ast.conditions.AndCondition.AndConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.EqualCondition.EqualConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.IsNullCondition.IsNullConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.LikeCondition.LikeConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.OrCondition.OrConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.RangeCondition.RangeConditionAst;
import io.github.skycloud.fastdao.core.ast.model.SortLimitClause;
import io.github.skycloud.fastdao.core.ast.request.CountRequest.CountRequestAst;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest.DeleteRequestAst;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest.InsertRequestAst;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest.QueryRequestAst;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest.UpdateRequestAst;

import java.util.List;

/**
 * @author yuntian
 */
public class LogicDeleteVisitor implements Visitor {

    private String logicDeleteColumn;

    private Object logicDeleteDefaultValue;

    boolean hasLogicDeleteField=false;
    public LogicDeleteVisitor(String logicDeleteColumn, Object logicDeleteDefaultValue) {
        this.logicDeleteColumn = logicDeleteColumn;
        this.logicDeleteDefaultValue = logicDeleteDefaultValue;
    }

    @Override
    public void visit(QueryRequestAst request) {
        visit((ConditionalRequest)request);
    }

    @Override
    public void visit(UpdateRequestAst request) {
        visit((ConditionalRequest)request);
    }

    @Override
    public void visit(DeleteRequestAst request) {

    }

    @Override
    public void visit(InsertRequestAst request) {
        if(request.getInsertFields().containsKey(logicDeleteColumn)){
            return;
        }
        request.addInsertField(logicDeleteColumn,logicDeleteDefaultValue);
    }

    @Override
    public void visit(CountRequestAst request) {
        visit((ConditionalRequest)request);
    }

    @Override
    public void visit(SortLimitClause sortLimitClause) {

    }
    private void visit(ConditionalRequest request){
        if(request.getCondition()==null){
            request.setCondition(new EqualConditionAst(logicDeleteColumn,logicDeleteDefaultValue));
            return;
        }
        ((SqlAst)request.getCondition()).accept(this);
        if(!hasLogicDeleteField){
            addLogicDeleteCondition(request);
        }
    }

    private void addLogicDeleteCondition(ConditionalRequest request){
        Condition condition=Condition.and()
                .and(request.getCondition())
                .and(new EqualConditionAst(logicDeleteColumn,logicDeleteDefaultValue));
        request.setCondition(condition);
    }
    @Override
    public void visit(AndConditionAst condition) {
        List<Condition> subConditions=condition.getSubConditions();
        for(Condition subCondition:subConditions){
            ((SqlAst)subCondition).accept(this);
        }
    }

    @Override
    public void visit(OrConditionAst condition) {
        List<Condition> subConditions=condition.getSubConditions();
        for(Condition subCondition:subConditions){
            ((SqlAst)subCondition).accept(this);
        }
    }

    @Override
    public void visit(EqualConditionAst condition) {
        hasLogicDeleteField|=logicDeleteColumn.equals(condition.getField());
    }

    @Override
    public void visit(RangeConditionAst condition) {
        hasLogicDeleteField|=logicDeleteColumn.equals(condition.getField());
    }

    @Override
    public void visit(LikeConditionAst condition) {
        hasLogicDeleteField|=logicDeleteColumn.equals(condition.getField());
    }

    @Override
    public void visit(IsNullConditionAst condition) {
        hasLogicDeleteField|=logicDeleteColumn.equals(condition.getField());
    }

}