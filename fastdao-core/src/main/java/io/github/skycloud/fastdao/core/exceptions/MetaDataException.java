/**
 * @(#)MetaDataException.java, 10月 11, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.exceptions;

/**
 * @author yuntian
 */
public class MetaDataException extends FastDAOException {

    public MetaDataException(String msg, Object... param) {
        super(msg, param);
    }
}