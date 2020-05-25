package net.xiaosaguo.myblog.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * description: 自定义全局异常处理器
 *
 * @author xiaosaguo
 * @date 2020/04/14
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) {
        log.debug("------request-----exception------start--------");
        Long timestamp = System.currentTimeMillis();
        log.error("\nRequest URL : {}, Timestamp : {}, Exception : ", request.getRequestURL(), timestamp, e);

        ModelAndView mv = new ModelAndView();
        // 通用，注释中显示
        mv.addObject("url", request.getRequestURL());
        // 通用，注释中显示
        mv.addObject("timestamp", timestamp);
        // 通用，页面上显示
        /* TODO 这里可以优化为国际化信息
         *  1.维护一个国际化异常提示资源文件，
         *  2.在 java 代码中抛出 key，
         *  3.在此处使用根据 key 获取的国际化信息。
         */
        mv.addObject("digest", e.getMessage());
        // 通用，注释中显示
        mv.addObject("exception", e);
        // 通用，页面上显示
        mv.addObject("httpStatus", "发生异常");
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            // title 和 h2 标题，不同的异常值不同
            mv.addObject("httpStatus", responseStatus.value().value());
        }

        mv.setViewName("error/error");
        log.debug("------request-----exception--------end--------");
        return mv;
    }
}
