/**
 * @(#)NowAutoFillHandler.java, 10月 07, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.plugins.autofill;

import com.skycloud.fastdao.core.ast.Request;

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