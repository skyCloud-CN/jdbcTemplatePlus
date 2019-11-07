/**
 * @(#)ColumnMapColumnMappingHandler.java, 10月 13, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.plugins.columnmap;

import io.github.skycloud.fastdao.core.mapping.ColumnMapping;
import io.github.skycloud.fastdao.core.mapping.JdbcType;
import io.github.skycloud.fastdao.core.mapping.TypeHandler;
import io.github.skycloud.fastdao.core.mapping.TypeHandlerResolver;
import io.github.skycloud.fastdao.core.plugins.PluggableHandler;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import io.github.skycloud.fastdao.core.reflection.MetaClassUtil;
import io.github.skycloud.fastdao.core.reflection.MetaField;
import org.apache.commons.lang3.StringUtils;

import java.sql.JDBCType;
import java.util.Optional;

/**
 * @author yuntian
 */
public class ColumnMapColumnMappingHandler implements PluggableHandler<ColumnMapping> {

    @Override
    public ColumnMapping handle(ColumnMapping columnMapping, Class clazz) {
        MetaField field = MetaClass.of(clazz).getMetaField(columnMapping.getFieldName());
        ColumnMap annotation = field.getAnnotation(ColumnMap.class);
        if (annotation == null) {
            return columnMapping;
        }
        if (StringUtils.isNotBlank(annotation.column())) {
            columnMapping.setColumnName(annotation.column());
        }

        if (annotation.jdbcType() != JdbcType.UNDEFINED) {
            columnMapping.setJdbcType(annotation.jdbcType());
            Optional.ofNullable(TypeHandlerResolver.getTypeHandler(columnMapping.getJavaType(), columnMapping.getJdbcType()))
                    .ifPresent(columnMapping::setHandler);
        }
        if (annotation.handler() != TypeHandler.class) {
            try {
                columnMapping.setHandler(annotation.handler().newInstance());
            } catch (Exception e) {
                throw new RuntimeException("create Handler fail");
            }
        }
        return columnMapping;
    }
}