/**
 * @(#)SqlFunctionEnum.java, 10月 27, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.enums;

/**
 * @author yuntian
 */
public enum SqlFunEnum {
    AVG,
    MIN,
    MAX,
    SUM,
    COUNT;

    public String genKey(String field) {
        return name() + '|' + field;
    }
}