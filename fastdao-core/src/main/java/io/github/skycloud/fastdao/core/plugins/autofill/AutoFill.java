package io.github.skycloud.fastdao.core.plugins.autofill;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface AutoFill {

    Class<? extends AutoFillHandler> handler() default AutoFillHandler.class;

    AutoFillValueEnum fillValue();

    RequestType[] onOperation();

}
