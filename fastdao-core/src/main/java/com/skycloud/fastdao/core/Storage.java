/**
 * @(#)BaseStorage.java, 9月 26, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core;

import com.skycloud.fastdao.core.ast.request.CountRequest;
import com.skycloud.fastdao.core.ast.request.DeleteRequest;
import com.skycloud.fastdao.core.ast.request.QueryRequest;
import com.skycloud.fastdao.core.ast.request.UpdateRequest;

import java.util.Collection;
import java.util.List;

/**
 * @author yuntian
 */
public interface Storage<DATA, PRIM_KEY> {

    DATA selectByPrimaryKey(PRIM_KEY key);

    List<DATA> selectByPrimaryKeys(Collection<PRIM_KEY> keys);

    List<DATA> selectByPrimaryKeys(PRIM_KEY... keys);

    int insert(DATA t);

    int insertSelective(DATA t);

    int updateByPrimaryKey(DATA t);

    int updateByPrimaryKeySelective(DATA t);

    int deleteByPrimaryKey(PRIM_KEY t);

    List<DATA> select(QueryRequest queryRequest);

    int count(CountRequest countRequest);

    int update(UpdateRequest updateRequest);

    int delete(DeleteRequest deleteRequest);

}