/**
 * @(#)Plugin.java, 10月 07, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins;

import java.util.List;

/**
 * @author yuntian
 */
public interface Plugin {

    List<PluggableHandler> getHandlers();
}