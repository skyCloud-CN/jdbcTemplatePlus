/**
 * @(#)SqlGenerator.java, 9月 26, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast;

import io.github.skycloud.fastdao.core.ast.visitor.Visitor;
import io.github.skycloud.fastdao.core.plugins.Pluggable;

/**
 * @author yuntian
 */
public interface SqlAst extends Pluggable {

    void accept(Visitor visitor);

    SqlAst copy();
}