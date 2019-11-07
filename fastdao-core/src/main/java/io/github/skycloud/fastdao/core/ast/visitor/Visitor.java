package io.github.skycloud.fastdao.core.ast.visitor; /**
 * @(#)Visitor.java, 9月 10, 2019.
 * <p>
 *
 */

import io.github.skycloud.fastdao.core.ast.conditions.AndConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.EqualConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.IsNullConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.LikeConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.OrConditionAst;
import io.github.skycloud.fastdao.core.ast.conditions.RangeConditionAst;
import io.github.skycloud.fastdao.core.ast.model.SortLimitClause;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequestAst;
import io.github.skycloud.fastdao.core.ast.request.InsertRequestAst;
import io.github.skycloud.fastdao.core.ast.request.QueryRequestAst;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequestAst;
import io.github.skycloud.fastdao.core.plugins.Pluggable;


/**
 * @author yuntian
 */
public interface Visitor extends Pluggable {

    void visit(QueryRequestAst request);

    void visit(UpdateRequestAst request);

    void visit(DeleteRequestAst request);

    void visit(InsertRequestAst request);

    void visit(SortLimitClause sortLimitClause);

    void visit(AndConditionAst condition);

    void visit(OrConditionAst condition);

    void visit(EqualConditionAst condition);

    void visit(RangeConditionAst condition);

    void visit(LikeConditionAst condition);

    void visit(IsNullConditionAst condition);

}