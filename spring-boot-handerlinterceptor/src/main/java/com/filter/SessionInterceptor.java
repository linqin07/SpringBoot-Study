package com.filter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/05
 */
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器");
        if (request.getRequestURI().equals("/login")) {
            return true;
        }

        //验证session是否存在
        Object user = request.getSession().getAttribute("session_user");
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }
            return true;
    }
}
