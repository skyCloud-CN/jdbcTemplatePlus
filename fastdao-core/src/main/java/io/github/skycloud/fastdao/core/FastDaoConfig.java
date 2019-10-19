/**
 * @(#)FastDaoConfig.java, 10月 13, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core;

/**
 * @author yuntian
 * configuration class, currently is very simple and only support for jdbcTemplate and Mysql
 */
public class FastDaoConfig {

    private static boolean mapUnderscoreToCamelCase = false;

    public static boolean isMapUnderscoreToCamelCase() {
        return mapUnderscoreToCamelCase;
    }

    public static void setMapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase) {
        FastDaoConfig.mapUnderscoreToCamelCase = mapUnderscoreToCamelCase;
    }
}