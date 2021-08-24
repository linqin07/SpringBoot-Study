package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringBootDruidApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDruidApplication.class, args);
	}
}
