package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.commons.MetaDataType;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.entity.project.email.EmailSenderCase;
import com.epam.ta.reportportal.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * @author Ivan Budayeu
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "MetaDataType", typeClass = MetaDataType.class)
@Table(name = "project", schema = "public")
public class Project implements Serializable {

	private static final long serialVersionUID = -263516611;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 64)
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonBackReference
	private Set<Integration> integrations = Sets.newHashSet();

	@Column(name = "additional_info")
	private String addInfo;

	@Transient
	@JsonSerialize
	private ProjectConfiguration configuration;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<ProjectAttribute> projectAttributes;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<UserConfig> users;

	@OneToMany(mappedBy = "project")
	@JsonBackReference
	private List<DemoDataPostfix> demoDataPostfix;

	@Column(name = "creation_date")
	private Date creationDate;

	@Type(type = "MetaDataType")
	@Column(name = "metadata")
	private Map<String, String> metadata;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "issue_type_project", joinColumns = { @JoinColumn(name = "project_id") }, inverseJoinColumns = {
			@JoinColumn(name = "issue_type_id") })
	private List<IssueType> issueTypes;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<EmailSenderCase> emailCases;


	public Project(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Project() {
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}

	public void setUsers(List<UserConfig> users) {
		this.users = users;
	}

	/*
	 * Null-safe getter
	 */
	public List<UserConfig> getUsers() {
		return users == null ? users = Collections.emptyList() : users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Integration> getIntegrations() {
		return integrations;
	}

	public void setIntegrations(Set<Integration> integrations) {
		this.integrations = integrations;
	}

	public Set<ProjectAttribute> getProjectAttributes() {
		return projectAttributes;
	}

	public List<IssueType> getIssueTypes() {
		return issueTypes;
	}

	public void setIssueTypes(List<IssueType> issueTypes) {
		this.issueTypes = issueTypes;
	}

	public void setProjectAttributes(Set<ProjectAttribute> projectAttributes) {
		this.projectAttributes = projectAttributes;
	}

	public Set<EmailSenderCase> getEmailCases() {
		return emailCases;
	}

	public void setEmailCases(Set<EmailSenderCase> emailCases) {
		this.emailCases = emailCases;
	}

	/**
	 * NULL-safe getter
	 *
	 * @return the configuration
	 */
	public ProjectConfiguration getConfiguration() {
		return new ProjectConfiguration(this.projectAttributes);
	}

	/**
	 * @param configuration the configuration to set
	 */
	public void setConfiguration(ProjectConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * NULL-safe getter
	 *
	 * @return the list of demo-data postfix
	 */
	public List<DemoDataPostfix> getDemoDataPostfix() {
		return demoDataPostfix == null ? demoDataPostfix = Collections.emptyList() : demoDataPostfix;
	}

	public void setDemoDataPostfix(List<DemoDataPostfix> demoDataPostfix) {
		this.demoDataPostfix = demoDataPostfix;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Project project = (Project) o;
		return Objects.equals(name, project.name) && Objects.equals(addInfo, project.addInfo) && Objects.equals(configuration,
				project.configuration
		) && Objects.equals(users, project.users) && Objects.equals(creationDate, project.creationDate) && Objects.equals(
				metadata, project.metadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, addInfo, configuration, users, creationDate);
	}

	@Entity
	@Table(name = "user_config")
	public static class UserConfig implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@ManyToOne(cascade = CascadeType.ALL)
		@JoinColumn(name = "user_id")
		private User user;

		@ManyToOne(cascade = CascadeType.ALL)
		@JoinColumn(name = "project_id")
		@JsonManagedReference
		private Project project;

		private ProjectRole proposedRole;
		private ProjectRole projectRole;

		public static UserConfig newOne() {
			return new UserConfig();
		}

		public UserConfig() {

		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public void setProjectRole(ProjectRole projectRole) {
			this.projectRole = projectRole;
		}

		public void setProposedRole(ProjectRole proposedRole) {
			this.proposedRole = proposedRole;
		}

		public ProjectRole getProjectRole() {
			return projectRole;
		}

		public ProjectRole getProposedRole() {
			return proposedRole;
		}

		public Project getProject() {
			return project;
		}

		public void setProject(Project project) {
			this.project = project;
		}

		public UserConfig withProposedRole(ProjectRole proposedRole) {
			this.proposedRole = proposedRole;
			return this;
		}

		public UserConfig withProjectRole(ProjectRole projectRole) {
			this.projectRole = projectRole;
			return this;
		}

		public UserConfig withUser(User user) {
			this.user = user;
			return this;
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this)
					.add("user login", user.getLogin())
					.add("proposedRole", proposedRole)
					.add("projectRole", projectRole)
					.toString();
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("addInfo", addInfo)
				.add("configuration", configuration)
				.add("users", users)
				.add("creationDate", creationDate)
				.toString();
	}
}
