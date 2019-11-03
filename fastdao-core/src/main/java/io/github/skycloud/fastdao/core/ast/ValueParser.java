/**
 * @(#)ValueParser.java, 10月 12, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast;

import io.github.skycloud.fastdao.core.plugins.Pluggable;

import java.util.Map;

/**
 * @author yuntian
 */
public interface ValueParser extends Pluggable {

    String parseValue(String field, Object value);

    Map<String, Object> getParamMap();
}