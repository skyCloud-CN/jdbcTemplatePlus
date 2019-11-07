
package io.github.skycloud.fastdao.core.plugins;

/**
 * @author yuntian
 */
public interface Pluggable {

    default <T extends Pluggable> T invokePlugin(Class clazz) {
        return PluginManager.plugin((T) this, clazz);
    }

}