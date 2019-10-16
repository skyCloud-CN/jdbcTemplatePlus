/**
 * @(#)RowMappingPlugin.java, 10月 06, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.plugins;

import com.skycloud.fastdao.core.mapping.RowMapping;

/**
 * @author yuntian
 */
public interface RowMappingHandler {

    boolean handle(RowMapping rowMapping);
}