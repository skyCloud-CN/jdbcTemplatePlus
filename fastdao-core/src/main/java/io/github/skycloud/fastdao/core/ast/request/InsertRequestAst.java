/**
 * @(#)InsertRequestAst.java, 11月 01, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.skycloud.fastdao.core.ast.request;

import com.google.common.collect.Maps;
import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.visitor.Visitor;
import io.github.skycloud.fastdao.core.exceptions.IllegalConditionException;
import io.github.skycloud.fastdao.core.exceptions.IllegalRequestException;
import io.github.skycloud.fastdao.core.models.Column;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * @author yuntian
 */

public class InsertRequestAst implements InsertRequest, SqlAst {

    private Map<String, Object> updateFields = Maps.newLinkedHashMap();

    private Function<IllegalConditionException, ?> onSyntaxError;

    private Map<String, Object> onDuplicateKeyUpdateFields = Maps.newLinkedHashMap();

    private boolean reuse = true;

    @Override
    public InsertRequest addUpdateField(Column field, Object value) {
        updateFields.put(field.getName(), value);
        return this;
    }

    @Override
    public InsertRequest addUpdateField(String field, Object value) {
        updateFields.put(field, value);
        return this;
    }

    @Override
    public InsertRequest addOnDuplicateUpdateField(Column... fields) {
        for (Column field : fields) {
            addOnDuplicateUpdateField(field.getName(), null);
        }
        return this;
    }

    @Override
    public InsertRequest addOnDuplicateUpdateField(String... fields) {
        for (String field : fields) {
            addOnDuplicateUpdateField(field, null);
        }
        return this;
    }

    @Override
    public InsertRequest addOnDuplicateUpdateField(Column field, Object value) {
        addOnDuplicateUpdateField(field.getName(), value);
        return this;
    }

    @Override
    public InsertRequest addOnDuplicateUpdateField(String field, Object value) {
        onDuplicateKeyUpdateFields.put(field, value);
        return this;
    }

    @Override
    public InsertRequest addOnDuplicateUpdateField(Collection fields) {
        for (Object field : fields) {
            if (field instanceof Column) {
                addOnDuplicateUpdateField(((Column) field).getName(), null);
            } else if (field instanceof String) {
                addOnDuplicateUpdateField((String) field, null);
            } else {
                throw new IllegalRequestException("only Column, String is allowed as onDuplicateUpdateField type");
            }
        }
        return this;
    }

    @Override
    public Map<String, Object> getUpdateFields() {
        return updateFields;
    }

    public Map<String, Object> getOnDuplicateKeyUpdateFields() {
        return onDuplicateKeyUpdateFields;
    }

    @Override
    public <T extends Request> T onSyntaxError(Function<IllegalConditionException, ?> action) {
        onSyntaxError = action;
        return (T) this;
    }

    @Override
    public Function<IllegalConditionException, ?> getOnSyntaxError() {
        return onSyntaxError;
    }

    @Override
    public <T extends Request> T notReuse() {
        reuse = false;
        return (T) this;
    }

    @Override
    public boolean isReuse() {
        return reuse;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SqlAst copy() {
        io.github.skycloud.fastdao.core.ast.request.InsertRequestAst request = new io.github.skycloud.fastdao.core.ast.request.InsertRequestAst();
        request.updateFields = Maps.newLinkedHashMap(updateFields);
        request.onSyntaxError = onSyntaxError;
        request.onDuplicateKeyUpdateFields = onDuplicateKeyUpdateFields;
        return request;
    }


}