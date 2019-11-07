/**
 * @(#)RowMapperWrapper.java, 10月 19, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.jdbctemplate.plus;

import com.google.common.collect.Maps;
import io.github.skycloud.fastdao.core.ast.model.SqlFunction;
import io.github.skycloud.fastdao.core.mapping.ColumnMapping;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.mapping.TypeHandler;
import io.github.skycloud.fastdao.core.models.QueryResult;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import io.github.skycloud.fastdao.core.util.SingletonCache;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author yuntian
 */
public class RowMapperWrapper<T> {

    private final static Map<Class, RowMapperWrapper> rowMappings = new SingletonCache<>(new ConcurrentHashMap<>(), RowMapperWrapper::newInstance);

    private Class<T> clazz;

    private MetaClass metaClass;

    private RowMapping rowMapping;


    public static <T> RowMapperWrapper<T> of(Class<T> clazz) {
        return rowMappings.get(clazz);
    }

    private static RowMapperWrapper newInstance(Class clazz) {
        return new RowMapperWrapper(clazz);
    }

    private RowMapperWrapper(Class clazz) {
        this.clazz = clazz;
        this.metaClass = MetaClass.of(clazz);
        this.rowMapping = RowMapping.of(clazz);
    }

    public RowMapper<T> getMapper(Collection<Object> columns) {
        return (rs, rowNum) -> {
            try {
                return buildData(rs, columns);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        };
    }

    public RowMapper<QueryResult<T>> getMapperAdvance(Collection<Object> columns) {
        return (rs, rowNum) -> {
            try {
                QueryResult result = new QueryResult<>();
                Object data = buildData(rs, columns);
                Map<String, Object> functionData = Maps.newHashMap();
                List<SqlFunction> functions = (List) columns.stream().filter(x -> x instanceof SqlFunction).collect(Collectors.toList());
                if (columns.size() == 1&&functions.size()==1) {
                    functionData.put(functions.get(0).genKey(), rs.getLong(1));
                } else {
                    for (SqlFunction function : functions) {
                        functionData.put(function.genKey(), rs.getLong(function.genKey()));
                    }
                }
                result.setData(data);
                result.setSqlFunData(functionData);
                return result;
            } catch (Exception e) {
                throw new RuntimeException();
            }
        };
    }

    private T buildData(ResultSet resultSet, Collection<Object> columns) throws Exception {
        T instance = clazz.newInstance();
        Collection<ColumnMapping> columnMappings;
        if (CollectionUtils.isEmpty(columns)) {
            columnMappings = rowMapping.getColumnMapping();
        } else {
            columnMappings = columns.stream().filter(x -> x instanceof String).map(x -> (String) x).map(rowMapping::getColumnMappingByColumnName).collect(Collectors.toList());
        }
        for (ColumnMapping cm : columnMappings) {
            TypeHandler handler = cm.getHandler();
            Object value = handler.getResult(resultSet, cm.getColumnName());
            metaClass.getMetaField(cm.getFieldName()).invokeSetter(instance, value);
        }
        return instance;
    }
}