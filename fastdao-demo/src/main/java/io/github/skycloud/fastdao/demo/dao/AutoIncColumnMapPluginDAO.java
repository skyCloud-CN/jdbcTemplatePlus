/**
 * @(#)AutoIncColumnMapPluginTest.java, 10月 16, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.demo.dao;

import io.github.skycloud.fastdao.demo.model.AutoIncPluginTestModel;
import io.github.skycloud.fastdao.jdbctemplate.plus.BaseStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;

/**
 * @author yuntian
 */
@Service
public class AutoIncColumnMapPluginDAO extends BaseStorage<AutoIncPluginTestModel, Long> {

    @Autowired
    NamedParameterJdbcOperations db;

    @Override
    protected NamedParameterJdbcOperations getJdbcTemplate() {
        return db;
    }
}