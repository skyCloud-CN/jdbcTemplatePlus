/**
 * @(#)ExcludePlugin.java, 10月 06, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins.exclude;

import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.plugins.PluggableHandler;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import io.github.skycloud.fastdao.core.reflection.MetaField;

/**
 * @author yuntian
 */
public class ExcludeHandler implements PluggableHandler<RowMapping> {

    @Override
    public RowMapping handle(RowMapping rowMapping, Class clazz) {
        MetaClass metaClass = MetaClass.of(clazz);
        for (MetaField field : metaClass.metaFields()) {
            Exclude annotation = field.getAnnotation(Exclude.class);
            if (annotation != null) {
                rowMapping.getFieldNameMap().remove(field.getFieldName());
            }
        }
        return rowMapping;
    }

}