/**
 * @(#)AutoFillRequstHandler.java, 10月 12, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins.autofill;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest.DefaultUpdateRequest;
import io.github.skycloud.fastdao.core.mapping.ColumnMapping;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.plugins.PluggableHandler;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import io.github.skycloud.fastdao.core.reflection.MetaField;
import io.github.skycloud.fastdao.core.util.SingletonCache;

import java.util.Map;
import java.util.Set;

/**
 * @author yuntian
 */
public class AutoFillUpdateRequestHandler implements PluggableHandler<DefaultUpdateRequest> {

    private Map<Class, Map<String, AutoFillHandler>> autoFillHandlerMap = new SingletonCache<>(Maps.newConcurrentMap(),this::fillAutoFillHandlerMap);

    @Override
    public DefaultUpdateRequest handle(DefaultUpdateRequest pluggable, Class clazz) {
        Map<String, AutoFillHandler> fieldAutoFillHandlerMap = autoFillHandlerMap.get(clazz);
        Set<String> prepareUpdate = pluggable.getUpdateFields().keySet();
        Set<String> needUpdateFields = Sets.newHashSet(fieldAutoFillHandlerMap.keySet());
        needUpdateFields.removeAll(prepareUpdate);
        for (String needUpdateField : needUpdateFields) {
            AutoFillHandler handler = fieldAutoFillHandlerMap.get(needUpdateField);
            pluggable.addUpdateField(needUpdateField, handler.handle(pluggable));
        }
        return pluggable;
    }

    private Map<String, AutoFillHandler> fillAutoFillHandlerMap(Class clazz) {
        MetaClass metaClass = MetaClass.of(clazz);
        RowMapping rowMapping = RowMapping.of(clazz);
        Map<String, AutoFillHandler> fieldAutoFillHandlerMap = Maps.newHashMap();
        for (MetaField metaField : metaClass.metaFields()) {
            AutoFill autoFill = metaField.getAnnotation(AutoFill.class);
            if (autoFill == null) {
                continue;
            }
            ColumnMapping columnMapping = rowMapping.getColumnMappingByFieldName(metaField.getFieldName());
            if (columnMapping == null) {
                continue;
            }
            AutoFillHandler handler=createHandler(autoFill);
            if(handler!=null) {
                fieldAutoFillHandlerMap.put(columnMapping.getColumnName(), createHandler(autoFill));
            }
        }
        return fieldAutoFillHandlerMap;
    }

    private AutoFillHandler createHandler(AutoFill autoFill) {
        boolean match = false;
        for (AutoFillOperation operation : autoFill.onOperation()) {
            if (operation == AutoFillOperation.UPDATE) {
                match = true;
            }
        }
        if (!match) {
            return null;
        }
        if (autoFill.fillValue() == AutoFillValueEnum.NOW) {
            return new NowDateAutoFillHandler();
        }
        if (autoFill.handler() != AutoFillHandler.class) {
            try {
                return autoFill.handler().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("create autoFill handler fail");
            }
        }
        throw new RuntimeException("autoFill annotation exception");
    }
}