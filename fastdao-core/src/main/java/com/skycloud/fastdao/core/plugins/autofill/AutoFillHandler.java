/**
 * @(#)AutoFillHandler.java, 10月 07, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.plugins.autofill;

import com.skycloud.fastdao.core.ast.Request;

/**
 * @author yuntian
 */
public interface AutoFillHandler<T> {

    T handle(Request request);
}