/**
 * @(#)SqlHelper.java, 9月 30, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.jdbctemplate.plus;

import com.google.common.collect.Lists;
import io.github.skycloud.fastdao.core.SqlVisitor;
import io.github.skycloud.fastdao.core.ast.MysqlVisitor;
import io.github.skycloud.fastdao.core.ast.Request;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.ValueParser;
import io.github.skycloud.fastdao.core.ast.model.SqlFun;
import io.github.skycloud.fastdao.core.ast.request.CountRequest;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest.QueryRequestAst;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest;
import io.github.skycloud.fastdao.core.exceptions.IllegalConditionException;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.util.QueryResult;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author yuntian
 */
public class JdbcTemplateSqlHelper {

    public static int update(NamedParameterJdbcOperations db, UpdateRequest request, Class clazz) {
        request = (UpdateRequest) ((SqlAst) request).copy();
        return sendRequest(db, request, clazz, (visitor, source) ->
                db.update(visitor.getSql(), source));
    }

    public static Number[] insert(NamedParameterJdbcOperations db, InsertRequest request, Class clazz) {
        request = (InsertRequest) ((SqlAst) request).copy();
        return sendRequest(db, request, clazz, (visitor, source) -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int count = db.update(visitor.getSql(), source, keyHolder);
            return new Number[]{count, keyHolder.getKey()};
        });
    }

    public static <T> List<T> select(NamedParameterJdbcOperations db, QueryRequest request, Class<T> clazz) {
        QueryRequestAst copyRequest = (QueryRequestAst) ((SqlAst) request).copy();
        return sendRequest(db, copyRequest, clazz, (visitor, source) ->
                db.query(visitor.getSql(), source, RowMappers.of(clazz).getMapper(copyRequest.getSelectFields())));
    }

    public static <T> List<QueryResult<T>> selectAdvance(NamedParameterJdbcOperations db, QueryRequest request, Class<T> clazz) {
        QueryRequestAst copyRequest = (QueryRequestAst) ((SqlAst) request).copy();
        return sendRequest(db, copyRequest, clazz, (visitor, source) ->
                db.query(visitor.getSql(), source, RowMappers.of(clazz).getMapperAdvance(copyRequest.getSelectFields())));
    }

    public static <T> int count(NamedParameterJdbcOperations db, CountRequest request, Class<T> clazz) {
        request = (CountRequest) ((SqlAst) request).copy();
        return sendRequest(db, request, clazz, (visitor, source) ->
                db.queryForObject(visitor.getSql(), source, Integer.class));

    }

    public static int delete(NamedParameterJdbcOperations db, DeleteRequest request, Class clazz) {
        request = (DeleteRequest) ((SqlAst) request).copy();
        return sendRequest(db, request, clazz, (visitor, source) ->
                db.update(visitor.getSql(), source));
    }

    private static <T> T sendRequest(NamedParameterJdbcOperations db, Request request, Class clazz, BiFunction<SqlVisitor, SqlParameterSource, T> execute) {
        ValueParser parser = new JdbcTemplateParser().invokePlugin(clazz);
        SqlVisitor visitor = new MysqlVisitor(getTableName(clazz), parser);
        request.invokePlugin(clazz);
        visitor.invokePlugin(clazz);
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