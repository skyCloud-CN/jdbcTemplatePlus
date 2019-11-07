
package io.github.skycloud.fastdao.core.plugins.autofill;

import io.github.skycloud.fastdao.core.plugins.autofill.handler.UnknownAutoFillHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuntian
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoFillOnUpdate {

    String value() default "";

    Class<? extends AutoFillHandler> handler() default UnknownAutoFillHandler.class;
}