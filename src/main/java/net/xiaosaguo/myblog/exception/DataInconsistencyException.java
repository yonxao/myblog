package net.xiaosaguo.myblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * description: 数据不一致异常，应用场景：
 * <p>
 * 1. 数据库中的数据理应只从在一条，根据 ID 查询、修改或删除受影响的条数却是多条：
 * 可能原因：
 * A: 表中没建主键，与云数仓表结构不一致。
 * B: 不是在应用中操作，而是直接在数据库中修改了数据。
 * <p>
 * 2. 数据不完整：例如维度信息分表存在两个表中，字典表和说明表，根据 ID 操作时只能在一个表中查到数据：
 * 可能原因：
 * A: 不是在应用中新建或删除操作，而是直接在数据库中操作数据，但是没有在两个表中都操作。
 *
 * @author guoyx
 * @date 2018/8/16
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataInconsistencyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataInconsistencyException() {
    }

    public DataInconsistencyException(String message) {
        super(message);
    }

    public DataInconsistencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
