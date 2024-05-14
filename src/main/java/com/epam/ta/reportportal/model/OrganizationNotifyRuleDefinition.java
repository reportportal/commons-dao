package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * #TODO
 */
@Schema(description = "#TODO")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T07:48:32.405970064+03:00[Europe/Istanbul]")


public class OrganizationNotifyRuleDefinition   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("enabled")
  private Boolean enabled = null;

  @JsonProperty("conditions")
  private OrganizationNotifyRuleDefinitionConditions conditions = null;

  @JsonProperty("actions")
  private AllOfOrganizationNotifyRuleDefinitionActions actions = null;

  public OrganizationNotifyRuleDefinition name(String name) {
    this.name = name;
    return this;
  }

  /**
   * #TODO
   * @return name
   **/
  @Schema(description = "#TODO")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OrganizationNotifyRuleDefinition enabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /**
   * #TODO
   * @return enabled
   **/
  @Schema(description = "#TODO")
  
    public Boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public OrganizationNotifyRuleDefinition conditions(OrganizationNotifyRuleDefinitionConditions conditions) {
    this.conditions = conditions;
    return this;
  }

  /**
   * Get conditions
   * @return conditions
   **/
  @Schema(description = "")
  
    @Valid
    public OrganizationNotifyRuleDefinitionConditions getConditions() {
    return conditions;
  }

  public void setConditions(OrganizationNotifyRuleDefinitionConditions conditions) {
    this.conditions = conditions;
  }

  public OrganizationNotifyRuleDefinition actions(AllOfOrganizationNotifyRuleDefinitionActions actions) {
    this.actions = actions;
    return this;
  }

  /**
   * #TODO
   * @return actions
   **/
  @Schema(description = "#TODO")
  
    public AllOfOrganizationNotifyRuleDefinitionActions getActions() {
    return actions;
  }

  public void setActions(AllOfOrganizationNotifyRuleDefinitionActions actions) {
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
    OrganizationNotifyRuleDefinition organizationNotifyRuleDefinition = (OrganizationNotifyRuleDefinition) o;
    return Objects.equals(this.name, organizationNotifyRuleDefinition.name) &&
        Objects.equals(this.enabled, organizationNotifyRuleDefinition.enabled) &&
        Objects.equals(this.conditions, organizationNotifyRuleDefinition.conditions) &&
        Objects.equals(this.actions, organizationNotifyRuleDefinition.actions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, enabled, conditions, actions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationNotifyRuleDefinition {\n");
    
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
