/**
 * @(#)AbstractStorage.java, 9月 26, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.springjdbc.plus;

import com.skycloud.fastdao.core.Storage;
import com.skycloud.fastdao.core.ast.conditions.EqualCondition;
import com.skycloud.fastdao.core.ast.request.CountRequest;
import com.skycloud.fastdao.core.ast.request.DeleteRequest;
import com.skycloud.fastdao.core.ast.request.InsertRequest;
import com.skycloud.fastdao.core.ast.request.QueryRequest;
import com.skycloud.fastdao.core.ast.request.UpdateRequest;
import com.skycloud.fastdao.core.mapping.ColumnMapping;
import com.skycloud.fastdao.core.mapping.RowMapping;
import com.skycloud.fastdao.core.reflection.MetaClass;
import com.skycloud.fastdao.core.reflection.MetaField;
import javafx.util.Pair;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author yuntian
 */
public abstract class AbstractStorage<DATA, PRIM_KEY> implements Storage<DATA, PRIM_KEY> {

    private Class<DATA> dataClass;

    {
        Class clazz = this.getClass();
        while (clazz.getSuperclass() != AbstractStorage.class) {
            clazz = clazz.getSuperclass();
        }
        ParameterizedType genericAbstractStorageClass = (ParameterizedType) clazz.getGenericSuperclass();
        Type[] types = genericAbstractStorageClass.getActualTypeArguments();
        this.dataClass = (Class<DATA>) types[0];
    }

    @Override
    public DATA selectByPrimaryKey(PRIM_KEY key) {
        List<DATA> result = selectByPrimaryKeys(Collections.singletonList(key));
        if (CollectionUtils.isEmpty(result)) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public List<DATA> selectByPrimaryKeys(Collection<PRIM_KEY> keys) {
        QueryRequest request = new QueryRequest();
        RowMapping rowMapping = RowMapping.of(dataClass);
        request.setCondition(new EqualCondition(rowMapping.getPrimaryKeyColumn().getColumnName(), keys));
        List<DATA> result = JdbcTemplateSqlHelper.select(getJdbcTemplate(), request, dataClass);
        return result;
    }

    @Override
    public List<DATA> selectByPrimaryKeys(PRIM_KEY... keys) {
        QueryRequest request = new QueryRequest();
        RowMapping rowMapping = RowMapping.of(dataClass);
        request.setCondition(new EqualCondition(rowMapping.getPrimaryKeyColumn().getColumnName(), keys));
        List<DATA> result = JdbcTemplateSqlHelper.select(getJdbcTemplate(), request, dataClass);
        return result;
    }

    @Override
    public int insert(DATA t) {
        return insert(t, false);
    }

    @Override
    public int insertSelective(DATA t) {
        return insert(t, true);
    }

    private int insert(DATA t, boolean selective) {
        InsertRequest request = new InsertRequest();
        MetaClass metaClass = MetaClass.of(dataClass);
        RowMapping rowMapping = RowMapping.of(dataClass);
        for (ColumnMapping columnMapping : rowMapping.getColumnMapping()) {
            MetaField metaField = metaClass.getMetaField(columnMapping.getFieldName());
            Object value = metaField.invokeGetter(t);
            if (selective && value == null) {
                continue;
            }
            request.addInsertField(columnMapping.getColumnName(), value);

        }
        Pair<Integer, Number> result = JdbcTemplateSqlHelper.insert(getJdbcTemplate(), request, dataClass);
        if (result.getValue() != null) {
            metaClass.invokeSetter(t, rowMapping.getPrimaryKeyColumn().getFieldName(), result.getValue());
        }
        return result.getKey();
    }

    @Override
    public int updateByPrimaryKey(DATA t) {
        return updateByPrimaryKey(t, false);
    }

    @Override
    public int updateByPrimaryKeySelective(DATA t) {
        return updateByPrimaryKey(t, true);
    }

    private int updateByPrimaryKey(DATA t, boolean selective) {
        UpdateRequest request = new UpdateRequest();
        MetaClass metaClass = MetaClass.of(dataClass);
        RowMapping rowMapping = RowMapping.of(dataClass);
        ColumnMapping primaryColumn = rowMapping.getPrimaryKeyColumn();
        if (primaryColumn == null) {
            throw new RuntimeException("has not found PrimaryKey annotation");
        }
        for (ColumnMapping columnMapping : rowMapping.getColumnMapping()) {
            if (primaryColumn == columnMapping) {
                continue;
            }
            MetaField metaField = metaClass.getMetaField(columnMapping.getFieldName());
            Object value = metaField.invokeGetter(t);
            // if selective then set non-null value
            if (selective && value == null) {
                continue;
            }
            request.addUpdateField(columnMapping.getColumnName(), value);
        }

        Object primaryKeyValue = metaClass.getMetaField(primaryColumn.getColumnName()).invokeGetter(t);
        request.setCondition(new EqualCondition(primaryColumn.getColumnName(), primaryKeyValue));
        return JdbcTemplateSqlHelper.update(getJdbcTemplate(), request, dataClass);
    }

    @Override
    public int deleteByPrimaryKey(PRIM_KEY t) {
        RowMapping rowMapping = RowMapping.of(dataClass);
        ColumnMapping primaryKeyColumn = rowMapping.getPrimaryKeyColumn();
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setCondition(new EqualCondition(primaryKeyColumn.getColumnName(), t));
        deleteRequest.limit(1);
        return JdbcTemplateSqlHelper.delete(getJdbcTemplate(), deleteRequest, dataClass);
    }

    @Override
    public List<DATA> select(QueryRequest queryRequest) {
        return JdbcTemplateSqlHelper.select(getJdbcTemplate(), queryRequest, dataClass);
    }

    @Override
    public int count(CountRequest countRequest) {
        return JdbcTemplateSqlHelper.count(getJdbcTemplate(), countRequest, dataClass);
    }

    @Override
    public int update(UpdateRequest updateRequest) {
        return JdbcTemplateSqlHelper.update(getJdbcTemplate(), updateRequest, dataClass);
    }

    @Override
    public int delete(DeleteRequest deleteRequest) {
        return JdbcTemplateSqlHelper.delete(getJdbcTemplate(), deleteRequest, dataClass);
    }

    protected abstract NamedParameterJdbcOperations getJdbcTemplate();


}