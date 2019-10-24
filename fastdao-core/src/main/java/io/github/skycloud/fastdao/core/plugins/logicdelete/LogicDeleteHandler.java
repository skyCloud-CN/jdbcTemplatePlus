package io.github.skycloud.fastdao.core.plugins.logicdelete;

import com.google.common.collect.Maps;
import io.github.skycloud.fastdao.core.ast.Condition;
import io.github.skycloud.fastdao.core.ast.ConditionalRequest;
import io.github.skycloud.fastdao.core.ast.Request;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.exceptions.FastDAOException;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.plugins.PluggableHandler;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import io.github.skycloud.fastdao.core.reflection.MetaField;
import io.github.skycloud.fastdao.core.util.SingletonCache;

import java.lang.annotation.Annotation;

public class LogicDeleteHandler implements PluggableHandler<Request> {
    private SingletonCache<Class, LogicDeleteConfig> logicDeleteCache = new SingletonCache<>(Maps.newConcurrentMap(), this::getLogicDeleteConfig);

    /**
     * DeleteRequest : do not need Handle
     * UpdateRequest : if logicDelete field is not in condition, add condition of logicDelete
     * InsertRequest : if logicDelete field is not in insert field or logicDelete field=null, set logicDelete field to defaultUnDeleteValue;
     * QueryRequest : if logicDelete field is not in condition, add condition of logicDelete
     * CountRequest : if logicDelete field is not in condition, add condition of logicDelete
     * @param pluggable
     * @param clazz
     * @return
     */
    @Override
    public Request handle(Request pluggable, Class clazz) {
        LogicDeleteConfig config= logicDeleteCache.get(clazz);
        if(!config.exist){
            return pluggable;
        }
        LogicDeleteVisitor logicDeleteVisitor=new LogicDeleteVisitor(config.columnName,config.defaultUnDeleteValue);
        ((SqlAst)pluggable).accept(logicDeleteVisitor);
        return pluggable;
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
