package net.xiaosaguo.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description: 后台登录拦截器
 *
 * @author xiaosaguo
 * @date 2020/04/26
 */
public class AdminLoginInterceptor extends HandlerInterceptorAdapter {

    public static final String USER_ATTRIBUTE_NAME_IN_SESSION = "user";
    public static final String ADMIN_LOGIN_VIEW = "/admin";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute(USER_ATTRIBUTE_NAME_IN_SESSION) == null) {
            response.sendRedirect(ADMIN_LOGIN_VIEW);
            return false;
        }
        return true;
    }
}
