/**
 * @(#)IUpdateRequest.java, 10月 20, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.request;

import io.github.skycloud.fastdao.core.ast.enums.OrderEnum;
import io.github.skycloud.fastdao.core.models.Column;

/**
 * @author yuntian
 */
public interface UpdateRequest extends Sortable<UpdateRequest>, ConditionalRequest<UpdateRequest>, FieldUpdateRequest<UpdateRequest> {

    static UpdateRequest newInstance() {
        return new UpdateRequestAst();
    }

    /**
     * override from {@link FieldUpdateRequest}
     */
    @Override
    UpdateRequest addUpdateField(String field, Object value);

    /**
     * override from {@link FieldUpdateRequest}
     */
    @Override
    UpdateRequest addUpdateField(Column field, Object value);

    /**
     * override from {@link Sortable}
     */
    @Override
    UpdateRequest limit(int limit);

    /**
     * override from {@link Sortable}
     */
    @Override
    UpdateRequest offset(int offset);

    /**
     * override from {@link Sortable}
     */
    @Override
    UpdateRequest addSort(Column column, OrderEnum order);

    /**
     * override from {@link Sortable}
     */
    @Override
    UpdateRequest addSort(String field, OrderEnum order);


}