
package io.github.skycloud.fastdao.core.plugins;

/**
 * @author yuntian
 */
public interface PluggableHandler<T extends Pluggable> {

    T handle(T pluggable, Class clazz);
}