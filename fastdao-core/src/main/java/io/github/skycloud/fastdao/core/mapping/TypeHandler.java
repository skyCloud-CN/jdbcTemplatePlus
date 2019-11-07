/**
 * @(#)TypeHandler.java, 9月 28, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yuntian
 */

public interface TypeHandler<T> {

    Object parseParam(T param);

    T getResult(ResultSet rs, String columnName) throws SQLException;
}