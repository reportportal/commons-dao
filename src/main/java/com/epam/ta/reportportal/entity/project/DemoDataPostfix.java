package com.epam.ta.reportportal.entity.project;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ivan Budayeu
 */
@Entity
@Table(name = "demo_data_postfix")
public class DemoDataPostfix implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "data")
	private String data;

	@ManyToOne
	@JoinColumn(name = "project_id")
	@JsonBackReference(value = "demoDataPostfix")
	private Project project;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DemoDataPostfix that = (DemoDataPostfix) o;
		return Objects.equals(id, that.id) && Objects.equals(data, that.data) && Objects.equals(project, that.project);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, data, project);
	}
}
