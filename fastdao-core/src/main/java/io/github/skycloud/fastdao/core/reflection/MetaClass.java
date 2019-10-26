/**
 * @(#)MetaClass.java, 9月 27, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.reflection;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.common.collect.Maps;
import io.github.skycloud.fastdao.core.util.SingletonCache;
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

    private final static Map<Class, MetaClass> metaClassMap = new SingletonCache<>(new ConcurrentHashMap<>(), MetaClass::newInstance);

    private Class clazz;

    private Map<String, MetaField> map;

    private List<Annotation> annotations;

    private MethodAccess methodAccess;

    public static MetaClass of(Class clazz) {
        return metaClassMap.get(clazz);
    }

    private static MetaClass newInstance(Class clazz) {
        return new MetaClass(clazz);
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

    public Iterable<MetaField> metaFields() {
        return map.values();
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
