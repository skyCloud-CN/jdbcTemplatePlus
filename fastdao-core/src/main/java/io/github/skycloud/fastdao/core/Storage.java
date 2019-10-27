/**
 * @(#)BaseStorage.java, 9月 26, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core;

import io.github.skycloud.fastdao.core.ast.request.CountRequest;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest;
import io.github.skycloud.fastdao.core.util.Page;

import java.util.Collection;
import java.util.List;

/**
 * @author yuntian
 */
public interface Storage<DATA, PRIM_KEY> {

    /**
     * insert DATA to db, all field will be set include null
     * if you want to update only when none of field is null you can use this method
     */
    int insert(DATA t);

    int insert(InsertRequest insertRequest);
    /**
     * insert DATA to db, only non-null field will be insert
     * if you want null field to be default value in db ,you can use this method
     */
    int insertSelective(DATA t);

    /**
     * update by UpdateRequest
     * if you want to update only a few field, or want to update by condition, you can use this method
     */
    int update(UpdateRequest updateRequest);

    /**
     * update DATA by primaryKey, all field will be set include null
     * make sure primaryKey in DATA is set
     */
    int updateByPrimaryKey(DATA t);

    /**
     * update DATA by primaryKey, only non-null field will be updated
     */
    int updateByPrimaryKeySelective(DATA t);

    /**
     * delete by condition
     */
    int delete(DeleteRequest deleteRequest);

    /**
     * delete by primaryKey
     */
    int deleteByPrimaryKey(PRIM_KEY t);

    /**
     * count by condition
     */
    int count(CountRequest countRequest);

    /**
     * select by primaryKey
     */
    DATA selectByPrimaryKey(PRIM_KEY key);

    /**
     * select by QueryRequest
     */
    List<DATA> select(QueryRequest queryRequest);

    /**
     * multiple selection
     */
    List<DATA> selectByPrimaryKeys(Collection<PRIM_KEY> keys);

    /**
     * multiple selection
     */
    List<DATA> selectByPrimaryKeys(PRIM_KEY... keys);

    /**
     * select by a page, and count result will set to page
     */
    List<DATA> selectPage(QueryRequest request, Page page);


}