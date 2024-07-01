package com.epam.ta.reportportal.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * #TODO
 */
@Schema(description = "#TODO")
@Validated



public class OrganizationNotifyRule   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("created_at")
  private Instant createdAt = null;

  @JsonProperty("updated_at")
  private Instant updatedAt = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("enabled")
  private Boolean enabled = null;

  @JsonProperty("conditions")
  private OrganizationNotifyRuleDefinitionConditions conditions = null;

  @JsonProperty("actions")
  private Object actions = null;

  public OrganizationNotifyRule id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(description = "")
      @NotNull

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public OrganizationNotifyRule createdAt(Instant createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public OrganizationNotifyRule updatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public OrganizationNotifyRule name(String name) {
    this.name = name;
    return this;
  }

  /**
   * #TODO
   * @return name
   **/
  @Schema(description = "#TODO")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OrganizationNotifyRule enabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /**
   * #TODO
   * @return enabled
   **/
  @Schema(description = "#TODO")
      @NotNull

    public Boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public OrganizationNotifyRule conditions(OrganizationNotifyRuleDefinitionConditions conditions) {
    this.conditions = conditions;
    return this;
  }

  /**
   * Get conditions
   * @return conditions
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public OrganizationNotifyRuleDefinitionConditions getConditions() {
    return conditions;
  }

  public void setConditions(OrganizationNotifyRuleDefinitionConditions conditions) {
    this.conditions = conditions;
  }

  public OrganizationNotifyRule actions(Object actions) {
    this.actions = actions;
    return this;
  }

  /**
   * #TODO
   * @return actions
   **/
  @Schema(description = "#TODO")
      @NotNull

    public Object getActions() {
    return actions;
  }

  public void setActions(Object actions) {
    this.actions = actions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationNotifyRule organizationNotifyRule = (OrganizationNotifyRule) o;
    return Objects.equals(this.id, organizationNotifyRule.id) &&
        Objects.equals(this.createdAt, organizationNotifyRule.createdAt) &&
        Objects.equals(this.updatedAt, organizationNotifyRule.updatedAt) &&
        Objects.equals(this.name, organizationNotifyRule.name) &&
        Objects.equals(this.enabled, organizationNotifyRule.enabled) &&
        Objects.equals(this.conditions, organizationNotifyRule.conditions) &&
        Objects.equals(this.actions, organizationNotifyRule.actions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, updatedAt, name, enabled, conditions, actions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationNotifyRule {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
    sb.append("    conditions: ").append(toIndentedString(conditions)).append("\n");
    sb.append("    actions: ").append(toIndentedString(actions)).append("\n");
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
