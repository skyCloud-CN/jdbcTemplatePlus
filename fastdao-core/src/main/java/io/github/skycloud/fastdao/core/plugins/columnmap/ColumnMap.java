package io.github.skycloud.fastdao.core.plugins.columnmap;

import io.github.skycloud.fastdao.core.mapping.JdbcType;
import io.github.skycloud.fastdao.core.mapping.TypeHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuntian
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface ColumnMap {

    /**
     * columnName should be defined if column name is not same with fieldName
     */
    String column() default "";

    /**
     * if a unique handler is need, define here
     */
    Class<? extends TypeHandler> handler() default TypeHandler.class;

    /**
     * if jdbcType and javaType doesn't match, define it here
     */
    JdbcType jdbcType() default JdbcType.UNDEFINED;

}
