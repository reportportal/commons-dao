/*
 * Copyright (C) 2018 EPAM Systems
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

package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.entity.item.issue.IssueType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Pavel Bortnik
 */
@Entity
@Table(name = "issue_type_project")
public class ProjectIssueType implements Serializable {

	@EmbeddedId
	private ProjectIssueTypeId id = new ProjectIssueTypeId();

	@MapsId("typeId")
	@ManyToOne(fetch = FetchType.LAZY)
	private IssueType issueType;

	@MapsId("projectId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Project project;

	public ProjectIssueTypeId getId() {
		return id;
	}

	public void setId(ProjectIssueTypeId id) {
		this.id = id;
	}

	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ProjectIssueType withProjectUserId(ProjectIssueTypeId id) {
		this.id = id;
		return this;
	}

	public ProjectIssueType withIssueType(IssueType issueType) {
		this.issueType = issueType;
		return this;
	}

	public ProjectIssueType withProject(Project project) {
		this.project = project;
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
		ProjectIssueType that = (ProjectIssueType) o;
		return Objects.equals(issueType, that.issueType) && Objects.equals(project, that.project);
	}

	@Override
	public int hashCode() {

		return Objects.hash(issueType, project);
	}
}
