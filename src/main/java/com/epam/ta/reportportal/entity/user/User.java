package com.epam.ta.reportportal.entity.user;

import com.epam.ta.reportportal.entity.project.Project;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Andrei Varabyeu
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users", schema = "public")
public class User implements Serializable {

	private static final long serialVersionUID = 923392981;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 64)
	private Long id;

	@Column(name = "login")
	private String login;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Column(name = "type")
	private String type;

	@JoinColumn(name = "default_project_id")
	private Project defaultProject;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "expired")
	private boolean isExpired;

	@JoinColumn(name = "meta_info_id")
	private MetaInfo metaInfo;

	@Column(name = "photo_path")
	private String photoPath;

	private UserType userType;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
	@Fetch(value = FetchMode.JOIN)
	private List<ProjectUser> projects;

	public User() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public List<ProjectUser> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectUser> projects) {
		this.projects = projects;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Project getDefaultProject() {
		return this.defaultProject;
	}

	public void setDefaultProject(Project defaultProjectId) {
		this.defaultProject = defaultProjectId;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean expired) {
		isExpired = expired;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	/**
	 * Null-safe getter
	 *
	 * @return
	 */
	@NotNull
	public MetaInfo getMetaInfo() {
		return metaInfo == null ? metaInfo = new MetaInfo() : metaInfo;
	}

	public void setMetaInfo(MetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		return isExpired == user.isExpired && Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password,
				user.password
		) && Objects.equals(email, user.email) && role == user.role && Objects.equals(type, user.type) && Objects.equals(defaultProject,
				user.defaultProject
		) && Objects.equals(fullName, user.fullName);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, login, password, email, role, type, defaultProject, fullName, isExpired);
	}

	@Entity
	@Table(name = "meta_info")
	public static class MetaInfo implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue
		private Long id;

		private Date lastLogin;

		private Date synchronizationDate;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		/**
		 * @return the lastLogin
		 */
		public Date getLastLogin() {
			return lastLogin;
		}

		/**
		 * @param lastLogin the lastLogin to set
		 */
		public void setLastLogin(Date lastLogin) {
			this.lastLogin = lastLogin;
		}

		/**
		 * @return the synchronizationDate
		 */
		public Date getSynchronizationDate() {
			return synchronizationDate;
		}

		/**
		 * @param synchronizationDate the synchronizationDate to set
		 */
		public void setSynchronizationDate(Date synchronizationDate) {
			this.synchronizationDate = synchronizationDate;
		}

	}

}
