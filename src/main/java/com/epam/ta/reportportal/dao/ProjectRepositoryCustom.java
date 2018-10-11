package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryCustom extends FilterableRepository<Project> {

	Page<ProjectInfo> findProjectInfoByFilter(Filter filter, Pageable pageable);

	Optional<String> findPersonalProjectName(String username);

	List<Project> findUserProjects(String username);
}
