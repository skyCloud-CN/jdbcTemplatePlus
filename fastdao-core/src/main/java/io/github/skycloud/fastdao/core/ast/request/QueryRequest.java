/**
 * @(#)IQueryRequest.java, 10月 20, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.request;

import io.github.skycloud.fastdao.core.ast.conditions.Condition;
import io.github.skycloud.fastdao.core.ast.enums.OrderEnum;
import io.github.skycloud.fastdao.core.ast.model.SqlFunction;
import io.github.skycloud.fastdao.core.models.Column;

import java.util.Collection;

/**
 * @author yuntian
 */
public interface QueryRequest extends Sortable<QueryRequest>, ConditionalRequest<QueryRequest> {

    static QueryRequest newInstance() {
        return new QueryRequestAst();
    }

    /**
     * SELECT DISTINCT ...
     */
    QueryRequest distinct();

    /**
     * add select fields to queryRequest
     */
    QueryRequest addSelectFields(Column... fields);

    QueryRequest addSelectFields(Collection fields);

    QueryRequest addSelectFields(SqlFunction... fields);

    QueryRequest addSelectFields(String... fields);

    /**
     * SELECT ... GROUP BY
     */
    QueryRequest groupBy(Column... columns);

    /**
     * SELECT ... HAVING
     */
    QueryRequest having(Condition condition);

    /**
     * override from {@link Sortable}
     */
    @Override
    QueryRequest limit(int limit);

    /**
     * override from {@link Sortable}
     */
    @Override
    QueryRequest offset(int offset);

    /**
     * override from {@link Sortable}
     */
    @Override
    QueryRequest addSort(Column column, OrderEnum order);

    /**
     * override from {@link Sortable}
     */
    @Override
    QueryRequest addSort(String field, OrderEnum order);

}