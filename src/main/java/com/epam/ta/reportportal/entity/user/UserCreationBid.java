package com.epam.ta.reportportal.entity.user;

import com.epam.ta.reportportal.entity.Modifiable;
import com.epam.ta.reportportal.entity.project.Project;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ivan Budaev
 */
@Entity
@Table(name = "user_creation_bid")
public class UserCreationBid implements Serializable, Modifiable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@LastModifiedDate
	@Column(name = LAST_MODIFIED)
	private Date lastModified;

	@Column(name = "email")
	private String email;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "default_project_id")
	private Project defaultProject;

	@Column(name = "role")
	private String role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Project getDefaultProject() {
		return defaultProject;
	}

	public void setDefaultProject(Project defaultProject) {
		this.defaultProject = defaultProject;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}
