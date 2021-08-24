package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringBootCorsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCorsApplication.class, args);
	}
}
