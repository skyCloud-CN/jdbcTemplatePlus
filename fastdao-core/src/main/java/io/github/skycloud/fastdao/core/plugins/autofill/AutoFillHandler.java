/**
 * @(#)AutoFillHandler.java, 10月 07, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.plugins.autofill;

import io.github.skycloud.fastdao.core.ast.request.Request;

/**
 * @author yuntian
 */
public interface AutoFillHandler<T> {

    T handle(Request request);
}