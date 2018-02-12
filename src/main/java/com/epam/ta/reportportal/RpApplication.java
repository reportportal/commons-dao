package com.epam.ta.reportportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Pavel Bortnik
 */
@SpringBootApplication
@EnableJpaAuditing
public class RpApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpApplication.class, args);
	}

}
