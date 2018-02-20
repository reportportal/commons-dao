package com.epam.ta.reportportal.database.entity.launch;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Pavel Bortnik
 */
@Entity
@Table(name = "launch_tag", schema = "public", indexes = { @Index(name = "launch_tag_pk", unique = true, columnList = "id ASC") })
public class LaunchTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 32)
	private Long id;

	@Column(name = "value")
	private String value;

	@Column(name = "launch_id", precision = 64)
	private Long launchId;

	public LaunchTag(String value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getLaunchId() {
		return launchId;
	}

	public void setLaunchId(Long launchId) {
		this.launchId = launchId;
	}

	@Override
	public String toString() {
		return "LaunchTag{" + "id=" + id + ", value='" + value + '\'' + ", launchId=" + launchId + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		LaunchTag launchTag = (LaunchTag) o;
		return Objects.equals(id, launchTag.id) && Objects.equals(value, launchTag.value) && Objects.equals(launchId, launchTag.launchId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, value, launchId);
	}
}
