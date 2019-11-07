/**
 * @(#)ILikeCondition.java, 10月 20, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.conditions;


import io.github.skycloud.fastdao.core.ast.SqlAst;
import io.github.skycloud.fastdao.core.ast.visitor.Visitor;
import lombok.Getter;

/**
 * @author yuntian
 */
public interface LikeCondition extends Condition {

    LikeCondition matchLeft();

    LikeCondition matchRight();


}