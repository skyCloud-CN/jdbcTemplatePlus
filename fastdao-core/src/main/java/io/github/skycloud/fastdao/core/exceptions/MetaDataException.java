/**
 * @(#)MetaDataException.java, 10月 11, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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