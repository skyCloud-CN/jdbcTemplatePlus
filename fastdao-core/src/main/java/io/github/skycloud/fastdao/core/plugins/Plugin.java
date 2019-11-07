/**
 * @(#)Plugin.java, 10月 07, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.plugins;

import java.util.List;

/**
 * @author yuntian
 */
public interface Plugin {

    List<PluggableHandler> getHandlers();
}