/**
 * @(#)ValueParser.java, 10月 12, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.ast;

import com.skycloud.fastdao.core.plugins.Pluggable;

import java.util.Map;

/**
 * @author yuntian
 */
public interface ValueParser extends Pluggable {

    public String parseField(String field);

    public String parseValue(String field, Object value);

    Map<String, Object> getParamMap();
}