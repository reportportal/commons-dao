package com.epam.ta.reportportal.database.entity.project;

import org.jooq.tools.json.JSONObject;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Pavel Bortnik
 */

@Entity
@Table(name = "project", schema = "public", indexes = { @Index(name = "project_pk", unique = true, columnList = "id ASC"),
		@Index(name = "project_project_configuration_id_key", unique = true, columnList = "project_configuration_id ASC") })
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 32)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "metadata")
	private JSONObject metadata;

	@Column(name = "project_configuration_id", unique = true, precision = 32)
	private Integer projectConfigurationId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JSONObject getMetadata() {
		return metadata;
	}

	public void setMetadata(JSONObject metadata) {
		this.metadata = metadata;
	}

	public Integer getProjectConfigurationId() {
		return projectConfigurationId;
	}

	public void setProjectConfigurationId(Integer projectConfigurationId) {
		this.projectConfigurationId = projectConfigurationId;
	}

	@Override
	public String toString() {
		return "Project{" + "id=" + id + ", name='" + name + '\'' + ", metadata=" + metadata + ", projectConfigurationId="
				+ projectConfigurationId + '}';
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
		return Objects.equals(id, project.id) && Objects.equals(name, project.name) && Objects.equals(metadata, project.metadata) && Objects
				.equals(projectConfigurationId, project.projectConfigurationId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, metadata, projectConfigurationId);
	}
}
