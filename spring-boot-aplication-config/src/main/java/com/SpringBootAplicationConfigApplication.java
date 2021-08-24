package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class SpringBootAplicationConfigApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SpringBootAplicationConfigApplication.class, args);
		Properties properties = PropertiesLoaderUtils.loadAllProperties("server.properties");
		System.out.println(properties.getProperty("server.i", "7200"));
	}
}
