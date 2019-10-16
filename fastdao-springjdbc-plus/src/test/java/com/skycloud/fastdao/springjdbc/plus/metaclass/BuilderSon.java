/**
 * @(#)GrandSon.java, 10月 10, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.springjdbc.plus.metaclass;

/**
 * @author yuntian
 */
public class BuilderSon extends Son {

    private Long id;

    private String buildSonName;

    public String getBuildSonName() {
        return buildSonName;
    }

    public BuilderSon setBuildSonName(String buildSonName) {
        this.buildSonName = buildSonName;
        return this;
    }

    public Long getId() {
        return id;
    }

    public BuilderSon setId(Long id) {
        this.id = id;
        return this;
    }
}