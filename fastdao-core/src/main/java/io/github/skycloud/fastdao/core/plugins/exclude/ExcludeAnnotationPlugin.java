/**
 * @(#)ExcludePlugin.java, 10月 08, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins.exclude;

import com.google.common.collect.Lists;
import io.github.skycloud.fastdao.core.plugins.PluggableHandler;
import io.github.skycloud.fastdao.core.plugins.Plugin;

import java.util.List;

/**
 * @author yuntian
 */
public class ExcludeAnnotationPlugin implements Plugin {

    @Override
    public List<PluggableHandler> getHandlers() {
        return Lists.newArrayList(new ExcludeHandler());
    }
}