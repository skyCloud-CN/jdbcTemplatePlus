/**
 * @(#)FormatTupple.java, 10月 10, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.exceptions;

/**
 * @author yuntian
 */
public class FormattingTuple {

    public static FormattingTuple NULL = new FormattingTuple((String) null);

    private String message;

    private Throwable throwable;

    private Object[] argArray;

    public FormattingTuple(String message) {
        this(message, (Object[]) null, (Throwable) null);
    }

    public FormattingTuple(String message, Object[] argArray, Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
        this.argArray = argArray;
    }

    public String getMessage() {
        return this.message;
    }

    public Object[] getArgArray() {
        return this.argArray;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }
}