package com.filter;

import com.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author: LinQin
 * @date: 2021/04/23
 */
@Component
public class LimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, Integer> limitRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 获取方法上的注解或者类上面的注解
        RateLimiter rateLimiter = handlerMethod.getMethodAnnotation(RateLimiter.class);
        if (rateLimiter == null) {
            rateLimiter = handlerMethod.getBeanType().getAnnotation(RateLimiter.class);
            if (rateLimiter == null) return true;
        }

        if (rateLimiter != null) {
            int period = rateLimiter.period();
            TimeUnit timeunit = rateLimiter.timeunit();
            int count = rateLimiter.count();
            String redisKey = rateLimiter.prefix() + ":" + handlerMethod.getMethod().getName();
            Integer num = limitRedisTemplate.opsForValue().get(redisKey);
            if (num != null) {
                if (count > num) {
                    limitRedisTemplate.opsForValue().increment(redisKey, 1);
                } else {
                    throw new Exception("访问过于频繁，请稍后再试！");
                }
            } else {
                limitRedisTemplate.opsForValue().set(redisKey, 1, period, timeunit);
            }
        }
        return true;
    }
}