/**
 * @(#)SqlHelper.java, 9月 30, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.jdbctemplate.plus;

import io.github.skycloud.fastdao.core.ast.visitor.SqlVisitor;
import io.github.skycloud.fastdao.core.ast.visitor.MysqlVisitor;
import io.github.skycloud.fastdao.core.ast.request.Request;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.ValueParser;
import io.github.skycloud.fastdao.core.ast.request.CountRequest;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequestAst;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest;
import io.github.skycloud.fastdao.core.exceptions.IllegalConditionException;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.models.QueryResult;
import io.github.skycloud.fastdao.core.models.Tuple;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.function.BiFunction;

/**
 * @author yuntian
 */
class JdbcTemplateSqlHelper {

    static int update(NamedParameterJdbcOperations db, UpdateRequest request, Class clazz) {
        return sendRequest(request, clazz, (visitor, source) ->
                db.update(visitor.getSql(), source));
    }

    static Tuple<Integer, Number> insert(NamedParameterJdbcOperations db, InsertRequest request, Class clazz) {
        return sendRequest(request, clazz, (visitor, source) -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int count = db.update(visitor.getSql(), source, keyHolder);
            return new Tuple<>(count, keyHolder.getKey());
        });
    }

    static <T> List<T> query(NamedParameterJdbcOperations db, QueryRequest request, Class<T> clazz) {
        return sendRequest(request, clazz, (visitor, source) ->
                db.query(visitor.getSql(), source, RowMapperWrapper.of(clazz).getMapper(((QueryRequestAst) request).getSelectFields())));
    }

    static <T> T queryOne(NamedParameterJdbcOperations db, QueryRequest request, Class<T> clazz) {
        List<T> result = sendRequest(request, clazz, (visitor, source) ->
                db.query(visitor.getSql(), source, RowMapperWrapper.of(clazz).getMapper(((QueryRequestAst) request).getSelectFields())));
        return CollectionUtils.isEmpty(result) ? null : result.get(0);
    }

    static <T> List<T> querySingleField(NamedParameterJdbcOperations db, QueryRequest request, Class clazz,Class<T> fieldClazz) {
        return sendRequest(request, clazz, (visitor, source) ->
                db.queryForList(visitor.getSql(), source, fieldClazz));
    }

    static <T> List<QueryResult<T>> selectAdvance(NamedParameterJdbcOperations db, QueryRequest request, Class<T> clazz) {
        return sendRequest(request, clazz, (visitor, source) ->
                db.query(visitor.getSql(), source, RowMapperWrapper.of(clazz).getMapperAdvance(((QueryRequestAst) request).getSelectFields())));
    }

    static <T> int count(NamedParameterJdbcOperations db, CountRequest request, Class<T> clazz) {
        return sendRequest(request, clazz, (visitor, source) ->
                db.queryForObject(visitor.getSql(), source, Integer.class));

    }

    static int delete(NamedParameterJdbcOperations db, DeleteRequest request, Class clazz) {
        return sendRequest(request, clazz, (visitor, source) ->
                db.update(visitor.getSql(), source));
    }

    private static <T> T sendRequest(Request request, Class clazz, BiFunction<SqlVisitor, SqlParameterSource, T> execute) {
        if (request.isReuse()) {
            request = (Request) ((SqlAst) request).copy();
        }
        request=request.invokePlugin(clazz);
        ValueParser parser = new JdbcTemplateParser().invokePlugin(clazz);
        SqlVisitor visitor = new MysqlVisitor(getTableName(clazz), parser).invokePlugin(clazz);
        try {
            ((SqlAst) request).accept(visitor);
        } catch (IllegalConditionException e) {
            if (request.getOnSyntaxError() == null) {
                throw e;
            } else {
                return (T) request.getOnSyntaxError().apply(e);
            }
        }
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(parser.getParamMap());
        return execute.apply(visitor, sqlParameterSource);

    }

    private static String getTableName(Class clazz) {
        return RowMapping.of(clazz).getTableName();
    }
}