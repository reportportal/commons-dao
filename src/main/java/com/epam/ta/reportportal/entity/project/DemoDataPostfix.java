package com.epam.ta.reportportal.entity.project;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "demo_data_postfix")
public class DemoDataPostfix implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "data")
	private String data;

	@ManyToOne
	@JoinColumn(name = "metadata_id")
	private Project.Metadata metadata;

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

	public Project.Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Project.Metadata metadata) {
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
		DemoDataPostfix that = (DemoDataPostfix) o;
		return Objects.equals(id, that.id) && Objects.equals(data, that.data) && Objects.equals(metadata, that.metadata);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, data, metadata);
	}
}
