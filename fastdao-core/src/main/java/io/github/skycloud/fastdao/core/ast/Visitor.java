package io.github.skycloud.fastdao.core.ast; /**
 * @(#)Visitor.java, 9月 10, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import io.github.skycloud.fastdao.core.ast.conditions.AndCondition.AndConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.EqualCondition.EqualConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.LikeCondition.LikeConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.OrCondition.OrConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.RangeCondition.RangeConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.IsNullCondition.IsNullConditionAst;
import io.github.skycloud.fastdao.core.ast.model.SortLimitClause;
import io.github.skycloud.fastdao.core.ast.request.CountRequest.CountRequestAst;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest.DeleteRequestAst;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest.QueryRequestAst;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest.UpdateRequestAst;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest.InsertRequestAst;
import io.github.skycloud.fastdao.core.plugins.Pluggable;


/**
 * @author yuntian
 */
public interface Visitor extends Pluggable {

    void visit(QueryRequestAst request);

    void visit(UpdateRequestAst request);

    void visit(DeleteRequestAst request);

    void visit(InsertRequestAst request);

    void visit(CountRequestAst request);

    void visit(SortLimitClause sortLimitClause);

    void visit(AndConditionAst condition);

    void visit(OrConditionAst condition);

    void visit(EqualConditionAst condition);

    void visit(RangeConditionAst condition);

    void visit(LikeConditionAst condition);

    void visit(IsNullConditionAst condition);

}