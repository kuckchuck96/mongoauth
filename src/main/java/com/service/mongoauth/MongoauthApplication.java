package com.service.mongoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MongoauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoauthApplication.class, args);
	}

}
