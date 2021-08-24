package com.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/03/12
 */
@Component
public class MyBean implements InitializingBean, BeanPostProcessor, BeanFactoryPostProcessor, DisposableBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean初始化Bean时执行一次afterPropertiesSet");
    }



    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("bean初始化前执行postProcessBeforeInitialization" + " " + beanName);
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("bean初始化后执行postProcessAfterInitialization" + " " + beanName);
        return null;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("bean初始化后执行一次postProcessBeanFactory");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("销毁");
    }
}