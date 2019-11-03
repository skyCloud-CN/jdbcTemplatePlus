/**
 * @(#)QueryResult.java, 10月 27, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.models;

import io.github.skycloud.fastdao.core.ast.enums.SqlFunEnum;
import io.github.skycloud.fastdao.core.ast.model.SqlFunction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author yuntian
 */
@Data
@NoArgsConstructor
public class QueryResult<DATA> {

    private DATA data;

    private Map<String, Object> sqlFunData;

    public QueryResult(DATA data, Map<String, Object> sqlFunData) {
        this.data = data;
        this.sqlFunData = sqlFunData;
    }

    public Object getSqlFunData(SqlFunEnum type, Column field) {
        return sqlFunData.get(type.genKey(field.getName()));
    }
    public Object getFunResult(SqlFunction fun){
        return sqlFunData.get(fun.genKey());
    }

    public DATA getData() {
        return data;
    }

}