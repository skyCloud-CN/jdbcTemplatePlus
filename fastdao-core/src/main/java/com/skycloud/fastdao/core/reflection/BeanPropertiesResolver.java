/**
 * @(#)IntrospectorWrapper.java, 9月 28, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.reflection;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.skycloud.fastdao.core.exceptions.MetaDataException;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Locale.ENGLISH;

/**
 * @author yuntian
 */

class BeanPropertiesResolver {

    private static final String GET_PREFIX = "get";

    private static final String IS_PREFIX = "is";

    private static final String SET_PREFIX = "set";

    static List<BeanProperty> findBeanProperties(Class clazz) {
        try {
            List<BeanProperty> result = Lists.newArrayList();
            Map<String, MethodDescriptor> methods = Maps.newHashMap();
            Map<String, Field> fieldMap = getFields(clazz).stream()
                    .collect(Collectors.toMap(Field::getName, Function.identity()));
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            for (MethodDescriptor md : beanInfo.getMethodDescriptors()) {
                methods.put(md.getName(), md);
            }
            Arrays.stream(beanInfo.getPropertyDescriptors()).forEach(pd -> {
                String name = pd.getName();
                String getter = findGetterName(pd, methods);
                String setter = findSetterName(pd, methods);
                if (getter == null || setter == null) {
                    return;
                }
                Field field = fieldMap.get(name);
                BeanProperty beanProperty = new BeanProperty();
                beanProperty.setName(name)
                        .setSetterName(setter)
                        .setGetterName(getter)
                        .setType(pd.getPropertyType())
                        .setAnnotations(getAnnotations(field));
                result.add(beanProperty);
            });
            return result;
        } catch (Exception e) {
            throw new MetaDataException("BeanInfo create fail");
        }
    }

    private static List<Annotation> getAnnotations(Field field) {
        if (field == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(field.getAnnotations()).collect(Collectors.toList());
    }

    private static String findGetterName(PropertyDescriptor pd, Map<String, MethodDescriptor> methods) {
        Method getter = pd.getReadMethod();
        return getter != null ? getter.getName() : null;
    }

    private static String findSetterName(PropertyDescriptor pd, Map<String, MethodDescriptor> methods) {
        Method setter = pd.getWriteMethod();
        if (setter != null) {
            return setter.getName();
        }
        MethodDescriptor md = methods.get(getSetterName(pd.getName()));
        if (md == null || md.getMethod().getParameterTypes().length != 1 || md.getMethod().getParameterTypes()[0] != pd.getPropertyType()) {
            return null;
        }
        return md.getName();
    }

    private static List<Field> getFields(Class clazz) {
        List<Field> result = Lists.newArrayList();
        while (clazz != null && clazz != Object.class) {
            result.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    private static String getSetterName(String propName) {
        return SET_PREFIX + getBaseName(propName);
    }

    private static String getGetterName(String propName) {
        return GET_PREFIX + getBaseName(propName);
    }

    private static String getBooleanGetterName(String propName) {
        return IS_PREFIX + getBaseName(propName);
    }

    private static String getBaseName(String propName) {
        return propName.substring(0, 1).toUpperCase(ENGLISH) + propName.substring(1);
    }
}