package com.skycloud.fastdao.springjdbc.plus.sqlgen; /**
 * @(#)SqlGenerateTest.java, 10月 01, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import com.google.common.collect.Lists;
import com.skycloud.fastdao.core.ast.MysqlVisitor;
import com.skycloud.fastdao.core.ast.conditions.AndCondition;
import com.skycloud.fastdao.core.ast.Condition;
import com.skycloud.fastdao.core.ast.conditions.EqualCondition;
import com.skycloud.fastdao.core.ast.conditions.LikeCondition;
import com.skycloud.fastdao.core.ast.conditions.OrCondition;
import com.skycloud.fastdao.core.ast.conditions.RangeCondition;

import com.skycloud.fastdao.core.ast.request.CountRequest;
import com.skycloud.fastdao.core.ast.request.InsertRequest;
import com.skycloud.fastdao.core.ast.request.QueryRequest;
import com.skycloud.fastdao.core.ast.request.UpdateRequest;
import com.skycloud.fastdao.core.table.Column;
import com.skycloud.fastdao.springjdbc.plus.JdbcTemplateParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yuntian
 */
public class SqlGenerateTest {

    private static Column ID = new Column("id");

    private static Column NAME = new Column("name");

    private static Column UPDATED = new Column("updated");
    
    private MysqlVisitor getVisitor(){
        return new MysqlVisitor("test",new JdbcTemplateParser());
    }
    @Test
    public void equalConditionTest1() {
        EqualCondition condition = ID.equal(3);
        MysqlVisitor visitor = getVisitor();
        visitor.visit(condition);
        System.out.println(visitor.getSql());
        Assert.assertEquals(visitor.getSql(), "`id` = :0 ");
        System.out.println(visitor.getSql());
        
        checkMap(((JdbcTemplateParser)((JdbcTemplateParser)visitor.getValueParser())).getParamMap(), 3);
    }

    @Test
    public void equalConditionTest2() {
        EqualCondition condition = ID.equal(Lists.newArrayList(3, 4, 5));
        MysqlVisitor visitor = getVisitor();
        visitor.visit(condition);
        System.out.println(visitor.getSql());
        Assert.assertEquals(visitor.getSql(), "`id` IN ( :0 ) ");
        checkMap(((JdbcTemplateParser)((JdbcTemplateParser)visitor.getValueParser())).getParamMap(), Lists.newArrayList(3, 4, 5));
    }

    @Test
    public void testRangeCondition1() {
        RangeCondition rangeCondition = ID.lt(3);
        MysqlVisitor visitor = getVisitor();
        visitor.visit(rangeCondition);
        System.out.println(visitor.getSql());
        Assert.assertEquals("`id` < :0 ", visitor.getSql());
        checkMap(((JdbcTemplateParser)((JdbcTemplateParser)visitor.getValueParser())).getParamMap(), 3);

    }

    @Test
    public void testRangeConditionTest2() {
        RangeCondition rangeCondition = ID.lte(3).gte(0);
        MysqlVisitor visitor = getVisitor();
        visitor.visit(rangeCondition);
        System.out.println(visitor.getSql());
        Assert.assertEquals(visitor.getSql(), "`id` >= :0 AND `id` <= :1 ");
        checkMap(((JdbcTemplateParser)((JdbcTemplateParser)visitor.getValueParser())).getParamMap(), 0, 3);
    }

    @Test
    public void testRangeConditionTest3() {
        RangeCondition rangeCondition = ID.gte(0);
        MysqlVisitor visitor = getVisitor();
        visitor.visit(rangeCondition);
        Assert.assertEquals("`id` >= :0 ", visitor.getSql());
        checkMap(((JdbcTemplateParser)((JdbcTemplateParser)visitor.getValueParser())).getParamMap(), 0);
    }

    @Test
    public void testLikeCondition1() {
        LikeCondition likeCondition = ID.like(10).matchLeft();
        MysqlVisitor visitor = getVisitor();
        visitor.visit(likeCondition);
        System.out.println(visitor.getSql());
        Assert.assertEquals("`id` LIKE :0 ", visitor.getSql());
        checkMap(((JdbcTemplateParser)((JdbcTemplateParser)visitor.getValueParser())).getParamMap(), "%10");
    }

    @Test
    public void testLikeCondition2() {
        LikeCondition likeCondition = ID.like("10").matchRight().matchLeft();
        MysqlVisitor visitor = getVisitor();
        visitor.visit(likeCondition);
        System.out.println(visitor.getSql());
        Assert.assertEquals("`id` LIKE :0 ", visitor.getSql());
        checkMap(((JdbcTemplateParser)((JdbcTemplateParser)visitor.getValueParser())).getParamMap(), "%10%");
    }

    @Test
    public void testAndCondition1() {
        AndCondition condition = Condition.andCondition().and(ID.equal(3)).and(NAME.equal("me"));
        MysqlVisitor visitor = getVisitor();
        visitor.visit(condition);
        System.out.println(visitor.getSql());
        Assert.assertEquals("`id` = :0 AND `name` = :1 ", visitor.getSql());
        checkMap(((JdbcTemplateParser)((JdbcTemplateParser)visitor.getValueParser())).getParamMap(), 3, "me");
    }

