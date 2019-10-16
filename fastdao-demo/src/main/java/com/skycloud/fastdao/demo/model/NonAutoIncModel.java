/**
 * @(#)NonAutoIncModel.java, 10月 13, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.demo.model;

import com.skycloud.fastdao.core.annotation.PrimaryKey;
import com.skycloud.fastdao.core.annotation.Table;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author yuntian
 */
@Data
@ToString
@Table(tableName = "non_auto_inc")
public class NonAutoIncModel {

    @PrimaryKey
    private Long id;

    private String name;

    private String text;

    private Long longTime;

    private Date updated;

    private Date created;

    private Boolean deleted;

}