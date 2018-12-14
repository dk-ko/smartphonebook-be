package com.soda.phonebook;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class Application {
	
	public static final String APPLICATION_LOCATIONS=
			"spring.config.location="
			+ "classpath:application.yml,"
			+ "/app/config/soda/real-application.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class)
		.properties(APPLICATION_LOCATIONS)
		.run(args);
		
		log.warn("start");
	}
}
