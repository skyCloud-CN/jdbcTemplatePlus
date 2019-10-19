/**
 * @(#)SqlHelper.java, 9月 30, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.jdbctemplate.plus;

import io.github.skycloud.fastdao.core.RequestProcessor;
import io.github.skycloud.fastdao.core.SqlVisitor;
import io.github.skycloud.fastdao.core.ast.MysqlVisitor;
import io.github.skycloud.fastdao.core.ast.Request;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.ValueParser;
import io.github.skycloud.fastdao.core.ast.request.CountRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest.DefaultQueryRequest;
import io.github.skycloud.fastdao.core.ast.request.DeleteRequest;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest;
import io.github.skycloud.fastdao.core.ast.request.QueryRequest;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import javafx.util.Pair;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @author yuntian
 */
public class JdbcTemplateSqlHelper {

    public static int update(NamedParameterJdbcOperations db, UpdateRequest request, Class clazz) {
        request=(UpdateRequest)((SqlAst)request).copy();
        return Optional.ofNullable(sendRequest(db, request, clazz, (visitor, source) ->
                db.update(visitor.getSql(), source))).orElse(0);
    }

    public static Pair<Integer, Number> insert(NamedParameterJdbcOperations db, InsertRequest request, Class clazz) {
        request=(InsertRequest)((SqlAst)request).copy();
        return Optional.ofNullable(sendRequest(db, request, clazz, (visitor, source) -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int count = db.update(visitor.getSql(), source, keyHolder);
            Pair<Integer, Number> pair = new Pair<>(count, keyHolder.getKey());
            return pair;
        })).orElse(new Pair<>(0, null));
    }

    public static <T> List<T> select(NamedParameterJdbcOperations db, QueryRequest request, Class<T> clazz) {
        DefaultQueryRequest copyRequest=(DefaultQueryRequest)((SqlAst)request).copy();
        return Optional.ofNullable(sendRequest(db, copyRequest, clazz, (visitor, source) ->
                db.query(visitor.getSql(), source, RowMappers.of(clazz).getMapper(copyRequest.getSelectFields())))).orElse(Collections.emptyList());
    }

    public static <T> int count(NamedParameterJdbcOperations db, CountRequest request, Class<T> clazz) {
        request=(CountRequest)((SqlAst)request).copy();
        return Optional.ofNullable(sendRequest(db, request, clazz, (visitor, source) ->
                db.queryForObject(visitor.getSql(), source, Integer.class))).orElse(0);

    }

    public static int delete(NamedParameterJdbcOperations db, DeleteRequest request, Class clazz) {
        request=(DeleteRequest)((SqlAst)request).copy();
        return Optional.ofNullable(sendRequest(db, request, clazz, (visitor, source) ->
                db.update(visitor.getSql(), source))).orElse(0);
    }

    private static <T> T sendRequest(NamedParameterJdbcOperations db, Request request, Class clazz, BiFunction<SqlVisitor, SqlParameterSource, T> execute) {
        ValueParser parser = new JdbcTemplateParser().invokePlugin(clazz);
        SqlVisitor visitor = new MysqlVisitor(getTableName(clazz), parser);
        RequestProcessor processor = new RequestProcessor(visitor, request, clazz);
        if (!processor.process()) {
            return null;
        }
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(parser.getParamMap());
        return execute.apply(visitor, sqlParameterSource);

    }

    private static String getTableName(Class clazz) {
        return RowMapping.of(clazz).getTableName();
    }
}