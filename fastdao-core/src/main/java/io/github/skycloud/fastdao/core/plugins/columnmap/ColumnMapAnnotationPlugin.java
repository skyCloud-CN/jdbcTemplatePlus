/**
 * @(#)ColumnMapPlugin.java, 10月 08, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.plugins.columnmap;

import com.google.common.collect.Lists;
import io.github.skycloud.fastdao.core.plugins.PluggableHandler;
import io.github.skycloud.fastdao.core.plugins.Plugin;

import java.util.List;

/**
 * @author yuntian
 */
public class ColumnMapAnnotationPlugin implements Plugin {

    @Override
    public List<PluggableHandler> getHandlers() {
        return Lists.newArrayList(new ColumnMapColumnMappingHandler(), new ColumnMappingValueParserHandler());
    }
}