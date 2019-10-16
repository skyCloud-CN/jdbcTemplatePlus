/**
 * @(#)TypeHandler.java, 9月 28, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yuntian
 */

public interface TypeHandler<T> {

    Object parseParam(T param);

    T getResult(ResultSet rs, String columnName) throws SQLException;
}