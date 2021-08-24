package com.filter;

import com.annotation.AccessLimit;
import com.google.common.collect.Maps;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description: 这里使用map代替redis的数据记录。
 * @author: LinQin
 * @date: 2019/11/26
 */
public class AccessLimitHandle implements HandlerInterceptor {
    private Map cache = Maps.newConcurrentMap();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器！");
        if (handler instanceof HandlerMethod) {
            AccessLimit limit = ((HandlerMethod) handler).getMethodAnnotation(AccessLimit.class);
            if (limit == null) {
                return true;
            }

            Integer count = (Integer) cache.get(request.getRequestURI());
            if (count == null) {
                cache.put(request.getRequestURI(), 1);
            } else {
                if (count > limit.maxCount()) return false;

                cache.put(request.getRequestURI(), ++count);
            }
        }
        return true;
    }

}
