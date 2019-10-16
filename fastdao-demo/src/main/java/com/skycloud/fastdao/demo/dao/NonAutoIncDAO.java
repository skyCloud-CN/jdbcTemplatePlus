/**
 * @(#)NonAutoIncDAO.java, 10月 13, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.demo.dao;

import com.skycloud.fastdao.demo.model.NonAutoIncModel;
import com.skycloud.fastdao.springjdbc.plus.AbstractStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;

/**
 * @author yuntian
 */
@Service
public class NonAutoIncDAO extends AbstractStorage<NonAutoIncModel, Long> {

    @Autowired
    NamedParameterJdbcOperations db;

    @Override
    protected NamedParameterJdbcOperations getJdbcTemplate() {
        return db;
    }
}