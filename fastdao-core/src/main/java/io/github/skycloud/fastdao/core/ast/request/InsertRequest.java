/**
 * @(#)IInsertRequest.java, 10月 20, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.request;

import io.github.skycloud.fastdao.core.models.Column;

import java.util.Collection;

/**
 * @author yuntian
 */
public interface InsertRequest extends FieldUpdateRequest<InsertRequest> {

    static InsertRequest newInstance() {
        return new InsertRequestAst();
    }

    /**
     * override from {@link FieldUpdateRequest}
     */
    @Override
    InsertRequest addUpdateField(Column field, Object value);

    /**
     * override from {@link FieldUpdateRequest}
     */
    @Override
    InsertRequest addUpdateField(String field, Object value);


    /**
     * add onDuplicateUpdateField
     */
    InsertRequest addOnDuplicateUpdateField(Column... fields);

    InsertRequest addOnDuplicateUpdateField(String... fields);

    InsertRequest addOnDuplicateUpdateField(Collection fields);

    InsertRequest addOnDuplicateUpdateField(Column field, Object value);

    InsertRequest addOnDuplicateUpdateField(String field, Object value);

}