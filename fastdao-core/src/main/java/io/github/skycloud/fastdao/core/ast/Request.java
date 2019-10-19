/**
 * @(#)Request.java, 10月 12, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast;

import io.github.skycloud.fastdao.core.ast.request.CountRequest;
import io.github.skycloud.fastdao.core.ast.request.CountRequest.DefaultCountRequest;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest.DefaultDeleteRequest;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest.DefaultInsertRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest.DefaultQueryRequest;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest.DefaultUpdateRequest;
import io.github.skycloud.fastdao.core.plugins.Pluggable;

/**
 * @author yuntian
 */
public interface Request extends Pluggable {

    static UpdateRequest updateRequest() {
        return new DefaultUpdateRequest();
    }

    static DeleteRequest deleteRequest() {
        return new DefaultDeleteRequest();
    }

    static InsertRequest insertRequest() {
        return new DefaultInsertRequest();
    }

    static CountRequest countRequest() {
        return new DefaultCountRequest();
    }

    static QueryRequest queryRequest() {
        return new DefaultQueryRequest();
    }
}