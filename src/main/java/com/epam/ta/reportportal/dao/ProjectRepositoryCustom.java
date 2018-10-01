package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.project.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryCustom extends FilterableRepository<Project> {

	Optional<String> findPersonalProjectName(String username);

	List<Project> findUserProjects(String username);
}
