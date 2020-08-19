/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.entity.user;

import com.epam.ta.reportportal.entity.enums.PostgreSQLEnumType;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Andrei Varabyeu
 */
@Entity
@TypeDef(name = "pqsql_enum", typeClass = PostgreSQLEnumType.class)
@Table(name = "project_user", schema = "public")
public class ProjectUser implements Serializable {

	@EmbeddedId
	private ProjectUserId id = new ProjectUserId();

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("projectId")
	private Project project;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	private User user;

	@Column(name = "project_role")
	@Enumerated(EnumType.STRING)
	@Type(type = "pqsql_enum")
	private ProjectRole projectRole;

	public ProjectUserId getId() {
		return id;
	}

	public void setId(ProjectUserId id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ProjectRole getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(ProjectRole projectRole) {
		this.projectRole = projectRole;
	}

	public ProjectUser withProjectUserId(ProjectUserId id) {
		this.id = id;
		return this;
	}

	public ProjectUser withUser(User user) {
		this.user = user;
		return this;
	}

	public ProjectUser withProject(Project project) {
		this.project = project;
		return this;
	}

	public ProjectUser withProjectRole(ProjectRole projectRole) {
		this.projectRole = projectRole;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ProjectUser that = (ProjectUser) o;
		return Objects.equals(id, that.id) && Objects.equals(project, that.project) && Objects.equals(user, that.user)
				&& projectRole == that.projectRole;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, project, user, projectRole);
	}
}
