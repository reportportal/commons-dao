package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.project.ProjectUser;

/**
 * @author Pavel Bortnik
 */
public interface ProjectRepositoryCustom {

	ProjectUser selectProjectUser(String projectName, String userName);

}
