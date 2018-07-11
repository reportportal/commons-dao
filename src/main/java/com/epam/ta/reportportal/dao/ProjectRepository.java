package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.project.Project;

import java.util.Optional;

public interface ProjectRepository extends ReportPortalRepository<Project, Long>, ProjectRepositoryCustom {

	Optional<Project> findByName(String name);

	boolean existsByName(String name);
}
