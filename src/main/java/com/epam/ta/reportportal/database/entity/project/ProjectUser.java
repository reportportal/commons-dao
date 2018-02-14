package com.epam.ta.reportportal.database.entity.project;

import com.epam.ta.reportportal.database.entity.enums.ProjectRoleEnum;
import com.epam.ta.reportportal.database.entity.user.Users;

/**
 * @author Pavel Bortnik
 */
public class ProjectUser {

	private Project project;

	private Users user;

	private ProjectRoleEnum projectRole;

	public ProjectUser() {
	}

	public ProjectUser(Project project, Users user, ProjectRoleEnum projectRole) {
		this.project = project;
		this.user = user;
		this.projectRole = projectRole;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public ProjectRoleEnum getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(ProjectRoleEnum projectRole) {
		this.projectRole = projectRole;
	}
}
