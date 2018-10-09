/*
 * Copyright 2016 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-dao
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.epam.ta.reportportal.personal;

import com.epam.ta.reportportal.dao.AttributeRepository;
import com.epam.ta.reportportal.dao.ProjectRepository;
import com.epam.ta.reportportal.entity.attribute.Attribute;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.entity.user.ProjectUser;
import com.epam.ta.reportportal.entity.user.User;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.Set;

import static com.epam.ta.reportportal.entity.project.ProjectUtils.defaultProjectAttributes;
import static com.epam.ta.reportportal.entity.project.ProjectUtils.setDefaultEmailConfiguration;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Generates Personal project for provided user
 *
 * @author <a href="mailto:andrei_varabyeu@epam.com">Andrei Varabyeu</a>
 */
@Service
public final class PersonalProjectService {

	public static final String PERSONAL_PROJECT_POSTFIX = "_personal";
	private final ProjectRepository projectRepository;
	private final AttributeRepository attributeRepository;

	@Autowired
	public PersonalProjectService(ProjectRepository projectRepository, AttributeRepository attributeRepository) {
		this.projectRepository = projectRepository;
		this.attributeRepository = attributeRepository;
	}

	/**
	 * Prefix from username with replaced dots as underscores
	 *
	 * @param username Name of user
	 * @return Corresponding personal project name
	 */
	@VisibleForTesting
	private String generatePersonalProjectName(String username) {
		String initialName = getProjectPrefix(username);

		String name = initialName;
		//iterate until we find free project name
		for (int i = 1; projectRepository.existsByName(name); i++) {
			name = initialName + "_" + i;
		}

		return name;
	}

	/**
	 * Generates personal project for provided user
	 *
	 * @param user User project should be created for
	 * @return Built Project object
	 */
	public Project generatePersonalProject(User user) {
		Project project = new Project();
		project.setName(generatePersonalProjectName(user.getLogin()));
		project.setCreationDate(Date.from(ZonedDateTime.now().toInstant()));

		ProjectUser projectUser = new ProjectUser().withUser(user).withProjectRole(ProjectRole.PROJECT_MANAGER).withProject(project);
		project.setUsers(ImmutableSet.<ProjectUser>builder().add(projectUser).build());

		project.setAddInfo("Personal project of " + (isNullOrEmpty(user.getFullName()) ? user.getLogin() : user.getFullName()));

		Set<Attribute> defaultAttributes = attributeRepository.getDefaultProjectAttributes();

		project.setProjectAttributes(defaultProjectAttributes(project, defaultAttributes));

		/* Default email configuration */
		setDefaultEmailConfiguration(project);

		return project;
	}

	/**
	 * Generates prefix for personal project
	 *
	 * @param username Name of user
	 * @return Prefix
	 */
	public String getProjectPrefix(String username) {
		String projectName = username.replaceAll("\\.", "_");
		return (projectName + PERSONAL_PROJECT_POSTFIX).toLowerCase();
	}
}
