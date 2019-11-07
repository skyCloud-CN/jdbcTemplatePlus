/**
 * @(#)IllegalConditionException.java, 10月 15, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.exceptions;

/**
 * @author yuntian
 */
public class IllegalConditionException extends FastDAOException {

    public IllegalConditionException() {
        super();
    }

    public IllegalConditionException(String msg, Object... param) {
        super(msg, param);
    }
}