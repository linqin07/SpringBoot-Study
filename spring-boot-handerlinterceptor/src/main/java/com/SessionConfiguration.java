package com;

import com.filter.CostomHandlerMethodArgumentResolver;
import com.filter.ParamHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 注册
 * author: LinQin
 * date: 2018/07/05
 */
@Configuration
public class SessionConfiguration implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
////        InterceptorRegistration interceptor = registry.addInterceptor(new SessionInterceptor());
//        InterceptorRegistration interceptor = registry.addInterceptor(new AccessLimitHandle())
//                ;
//        /**
//         * 添加拦截的路径
//         * /为根路径
//         * /*为一级路径
//         * /** 为所有路径包括多级
//         */
//        interceptor.addPathPatterns("/**");
//
//        //排除不拦截的，包括自己登录的页面不用拦截
//        interceptor.excludePathPatterns("/login");
//        interceptor.excludePathPatterns("/user/handle");
//    }

    @Bean
    public CostomHandlerMethodArgumentResolver costomHandlerMethodArgumentResolver() {
        return new CostomHandlerMethodArgumentResolver();
    }

    /**
     * 注册入参管理,顺序问题会造成无法识别map参数
     * @param resolvers
     */
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(costomHandlerMethodArgumentResolver());
//    }


    // 控制顺序
    @Autowired
    private RequestMappingHandlerAdapter adapter;
    @PostConstruct
    public void injectSelfMethodArgumentResolver()
    {
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
        argumentResolvers.add(costomHandlerMethodArgumentResolver());
        argumentResolvers.addAll(adapter.getArgumentResolvers());
        adapter.setArgumentResolvers(argumentResolvers);
    }

    @Bean
    public ParamHandle paramHandle() {
        return new ParamHandle();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptor = registry.addInterceptor(paramHandle());
        /**
         * 添加拦截的路径
         * /为根路径
         * /*为一级路径
         * /** 为所有路径包括多级
         */
        interceptor.addPathPatterns("/**");
    }
}
