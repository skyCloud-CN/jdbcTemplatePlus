/**
 * @(#)AutoFillRequstHandler.java, 10月 12, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.plugins.autofill;

import io.github.skycloud.fastdao.core.ast.request.FieldUpdateRequest;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.plugins.AnnotationInfo;
import io.github.skycloud.fastdao.core.plugins.AnnotationPluggableHandler;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author yuntian
 */
public class AutoFillRequestHandler<T extends FieldUpdateRequest> extends AnnotationPluggableHandler<T, AutoFill> {

    @Override
    protected T doHandle(T pluggable, AnnotationInfo<AutoFill> annotationInfo, Class modelClass) {
        RowMapping rowMapping = RowMapping.of(modelClass);
        annotationInfo.forEachAnnotatedField((autoFill, metaField) -> {
            if (match(pluggable, autoFill)) {
                String columnName = rowMapping.getColumnMappingByFieldName(metaField.getFieldName()).getColumnName();
                AutoFillHandler handler = createHandler(autoFill);
                pluggable.addUpdateField(columnName, handler.handle(pluggable));
            }
        });
        return pluggable;
    }

    private boolean match(T request, AutoFill autoFill) {
        if (request instanceof UpdateRequest) {
            return ArrayUtils.contains(autoFill.onOperation(), RequestType.UPDATE);
        } else if (request instanceof InsertRequest) {
            return ArrayUtils.contains(autoFill.onOperation(), RequestType.INSERT);
        } else {
            return false;
        }
    }

    private AutoFillHandler createHandler(AutoFill autoFill) {
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