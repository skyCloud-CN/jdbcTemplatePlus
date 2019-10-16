/**
 * @(#)AutoFillRequstHandler.java, 10月 12, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.plugins.autofill;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.skycloud.fastdao.core.ast.request.UpdateRequest;
import com.skycloud.fastdao.core.mapping.ColumnMapping;
import com.skycloud.fastdao.core.mapping.RowMapping;
import com.skycloud.fastdao.core.plugins.PluggableHandler;
import com.skycloud.fastdao.core.reflection.MetaClass;
import com.skycloud.fastdao.core.reflection.MetaField;

import java.util.Map;
import java.util.Set;

/**
 * @author yuntian
 */
public class AutoFillUpdateRequestHandler implements PluggableHandler<UpdateRequest> {

    private Map<Class, Map<String, AutoFillHandler>> autoFillHandlerMap = Maps.newHashMap();

    @Override
    public UpdateRequest handle(UpdateRequest pluggable, Class clazz) {
        Map<String, AutoFillHandler> fieldAutoFillHandlerMap = autoFillHandlerMap.get(clazz);
        if (fieldAutoFillHandlerMap == null) {
            fieldAutoFillHandlerMap = fillAutoFillHandlerMap(clazz);
        }
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
            fieldAutoFillHandlerMap.put(columnMapping.getColumnName(), createHandler(autoFill));
        }
        autoFillHandlerMap.put(clazz, fieldAutoFillHandlerMap);
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