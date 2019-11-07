/**
 * @(#)Sortable.java, 10月 20, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.request;

import io.github.skycloud.fastdao.core.ast.enums.OrderEnum;
import io.github.skycloud.fastdao.core.models.Column;


/**
 * @author yuntian
 */
public interface Sortable<T extends Sortable<T>> {

    T limit(int limit);

    T offset(int offset);

    T addSort(Column column, OrderEnum order);

    T addSort(String field, OrderEnum order);
}