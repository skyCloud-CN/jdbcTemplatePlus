package io.github.skycloud.fastdao.core.ast; /**
 * @(#)Visitor.java, 9月 10, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import io.github.skycloud.fastdao.core.ast.conditions.AndCondition.DefaultAndCondition;
import io.github.skycloud.fastdao.core.ast.conditions.EqualCondition.DefaultEqualCondition;
import io.github.skycloud.fastdao.core.ast.conditions.LikeCondition.DefaultLikeCondition;
import io.github.skycloud.fastdao.core.ast.conditions.OrCondition.DefaultOrCondition;
import io.github.skycloud.fastdao.core.ast.conditions.RangeCondition.DefaultRangeCondition;
import io.github.skycloud.fastdao.core.ast.conditions.IsNullCondition.DefaultIsNullCondition;
import io.github.skycloud.fastdao.core.ast.model.SortLimitClause;
import io.github.skycloud.fastdao.core.ast.request.CountRequest.DefaultCountRequest;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest.DefaultDeleteRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest.DefaultQueryRequest;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest.DefaultUpdateRequest;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest.DefaultInsertRequest;
import io.github.skycloud.fastdao.core.plugins.Pluggable;


/**
 * @author yuntian
 */
public interface Visitor extends Pluggable {

    void visit(DefaultQueryRequest request);

    void visit(DefaultUpdateRequest request);

    void visit(DefaultDeleteRequest request);

    void visit(DefaultInsertRequest request);

    void visit(DefaultCountRequest request);

    void visit(SortLimitClause sortLimitClause);

    void visit(DefaultAndCondition condition);

    void visit(DefaultOrCondition condition);

    void visit(DefaultEqualCondition condition);

    void visit(DefaultRangeCondition condition);

    void visit(DefaultLikeCondition condition);

    void visit(DefaultIsNullCondition condition);

}