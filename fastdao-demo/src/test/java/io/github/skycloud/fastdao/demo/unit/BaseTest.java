/**
 * @(#)BaseTest.java, 11月 01, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.demo.unit;

import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.conditions.Condition;
import io.github.skycloud.fastdao.core.ast.visitor.MysqlVisitor;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import io.github.skycloud.fastdao.core.reflection.MetaField;
import io.github.skycloud.fastdao.demo.model.Model;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import javax.jws.WebParam.Mode;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author yuntian
 */
public class BaseTest {
    public Model getFirst() {
        try {
            Model model = new Model();
            model.setId(1L);
            model.setName("Alice");
            model.setText("text 1");
            model.setLongTime(1570964184204L);
            model.setUpdated(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-03 20:36:14"));
            model.setCreated(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-10-02 21:45:53"));
            model.setDeleted(true);
            return model;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
    public Model getSix(){
        Model model=new Model();
        model.setId(6L);
        model.setDeleted(false);
        model.setName("Frank");
        return model;
    }
    public Model getDefaultModel() {
        Model model = new Model();
        model.setName("sky");
        model.setText("cloud");
        model.setCreated(new Date());
        model.setUpdated(new Date());
        model.setLongTime(new Date().getTime());
        model.setDeleted(false);
        return model;
    }


    public <T> void assertEqual(T model, T model2, String... exclude) {
        MetaClass metaClass = MetaClass.of(model.getClass());
        for (MetaField field : metaClass.metaFields()) {
            if (Arrays.asList(exclude).contains(field.getFieldName())) {
                continue;
            }
            Assert.assertEquals(field.invokeGetter(model), field.invokeGetter(model2));
        }
    }
    protected void assertSqlEqual(Object ast, String expect) {
        TestValueParser parser = new TestValueParser();
        MysqlVisitor visitor = new MysqlVisitor("table", parser);
        ((SqlAst) ast).copy().accept(visitor);
        System.out.println(visitor.getSql());
        Assert.assertEquals(StringUtils.trim(expect), StringUtils.trim(visitor.getSql()));
    }
}