package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class SpringBootBeanApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBeanApplication.class, args);
    }

    @Bean
    @Scope("prototype")
    public void testBean() {
        System.out.println("haha");
    }

}
