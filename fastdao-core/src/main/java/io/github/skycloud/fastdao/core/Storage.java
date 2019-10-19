/**
 * @(#)BaseStorage.java, 9月 26, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core;

import io.github.skycloud.fastdao.core.ast.request.CountRequest;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest;

import java.util.Collection;
import java.util.List;

/**
 * @author yuntian
 */
public interface Storage<DATA, PRIM_KEY> {

    /**
     * select by primary key
     *
     * @param key
     * @return if data not exist , return null;
     */
    DATA selectByPrimaryKey(PRIM_KEY key);

    /**
     * select by primary keys
     *
     * @param keys
     * @return if data not exist, return emptyList
     */
    List<DATA> selectByPrimaryKeys(Collection<PRIM_KEY> keys);

    /**
     * select by primary keys
     *
     * @param keys
     * @return if data not exist, return emptyList
     */
    List<DATA> selectByPrimaryKeys(PRIM_KEY... keys);

    /**
     * insert data to database, all fields will be updated, field = null will be updated as NULL
     *
     * @param t
     * @return
     */
    int insert(DATA t);

    /**
     * insert data to database, only fields non null in DATA will be updated
     * @param t
     * @return
     */
    int insertSelective(DATA t);

    /**
     * update data to database, all fields will be updated, field = null will be updated as NULL
     * @param t
     * @return
     */
    int updateByPrimaryKey(DATA t);
    /**
     * update data to database, only fields not null will be updated
     * @param t
     * @return
     */
    int updateByPrimaryKeySelective(DATA t);

    /**
     * delete data from database by primary key
     * @param t
     * @return
     */
    int deleteByPrimaryKey(PRIM_KEY t);

    /**
     * Dynamic query support Method
     * @param queryRequest
     * @return
     */
    List<DATA> select(QueryRequest queryRequest);

    /**
     * Dynamic count support Method
     * @param countRequest
     * @return
     */
    int count(CountRequest countRequest);

    /**
     * Dynamic update support Method
     * @param updateRequest
     * @return
     */
    int update(UpdateRequest updateRequest);

    /**
     * Dynamic delete support Method
     * @param deleteRequest
     * @return
     */
    int delete(DeleteRequest deleteRequest);

}