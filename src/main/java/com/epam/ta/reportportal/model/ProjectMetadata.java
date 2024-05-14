package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

/**
 * ProjectMetadata
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T07:48:32.405970064+03:00[Europe/Istanbul]")


public class ProjectMetadata   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("key")
  private String key = null;

  @JsonProperty("organization_id")
  private Long organizationId = null;

  @JsonProperty("created_at")
  private Instant createdAt = null;

  @JsonProperty("updated_at")
  private Instant updatedAt = null;

  public ProjectMetadata id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Project internal identifier.
   * minimum: 0
   * @return id
   **/
  @Schema(description = "Project internal identifier.")
  
  @Min(0L)  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ProjectMetadata key(String key) {
    this.key = key;
    return this;
  }

  /**
   * Unique identifying part of a project in an instance. Generated from an organization slug and project slug.
   * @return key
   **/
  @Schema(description = "Unique identifying part of a project in an instance. Generated from an organization slug and project slug.")
  
    public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public ProjectMetadata organizationId(Long organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  /**
   * Organization ID to which the project belongs.
   * minimum: 0
   * @return organizationId
   **/
  @Schema(description = "Organization ID to which the project belongs.")
  
  @Min(0L)  public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }

  public ProjectMetadata createdAt(Instant createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Timestamp when project was created.
   * @return createdAt
   **/
  @Schema(description = "Timestamp when project was created.")
  
    @Valid
    public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public ProjectMetadata updatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Timestamp when project was last updated.
   * @return updatedAt
   **/
  @Schema(description = "Timestamp when project was last updated.")
  
    @Valid
    public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectMetadata projectMetadata = (ProjectMetadata) o;
    return Objects.equals(this.id, projectMetadata.id) &&
        Objects.equals(this.key, projectMetadata.key) &&
        Objects.equals(this.organizationId, projectMetadata.organizationId) &&
        Objects.equals(this.createdAt, projectMetadata.createdAt) &&
        Objects.equals(this.updatedAt, projectMetadata.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, key, organizationId, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectMetadata {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
