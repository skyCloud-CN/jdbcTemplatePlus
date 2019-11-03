/**
 * @(#)RequestTest.java, 11月 02, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.demo.unit;

import io.github.skycloud.fastdao.core.ast.enums.OrderEnum;
import io.github.skycloud.fastdao.core.ast.model.SortLimitClause;
import io.github.skycloud.fastdao.core.ast.request.CountRequest;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest;
import io.github.skycloud.fastdao.demo.dao.AutoIncDAO;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.github.skycloud.fastdao.demo.model.Columns.ID;
import static io.github.skycloud.fastdao.demo.model.Columns.NAME;

/**
 * @author yuntian
 */
public class RequestTest extends BaseTest {

    @Autowired
    AutoIncDAO dao;

    @Test
    public void sortLimitClause() {
        SortLimitClause clause = new SortLimitClause();
        clause.addSort(ID.getName(), OrderEnum.DESC);
        clause.setLimit(10);
        assertSqlEqual(clause, "ORDER BY `id` DESC LIMIT 10");
        clause = new SortLimitClause();
        clause.setLimit(10);
        clause.setOffset(20);
        assertSqlEqual(clause, "LIMIT 20, 10");
    }

    //*********************** InsertRequest ***********************//
    @Test
    public void insertRequest() {
        InsertRequest request = InsertRequest.newInstance()
                .addUpdateField(ID, 1)
                .addUpdateField(NAME, "name");
        assertSqlEqual(request, "INSERT INTO `table` ( `id` , `name` ) VALUES ( 1 , name )");
    }

    @Test
    public void insertOnDuplicateKeyWithoutValue() {
        InsertRequest request = InsertRequest.newInstance()
                .addUpdateField(ID, 1)
                .addUpdateField(NAME, "name")
                .addOnDuplicateUpdateField(ID);
        assertSqlEqual(request, "INSERT INTO `table` ( `id` , `name` ) VALUES ( 1 , name ) ON DUPLICATE KEY UPDATE `id` = 1");
        request.addOnDuplicateUpdateField(NAME);
        assertSqlEqual(request, "INSERT INTO `table` ( `id` , `name` ) VALUES ( 1 , name ) ON DUPLICATE KEY UPDATE `id` = 1 , `name` = name");
    }

    @Test
    public void insertOnDuplicateKeyWithValue() {
        InsertRequest request = InsertRequest.newInstance()
                .addUpdateField(ID, 1)
                .addUpdateField(NAME, "name")
                .addOnDuplicateUpdateField(ID, 2);
        assertSqlEqual(request, "INSERT INTO `table` ( `id` , `name` ) VALUES ( 1 , name ) ON DUPLICATE KEY UPDATE `id` = 2");
        request.addOnDuplicateUpdateField(NAME, "namePlus");
        assertSqlEqual(request, "INSERT INTO `table` ( `id` , `name` ) VALUES ( 1 , name ) ON DUPLICATE KEY UPDATE `id` = 2 , `name` = namePlus");
    }

    @Test
    public void insertWithNullField() {
        InsertRequest request = InsertRequest.newInstance()
                .addUpdateField(ID, null);
        assertSqlEqual(request, "INSERT INTO `table` ( `id` ) VALUES ( null )");
    }

    //*********************** UpdateRequest ***********************//
    @Test
    public void update() {
        UpdateRequest request = UpdateRequest.newInstance()
                .addUpdateField(ID, 1)
                .addUpdateField(NAME, "name");
        assertSqlEqual(request, "UPDATE `table` SET `id` = 1 , `name` = name");
    }

    @Test
    public void updateSortLimit() {
        UpdateRequest request = UpdateRequest.newInstance()
                .addUpdateField(ID, 1)
                .addUpdateField(NAME, "name")
                .addSort(ID, OrderEnum.ASC)
                .addSort(NAME, OrderEnum.DESC)
                .limit(20)
                .offset(10);
        assertSqlEqual(request, "UPDATE `table` SET `id` = 1 , `name` = name ORDER BY `id` ASC , `name` DESC LIMIT 10, 20");
    }

