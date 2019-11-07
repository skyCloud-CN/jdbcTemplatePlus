/**
 * @(#)BeanProperty.java, 10月 11, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.reflection;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author yuntian
 */
class BeanProperty {

    private String name;

    private Class type;

    private String getterName;

    private String setterName;

    private List<Annotation> annotations;

    public String getName() {
        return name;
    }

    public BeanProperty setName(String propName) {
        this.name = propName;
        return this;
    }

    public Class getType() {
        return type;
    }

    public BeanProperty setType(Class type) {
        this.type = type;
        return this;
    }

    String getGetterName() {
        return getterName;
    }

    BeanProperty setGetterName(String getterName) {
        this.getterName = getterName;
        return this;
    }

    String getSetterName() {
        return setterName;
    }

    BeanProperty setSetterName(String setterName) {
        this.setterName = setterName;
        return this;
    }

    List<Annotation> getAnnotations() {
        return annotations;
    }

    BeanProperty setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
        return this;
    }
}