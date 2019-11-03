/**
 * @(#)ICountRequest.java, 10月 20, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.request;

import io.github.skycloud.fastdao.core.models.Column;

/**
 * @author yuntian
 */
public interface CountRequest extends ConditionalRequest<CountRequest> {

    static CountRequest newInstance() {
        return new CountRequestAst();
    }

    /**
     * SELECT DISTINCT COUNT(*) ...
     */
    CountRequest distinct();

    /**
     * SELECT COUNT(field)
     */
    CountRequest setCountField(String field);

    /**
     * SELECT COUNT(field)
     */
    CountRequest setCountField(Column field);

}