/**
 * @(#)AutoIncPluginTestModel.java, 10月 16, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.demo.model;

import io.github.skycloud.fastdao.core.annotation.PrimaryKey;
import io.github.skycloud.fastdao.core.annotation.Table;
import io.github.skycloud.fastdao.core.mapping.JdbcType;
import io.github.skycloud.fastdao.core.plugins.autofill.AutoFill;
import io.github.skycloud.fastdao.core.plugins.autofill.AutoFillValueEnum;
import io.github.skycloud.fastdao.core.plugins.autofill.RequestType;
import io.github.skycloud.fastdao.core.plugins.columnmap.ColumnMap;
import io.github.skycloud.fastdao.core.plugins.exclude.Exclude;
import io.github.skycloud.fastdao.core.plugins.logicdelete.LogicDelete;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author yuntian
 */
@Data
@ToString
@Table(tableName = "auto_inc")
public class AutoIncPluginTestModel {

    // test handler not exist
    @PrimaryKey
    @ColumnMap(jdbcType = JdbcType.INTEGER)
    private Long id;

    private String name;

    private String text;

    @ColumnMap(column = "longTime", jdbcType = JdbcType.BIGINT)
    private Date time;

    @ColumnMap(jdbcType = JdbcType.TIMESTAMP)
    @AutoFill(fillValue = AutoFillValueEnum.NOW, onOperation = {RequestType.INSERT, RequestType.UPDATE})
    private Long updated;

    @AutoFill(fillValue = AutoFillValueEnum.NOW, onOperation = RequestType.INSERT)
    private Date created;

    @LogicDelete
    private Boolean deleted;

    @Exclude
    private Integer exclude;
}
