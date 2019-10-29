/**
 * @(#)RowMapperWrapper.java, 10月 19, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.jdbctemplate.plus;

import com.google.common.collect.Maps;
import io.github.skycloud.fastdao.core.ast.model.SqlFun;
import io.github.skycloud.fastdao.core.mapping.ColumnMapping;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.mapping.TypeHandler;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import io.github.skycloud.fastdao.core.util.QueryResult;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yuntian
 */
public class RowMapperWrapper<T> {

    private Class<T> clazz;

    public RowMapperWrapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    public RowMapper<T> getMapper(Collection<Object> columns) {
        return (rs, rowNum) -> {
            try {
                T instance = clazz.newInstance();
                Collection<ColumnMapping> columnMappings;
                RowMapping rowMapping = RowMapping.of(clazz);
                if (CollectionUtils.isEmpty(columns)) {
                    columnMappings = rowMapping.getColumnMapping();
                } else {
                    columnMappings = columns.stream().filter(x->x instanceof String).map(x->(String)x).map(rowMapping::getColumnMappingByColumnName).collect(Collectors.toList());
                }
                MetaClass metaClass = MetaClass.of(clazz);
                for (ColumnMapping cm : columnMappings) {
                    TypeHandler handler = cm.getHandler();
                    Object value = handler.getResult(rs, cm.getColumnName());
                    metaClass.getMetaField(cm.getFieldName()).invokeSetter(instance,value);
                }
                return instance;
            } catch (Exception e) {
                throw new RuntimeException();
            }
        };
    }
    public RowMapper<QueryResult<T>> getMapperAdvance(Collection<Object> columns){
        return (rs, rowNum) -> {
            try {
                QueryResult<T> result=new QueryResult<>();
                T instance = clazz.newInstance();
                Collection<ColumnMapping> columnMappings;
                RowMapping rowMapping = RowMapping.of(clazz);
                if (CollectionUtils.isEmpty(columns)) {
                    columnMappings = rowMapping.getColumnMapping();
                } else {
                    columnMappings = columns.stream().filter(x->x instanceof String).map(x->(String)x).map(rowMapping::getColumnMappingByColumnName).collect(Collectors.toList());
                }
                MetaClass metaClass = MetaClass.of(clazz);
                for (ColumnMapping cm : columnMappings) {
                    TypeHandler handler = cm.getHandler();
                    Object value = handler.getResult(rs, cm.getColumnName());
                    metaClass.getMetaField(cm.getFieldName()).invokeSetter(instance,value);
                }
                List<SqlFun> functions=(List)columns.stream().filter(x->x instanceof SqlFun).collect(Collectors.toList());
                Map<String,Object> functionData= Maps.newHashMap();
                for(SqlFun function:functions){
                    functionData.put(function.genKey(),rs.getLong(function.genKey()));
                }
                result.setData(instance);
                result.setSqlFunData(functionData);
                return result;
            } catch (Exception e) {
                throw new RuntimeException();
            }
        };
    }
}