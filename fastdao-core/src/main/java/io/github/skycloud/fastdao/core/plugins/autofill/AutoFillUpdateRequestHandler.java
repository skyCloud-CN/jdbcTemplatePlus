/**
 * @(#)AutoFillRequstHandler.java, 10月 12, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins.autofill;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest;
import io.github.skycloud.fastdao.core.ast.request.UpdateRequest.DefaultUpdateRequest;
import io.github.skycloud.fastdao.core.mapping.ColumnMapping;
import io.github.skycloud.fastdao.core.mapping.RowMapping;
import io.github.skycloud.fastdao.core.plugins.PluggableHandler;
import io.github.skycloud.fastdao.core.reflection.MetaClass;
import io.github.skycloud.fastdao.core.reflection.MetaField;
import io.github.skycloud.fastdao.core.util.SingletonCache;

import java.util.Map;
import java.util.Set;

/**
 * @author yuntian
 */
public class AutoFillUpdateRequestHandler extends AutoFillRequestHandler<UpdateRequest> {

    public AutoFillUpdateRequestHandler() {
        super(AutoFillOperation.UPDATE);
    }

    @Override
    public DefaultUpdateRequest handle(UpdateRequest pluggable, Class clazz) {
        DefaultUpdateRequest request=(DefaultUpdateRequest)pluggable;
        Map<String, AutoFillHandler> fieldAutoFillHandlerMap = autoFillHandlerMap.get(clazz);
        Set<String> prepareUpdate = request.getUpdateFields().keySet();
        Set<String> needUpdateFields = Sets.newHashSet(fieldAutoFillHandlerMap.keySet());
        needUpdateFields.removeAll(prepareUpdate);
        for (String needUpdateField : needUpdateFields) {
            AutoFillHandler handler = fieldAutoFillHandlerMap.get(needUpdateField);
            request.addUpdateField(needUpdateField, handler.handle(request));
        }
        return request;
    }

}