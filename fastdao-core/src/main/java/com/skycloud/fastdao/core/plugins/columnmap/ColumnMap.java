package com.skycloud.fastdao.core.plugins.columnmap;

import com.skycloud.fastdao.core.mapping.TypeHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.JDBCType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnMap {

    String column() default "";

    Class<? extends TypeHandler> handler() default TypeHandler.class;

    JDBCType jdbcType() default JDBCType.NULL;

}
