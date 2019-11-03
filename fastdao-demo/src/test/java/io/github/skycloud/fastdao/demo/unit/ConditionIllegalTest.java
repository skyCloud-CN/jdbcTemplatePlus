/**
 * @(#)ConditionIllegleTest.java, 11月 02, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.demo.unit;

import io.github.skycloud.fastdao.core.ast.request.QueryRequest;
import io.github.skycloud.fastdao.core.exceptions.IllegalConditionException;
import org.junit.Test;

import static io.github.skycloud.fastdao.demo.model.Columns.ID;

/**
 * @author yuntian
 */
public class ConditionIllegalTest extends BaseTest {

    @Test
    public void emptyAndCondition() {
        QueryRequest request = QueryRequest.newInstance()
                .beginAndCondition()
                .allowEmpty()
                .endCondition();
        assertSqlEqual(request, "SELECT * FROM `table` ");
    }

    @Test
    public void emptyOrCondition() {
        QueryRequest request = QueryRequest.newInstance()
                .beginOrCondition()
                .allowEmpty()
                .endCondition();
        assertSqlEqual(request, "SELECT * FROM `table` ");
    }

    @Test(expected = IllegalConditionException.class)
    public void emptyAndConditionNotAllowEmpty() {
        QueryRequest request = QueryRequest.newInstance()
                .beginAndCondition()
                .endCondition();
        assertSqlEqual(request, "");
    }

    @Test(expected = IllegalConditionException.class)
    public void emptyOrConditionNotAllowEmpty() {
        QueryRequest request = QueryRequest.newInstance()
                .beginOrCondition()
                .endCondition();
        assertSqlEqual(request, "");
    }

    @Test(expected = IllegalConditionException.class)
    public void emptyConditionWithIllegalSubCondition() {
        QueryRequest request = QueryRequest.newInstance()
                .beginAndCondition()
                .and(ID.eq())
                .endCondition();
        assertSqlEqual(request, "");
    }

    @Test
    public void emptyConditionWithOptionalIllegalSubCondition() {
        QueryRequest request = QueryRequest.newInstance()
                .beginAndCondition()
                .andOptional(ID.eq())
                .allowEmpty()
                .endCondition();
        assertSqlEqual(request, "SELECT * FROM `table`");
    }
}