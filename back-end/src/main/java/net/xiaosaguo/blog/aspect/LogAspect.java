package net.xiaosaguo.blog.aspect;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * description: 使用aop记录请求日志
 *
 * @author xiaosaguo
 * @version 2 xiaosaguo 在日志对象中增加请求方式
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(* net.xiaosaguo.blog.web..*(..))")
    public void log() {
        /// 切点表达式 execution([方法的可见性] 返回值类型 [方法所在类的全路径名] 方法名(参数类型列表) [方法抛出的异常类型])
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        log.debug("------request-----before------start--------");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String handler = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestParam requestParam = new RequestParam(method, url, ip, handler, args);
        log.info("\n{}", requestParam);
        log.debug("------request-----before------end----------");
    }

    @After("log()")
    public void doAfter() {
        log.debug("------request-----after------start--------");
        log.debug("------request-----after------end----------");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        log.debug("------request-----result------start--------");
        log.info("\nRequestResult : {}", result);
        log.debug("------request-----result------end----------");
    }

    @AllArgsConstructor
    private static class RequestParam {
        private String method;
        private String url;
        private String ip;
        private String handler;
        private Object[] args;

        @Override
        public String toString() {
            return "RequestParam : " +
                    "\n{" +
                    "\n\tmethod  = '" + method + "'," +
                    "\n\turl     = '" + url + "'," +
                    "\n\tip      = '" + ip + "'," +
                    "\n\thandler = '" + handler + "'," +
                    "\n\targs    = " + Arrays.toString(args) +
                    "\n}";
        }
    }

}
