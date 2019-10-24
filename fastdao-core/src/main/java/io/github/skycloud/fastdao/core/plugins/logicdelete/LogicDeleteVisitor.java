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
import io.github.skycloud.fastdao.core.ast.conditions.AndCondition.DefaultAndCondition;
import io.github.skycloud.fastdao.core.ast.conditions.EqualCondition.DefaultEqualCondition;
import io.github.skycloud.fastdao.core.ast.conditions.IsNullCondition.DefaultIsNullCondition;
import io.github.skycloud.fastdao.core.ast.conditions.LikeCondition.DefaultLikeCondition;
import io.github.skycloud.fastdao.core.ast.conditions.OrCondition.DefaultOrCondition;
import io.github.skycloud.fastdao.core.ast.conditions.RangeCondition.DefaultRangeCondition;
import io.github.skycloud.fastdao.core.ast.model.SortLimitClause;
import io.github.skycloud.fastdao.core.ast.request.CountRequest.DefaultCountRequest;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest.DefaultDeleteRequest;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest.DefaultInsertRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest.DefaultQueryRequest;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest.DefaultUpdateRequest;

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
    public void visit(DefaultQueryRequest request) {
        visit((ConditionalRequest)request);
    }

    @Override
    public void visit(DefaultUpdateRequest request) {
        visit((ConditionalRequest)request);
    }

    @Override
    public void visit(DefaultDeleteRequest request) {

    }

    @Override
    public void visit(DefaultInsertRequest request) {
        if(request.getInsertFields().containsKey(logicDeleteColumn)){
            return;
        }
        request.addInsertField(logicDeleteColumn,logicDeleteDefaultValue);
    }

    @Override
    public void visit(DefaultCountRequest request) {
        visit((ConditionalRequest)request);
    }

    @Override
    public void visit(SortLimitClause sortLimitClause) {

    }
    private void visit(ConditionalRequest request){
        if(request.getCondition()==null){
            request.setCondition(new DefaultEqualCondition(logicDeleteColumn,logicDeleteDefaultValue));
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
                .and(new DefaultEqualCondition(logicDeleteColumn,logicDeleteDefaultValue));
        request.setCondition(condition);
    }
    @Override
    public void visit(DefaultAndCondition condition) {
        List<Condition> subConditions=condition.getSubConditions();
        for(Condition subCondition:subConditions){
            ((SqlAst)subCondition).accept(this);
        }
    }

    @Override
    public void visit(DefaultOrCondition condition) {
        List<Condition> subConditions=condition.getSubConditions();
        for(Condition subCondition:subConditions){
            ((SqlAst)subCondition).accept(this);
        }
    }

    @Override
    public void visit(DefaultEqualCondition condition) {
        hasLogicDeleteField|=logicDeleteColumn.equals(condition.getField());
    }

    @Override
    public void visit(DefaultRangeCondition condition) {
        hasLogicDeleteField|=logicDeleteColumn.equals(condition.getField());
    }

    @Override
    public void visit(DefaultLikeCondition condition) {
        hasLogicDeleteField|=logicDeleteColumn.equals(condition.getField());
    }

    @Override
    public void visit(DefaultIsNullCondition condition) {
        hasLogicDeleteField|=logicDeleteColumn.equals(condition.getField());
    }

}