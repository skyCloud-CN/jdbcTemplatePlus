/**
 * @(#)RequestProcessor.java, 10月 07, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core;

import io.github.skycloud.fastdao.core.ast.Request;
import io.github.skycloud.fastdao.core.ast.SqlAst;

/**
 * @author yuntian
 */
public class RequestProcessor {

    private SqlVisitor sqlVisitor;

    private Request request;

    public RequestProcessor(SqlVisitor sqlVisitor, Request request, Class clazz) {
        this.sqlVisitor = sqlVisitor.invokePlugin(clazz);
        this.request = request.invokePlugin(clazz);
    }

    public boolean process() {
        ((SqlAst)request).accept(sqlVisitor);
        return sqlVisitor.isLegal();
    }

}