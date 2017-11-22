package com.obs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ObsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObsApplication.class, args);
	}
}
