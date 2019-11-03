/**
 * @(#)ShardVisitorHandler.java, 10月 12, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins.shard;

import io.github.skycloud.fastdao.core.ast.visitor.SqlVisitor;
import io.github.skycloud.fastdao.core.plugins.PluggableHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yuntian
 */
public class ShardVisitorHandler implements PluggableHandler<SqlVisitor> {

    @Override
    public SqlVisitor handle(SqlVisitor pluggable, Class clazz) {
        if (StringUtils.isNotEmpty(ShardUtil.getCurrentSuffix())) {
            pluggable.setTableName(pluggable.getTableName() + ShardUtil.getCurrentSuffix());
        }
        return pluggable;
    }
}