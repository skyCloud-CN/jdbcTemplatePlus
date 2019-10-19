/**
 * @(#)Plugable.java, 10月 12, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins;

/**
 * @author yuntian
 */
public interface Pluggable {

    default <T extends Pluggable> T invokePlugin(Class clazz) {
        return PluginManager.plugin((T) this, clazz);
    }

}