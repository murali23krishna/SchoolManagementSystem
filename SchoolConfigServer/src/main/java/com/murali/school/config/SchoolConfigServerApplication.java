package com.murali.school.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SchoolConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolConfigServerApplication.class, args);
	}

}
