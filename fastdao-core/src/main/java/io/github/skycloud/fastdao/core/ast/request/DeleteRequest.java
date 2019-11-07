/**
 * @(#)IDeleteRequest.java, 10月 20, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.request;


import io.github.skycloud.fastdao.core.ast.enums.OrderEnum;
import io.github.skycloud.fastdao.core.models.Column;

/**
 * @author yuntian
 */
public interface DeleteRequest extends Sortable<DeleteRequest>, ConditionalRequest<DeleteRequest> {

    static DeleteRequest newInstance() {
        return new DeleteRequestAst();
    }

    /**
     * override from {@link Sortable}
     */
    @Override
    DeleteRequest limit(int limit);

    /**
     * override from {@link Sortable}
     */
    @Override
    DeleteRequest offset(int offset);

    /**
     * override from {@link Sortable}
     */
    @Override
    DeleteRequest addSort(Column column, OrderEnum order);

    /**
     * override from {@link Sortable}
     */
    @Override
    DeleteRequest addSort(String field, OrderEnum order);

}