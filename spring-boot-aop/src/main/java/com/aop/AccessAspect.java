package com.aop;

import com.annotation.AccessLimit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 这里使用 aop 来实现。全局的拦截器其实更适合 限流场景。
 *              这里使用guava模拟redis缓存。
 * @author: LinQin
 * @date: 2019/11/26
 */
@Aspect
@Component
public class AccessAspect {
    private int count;


    @Pointcut("@annotation(com.annotation.AccessLimit)")
    public void webLog(){
    }

    @Order(2)
    @Before("webLog()")
    public void Before(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取注解
        AccessLimit annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(AccessLimit.class);

        if (count > annotation.maxCount()) return;
        System.out.println("继续访问！");
        count++;
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容

    }
}
