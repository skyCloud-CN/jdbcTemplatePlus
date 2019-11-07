/**
 * @(#)AnnotationInfo.java, 10月 26, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.plugins;

import io.github.skycloud.fastdao.core.models.Tuple;
import io.github.skycloud.fastdao.core.reflection.MetaField;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author yuntian
 */
@Getter
public class AnnotationInfo<T extends Annotation> {

    private boolean annotated;

    private List<Tuple<T, MetaField>> annotatedField;

    private Annotation classAnnotation;

    public AnnotationInfo setAnnotatedField(List<Tuple<T, MetaField>> annotatedField) {
        if (CollectionUtils.isNotEmpty(annotatedField)) {
            annotated = true;
        }
        this.annotatedField = annotatedField;
        return this;
    }

    public AnnotationInfo setClassAnnotation(T classAnnotation) {
        if (classAnnotation != null) {
            this.classAnnotation = classAnnotation;
            annotated = true;
        }
        return this;
    }

    public void forEachAnnotatedField(BiConsumer<T, MetaField> biConsumer) {
        if (CollectionUtils.isEmpty(annotatedField)) {
            return;
        }
        for (Tuple<T, MetaField> tuple : annotatedField) {
            biConsumer.accept(tuple.getKey(), tuple.getValue());
        }
    }

    public int annotatedFieldSize() {
        return CollectionUtils.size(annotatedField);
    }
}