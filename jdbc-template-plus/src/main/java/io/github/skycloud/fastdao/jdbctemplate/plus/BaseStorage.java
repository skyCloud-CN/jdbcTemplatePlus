/**
 * @(#)AbstractStorage.java, 9月 26, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.jdbctemplate.plus;

import io.github.skycloud.fastdao.core.Storage;
import io.github.skycloud.fastdao.core.ast.Request;
import io.github.skycloud.fastdao.core.ast.conditions.EqualCondition.EqualConditionAst;
import io.github.skycloud.fastdao.core.ast.request.CountRequest;
import io.github.skycloud.fastdao.core.ast.request.CountRequest.CountRequestAst;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest.DeleteRequestAst;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest.InsertRequestAst;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest.QueryRequestAst;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest.UpdateRequestAst;
import io.github.skycloud.fastdao.core.mapping.ColumnMapping;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import io.github.skycloud.fastdao.core.reflection.MetaField;
import io.github.skycloud.fastdao.core.util.Page;
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
public abstract class BaseStorage<DATA, PRIM_KEY> implements Storage<DATA, PRIM_KEY> {

    private Class<DATA> dataClass;

    {
        Class clazz = this.getClass();
        while (clazz.getSuperclass() != BaseStorage.class) {
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
        return selectByPrimaryKeys((PRIM_KEY[]) keys.toArray());
    }

    @Override
    public List<DATA> selectByPrimaryKeys(PRIM_KEY... keys) {
        QueryRequestAst request = new QueryRequestAst();
        RowMapping rowMapping = RowMapping.of(dataClass);
        request.setCondition(new EqualConditionAst(rowMapping.getPrimaryKeyColumn().getColumnName(), keys));
        List<DATA> result = JdbcTemplateSqlHelper.select(getJdbcTemplate(), request, dataClass);
        return result;
    }

    @Override
    public List<DATA> selectPage(QueryRequest request, Page page) {
        QueryRequestAst queryRequest = (QueryRequestAst) request;
        CountRequestAst countRequest = new CountRequestAst();
        countRequest.setCondition(queryRequest.getCondition());
        int count = JdbcTemplateSqlHelper.count(getJdbcTemplate(), countRequest, dataClass);
        page.setTotal(count);
        return JdbcTemplateSqlHelper.select(getJdbcTemplate(), queryRequest, dataClass);
    }

    @Override
    public int insert(DATA t) {
        return insert(t, false);
    }

    @Override
    public int insert(InsertRequest insertRequest) {
        MetaClass metaClass=MetaClass.of(dataClass);
        JdbcTemplateSqlHelper.insert(getJdbcTemplate(), insertRequest,dataClass);
        Number[] result = JdbcTemplateSqlHelper.insert(getJdbcTemplate(), insertRequest, dataClass);
        return 0;
    }

    @Override
    public int insertSelective(DATA t) {
        return insert(t, true);
    }

    private int insert(DATA t, boolean selective) {
        InsertRequestAst request = new InsertRequestAst();
        MetaClass metaClass = MetaClass.of(dataClass);
        RowMapping rowMapping = RowMapping.of(dataClass);
        for (ColumnMapping columnMapping : rowMapping.getColumnMapping()) {
            MetaField metaField = metaClass.getMetaField(columnMapping.getFieldName());
            Object value = metaField.invokeGetter(t);
            if (selective && value == null) {
                continue;
            }
            request.addUpdateField(columnMapping.getColumnName(), value);

        }
        Number[] result = JdbcTemplateSqlHelper.insert(getJdbcTemplate(), request, dataClass);
        if (result[1] != null) {
            metaClass.getMetaField(rowMapping.getPrimaryKeyColumn().getFieldName())
                    .invokeSetter(t,result[1]);
        }
        return (int) result[0];
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
        UpdateRequestAst request = new UpdateRequestAst();
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
        request.setCondition(new EqualConditionAst(primaryColumn.getColumnName(), primaryKeyValue));
        return JdbcTemplateSqlHelper.update(getJdbcTemplate(), request, dataClass);
    }

    @Override
    public int deleteByPrimaryKey(PRIM_KEY t) {
        RowMapping rowMapping = RowMapping.of(dataClass);
        ColumnMapping primaryKeyColumn = rowMapping.getPrimaryKeyColumn();
        DeleteRequestAst deleteRequest = new DeleteRequestAst();
        deleteRequest.setCondition(new EqualConditionAst(primaryKeyColumn.getColumnName(), t));
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