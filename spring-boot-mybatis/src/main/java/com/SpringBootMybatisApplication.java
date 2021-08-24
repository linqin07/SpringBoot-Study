package com;

import com.upload.CustomMultipartResolver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.multipart.MultipartResolver;

@SpringBootApplication(exclude = MultipartAutoConfiguration.class)
@MapperScan("com.dao")
@EnableAspectJAutoProxy(exposeProxy = true)
public class SpringBootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisApplication.class, args);
    }

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        return new CustomMultipartResolver();
    }
}
