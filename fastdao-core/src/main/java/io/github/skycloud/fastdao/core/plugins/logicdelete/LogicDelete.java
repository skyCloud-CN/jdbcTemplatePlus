package io.github.skycloud.fastdao.core.plugins.logicdelete;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface LogicDelete {
    /**
     * deleted default value is 1 or true, depends on LogicDelete field class
     */
    String deleted() default "1";
    /**
     * undeleted default value is 0 or false, depends on LogicDelete field class
     */
    String undeleted() default "0";

}
