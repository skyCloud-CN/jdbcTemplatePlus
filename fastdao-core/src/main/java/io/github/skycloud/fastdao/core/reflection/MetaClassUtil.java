/**
 * @(#)MetaClassUtil.java, 11月 06, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.reflection;

import com.google.common.collect.Lists;
import io.github.skycloud.fastdao.core.models.Tuple;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author yuntian
 */
public class MetaClassUtil {
    public static <A extends Annotation> List<Tuple<MetaField, A>> getAnnotatedFields(MetaClass metaClass, Class<A> annotationClass){
        List<Tuple<MetaField, A>> result= Lists.newArrayList();
        for(MetaField metaField:metaClass.metaFields()){
            A annotation=metaField.getAnnotation(annotationClass);
            if(annotation==null){
                continue;
            }
            Tuple<MetaField,A> tuple=new Tuple<>(metaField,annotation);
            result.add(tuple);
        }
        return result;
    }
}