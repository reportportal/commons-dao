package com.epam.ta.reportportal;

import com.epam.ta.reportportal.database.dao.ReportPortalRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Pavel Bortnik
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(repositoryBaseClass = ReportPortalRepositoryImpl.class)
public class RpApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpApplication.class, args);
	}

}
