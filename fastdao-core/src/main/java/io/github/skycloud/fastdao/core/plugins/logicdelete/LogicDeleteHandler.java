package io.github.skycloud.fastdao.core.plugins.logicdelete;

import io.github.skycloud.fastdao.core.ast.Request;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.exceptions.FastDAOException;
import io.github.skycloud.fastdao.core.mapping.ColumnMapping;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.plugins.AnnotationInfo;
import io.github.skycloud.fastdao.core.plugins.AnnotationPluggableHandler;

public class LogicDeleteHandler extends AnnotationPluggableHandler<Request, LogicDelete> {

    /**
     * DeleteRequest : do not need Handle
     * UpdateRequest : if logicDelete field is not in condition, add condition of logicDelete
     * InsertRequest : if logicDelete field is not in insert field or logicDelete field=null, set logicDelete field to defaultUnDeleteValue;
     * QueryRequest : if logicDelete field is not in condition, add condition of logicDelete
     * CountRequest : if logicDelete field is not in condition, add condition of logicDelete
     *
     * @param pluggable
     * @param clazz
     * @return
     */
    @Override
    protected Request doHandle(Request pluggable, AnnotationInfo<LogicDelete> annotationInfo, Class clazz) {
        if (annotationInfo.annotatedFieldSize() > 1) {
            throw new RuntimeException();
        }
        RowMapping rowMapping = RowMapping.of(clazz);
        annotationInfo.forEachAnnotatedField((annotation, metaField) -> {
            ColumnMapping columnMapping = rowMapping.getColumnMappingByFieldName(metaField.getFieldName());
            Object defaultValue = getDefaultUnDeleteValue(metaField.getFieldType());
            LogicDeleteVisitor logicDeleteVisitor = new LogicDeleteVisitor(columnMapping.getColumnName(), defaultValue);
            ((SqlAst) pluggable).accept(logicDeleteVisitor);
        });
        return pluggable;
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

    private static class LogicDeleteConfig {

        public Object defaultUnDeleteValue;

        public String columnName;

        public boolean exist;
    }
}
