/**
 * @(#)NowAutoFillHandler.java, 10月 07, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.plugins.autofill;

import io.github.skycloud.fastdao.core.ast.request.Request;

import java.util.Date;

/**
 * @author yuntian
 */
public class NowDateAutoFillHandler implements AutoFillHandler<Date> {

    @Override
    public Date handle(Request request) {
        return new Date();
    }
}