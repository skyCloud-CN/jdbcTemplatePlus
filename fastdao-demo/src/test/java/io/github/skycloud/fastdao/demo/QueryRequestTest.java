/**
 * @(#)AutoIncTest.java, 10月 13, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.demo;

import com.google.common.collect.Lists;
import io.github.skycloud.fastdao.core.ast.enums.OrderEnum;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest;
import io.github.skycloud.fastdao.core.models.Page;
import io.github.skycloud.fastdao.core.models.QueryResult;
import io.github.skycloud.fastdao.demo.dao.AutoIncDAO;
import io.github.skycloud.fastdao.demo.model.Model;
import io.github.skycloud.fastdao.demo.unit.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jws.WebParam.Mode;
import java.util.List;

import static io.github.skycloud.fastdao.demo.model.Columns.DELETED;
import static io.github.skycloud.fastdao.demo.model.Columns.ID;
import static io.github.skycloud.fastdao.demo.model.Columns.NAME;

/**
 * @author yuntian
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FastdaoDemoApplication.class)
public class QueryRequestTest extends BaseTest {

    @Autowired
    AutoIncDAO dao;

    //*********************** selectByPrimaryKey ***********************//
    @Test
    public void selectByPrimaryKeyExist() {
        Model model = dao.selectByPrimaryKey(1L);
        Assert.assertNotNull(model);
        assertEqual(model, getFirst());
        System.out.println(model);
    }

    @Test
    public void selectByPrimaryKeyNotExist() {
        Model model = dao.selectByPrimaryKey(0L);
        Assert.assertNull(model);
        System.out.println(model);
    }

    @Test
    public void selectByPrimaryKeysExist() {
        List<Model> models = dao.selectByPrimaryKeys(1L, 2L);
        Assert.assertEquals(2, models.size());
    }

    @Test
    public void selectByPrimaryKeysNotExist() {
        List<Model> models = dao.selectByPrimaryKeys(0L, -1L);
        Assert.assertEquals(0, models.size());
    }

    @Test
    public void selectByPrimaryKeyHalfExist() {
        List<Model> models = dao.selectByPrimaryKeys(1L, 0L);
        Assert.assertEquals(1, models.size());
        assertEqual(getFirst(), models.get(0));
    }

    @Test
    public void selectByPrimaryKeys() {
        List<Model> models = dao.selectByPrimaryKeys(Lists.newArrayList(1L, 0L));
        Assert.assertEquals(1, models.size());
        assertEqual(getFirst(), models.get(0));
    }

    @Test
    public void selectNullEntity() {
        Model model = dao.selectByPrimaryKey(6L);
        assertEqual(getSix(), model);
    }

    //*********************** select ***********************//
    @Test
    public void selectWithEmptyRequest() {
        List<Model> models = dao.select(QueryRequest.newInstance());
        Assert.assertEquals(6, models.size());
        assertEqual(getFirst(), models.stream().filter(m -> m.getId() == 1L).findAny().get());
    }

    @Test
    public void selectWithColumnField() {
        QueryRequest request = QueryRequest.newInstance().addSelectFields(ID);
        List<Model> models = dao.select(request);
        models.stream().forEach(model -> Assert.assertNull(model.getName()));
        models.stream().forEach(model -> Assert.assertNotNull(model.getId()));
        List<Model> models2 = dao.select(request.addSelectFields(NAME));
        models2.stream().forEach(model -> Assert.assertNotNull(model.getName()));
    }

    @Test
    public void selectWithDistinct() {
        QueryRequest request = QueryRequest.newInstance().distinct().addSelectFields(DELETED);
        List<Model> models = dao.select(request);
        Assert.assertEquals(2, models.size());
        request.addSelectFields(ID);
        List<Model> models2 = dao.select(request);
        Assert.assertEquals(6, models2.size());
        models2.stream().forEach(m -> Assert.assertNotNull(m.getId()));
    }

    @Test
    public void selectWithSort() {
        QueryRequest request = QueryRequest.newInstance().addSort(ID, OrderEnum.ASC).addSort(NAME, OrderEnum.DESC);
        List<Model> models = dao.select(request);
        for (int i = 0; i < models.size(); i++) {
            Assert.assertEquals(i + 1, (long) (models.get(i).getId()));
        }
    }

    @Test
    public void selectWithLimit() {
        QueryRequest request = QueryRequest.newInstance().addSort(ID, OrderEnum.ASC).addSort(NAME, OrderEnum.ASC).limit(3).offset(3);
        List<Model> models = dao.select(request);
        for (int i = 0; i < models.size(); i++) {
            Assert.assertEquals(i + 4, (long) (models.get(i).getId()));
        }
    }

    @Test
    public void selectWithFieldSelect() {
        QueryRequest request = QueryRequest.newInstance().addSelectFields(ID, NAME);
        List<Model> models = dao.select(request);
        System.out.println(models);
        models.stream().forEach(m -> Assert.assertNotNull(m.getId()));
        models.stream().forEach(m -> Assert.assertNotNull(m.getName()));
        models.stream().forEach(m -> Assert.assertNull(m.getText()));
    }

    @Test
    public void selectWithGroupBy() {
        QueryRequest request = QueryRequest.newInstance()
                .addSelectFields(DELETED)
                .groupBy(DELETED)
                .having(DELETED.gt(0));
        List<Model> models = dao.select(request);
        Assert.assertEquals(1, models.size());
        Assert.assertEquals(true, models.get(0).getDeleted());
    }

    @Test
    public void selectWithGroupByFunction() {
        QueryRequest request = QueryRequest.newInstance()
                .addSelectFields(ID.fun().AVG())
                .groupBy(DELETED);
        List<Model> models = dao.select(request);
        Assert.assertEquals(2, models.size());
    }

    //*********************** selectOne ***********************//
    @Test
    public void selectOne() {
        QueryRequest request = QueryRequest.newInstance().addSelectFields(ID, NAME).addSort(ID, OrderEnum.ASC);
        Model model = dao.selectOne(request);
        Assert.assertEquals(1L, (long) model.getId());
        Assert.assertNotNull(model.getName());
    }

    //*********************** selectSingleField ***********************//
    @Test
    public void selectSingleField() {
        QueryRequest request = QueryRequest.newInstance()
                .addSelectFields(NAME)
                .beginAndCondition()
                .and(ID.gt(0))
                .endCondition();
        List<String> names = dao.selectSingleField(request, String.class);
        Assert.assertEquals(6, names.size());
    }

    @Test
    public void selectSingleFunctionField() {
        QueryRequest request = QueryRequest.newInstance().addSelectFields(ID.fun().MAX());
        List<Long> maxId = dao.selectSingleField(request, Long.class);
        Assert.assertEquals(1, maxId.size());
        Assert.assertEquals(6, (long) maxId.get(0));
    }

    @Test
    public void selectSingleFunctionFieldWithGroupByFunction() {
        QueryRequest request = QueryRequest.newInstance()
                .addSelectFields(ID.fun().AVG())
                .groupBy(DELETED)
                .addSort(DELETED, OrderEnum.DESC);
        List<Integer> avgIds = dao.selectSingleField(request, Integer.class);
        Assert.assertEquals(2, avgIds.size());
        Assert.assertEquals(2, (long) avgIds.get(0));
        Assert.assertEquals(5, (long) avgIds.get(1));
    }

    //*********************** selectAdvance ***********************//
    @Test
    public void selectAdvance() {
        QueryRequest request = QueryRequest.newInstance()
                .addSelectFields(ID.fun().AVG())
                .addSelectFields(DELETED)
                .groupBy(DELETED)
                .addSort(DELETED, OrderEnum.DESC);
        List<QueryResult<Model>> results = dao.selectAdvance(request);
        results.forEach(m -> Assert.assertNotNull(m.getData().getDeleted() != null));
        results.forEach(m -> Assert.assertNotNull(m.getFunResult(ID.fun().AVG())));
    }

    @Test
    public void normalSelectBySelectAdvance() {
        QueryRequest request = QueryRequest.newInstance()
                .beginAndCondition()
                .and(ID.eq(1L))
                .endCondition();
        List<QueryResult<Model>> results = dao.selectAdvance(request);
        Model model = results.get(0).getData();
        assertEqual(model, getFirst());
    }

    @Test
    public void singleFieldSelectBySelectAdvance() {
        QueryRequest request = QueryRequest.newInstance().addSelectFields(NAME);
        List<QueryResult<Model>> results = dao.selectAdvance(request);
        results.forEach(m -> Assert.assertNotNull(m.getData().getName()));
    }

    @Test
    public void singleFunctionFieldSelectBySelectAdvance() {
        QueryRequest request = QueryRequest.newInstance().addSelectFields(ID.fun().AVG());
        List<QueryResult<Model>> results = dao.selectAdvance(request);
        results.forEach(m -> Assert.assertNotNull(m.getFunResult(ID.fun().AVG())));
    }
    //*********************** selectPage ***********************//
    @Test
    public void selectPageByLimit(){
        QueryRequest request=QueryRequest.newInstance().setCondition(ID.gt(0)).addSort(ID,OrderEnum.ASC);
        Page page=Page.byLimit(2,2);
        List<Model> models=dao.selectPage(request,page);
        Assert.assertEquals(2,models.size());
        for(int i=0;i<models.size();i++){
            Assert.assertEquals(i+3,(long)(models.get(i).getId()));
        }
        Assert.assertEquals(6,page.getTotal());
        Assert.assertTrue(page.hasNext());

    }
    @Test
    public void selectPageByLimitNoNext(){
        QueryRequest request=QueryRequest.newInstance().setCondition(ID.gt(0)).addSort(ID,OrderEnum.ASC);
        Page page=Page.byLimit(4,2);
        List<Model> models=dao.selectPage(request,page);
        Assert.assertEquals(2,models.size());
        for(int i=0;i<models.size();i++){
            Assert.assertEquals(i+5,(long)(models.get(i).getId()));
        }
        Assert.assertEquals(6,page.getTotal());
        Assert.assertFalse(page.hasNext());
    }
    @Test
    public void selectPageByPage(){
        QueryRequest request=QueryRequest.newInstance().setCondition(ID.gt(0)).addSort(ID,OrderEnum.ASC);
        Page page=Page.byPage(1,2);
        List<Model> models=dao.selectPage(request,page);
        Assert.assertEquals(2,models.size());
        for(int i=0;i<models.size();i++){
            Assert.assertEquals(i+1,(long)(models.get(i).getId()));
        }
        Assert.assertEquals(6,page.getTotal());
        Assert.assertEquals(0,page.getOffset());
        Assert.assertEquals(2,page.getLimit());
        Assert.assertTrue(page.hasNext());
    }
    @Test
    public void selectPageByPageNoNext(){
        QueryRequest request=QueryRequest.newInstance().setCondition(ID.gt(0)).addSort(ID,OrderEnum.ASC);
        Page page=Page.byPage(3,2);
        List<Model> models=dao.selectPage(request,page);
        Assert.assertEquals(2,models.size());
        for(int i=0;i<models.size();i++){
            Assert.assertEquals(i+5,(long)(models.get(i).getId()));
        }
        Assert.assertEquals(6,page.getTotal());
        Assert.assertEquals(4,page.getOffset());
        Assert.assertEquals(2,page.getLimit());
        Assert.assertFalse(page.hasNext());
    }
}