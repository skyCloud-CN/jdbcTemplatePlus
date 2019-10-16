/**
 * @(#)SortLimitClause.java, 10月 07, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.ast.model;

import com.skycloud.fastdao.core.ast.SqlAst;
import com.skycloud.fastdao.core.ast.Visitor;

import java.util.List;

/**
 * @author yuntian
 */
public class SortLimitClause implements SqlAst {

    private List<Sort> sorts;

    private Integer limit;

    private Integer offset;

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        SortLimitClause sortLimitClause = new SortLimitClause();
        for (Sort sort : sorts) {
            sortLimitClause.sorts.add(new Sort(sort.getField(), sort.getOrder()));
        }
        sortLimitClause.limit = limit;
        sortLimitClause.offset = offset;
        return sortLimitClause;
    }
}