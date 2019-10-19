/**
 * @(#)RowMappingPlugin.java, 10月 06, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins;

import io.github.skycloud.fastdao.core.mapping.RowMapping;

/**
 * @author yuntian
 */
public interface RowMappingHandler {

    boolean handle(RowMapping rowMapping);
}