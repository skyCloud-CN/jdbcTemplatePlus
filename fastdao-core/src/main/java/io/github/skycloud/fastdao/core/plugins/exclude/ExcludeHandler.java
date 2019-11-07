/**
 * @(#)ExcludePlugin.java, 10月 06, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.plugins.exclude;

import io.github.skycloud.fastdao.core.mapping.ColumnMapping;
import io.github.skycloud.fastdao.core.plugins.PluggableHandler;
import io.github.skycloud.fastdao.core.reflection.MetaClass;

/**
 * @author yuntian
 */
public class ExcludeHandler implements PluggableHandler<ColumnMapping> {

    @Override
    public ColumnMapping handle(ColumnMapping pluggable, Class clazz) {
        MetaClass metaClass = MetaClass.of(clazz);
        Exclude annotation = metaClass.getMetaField(pluggable.getFieldName()).getAnnotation(Exclude.class);
        if (annotation != null) {
            return null;
        }
        return pluggable;
    }
}