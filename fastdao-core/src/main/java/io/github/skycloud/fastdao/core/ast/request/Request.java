/**
 * @(#)Request.java, 10月 12, 2019.
 * <p>
 *
 */
package io.github.skycloud.fastdao.core.ast.request;

import io.github.skycloud.fastdao.core.exceptions.IllegalConditionException;
import io.github.skycloud.fastdao.core.plugins.Pluggable;

import java.util.function.Function;

/**
 * @author yuntian
 * all request can be get from here by static method, or you can just new one
 * <p>
 * all request are provided to user by interface just to hide unsafe method
 */
public interface Request extends Pluggable {

    <T extends Request> T onSyntaxError(Function<IllegalConditionException, ?> action);

    Function<IllegalConditionException, ?> getOnSyntaxError();

    <T extends Request> T notReuse();

    boolean isReuse();

    static UpdateRequest updateRequest() {
        return new UpdateRequestAst();
    }

    static DeleteRequest deleteRequest() {
        return new DeleteRequestAst();
    }

    static InsertRequest insertRequest() {
        return new InsertRequestAst();
    }

    static CountRequest countRequest() {
        return new CountRequestAst();
    }

    static QueryRequest queryRequest() {
        return new QueryRequestAst();
    }
}