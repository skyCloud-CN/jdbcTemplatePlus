/**
 * @(#)QueryResult.java, 10月 27, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.util;

import io.github.skycloud.fastdao.core.ast.enums.SqlFunctionEnum;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest;
import io.github.skycloud.fastdao.core.table.Column;

import java.util.Map;

/**
 * @author yuntian
 */
public class QueryResult<DATA> {

    private DATA data;

    private Map<String, Object> sqlFunctionData;

    public QueryResult(DATA  data,Map sqlFunctionData){
        this.data=data;
        this.sqlFunctionData=sqlFunctionData;
    }
    public Object getSqlFunctionData(SqlFunctionEnum type, Column field) {
        return sqlFunctionData.get(genKey(type, field.getName()));
    }

    public DATA getData() {
        return data;
    }

    private String genKey(SqlFunctionEnum type, String field) {
        return type.name() + '|' + field;
    }

}