    @Test
    public void testAndCondition2() {
        AndCondition condition = Condition.andCondition().andIgnoreIllegal(ID.equal(3)).andIgnoreIllegal(NAME.equal("null"));
        MysqlVisitor visitor = getVisitor();
        visitor.visit(condition);
        System.out.println(visitor.getSql());
        Assert.assertEquals("`id` = :0 ", visitor.getSql());
        checkMap(((JdbcTemplateParser)((JdbcTemplateParser)visitor.getValueParser())).getParamMap(), 3);
    }

    @Test
    public void testAndCondition3() {
        AndCondition condition = Condition.andCondition().andIgnoreIllegal(ID.equal(Lists.newArrayList()))
                .andIgnoreIllegal(NAME.equal("null"));
        MysqlVisitor visitor = getVisitor();
        visitor.visit(condition);
        System.out.println(visitor.getSql());
        Assert.assertEquals("", visitor.getSql());
        checkMap(((JdbcTemplateParser)((JdbcTemplateParser)visitor.getValueParser())).getParamMap());
    }

    @Test
    public void testOrCondition1() {
        OrCondition condition = Condition.orCondition().or(ID.equal(3)).or(NAME.equal("me"));
        MysqlVisitor visitor = getVisitor();
        visitor.visit(condition);
        System.out.println(visitor.getSql());
        Assert.assertEquals("( `id` = :0 OR `name` = :1 ) ", visitor.getSql());
        checkMap(((JdbcTemplateParser)visitor.getValueParser()).getParamMap(), 3, "me");
    }

    @Test
    public void testOrCondition2() {
        OrCondition condition = Condition.orCondition().orIgnoreIllegal(ID.equal("null")).or(NAME.equal("me"));
        MysqlVisitor visitor = getVisitor();
        visitor.visit(condition);
        System.out.println(visitor.getSql());
        Assert.assertEquals("( `name` = :0 ) ", visitor.getSql());
        checkMap(((JdbcTemplateParser)visitor.getValueParser()).getParamMap(), "me");
    }

    @Test
    public void testComplexCondition() {
        AndCondition condition =
                Condition.andCondition().and(Condition.orCondition().orIgnoreIllegal(ID.gt(3)).or(NAME.equal("me")))
                        .and(UPDATED.lt(1111L));
        MysqlVisitor visitor = getVisitor();
        visitor.visit(condition);
        System.out.println(visitor.getSql());
        Assert.assertEquals("( `id` > :0 OR `name` = :1 ) AND `updated` < :2 ", visitor.getSql());
        checkMap(((JdbcTemplateParser)visitor.getValueParser()).getParamMap(), 3, "me", 1111L);
    }

    @Test
    public void testQueryRequest() {
        QueryRequest request = new QueryRequest().addSelectFields(ID, NAME).distinct()
                .setCondition(Condition.andCondition().and(ID.gt(3).lte(10))).offset(10).limit(3);
        MysqlVisitor visitor = getVisitor();
        visitor.visit(request);
        System.out.println(visitor.getSql());
        Assert.assertEquals("SELECT DISTINCT `id` , `name` FROM `test` WHERE `id` > :0 AND `id` <= :1 LIMIT 3, 10",
                visitor.getSql());
        checkMap(((JdbcTemplateParser)visitor.getValueParser()).getParamMap(), 3, 10);
    }

    @Test
    public void testCountRequest() {
        CountRequest request = new CountRequest();
        Condition condition = ID.gt(3);
        request.setCondition(condition);
        MysqlVisitor visitor = getVisitor();
        visitor.visit(request);
        System.out.println(visitor.getSql());
        checkMap(((JdbcTemplateParser)visitor.getValueParser()).getParamMap(), 3);
    }

    @Test
    public void testUpdateRequest() {
        UpdateRequest request = new UpdateRequest().addUpdateField(ID, 1).addUpdateField(NAME, "skycloud")
                .setCondition(UPDATED.gt(1000L)).offset(3).limit(10);
        MysqlVisitor visitor = getVisitor();
        visitor.visit(request);
        System.out.println(visitor.getSql());
        Assert.assertEquals("UPDATE `test` SET `id` = :0 , `name` = :1 WHERE `updated` > :2 LIMIT 10, 3",visitor.getSql());
        checkMap(((JdbcTemplateParser)visitor.getValueParser()).getParamMap(),1,"skycloud",1000L);
    }

    @Test
    public void testInsertRequest(){
        InsertRequest request=new InsertRequest().addInsertField(ID,1).addInsertField(NAME,"skycloud");
        MysqlVisitor visitor = getVisitor();
        visitor.visit(request);
        System.out.println(visitor.getSql());
        Assert.assertEquals("INSERT INTO `test` SET `id` = :0 , `name` = :1 ",visitor.getSql());
        checkMap(((JdbcTemplateParser)visitor.getValueParser()).getParamMap(),1,"skycloud");
    }
    
    private void checkMap(Map<String, Object> map, Object... value) {
        int size = map.size();
        Assert.assertEquals(size, value.length);
        List<Object> sortedResult = new ArrayList<>(size + 1);
        map.forEach((key, val) -> sortedResult.add(Integer.parseInt(key), val));
        for (int i = 0; i < sortedResult.size(); i++) {
            Assert.assertEquals(sortedResult.get(i), (value[i]));
        }
    }


}