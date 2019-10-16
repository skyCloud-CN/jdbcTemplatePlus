/**
 * @(#)MetaClass.java, 9月 27, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.reflection;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author yuntian
 */
public class MetaClass {

    private final static Map<Class, MetaClass> metaClassMap = new ConcurrentHashMap<>(16);

    private Class clazz;

    private Map<String, MetaField> map;

    private List<Annotation> annotations;

    private MethodAccess methodAccess;

    public static MetaClass of(Class clazz) {
        MetaClass metaClass = metaClassMap.get(clazz);
        if (metaClass == null) {
            metaClass = addMetaClass(clazz);
        }
        return metaClass;
    }

    private static MetaClass addMetaClass(Class clazz) {
        MetaClass metaClass = metaClassMap.get(clazz);
        if (metaClass == null) {
            metaClass = new MetaClass(clazz);
            metaClassMap.put(clazz, metaClass);
        }
        return metaClass;
    }

    private MetaClass(Class clazz) {
        this.methodAccess = MethodAccess.get(clazz);
        this.clazz = clazz;
        map = Maps.newHashMap();
        List<BeanProperty> properties = BeanPropertiesResolver.findBeanProperties(clazz);
        this.annotations = Arrays.stream(clazz.getAnnotations()).collect(Collectors.toList());
        for (BeanProperty property : properties) {
            MetaField metaField = new MetaField(this, property);
            map.put(property.getName(), metaField);
        }
    }

    MethodAccess getMethodAccess() {
        return methodAccess;
    }

    public boolean fieldExist(String fieldName) {
        return map.get(fieldName) != null;
    }

    public Object invokeGetter(Object obj, String fieldName) {
        return map.get(fieldName).invokeGetter(obj);
    }

    public void invokeSetter(Object obj, String fieldName, Object value) {
        map.get(fieldName).invokeSetter(obj, value);
    }

    public Iterable<MetaField> metaFields() {
        return map.values();
    }

    public List<Annotation> getAnnotations() {
        return annotations;
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

    public Class getJavaClass() {
        return clazz;
    }

    public MetaField getMetaField(String fieldName) {
        return map.get(fieldName);
    }
}
