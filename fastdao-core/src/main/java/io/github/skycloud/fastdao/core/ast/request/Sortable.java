/**
 * @(#)Sortable.java, 10月 20, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast;

import io.github.skycloud.fastdao.core.ast.enums.OrderEnum;
import io.github.skycloud.fastdao.core.table.Column;


/**
 * @author yuntian
 */
public interface Sortable<T extends Sortable<T>> {

    T limit(int limit);

    T offset(int offset);

    T addSort(Column column, OrderEnum order);

    T addSort(String field, OrderEnum order);
}