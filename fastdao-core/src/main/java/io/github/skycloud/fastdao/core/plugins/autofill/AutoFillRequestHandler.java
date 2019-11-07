/**
 * @(#)AutoFillRequstHandler.java, 10月 12, 2019.
 * <p>
 */
package io.github.skycloud.fastdao.core.plugins.autofill;

import com.google.common.collect.Maps;
import io.github.skycloud.fastdao.core.ast.request.FieldUpdateRequest;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest;
import io.github.skycloud.fastdao.core.ast.request.Request;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest;
import io.github.skycloud.fastdao.core.exceptions.MetaDataException;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.models.Tuple;
import io.github.skycloud.fastdao.core.plugins.PluggableHandler;
import io.github.skycloud.fastdao.core.plugins.autofill.handler.CurrentDateAutoFillHandler;
import io.github.skycloud.fastdao.core.plugins.autofill.handler.CurrentTimestampAutoFillHandler;
import io.github.skycloud.fastdao.core.plugins.autofill.handler.UnknownAutoFillHandler;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import io.github.skycloud.fastdao.core.reflection.MetaClassUtil;
import io.github.skycloud.fastdao.core.reflection.MetaField;
import io.github.skycloud.fastdao.core.util.SingletonCache;
import org.apache.commons.beanutils.ConvertUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yuntian
 */
public class AutoFillRequestHandler<T extends FieldUpdateRequest> implements PluggableHandler<T> {

    private static String NOW = "now";

    private SingletonCache<Class, Model> cache = new SingletonCache<>(Maps.newConcurrentMap(), this::checkAnnotation);

    @Override
    public T handle(T pluggable, Class modelClass) {
        Model model = cache.get(modelClass);
        if (!model.match) {
            return pluggable;
        }
        Map<String, AutoFillHandler> handlerMap;
        if (pluggable instanceof UpdateRequest) {
            handlerMap = model.updateHandlerMap;
        } else if (pluggable instanceof InsertRequest) {
            handlerMap = model.insertHandlerMap;
        } else {
            return pluggable;
        }
        handlerMap.forEach((fieldName, handler) -> {
            if(pluggable.getUpdateFields().get(fieldName)==null) {
                pluggable.addUpdateField(fieldName, handler.handle(pluggable));
            }
        });
        return pluggable;
    }

    private Model checkAnnotation(Class modelClass) {
        Model model = new Model();
        List<Tuple<MetaField, AutoFillOnInsert>> autoFillOnInsert = MetaClassUtil.getAnnotatedFields(MetaClass.of(modelClass), AutoFillOnInsert.class);
        List<Tuple<MetaField, AutoFillOnUpdate>> autoFillOnUpdate = MetaClassUtil.getAnnotatedFields(MetaClass.of(modelClass), AutoFillOnUpdate.class);
        RowMapping rowMapping = RowMapping.of(modelClass);
        Map<String, AutoFillHandler> autoFillOnInsertMap = Maps.newHashMap();
        Map<String, AutoFillHandler> autoFillOnUpdateMap = Maps.newHashMap();
        try {
            for (Tuple<MetaField, AutoFillOnInsert> tuple : autoFillOnInsert) {
                MetaField metaField = tuple.getKey();
                AutoFillOnInsert annotation = tuple.getValue();
                AutoFillHandler handler = annotation.handler() == UnknownAutoFillHandler.class ?
                        createHandler(metaField, annotation.value()) : annotation.handler().newInstance();
                autoFillOnInsertMap.put(rowMapping.fieldName2ColumnName(tuple.getKey().getFieldName()), handler);
            }
            for (Tuple<MetaField, AutoFillOnUpdate> tuple : autoFillOnUpdate) {
                MetaField metaField = tuple.getKey();
                AutoFillOnUpdate annotation = tuple.getValue();
                AutoFillHandler handler = annotation.handler() == UnknownAutoFillHandler.class ?
                        createHandler(metaField, annotation.value()) : annotation.handler().newInstance();
                autoFillOnUpdateMap.put(rowMapping.fieldName2ColumnName(tuple.getKey().getFieldName()), handler);
            }
        } catch (Exception e) {
            throw new MetaDataException("@AutoFillOnUpdate/Insert annotation on field can't be analysis");
        }
        if (autoFillOnInsertMap.isEmpty() && autoFillOnUpdateMap.isEmpty()) {
            model.match = false;
        } else {
            model.match = true;
            model.insertHandlerMap = autoFillOnInsertMap;
            model.updateHandlerMap = autoFillOnUpdateMap;
        }
        return model;
    }

    private AutoFillHandler createHandler(MetaField field, String value) {
        if (NOW.equals(value)) {
            if (field.getFieldType() == Long.class) {
                return new CurrentTimestampAutoFillHandler();
            } else if (field.getFieldType() == Date.class) {
                return new CurrentDateAutoFillHandler();
            }
        }
        Object realValue = ConvertUtils.convert(value, field.getFieldType());
        return new AutoFillHandler() {
            @Override
            public Object handle(Request request) {
                return realValue;
            }
        };
    }

    private AutoFillHandler createHandler(AutoFill autoFill) {
        if (autoFill.fillValue() == AutoFillValueEnum.NOW) {
            return new CurrentDateAutoFillHandler();
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


    private static class Model {

        private boolean match;

        private Map<String, AutoFillHandler> updateHandlerMap;

        private Map<String, AutoFillHandler> insertHandlerMap;

    }
}