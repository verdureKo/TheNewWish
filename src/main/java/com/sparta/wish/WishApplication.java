package com.sparta.wish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class WishApplication {

	public static void main(String[] args) {
		SpringApplication.run(WishApplication.class, args);
	}

}
