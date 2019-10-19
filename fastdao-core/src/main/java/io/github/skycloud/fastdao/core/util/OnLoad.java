/**
 * @(#)OnLoad.java, 10月 17, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.util;

/**
 * @author yuntian
 */
@FunctionalInterface
public interface OnLoad<K, V> {

    V load(K key);
}