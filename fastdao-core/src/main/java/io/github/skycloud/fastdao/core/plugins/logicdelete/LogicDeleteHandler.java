package io.github.skycloud.fastdao.core.plugins.logicdelete;

import com.google.common.collect.Maps;
import io.github.skycloud.fastdao.core.ast.Request;
import io.github.skycloud.fastdao.core.exceptions.FastDAOException;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.plugins.PluggableHandler;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import io.github.skycloud.fastdao.core.reflection.MetaField;
import io.github.skycloud.fastdao.core.util.SingletonCache;

import java.lang.annotation.Annotation;

public class LogicDeleteHandler implements PluggableHandler<Request> {
    private SingletonCache<Class, LogicDeleteConfig> logicDeleteCache = new SingletonCache<>(Maps.newConcurrentMap(), this::getLogicDeleteConfig);

    @Override
    public Request handle(Request pluggable, Class clazz) {
        LogicDeleteConfig config= logicDeleteCache.get(clazz);
        if(config.exist){

        }
    }

    private LogicDeleteConfig getLogicDeleteConfig(Class clazz) {
        RowMapping rowMapping = RowMapping.of(clazz);
        MetaClass metaClass = MetaClass.of(clazz);
        LogicDeleteConfig config=new LogicDeleteConfig();
        for (MetaField metaField : metaClass.metaFields()) {
            Annotation annotation = metaField.getAnnotation(LogicDelete.class);
            if (annotation != null) {
                config.columnName=rowMapping.getColumnMappingByColumnName(metaField.getFieldName()).getColumnName();
                config.defaultUnDeleteValue=rowMapping.getColumnMappingByFieldName(metaField.getFieldName()).getHandler().parseParam(getDefaultUnDeleteValue(metaField.getFieldType()));
                config.exist=true;
            }
        }
        return config;
    }

    private Object getDefaultUnDeleteValue(Class clazz) {
        if (clazz == Boolean.class || clazz == boolean.class) {
            return false;
        } else if (clazz == Long.class || clazz == long.class || clazz == Integer.class || clazz == int.class) {
            return 0;
        } else {
            throw new FastDAOException("can't define LogicDelete default value");
        }
    }
    private static class LogicDeleteConfig{
        public Object defaultUnDeleteValue;
        public String columnName;
        public boolean exist;
    }
}
