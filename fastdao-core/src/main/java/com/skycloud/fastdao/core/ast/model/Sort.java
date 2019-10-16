/**
 * @(#)Order.java, 10月 01, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.skycloud.fastdao.core.ast.model;

import com.skycloud.fastdao.core.ast.enums.OrderEnum;

/**
 * @author yuntian
 */

public class Sort {

    private String field;

    private OrderEnum order;

    public Sort(String field, OrderEnum order) {
        this.field = field;
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public OrderEnum getOrder() {
        return order;
    }
}