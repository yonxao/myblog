package net.xiaosaguo.myblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * description 参数有误异常
 *
 * @author guoyx
 * @date 2018/8/25
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadParamException extends RuntimeException {

    public static final Long serialVersionUID = 1L;

    public BadParamException() {
    }

    public BadParamException(String message) {
        super(message);
    }

    public BadParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
