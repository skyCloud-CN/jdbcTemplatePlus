/**
 * @(#)IllegalRequestException.java, 10月 29, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.exceptions;

/**
 * @author yuntian
 */
public class IllegalRequestException extends FastDAOException {

    public IllegalRequestException(String msg, Object... param) {
        super(msg, param);
    }
}