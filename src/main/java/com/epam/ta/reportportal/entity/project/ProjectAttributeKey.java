package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.entity.attribute.Attribute;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author Andrey Plisunov
 */
public class ProjectAttributeKey implements Serializable {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id", nullable = false, insertable = false, updatable = false)
	private Project project;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "attribute_id", nullable = false, insertable = false, updatable = false)
	private Attribute attribute;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(project).append(attribute).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof ProjectAttributeKey)) {
			return false;
		}

		ProjectAttributeKey projectAttributeKey = (ProjectAttributeKey) obj;

		return new EqualsBuilder().append(project.getId(), projectAttributeKey.project.getId())
				.append(attribute.getId(), projectAttributeKey.attribute.getId())
				.isEquals();
	}
}
