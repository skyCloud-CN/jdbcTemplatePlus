
package io.github.skycloud.fastdao.core.plugins.autofill.handler;

import io.github.skycloud.fastdao.core.ast.request.Request;
import io.github.skycloud.fastdao.core.plugins.autofill.AutoFillHandler;

import java.util.Date;

/**
 * @author yuntian
 */
public class CurrentTimestampAutoFillHandler implements AutoFillHandler<Long> {
    @Override
    public Long handle(Request request) {
        return System.currentTimeMillis();
    }
}