/**
 * @(#)AutoFillInsertRequestHandler.java, 10月 13, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.plugins.autofill;

import com.google.common.collect.Sets;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest;
import io.github.skycloud.fastdao.core.ast.request.InsertRequest.InsertRequestAst;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yuntian
 */
public class AutoFillInsertRequestHandler extends AutoFillRequestHandler<InsertRequest> {



    public AutoFillInsertRequestHandler() {
        super(AutoFillOperation.INSERT);
    }

    @Override
    public InsertRequest handle(InsertRequest pluggable, Class clazz) {
        InsertRequestAst request=(InsertRequestAst)pluggable;
        Map<String, AutoFillHandler> fieldAutoFillHandlerMap = autoFillHandlerMap.get(clazz);
        Set<String> prepareUpdate = request.getInsertFields().entrySet().stream()
                .filter(entry -> entry.getValue() != null).map(Entry::getKey)
                .collect(Collectors.toSet());
        Set<String> needUpdateFields = Sets.newHashSet(fieldAutoFillHandlerMap.keySet());
        needUpdateFields.removeAll(prepareUpdate);
        for (String needUpdateField : needUpdateFields) {
            AutoFillHandler handler = fieldAutoFillHandlerMap.get(needUpdateField);
            request.addInsertField(needUpdateField, handler.handle(request));
        }
        return request;
    }

}