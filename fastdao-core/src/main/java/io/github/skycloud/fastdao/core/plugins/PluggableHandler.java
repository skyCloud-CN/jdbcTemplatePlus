/**
 * @(#)PluginHandler.java, 10月 11, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins;

/**
 * @author yuntian
 */
public interface PluggableHandler<T extends Pluggable> {

    T handle(T pluggable, Class clazz);
}