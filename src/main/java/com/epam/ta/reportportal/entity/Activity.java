package com.epam.ta.reportportal.entity;

import com.epam.ta.reportportal.entity.enums.PostgreSQLEnumType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Activity table entity
 *
 * @author Andrei Varabyeu
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "activity", schema = "public")
@TypeDef(name = "jsonb", typeClass = JsonMap.class)
@TypeDef(name = "pqsql_enum", typeClass = PostgreSQLEnumType.class)
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 64)
	private Long id;

	@Column(name = "user_id", nullable = false, precision = 32)
	private Long userId;

	@Column(name = "project_id", nullable = false)
	private Long projectId;

	@Enumerated(EnumType.STRING)
	@Column(name = "entity", unique = true, nullable = false)
	@Type(type = "pqsql_enum")
	private Entity entity;

	@Column(name = "action", nullable = false)
	private String action;

	@Column(name = "details")
	@Type(type = "jsonb")
	private JsonbObject details;

	@Column(name = "creation_date")
	private LocalDateTime createdAt;

	@Column(name = "object_id")
	private Long objectId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public JsonbObject getDetails() {
		return details;
	}

	public void setDetails(JsonbObject details) {
		this.details = details;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public enum Entity {
		LAUNCH,
		ITEM,
		DASHBOARD,
		DEFECT_TYPE,
		EMAIL_CONFIG,
		FILTER,
		IMPORT,
		INTEGRATION,
		ITEM_ISSUE,
		PROJECT,
		SHARING,
		TICKET,
		USER,
		WIDGET
	}
}
