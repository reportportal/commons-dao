package com.epam.ta.reportportal.entity.pattern;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Entity
@Table(name = "pattern_template")
public class PatternTemplate implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "value")
	private String value;

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private PatternTemplateType templateType;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "project_id")
	private Long projectId;

	public PatternTemplate() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public PatternTemplateType getTemplateType() {
		return templateType;
	}

	public void setTemplateType(PatternTemplateType templateType) {
		this.templateType = templateType;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
