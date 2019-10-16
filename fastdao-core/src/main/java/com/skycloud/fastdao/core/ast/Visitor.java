package com.skycloud.fastdao.core.ast; /**
 * @(#)Visitor.java, 9月 10, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import com.skycloud.fastdao.core.ast.conditions.AndCondition;
import com.skycloud.fastdao.core.ast.conditions.EqualCondition;
import com.skycloud.fastdao.core.ast.conditions.LikeCondition;
import com.skycloud.fastdao.core.ast.conditions.OrCondition;
import com.skycloud.fastdao.core.ast.conditions.RangeCondition;
import com.skycloud.fastdao.core.ast.model.SortLimitClause;
import com.skycloud.fastdao.core.ast.request.CountRequest;
import com.skycloud.fastdao.core.ast.request.DeleteRequest;
import com.skycloud.fastdao.core.ast.request.InsertRequest;
import com.skycloud.fastdao.core.ast.request.QueryRequest;
import com.skycloud.fastdao.core.ast.request.UpdateRequest;
import com.skycloud.fastdao.core.plugins.Pluggable;


/**
 * @author yuntian
 */
public interface Visitor extends Pluggable {

    void visit(QueryRequest request);

    void visit(UpdateRequest request);

    void visit(DeleteRequest request);

    void visit(InsertRequest request);

    void visit(CountRequest request);

    void visit(SortLimitClause sortLimitClause);

    void visit(AndCondition andCondition);

    void visit(OrCondition orCondition);

    void visit(EqualCondition equalCondition);

    void visit(RangeCondition rangeCondition);

    void visit(LikeCondition likeCondition);

}