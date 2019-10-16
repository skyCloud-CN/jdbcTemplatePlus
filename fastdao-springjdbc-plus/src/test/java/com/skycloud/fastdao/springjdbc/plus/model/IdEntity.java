/**
 * @(#)IdEntity.java, 10月 04, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.springjdbc.plus.model;

import com.skycloud.fastdao.core.annotation.PrimaryKey;

/**
 * @author yuntian
 */
public class IdEntity {
    @PrimaryKey
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}