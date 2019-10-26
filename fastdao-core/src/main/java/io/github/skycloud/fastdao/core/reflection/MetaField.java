/**
 * @(#)MetaField.java, 9月 27, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.reflection;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author yuntian
 */

public class MetaField {

    private String fieldName;

    private int getterIndex;

    private int setterIndex;

    private Class<?> fieldType;

    private MethodAccess methodAccess;

    private List<Annotation> annotations;

    MetaField(MetaClass metaClass, BeanProperty beanProperty) {
        this.methodAccess = metaClass.getMethodAccess();
        this.fieldType = beanProperty.getType();
        this.fieldName = beanProperty.getName();
        this.annotations = beanProperty.getAnnotations();
        this.getterIndex = methodAccess.getIndex(beanProperty.getGetterName());
        this.setterIndex = methodAccess.getIndex(beanProperty.getSetterName());
    }


    public <T extends Annotation> T getAnnotation(Class<T> clazz) {
        if (CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() != clazz) {
                continue;
            }
            return (T) annotation;
        }
        return null;
    }

    public Object invokeGetter(Object obj) {
        return methodAccess.invoke(obj, getterIndex);
    }

    public void invokeSetter(Object obj, Object value) {
        methodAccess.invoke(obj, setterIndex, value);
    }

    public String getFieldName() {
        return fieldName;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }
    ;
}