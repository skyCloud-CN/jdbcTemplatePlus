/**
 * @(#)NowAutoFillHandler.java, 10月 07, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins.autofill;

import io.github.skycloud.fastdao.core.ast.request.Request;

import java.util.Date;

/**
 * @author yuntian
 */
public class NowDateAutoFillHandler implements AutoFillHandler<Date> {

    @Override
    public Date handle(Request request) {
        return new Date();
    }
}