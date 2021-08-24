package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Description: 限流注解，放在类上全限流，单个method也限流
 * author: linqin
 * date: 2021/04/23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RateLimiter {
    // 时间间隔
    int period() default 5;

    // 单位(例:分钟/秒/毫秒) 默认: MINUTES
    TimeUnit timeunit() default TimeUnit.MINUTES;

    // 限制访问次数
    int count() default 30;

    String prefix() default "OPENAPI";
}