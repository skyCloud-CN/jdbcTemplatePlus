/**
 * @(#)AbstractStorage.java, 9月 26, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.jdbctemplate.plus;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import io.github.skycloud.fastdao.core.Storage;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.request.FieldUpdateRequest;
import io.github.skycloud.fastdao.core.ast.conditions.EqualConditionAst;
import io.github.skycloud.fastdao.core.ast.request.CountRequest;
import io.github.skycloud.fastdao.core.ast.request.CountRequestAst;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequestAst;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest;
import io.github.skycloud.fastdao.core.ast.request.InsertRequestAst;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequestAst;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequestAst;
import io.github.skycloud.fastdao.core.exceptions.IllegalRequestException;
import io.github.skycloud.fastdao.core.mapping.ColumnMapping;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import io.github.skycloud.fastdao.core.reflection.MetaField;
import io.github.skycloud.fastdao.core.models.Page;
import io.github.skycloud.fastdao.core.models.QueryResult;
import io.github.skycloud.fastdao.core.models.Tuple;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author yuntian
 */
public abstract class BaseStorage<DATA, PRIM_KEY> implements Storage<DATA, PRIM_KEY>, InitializingBean {

    private Class<DATA> dataClass;

    private MetaClass metaClass;

    private RowMapping rowMapping;

    @Override
    public void afterPropertiesSet() {
        dataClass = (Class<DATA>) TypeToken.of(this.getClass()).resolveType(BaseStorage.class.getTypeParameters()[0]).getRawType();
        metaClass = MetaClass.of(dataClass);
        rowMapping = RowMapping.of(dataClass);
    }

    @Override
    public DATA selectByPrimaryKey(PRIM_KEY primaryKey) {
        QueryRequestAst request = new QueryRequestAst().notReuse();
        request.setCondition(new EqualConditionAst(rowMapping.getPrimaryKeyColumn().getColumnName(), primaryKey));
        return JdbcTemplateSqlHelper.queryOne(getJdbcTemplate(), request, dataClass);
    }

    @Override
    public List<DATA> selectByPrimaryKeys(Collection<PRIM_KEY> primaryKeys) {
        QueryRequestAst request = new QueryRequestAst().notReuse();
        request.setCondition(new EqualConditionAst(rowMapping.getPrimaryKeyColumn().getColumnName(), primaryKeys));
        return JdbcTemplateSqlHelper.query(getJdbcTemplate(), request, dataClass);
    }

    @SafeVarargs
    @Override
    public final List<DATA> selectByPrimaryKeys(PRIM_KEY... primaryKeys) {
        return selectByPrimaryKeys(Lists.newArrayList(primaryKeys));
    }

    @Override
    public List<DATA> selectPage(QueryRequest request, Page page) {
        QueryRequest requestCopy=(QueryRequest)((SqlAst)request).copy();
        requestCopy.limit(page.getLimit()).offset(page.getOffset()).notReuse();
        CountRequest countRequest = new CountRequestAst().setCondition(request.getCondition());
        int count = JdbcTemplateSqlHelper.count(getJdbcTemplate(), countRequest, dataClass);
        page.setTotal(count);
        return JdbcTemplateSqlHelper.query(getJdbcTemplate(), requestCopy, dataClass);
    }

    @Override
    public DATA selectOne(QueryRequest queryRequest) {
        List<DATA> data = select(queryRequest);
        return data.stream().findFirst().orElse(null);
    }

    @Override
    public <T> List<T> selectSingleField(QueryRequest queryRequest, Class<T> clazz) {
        if (((QueryRequestAst) queryRequest).getSelectFields().size() != 1) {
            throw new IllegalRequestException("select field not defined");
        }
        return JdbcTemplateSqlHelper.querySingleField(getJdbcTemplate(), queryRequest, dataClass,clazz);
    }

    @Override
    public List<QueryResult<DATA>> selectAdvance(QueryRequest queryRequest) {
        return JdbcTemplateSqlHelper.selectAdvance(getJdbcTemplate(), queryRequest, dataClass);
    }

    @Override
    public int insert(DATA model) {
        return insert(model, false);
    }

    @Override
    public int insertSelective(DATA model) {
        return insert(model, true);
    }


    @Override
    public int insert(InsertRequest insertRequest, Consumer<Number> doWithGeneratedKey) {
        Tuple<Integer, Number> result = JdbcTemplateSqlHelper.insert(getJdbcTemplate(), insertRequest, dataClass);
        if (result.getKey() > 0 && result.getValue() != null) {
            doWithGeneratedKey.accept(result.getValue());
        }
        return result.getKey();
    }

    @Override
    public int updateByPrimaryKey(DATA model) {
        return updateByPrimaryKey(model, false);
    }

    @Override
    public int updateByPrimaryKeySelective(DATA model) {
        return updateByPrimaryKey(model, true);
    }

    @Override
    public int deleteByPrimaryKey(PRIM_KEY primaryKey) {
        ColumnMapping primaryKeyColumn = rowMapping.getPrimaryKeyColumn();
        DeleteRequestAst request = new DeleteRequestAst().notReuse();
        request.setCondition(new EqualConditionAst(primaryKeyColumn.getColumnName(), primaryKey));
        request.limit(1);
        return JdbcTemplateSqlHelper.delete(getJdbcTemplate(), request, dataClass);
    }

    @Override
    public List<DATA> select(QueryRequest queryRequest) {
        return JdbcTemplateSqlHelper.query(getJdbcTemplate(), queryRequest, dataClass);
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

    private int insert(DATA t, boolean selective) {
        InsertRequestAst request = new InsertRequestAst().notReuse();
        fillFieldUpdateRequest(t, request, true, selective);
        return insert(request, (key) -> {
            metaClass.getMetaField(rowMapping.getPrimaryKeyColumn().getFieldName())
                    .invokeSetter(t, key);
        });
    }

    private int updateByPrimaryKey(DATA data, boolean selective) {
        UpdateRequestAst request = new UpdateRequestAst().notReuse();
        ColumnMapping primaryColumn = rowMapping.getPrimaryKeyColumn();
        fillFieldUpdateRequest(data, request, false, selective);
        Object primaryKeyValue = metaClass.getMetaField(primaryColumn.getFieldName()).invokeGetter(data);
        request.setCondition(new EqualConditionAst(primaryColumn.getColumnName(), primaryKeyValue));
        return JdbcTemplateSqlHelper.update(getJdbcTemplate(), request, dataClass);
    }

    private void fillFieldUpdateRequest(DATA source, FieldUpdateRequest request, boolean fillPrimary, boolean selective) {
        for (ColumnMapping columnMapping : rowMapping.getColumnMapping()) {
            if (columnMapping.isPrimary() && !fillPrimary) {
                continue;
            }
            MetaField metaField = metaClass.getMetaField(columnMapping.getFieldName());
            Object value = metaField.invokeGetter(source);
            if (selective && value == null) {
                continue;
            }
            request.addUpdateField(columnMapping.getColumnName(), value);
        }
    }

}