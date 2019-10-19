/**
 * @(#)RowMappingPluginChain.java, 10月 06, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins;

import com.google.common.collect.Lists;
import io.github.skycloud.fastdao.core.mapping.RowMapping;

import java.util.List;

/**
 * @author yuntian
 */
public class RowMappingHandlerChain implements RowMappingHandler {

    private List<RowMappingHandler> handlers = Lists.newArrayList();

    @Override
    public boolean handle(RowMapping rowMapping) {
        for (RowMappingHandler handler : handlers) {
            if (!handler.handle(rowMapping)) {
                return true;
            }
        }
        return true;
    }

    public void register(RowMappingHandler plugin) {
        if (plugin != null) {
            handlers.add(plugin);
        }
    }

}