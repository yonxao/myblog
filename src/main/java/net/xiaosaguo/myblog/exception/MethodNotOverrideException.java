package net.xiaosaguo.myblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * description: 方法没有被子类重写异常，应用场景：
 * <p>
 * 通用类中有些通用方法其实不通用，有些类不需要或者没有这个方法，但又不能让子类强制实现，
 * 所以这种方法要使用必须子类重写才生效，如果子类没有重写，这个方法是不能使用的，就报这个异常。
 *
 * @author guoyx
 * @date 2018/8/22 11:02
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MethodNotOverrideException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MethodNotOverrideException() {
    }

    public MethodNotOverrideException(String message) {
        super(message);
    }

    public MethodNotOverrideException(String message, Throwable cause) {
        super(message, cause);
    }

}
