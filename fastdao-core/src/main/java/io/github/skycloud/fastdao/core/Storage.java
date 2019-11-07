/**
 * @(#)BaseStorage.java, 9月 26, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core;

import io.github.skycloud.fastdao.core.ast.request.CountRequest;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest;
import io.github.skycloud.fastdao.core.models.Page;
import io.github.skycloud.fastdao.core.models.QueryResult;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author yuntian
 */
public interface Storage<DATA, PRIM_KEY> {

    /**
     * insert DATA to db, all field will be set include null
     * if you want to update only when none of field is null you can use this method
     */
    int insert(DATA model);

    /**
     * insert DATA to db, only non-null field will be insert
     * if you want null field to be default value in db ,you can use this method
     */
    int insertSelective(DATA model);

    int insert(InsertRequest insertRequest, Consumer<Number> doWithGeneratedKeyOnSuccess);

    /**
     * update by UpdateRequest
     * if you want to update only a few field, or want to update by condition, you can use this method
     */
    int update(UpdateRequest updateRequest);

    /**
     * update DATA by primaryKey, all field will be set include null
     * make sure primaryKey in DATA is set
     */
    int updateByPrimaryKey(DATA model);

    /**
     * update DATA by primaryKey, only non-null field will be updated
     */
    int updateByPrimaryKeySelective(DATA model);

    /**
     * delete by condition
     */
    int delete(DeleteRequest deleteRequest);

    /**
     * delete by primaryKey
     */
    int deleteByPrimaryKey(PRIM_KEY primaryKey);

    /**
     * count by condition
     */
    int count(CountRequest countRequest);

    /**
     * select by primaryKey
     */
    DATA selectByPrimaryKey(PRIM_KEY primaryKey);


    /**
     * select by QueryRequest
     * this method doesn't support extra function
     */
    List<DATA> select(QueryRequest queryRequest);

    /**
     * select by QueryRequest
     * if has multiple result, only return first row
     */
    DATA selectOne(QueryRequest queryRequest);

    /**
     * select by
     */
    <T> List<T> selectSingleField(QueryRequest queryRequest, Class<T> clazz);

    /**
     * support SqlFunction as request field
     */
    List<QueryResult<DATA>> selectAdvance(QueryRequest queryRequest);

    /**
     * multiple selection
     */
    List<DATA> selectByPrimaryKeys(Collection<PRIM_KEY> primaryKeys);

    /**
     * multiple selection
     */
    List<DATA> selectByPrimaryKeys(PRIM_KEY... primaryKeys);

    /**
     * select by a page, and count result will set to page
     */
    List<DATA> selectPage(QueryRequest request, Page page);


}