/**
 * @(#)TestDAO.java, 10月 04, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.springjdbc.plus.dao;

import com.skycloud.fastdao.springjdbc.plus.AbstractStorage;
import com.skycloud.fastdao.springjdbc.plus.model.TestData;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

/**
 * @author yuntian
 */
public class TestDAO extends AbstractStorage<TestData,Long> {

    @Override
    protected NamedParameterJdbcOperations getJdbcTemplate() {
        return null;
    }
}