package com.epam.ta.reportportal.database.entity.user;

import com.epam.ta.reportportal.database.entity.enums.UserRoleEnum;
import com.epam.ta.reportportal.database.entity.enums.UserTypeEnum;
import org.jooq.tools.json.JSONObject;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Pavel Bortnik
 */

@Entity
@Table(name = "users", schema = "public", indexes = { @Index(name = "users_login_key", unique = true, columnList = "login ASC"),
		@Index(name = "users_pk", unique = true, columnList = "id ASC") })
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 32)
	private Integer id;

	@Column(name = "login", unique = true, nullable = false)
	private String login;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "role", nullable = false)
	private UserRoleEnum role;

	@Column(name = "type", nullable = false)
	private UserTypeEnum type;

	@Column(name = "default_project_id", precision = 32)
	private Integer defaultProjectId;

	@Column(name = "full_name", nullable = false)
	private String fullName;

	@Column(name = "metadata")
	private JSONObject metadata;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRoleEnum getRole() {
		return role;
	}

	public void setRole(UserRoleEnum role) {
		this.role = role;
	}

	public UserTypeEnum getType() {
		return type;
	}

	public void setType(UserTypeEnum type) {
		this.type = type;
	}

	public Integer getDefaultProjectId() {
		return defaultProjectId;
	}

	public void setDefaultProjectId(Integer defaultProjectId) {
		this.defaultProjectId = defaultProjectId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public JSONObject getMetadata() {
		return metadata;
	}

	public void setMetadata(JSONObject metadata) {
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		return "Users{" + "id=" + id + ", login='" + login + '\'' + ", password='" + password + '\'' + ", email='" + email + '\''
				+ ", role=" + role + ", type=" + type + ", defaultProjectId=" + defaultProjectId + ", fullName='" + fullName + '\''
				+ ", metadata=" + metadata + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Users users = (Users) o;
		return Objects.equals(id, users.id) && Objects.equals(login, users.login) && Objects.equals(password, users.password)
				&& Objects.equals(email, users.email) && role == users.role && type == users.type && Objects.equals(
				defaultProjectId, users.defaultProjectId) && Objects.equals(fullName, users.fullName) && Objects.equals(
				metadata, users.metadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password, email, role, type, defaultProjectId, fullName, metadata);
	}
}
