package com.epam.ta.reportportal.entity.user;

import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Andrei Varabyeu
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "project_user", schema = "public")
public class ProjectUser implements Serializable {

	@EmbeddedId
	private ProjectUserId id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@MapsId("project_id")
	private Project project;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@MapsId("user_id")
	private User user;

	@Column(name = "project_role")
	@Enumerated(EnumType.STRING)
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
}
