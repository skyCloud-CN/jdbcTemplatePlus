/**
 * @(#)AutoFillRequestHandler.java, 10月 24, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins.autofill;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.skycloud.fastdao.core.ast.Request;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest.DefaultInsertRequest;
import io.github.skycloud.fastdao.core.mapping.ColumnMapping;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.plugins.Pluggable;
import io.github.skycloud.fastdao.core.plugins.PluggableHandler;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import io.github.skycloud.fastdao.core.reflection.MetaField;
import io.github.skycloud.fastdao.core.util.SingletonCache;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yuntian
 */
public abstract class AutoFillRequestHandler<T extends Request> implements PluggableHandler<T>{
    protected Map<Class, Map<String, AutoFillHandler>> autoFillHandlerMap = new SingletonCache<>(Maps.newConcurrentMap(),this::fillAutoFillHandlerMap);

    protected AutoFillOperation handleOnOperation;

    public AutoFillRequestHandler(AutoFillOperation handleOnOperation){
        this.handleOnOperation=handleOnOperation;
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
                fieldAutoFillHandlerMap.put(columnMapping.getColumnName(), handler);
            }
        }
        return fieldAutoFillHandlerMap;
    }

    private AutoFillHandler createHandler(AutoFill autoFill) {
        boolean match = false;
        for (AutoFillOperation operation : autoFill.onOperation()) {
            if (operation == handleOnOperation) {
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