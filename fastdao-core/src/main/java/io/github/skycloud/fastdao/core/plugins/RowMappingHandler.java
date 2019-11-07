/**
 * @(#)RowMappingPlugin.java, 10月 06, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.plugins;

import io.github.skycloud.fastdao.core.mapping.RowMapping;

/**
 * @author yuntian
 */
public interface RowMappingHandler {

    boolean handle(RowMapping rowMapping);
}