package com.securecar.safedrive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SafedriveApplication {

	public static void main(String[] args) {

		SpringApplication.run(SafedriveApplication.class, args);
	}

}
