/**
 * @(#)RequestProcessor.java, 10月 07, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core;

import com.skycloud.fastdao.core.ast.Request;

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
        request.accept(sqlVisitor);
        if (!sqlVisitor.isLegal()) {
            return false;
        }
        return true;
    }

}