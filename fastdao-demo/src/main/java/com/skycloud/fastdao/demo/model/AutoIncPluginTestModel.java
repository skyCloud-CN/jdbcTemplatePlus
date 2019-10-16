/**
 * @(#)AutoIncPluginTestModel.java, 10月 16, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.demo.model;

import com.skycloud.fastdao.core.annotation.PrimaryKey;
import com.skycloud.fastdao.core.annotation.Table;
import com.skycloud.fastdao.core.plugins.columnmap.ColumnMap;
import com.skycloud.fastdao.core.plugins.exclude.Exclude;
import lombok.Data;
import lombok.ToString;

import java.sql.JDBCType;
import java.util.Date;

/**
 * @author yuntian
 */
@Data
@ToString
@Table(tableName = "auto_inc")
public class AutoIncPluginTestModel {

    // test handler not exist
    @PrimaryKey
    @ColumnMap(jdbcType = JDBCType.INTEGER)
    private Long id;

    private String name;

    private String text;

    @ColumnMap(column = "longTime", jdbcType = JDBCType.BIGINT)
    private Date time;

    @ColumnMap(jdbcType = JDBCType.TIMESTAMP)
    private Long updated;

    private Date created;

    private Boolean deleted;

    @Exclude
    private Integer exclude;
}