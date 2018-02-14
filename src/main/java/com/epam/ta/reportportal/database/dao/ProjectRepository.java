package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pavel Bortnik
 */
public interface ProjectRepository extends JpaRepository<Project, Integer>, ProjectRepositoryCustom {
}
