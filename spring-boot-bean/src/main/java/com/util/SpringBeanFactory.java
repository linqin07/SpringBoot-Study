package com.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Description: spring容器工厂
 * @author: LinQin
 * @date: 2019/03/12
 */
//@Component
public class SpringBeanFactory implements ApplicationContextAware {

    @Autowired
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.err.println(applicationContext);
    }

    public static <T> T getBean(Class<T> c){
        return applicationContext.getBean(c);
    }

    public static <T> T getBean(String name,Class<T> clazz){
        return applicationContext.getBean(name,clazz);
    }
}
