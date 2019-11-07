/**
 * @(#)UpdateRequestTest.java, 11月 03, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.demo;

import io.github.skycloud.fastdao.core.ast.request.QueryRequest;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest;
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
import org.springframework.transaction.annotation.Transactional;
import static io.github.skycloud.fastdao.demo.model.Columns.*;
import java.util.List;

/**
 * @author yuntian
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FastdaoDemoApplication.class)
public class UpdateRequestTest extends BaseTest {

    @Autowired
    private AutoIncDAO dao;

    //*********************** updateRequest ***********************//
    @Test
    @Transactional
    public void updateByPrimaryKey() {
        Model model = getDefaultModel();
        model.setId(6L);
        int count = dao.updateByPrimaryKey(model);
        assertEqual(model, dao.selectByPrimaryKey(6L));
    }

    @Test
    @Transactional
    public void updateByPrimayKeyWithNullField() {
        Model model = getDefaultModel();
        model.setId(1L);
        model.setUpdated(null);
        int count = dao.updateByPrimaryKey(model);
        Assert.assertEquals(1, count);
        assertEqual(model, dao.selectByPrimaryKey(1L), "updated");
        Assert.assertNull(dao.selectByPrimaryKey(1L).getUpdated());
    }

    //*********************** updateSelective ***********************//
    @Test
    @Transactional
    public void updateByPrimaryKeySelective() {
        Model model = getDefaultModel();
        model.setId(6L);
        int count = dao.updateByPrimaryKeySelective(model);
        Assert.assertEquals(1, count);
        assertEqual(model, dao.selectByPrimaryKey(6L));
    }

    @Test
    @Transactional
    public void updateByPrimaryKeySelectiveWithNullField() {
        Model model = getDefaultModel();
        model.setId(1L);
        model.setUpdated(null);
        int count = dao.updateByPrimaryKeySelective(model);
        Assert.assertEquals(1,count);
        assertEqual(model, dao.selectByPrimaryKey(1L), "updated");
        Assert.assertNotNull(dao.selectByPrimaryKey(1L).getUpdated());
    }
    //*********************** update ***********************//
    @Test
    @Transactional
    public void update(){
        UpdateRequest request=UpdateRequest.newInstance().addUpdateField(NAME,"HELLO");
        int count=dao.update(request);
        Assert.assertEquals(6,count);
        List<Model> models= dao.select(QueryRequest.newInstance());
        models.forEach(m->Assert.assertEquals("HELLO",m.getName()));
    }
}