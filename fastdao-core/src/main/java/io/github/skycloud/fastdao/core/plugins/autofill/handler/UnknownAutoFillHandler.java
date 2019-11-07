/**
 * @(#)UnknownAutoFillHandler.java, 11月 07, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins.autofill.handler;

import io.github.skycloud.fastdao.core.ast.request.Request;
import io.github.skycloud.fastdao.core.plugins.autofill.AutoFillHandler;

/**
 * @author yuntian
 */
public class UnknownAutoFillHandler implements AutoFillHandler {

    @Override
    public Object handle(Request request) {
        return null;
    }
}