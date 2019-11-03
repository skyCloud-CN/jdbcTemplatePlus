/**
 * @(#)SqlGenerator.java, 9月 26, 2019.
 * <p>
 * Copyright 2019 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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