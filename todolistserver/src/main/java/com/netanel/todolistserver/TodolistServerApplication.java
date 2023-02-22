package com.netanel.todolistserver;

import com.netanel.todolistserver.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class TodolistServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistServerApplication.class, args);
	}

}
