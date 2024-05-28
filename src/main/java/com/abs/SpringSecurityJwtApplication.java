package com.abs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityJwtApplication {
	
	private static final Logger Logger=LoggerFactory.getLogger(SpringSecurityJwtApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtApplication.class, args);
		Logger.info("Starting Application");
	}

}
