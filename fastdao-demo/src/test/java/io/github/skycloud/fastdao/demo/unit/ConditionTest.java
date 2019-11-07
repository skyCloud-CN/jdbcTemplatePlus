/**
 * @(#)ConditionTest.java, 11月 01, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.demo.unit;

import com.google.common.collect.Lists;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.conditions.AndCondition;
import io.github.skycloud.fastdao.core.ast.conditions.Condition;
import io.github.skycloud.fastdao.core.ast.conditions.LikeCondition;
import io.github.skycloud.fastdao.core.ast.visitor.MysqlVisitor;
import io.github.skycloud.fastdao.jdbctemplate.plus.JdbcTemplateParser;
import org.junit.Assert;
import org.junit.Test;

import static io.github.skycloud.fastdao.demo.model.Columns.ID;

/**
 * @author yuntian
 */

public class ConditionTest extends BaseTest{

    @Test
    public void equalCondition() {
        assertSqlEqual(ID.eq(3), "`id` = 3");
        assertSqlEqual(ID.eq(Lists.newArrayList(1)), "`id` = 1");
        assertSqlEqual(ID.eq(3, 4), "`id` IN ( 3 ,4 )");
        assertSqlEqual(ID.eq(Lists.newArrayList(3, 4)), "`id` IN ( 3 ,4 )");
        Assert.assertFalse(ID.eq(Lists.newArrayList()).isLegal());
    }

    @Test
    public void rangeCondition() {
        assertSqlEqual(ID.gt(4), "`id` > 4 ");
        assertSqlEqual(ID.gte(4), "`id` >= 4");
        assertSqlEqual(ID.lt(4), "`id` < 4");
        assertSqlEqual(ID.lte(4), "`id` <= 4");
        Assert.assertFalse(ID.gt(null).isLegal());
        Assert.assertFalse(ID.lt(null).isLegal());

    }

    @Test
    public void rangeConditionBoth() {
        assertSqlEqual(ID.gt(4).lt(4), "`id` > 4 AND `id` < 4");
        Assert.assertFalse(ID.gt(null).lt(null).isLegal());
    }

    @Test
    public void isNullCondition() {
        assertSqlEqual(ID.isNull(), "`id` IS NULL");
    }

    @Test
    public void likeCondition() {
        assertSqlEqual(ID.like("hello"), "`id` LIKE hello");
        LikeCondition condition = ID.like("hello").matchLeft();
        JdbcTemplateParser parser = new JdbcTemplateParser();
        MysqlVisitor visitor = new MysqlVisitor("table", parser);
        ((SqlAst) condition).accept(visitor);
        Assert.assertEquals("%hello", parser.getParamMap().get("0"));
    }

    //*********************** And/OrConditionTest ***********************//

    // empty check is executed by Visitor on visit Where clause, so empty condition can't be tested here
    @Test
    public void andCondition() {
        assertSqlEqual(Condition.and(ID.eq(3)), "`id` = 3");
        assertSqlEqual(Condition.and(ID.eq(3)).and(ID.eq(4)), "`id` = 3 AND `id` = 4");
        assertSqlEqual(Condition.and(ID.eq(3)).and(Condition.and()), "`id` = 3");
        assertSqlEqual(Condition.and(Condition.and()).and(Condition.and()).and(ID.eq(3)), "`id` = 3");
        assertSqlEqual(Condition.and(Condition.and(ID.eq(1))).and(ID.eq(2)), "`id` = 1 AND `id` = 2");
        assertSqlEqual(Condition.and(Condition.or()).and(ID.eq(2)), "`id` = 2");
        assertSqlEqual(Condition.and(Condition.and(ID.eq(2)).and(Condition.or())), "`id` = 2");
    }

    @Test
    public void andConditionEmpty() {
        Assert.assertTrue(Condition.and().isEmpty());
        Assert.assertFalse(Condition.and().isLegal());
        Assert.assertTrue(Condition.and().allowEmpty().isEmpty());
        Assert.assertTrue(Condition.and().allowEmpty().isLegal());
        AndCondition complex = Condition
                .and(Condition
                        .and().and(Condition
                                .and())).and(Condition.and());
        Assert.assertTrue(complex.isEmpty());
        Assert.assertFalse(complex.isLegal());
        Assert.assertTrue(complex.allowEmpty().isEmpty());
        Assert.assertFalse(complex.isLegal());
    }

    @Test
    public void orCondition() {
        assertSqlEqual(Condition.or(ID.eq(3)), "( `id` = 3 )");
        assertSqlEqual(Condition.or(Condition.and(Condition.and())).or(ID.eq(3)), "( `id` = 3 )");
        assertSqlEqual(Condition.or(Condition.and(ID.eq(3))).or(Condition.or()), "( `id` = 3 )");
        assertSqlEqual(Condition.or(Condition.and(ID.eq(3))).or(Condition.or(ID.eq(4))), "( `id` = 3 OR ( `id` = 4 ) ) ");
        assertSqlEqual(Condition.or(ID.eq(3)).or(Condition.and(ID.eq(4)).and(ID.eq(5))), "( `id` = 3 OR `id` = 4 AND `id` = 5 ) ");
    }

    @Test
    public void orConditionEmpty() {
        Assert.assertTrue(Condition.or().isEmpty());
        Assert.assertFalse(Condition.or().isLegal());
        Assert.assertTrue(Condition.or().allowEmpty().isEmpty());
        Assert.assertTrue(Condition.or().allowEmpty().isLegal());
    }


}