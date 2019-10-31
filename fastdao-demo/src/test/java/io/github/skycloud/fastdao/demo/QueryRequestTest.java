/**
 * @(#)AutoIncTest.java, 10月 13, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.demo;

import io.github.skycloud.fastdao.core.Storage;
import io.github.skycloud.fastdao.core.ast.Request;
import io.github.skycloud.fastdao.core.ast.model.SqlFun;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest;
import io.github.skycloud.fastdao.core.util.QueryResult;
import io.github.skycloud.fastdao.demo.dao.AutoIncDAO;
import io.github.skycloud.fastdao.demo.model.AutoIncModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static io.github.skycloud.fastdao.demo.model.Columns.ID;

/**
 * @author yuntian
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FastdaoDemoApplication.class)
public class QueryRequestTest {

    @Autowired
    AutoIncDAO dao;

    @Test
    public void query_with_function_field(){
        QueryRequest request=Request.queryRequest()
                .addSelectFields(ID.AVG());
        List<QueryResult<AutoIncModel>> result=dao.selectAdvance(request);
        Storage storage=new AutoIncDAO();
    }
}