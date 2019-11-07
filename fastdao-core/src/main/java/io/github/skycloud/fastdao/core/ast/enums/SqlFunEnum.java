/**
 * @(#)SqlFunctionEnum.java, 10月 27, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.enums;

/**
 * @author yuntian
 */
public enum SqlFunEnum {
    AVG,
    MIN,
    MAX,
    SUM,
    COUNT;

    public String genKey(String field) {
        return name() + '|' + (field == null ? "ALL" : field);
    }
}