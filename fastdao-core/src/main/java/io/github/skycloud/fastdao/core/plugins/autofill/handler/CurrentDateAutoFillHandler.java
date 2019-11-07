
package io.github.skycloud.fastdao.core.plugins.autofill.handler;

import io.github.skycloud.fastdao.core.ast.request.Request;
import io.github.skycloud.fastdao.core.plugins.autofill.AutoFillHandler;

import java.util.Date;

/**
 * @author yuntian
 */
public class CurrentDateAutoFillHandler implements AutoFillHandler<Date> {
    @Override
    public Date handle(Request request) {
        return new Date();
    }
}