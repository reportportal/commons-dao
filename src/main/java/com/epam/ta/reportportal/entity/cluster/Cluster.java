package com.epam.ta.reportportal.entity.cluster;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "clusters", schema = "public")
public class Cluster implements Serializable {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "launch_id")
	private Long launchId;

	@Column(name = "message")
	private String message;

	public Cluster() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLaunchId() {
		return launchId;
	}

	public void setLaunchId(Long launchId) {
		this.launchId = launchId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
