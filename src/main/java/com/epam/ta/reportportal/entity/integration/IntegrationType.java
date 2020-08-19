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

import com.epam.ta.reportportal.entity.enums.IntegrationAuthFlowEnum;
import com.epam.ta.reportportal.entity.enums.IntegrationGroupEnum;
import com.epam.ta.reportportal.entity.enums.PostgreSQLEnumType;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Yauheni_Martynau
 */
@Entity
@TypeDef(name = "details", typeClass = IntegrationTypeDetails.class)
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Table(name = "integration_type", schema = "public")
public class IntegrationType implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 64)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Type(type = "pqsql_enum")
	@Column(name = "auth_flow", nullable = false)
	private IntegrationAuthFlowEnum authFlow;

	@CreatedDate
	@Column(name = "creation_date", nullable = false)
	private LocalDateTime creationDate;

	@Enumerated(EnumType.STRING)
	@Type(type = "pqsql_enum")
	@Column(name = "group_type", nullable = false)
	private IntegrationGroupEnum integrationGroup;

	@Column(name = "enabled")
	private boolean enabled;

	@Type(type = "details")
	@Column(name = "details")
	private IntegrationTypeDetails details;

	@OneToMany(mappedBy = "type", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Integration> integrations = Sets.newHashSet();

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

	public IntegrationAuthFlowEnum getAuthFlow() {
		return authFlow;
	}

	public void setAuthFlow(IntegrationAuthFlowEnum authFlow) {
		this.authFlow = authFlow;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public IntegrationGroupEnum getIntegrationGroup() {
		return integrationGroup;
	}

	public void setIntegrationGroup(IntegrationGroupEnum integrationGroup) {
		this.integrationGroup = integrationGroup;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public IntegrationTypeDetails getDetails() {
		return details;
	}

	public void setDetails(IntegrationTypeDetails details) {
		this.details = details;
	}

	public Set<Integration> getIntegrations() {
		return integrations;
	}

	public void setIntegrations(Set<Integration> integrations) {
		this.integrations = integrations;
	}
}
