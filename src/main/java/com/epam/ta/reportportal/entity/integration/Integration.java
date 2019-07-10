/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.entity.integration;

import com.epam.ta.reportportal.entity.project.Project;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Yauheni_Martynau
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "params", typeClass = IntegrationParams.class)
@Table(name = "integration", schema = "public")
@Inheritance(strategy = InheritanceType.JOINED)
public class Integration implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToOne
	@JoinColumn(name = "type")
	private IntegrationType type;

	@Type(type = "params")
	@Column(name = "params")
	private IntegrationParams params;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "creator")
	private String creator;

	@CreatedDate
	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	public Integration(Long id, Project project, IntegrationType type, IntegrationParams params, LocalDateTime creationDate) {
		this.id = id;
		this.project = project;
		this.type = type;
		this.params = params;
		this.creationDate = creationDate;
	}

	public Integration() {
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public IntegrationType getType() {
		return type;
	}

	public void setType(IntegrationType type) {
		this.type = type;
	}

	public IntegrationParams getParams() {
		return params;
	}

	public void setParams(IntegrationParams params) {
		this.params = params;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}

