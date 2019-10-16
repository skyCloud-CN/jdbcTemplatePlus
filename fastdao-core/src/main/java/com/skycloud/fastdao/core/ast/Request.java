/**
 * @(#)Request.java, 10月 12, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.ast;

import com.skycloud.fastdao.core.ast.request.CountRequest;
import com.skycloud.fastdao.core.ast.request.DeleteRequest;
import com.skycloud.fastdao.core.ast.request.InsertRequest;
import com.skycloud.fastdao.core.ast.request.QueryRequest;
import com.skycloud.fastdao.core.ast.request.UpdateRequest;

/**
 * @author yuntian
 */
public abstract class Request implements SqlAst {


    public static UpdateRequest updateRequest() {
        return new UpdateRequest();
    }

    public static DeleteRequest deleteRequest() {
        return new DeleteRequest();
    }

    public static InsertRequest insertRequest() {
        return new InsertRequest();
    }

    public static CountRequest countRequest() {
        return new CountRequest();
    }

    public static QueryRequest queryRequest() {
        return new QueryRequest();
    }
}