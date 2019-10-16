/**
 * @(#)AutoIncColumnMapPluginTest.java, 10月 16, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.demo.dao;

import com.skycloud.fastdao.demo.model.AutoIncPluginTestModel;
import com.skycloud.fastdao.springjdbc.plus.AbstractStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;

/**
 * @author yuntian
 */
@Service
public class AutoIncColumnMapPluginDAO extends AbstractStorage<AutoIncPluginTestModel, Long> {

    @Autowired
    NamedParameterJdbcOperations db;

    @Override
    protected NamedParameterJdbcOperations getJdbcTemplate() {
        return db;
    }
}