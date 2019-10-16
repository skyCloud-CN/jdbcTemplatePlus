/**
 * @(#)TestData.java, 10月 04, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.springjdbc.plus.model;

import com.skycloud.fastdao.core.plugins.columnmap.ColumnMap;
import com.skycloud.fastdao.core.annotation.Table;
import com.skycloud.fastdao.core.mapping.handlers.StringTypeHandler;
import com.skycloud.fastdao.core.plugins.exclude.Exclude;

import java.sql.JDBCType;
import java.util.Date;

/**
 * @author yuntian
 */
@Table(tableName = "test_table")
public class TestData extends IdEntity implements Nameable {

    @ColumnMap(column = "testName", jdbcType = JDBCType.ARRAY, handler = StringTypeHandler.class)
    private String name;

    private Date created;

    private Boolean check;

    @Exclude
    private String needExclude;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getNeedExclude() {
        return needExclude;
    }

    public void setNeedExclude(String needExclude) {
        this.needExclude = needExclude;
    }
}