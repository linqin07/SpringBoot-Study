package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/06
 */
@Configuration
public class ResourcesConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // super.addResourceHandlers(registry);
        //配置了静态资源的路径为/lin/qin/resources/**，那么只要访问地址前缀是/lin/qin/resources/，就会被自动转到项目根目录下的static文件夹内。
        registry.addResourceHandler("/lin/qin/**").addResourceLocations("classpath:/static/");
    }
}