    @Test
    public void updateWithCondition() {
        UpdateRequest request = UpdateRequest.newInstance()
                .addUpdateField(ID, 1)
                .addUpdateField(NAME, "name")
                .setCondition(ID.eq(3))
                .limit(20)
                .offset(10);
        assertSqlEqual(request, "UPDATE `table` SET `id` = 1 , `name` = name WHERE `id` = 3 LIMIT 10, 20");
    }

    @Test
    public void updateWithNullField() {
        UpdateRequest request = UpdateRequest.newInstance()
                .addUpdateField(ID, null);
        assertSqlEqual(request, "UPDATE `table` SET `id` = null");

    }

    //*********************** DeleteRequest ***********************//
    @Test
    public void delete() {
        DeleteRequest request = DeleteRequest.newInstance();
        assertSqlEqual(request, "DELETE FROM `table`");
    }

    @Test
    public void deleteWithCondition() {
        DeleteRequest request = DeleteRequest.newInstance()
                .setCondition(ID.eq(3))
                .addSort(ID, OrderEnum.DESC)
                .limit(10)
                .offset(20);
        assertSqlEqual(request, "DELETE FROM `table` WHERE `id` = 3 ORDER BY `id` DESC LIMIT 20, 10");
    }

    //*********************** CountRequest ***********************//
    @Test
    public void count() {
        CountRequest request = CountRequest.newInstance();
        assertSqlEqual(request, "SELECT COUNT( * ) FROM `table`");
        request.distinct();
        assertSqlEqual(request, "SELECT COUNT( DISTINCT * ) FROM `table`");
        request.setCountField(ID);
        assertSqlEqual(request, "SELECT COUNT( DISTINCT `id` ) FROM `table`");
        request.setCountField(NAME);
        assertSqlEqual(request, "SELECT COUNT( DISTINCT `name` ) FROM `table`");
    }

    @Test
    public void countWithCondition() {
        CountRequest request = CountRequest.newInstance()
                .beginAndCondition()
                .and(ID.gt(3))
                .endCondition();
        assertSqlEqual(request, "SELECT COUNT( * ) FROM `table` WHERE `id` > 3 ");
    }

    //*********************** QueryRequest ***********************//
    @Test
    public void queryRequest() {
        QueryRequest request = QueryRequest.newInstance();
        assertSqlEqual(request, "SELECT * FROM `table`");
    }

    @Test
    public void queryRequestWithSortLimit() {
        QueryRequest request = QueryRequest.newInstance()
                .addSort(ID, OrderEnum.DESC)
                .limit(10);
        assertSqlEqual(request, "SELECT * FROM `table` ORDER BY `id` DESC LIMIT 10");
    }

    @Test
    public void queryRequestWithCondition() {
        QueryRequest request = QueryRequest.newInstance()
                .beginAndCondition()
                .and(ID.eq(3))
                .endCondition();
        assertSqlEqual(request, "SELECT * FROM `table` WHERE `id` = 3");
    }

    @Test
    public void queryRequestWithSelectField() {
        QueryRequest request;
        // normal
        request = QueryRequest.newInstance()
                .addSelectFields(ID);
        assertSqlEqual(request, "SELECT `id` FROM `table`");
        // multi
        request = QueryRequest.newInstance()
                .addSelectFields(ID, NAME);
        assertSqlEqual(request, "SELECT `id` , `name` FROM `table`");
        // collection-normal
        request = QueryRequest.newInstance()
                .addSelectFields(Lists.newArrayList(ID, NAME));
        assertSqlEqual(request, "SELECT `id` , `name` FROM `table`");
        // collection-empty
        request = QueryRequest.newInstance()
                .addSelectFields(Lists.newArrayList());
        assertSqlEqual(request, "SELECT * FROM `table`");
        // function-normal
        request = QueryRequest.newInstance()
                .addSelectFields(ID.fun().AVG());
        assertSqlEqual(request, "SELECT AVG( `id` ) FROM `table`");
        // function-multi
        request = QueryRequest.newInstance()
                .addSelectFields(ID.fun().AVG().distinct(), ID.fun().COUNT());
        assertSqlEqual(request, "SELECT AVG( DISTINCT `id` ) AS `AVG|id` , COUNT( `id` ) AS `COUNT|id` FROM `table`");
    }

}