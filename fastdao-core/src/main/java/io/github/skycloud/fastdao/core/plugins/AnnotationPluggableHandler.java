/**
 * @(#)AnnotationPluggableHandler.java, 10月 26, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.plugins;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import io.github.skycloud.fastdao.core.models.Tuple;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import io.github.skycloud.fastdao.core.reflection.MetaField;
import io.github.skycloud.fastdao.core.util.SingletonCache;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author yuntian
 */
public abstract class AnnotationPluggableHandler<T extends Pluggable, A extends Annotation> implements PluggableHandler<T> {

    private SingletonCache<Class, AnnotationInfo> annotationInfoCache = new SingletonCache<>(Maps.newConcurrentMap(), this::checkAnnotation);


    private Class<A> annotationClass = (Class<A>) TypeToken
            .of(this.getClass())
            .resolveType(AnnotationPluggableHandler.class.getTypeParameters()[1])
            .getRawType();

    @Override
    public T handle(T pluggable, Class modelClass) {
        AnnotationInfo annotationInfo = annotationInfoCache.get(modelClass);
        if (!annotationInfo.isAnnotated()) {
            return pluggable;
        }
        return (T) doHandle(pluggable, annotationInfo, modelClass);
    }

    protected abstract T doHandle(T pluggable, AnnotationInfo<A> annotationInfo, Class modelClass);

    private AnnotationInfo checkAnnotation(Class clazz) {
        AnnotationInfo<A> info = new AnnotationInfo<>();
        MetaClass metaClass = MetaClass.of(clazz);
        A classAnnotation = metaClass.getAnnotation(annotationClass);
        info.setClassAnnotation(classAnnotation);
        List<Tuple<A, MetaField>> fieldAnnotations = Lists.newArrayList();
        for (MetaField metaField : metaClass.metaFields()) {
            A annotation = metaField.getAnnotation(annotationClass);
            if (annotation == null) {
                continue;
            }
            Tuple<A, MetaField> tuple = new Tuple<>(annotation, metaField);
            fieldAnnotations.add(tuple);
        }
        info.setAnnotatedField(fieldAnnotations);
        return info;
    }

}