/**
 * @(#)Parent.java, 10月 10, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.springjdbc.plus.metaclass;

/**
 * @author yuntian
 */
@TestAnnotation
public class Parent implements IParent<String> {
    @TestAnnotation
    private String parentName;

    @Override
    public String getParentName() {
        return null;
    }

    @Override
    public void setParentName(String name) {

    }
}