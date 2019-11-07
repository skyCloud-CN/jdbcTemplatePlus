/**
 * @(#)OnLoad.java, 10月 17, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.util;

/**
 * @author yuntian
 */
@FunctionalInterface
public interface OnLoad<K, V> {

    V load(K key);
}