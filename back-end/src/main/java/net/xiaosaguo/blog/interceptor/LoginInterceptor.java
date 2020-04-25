package net.xiaosaguo.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description: 登录拦截器
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    public static final String USER_ATTRIBUTE_NAME_IN_SESSION = "user";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getSession().getAttribute(USER_ATTRIBUTE_NAME_IN_SESSION) == null) {
            response.sendRedirect("/admin");
            return false;
        }

        return true;
    }
}
