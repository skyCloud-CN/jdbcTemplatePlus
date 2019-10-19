/**
 * @(#)FastDAOException.java, 10月 03, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.exceptions;

/**
 * @author yuntian
 */
public class FastDAOException extends RuntimeException {

    public FastDAOException() {
        super();
    }

    public FastDAOException(String msg, Object... param) {
        super(MessageFormatter.arrayFormat(msg, param).getMessage());
    }

}