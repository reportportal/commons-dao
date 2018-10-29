package com.epam.ta.reportportal.entity.integration;

import com.epam.ta.reportportal.commons.JsonbUserType;
import com.epam.ta.reportportal.entity.enums.IntegrationAuthFlowEnum;
import com.epam.ta.reportportal.entity.enums.PostgreSQLEnumType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonbUserType.class)
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

	@Column(name = "group_type", nullable = false)
	private String groupType;

	@Type(type = "jsonb")
	@Column(name = "details")
	private IntegrationTypeDetails details;

	@OneToMany(mappedBy = "type", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonManagedReference(value = "integrationTypes")
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

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
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
