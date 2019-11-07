package io.github.skycloud.fastdao.core.plugins.logicdelete;

import com.google.common.collect.Maps;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.request.Request;
import io.github.skycloud.fastdao.core.exceptions.MetaDataException;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.models.Tuple;
import io.github.skycloud.fastdao.core.plugins.PluggableHandler;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import io.github.skycloud.fastdao.core.reflection.MetaClassUtil;
import io.github.skycloud.fastdao.core.reflection.MetaField;
import io.github.skycloud.fastdao.core.util.SingletonCache;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class LogicDeleteHandler implements PluggableHandler<Request> {

    SingletonCache<Class, Model> cache = new SingletonCache<>(Maps.newConcurrentMap(), this::checkAnnotation);

    /**
     * DeleteRequest : do not need Handle
     * UpdateRequest : if logicDelete field is not in condition, add condition of logicDelete
     * InsertRequest : if logicDelete field is not in insert field or logicDelete field=null, set logicDelete field to defaultUnDeleteValue;
     * QueryRequest : if logicDelete field is not in condition, add condition of logicDelete
     * CountRequest : if logicDelete field is not in condition, add condition of logicDelete
     */
    @Override
    public Request handle(Request pluggable, Class modelClass) {
        Model model = cache.get(modelClass);
        if (!model.match) {
            return pluggable;
        }
        LogicDeleteVisitor visitor = new LogicDeleteVisitor(model.columnName, model.undeleted);
        ((SqlAst) pluggable).accept(visitor);
        return pluggable;
    }


    private Model checkAnnotation(Class modelClass) {
        List<Tuple<MetaField, LogicDelete>> annotatedFields = MetaClassUtil.getAnnotatedFields(MetaClass.of(modelClass), LogicDelete.class);
        if (CollectionUtils.isEmpty(annotatedFields)) {
            return new Model();
        }
        if (annotatedFields.size() > 1) {
            throw new MetaDataException("@LogicDelete annotation should be unique in a class");
        }
        return buildModel(modelClass, annotatedFields.get(0).getKey(), annotatedFields.get(0).getValue());
    }

    private Model buildModel(Class modelClass, MetaField field, LogicDelete annotation) {
        Model model = new Model();
        model.match = true;
        model.columnName = RowMapping.of(modelClass).fieldName2ColumnName(field.getFieldName());
        model.deleted = ConvertUtils.convert(annotation.deleted(), field.getFieldType());
        model.undeleted = ConvertUtils.convert(annotation.undeleted(), field.getFieldType());
        return model;
    }

    private static class Model {

        private boolean match = false;

        private String columnName;

        private Object deleted;

        private Object undeleted;

    }

}
