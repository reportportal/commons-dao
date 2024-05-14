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


public class OrganizationNotifyRuleDefinitionConditions   {
  @JsonProperty("launch")
  private OrganizationNotifyRuleDefinitionConditionsLaunch launch = null;

  public OrganizationNotifyRuleDefinitionConditions launch(OrganizationNotifyRuleDefinitionConditionsLaunch launch) {
    this.launch = launch;
    return this;
  }

  /**
   * Get launch
   * @return launch
   **/
  @Schema(description = "")
  
    @Valid
    public OrganizationNotifyRuleDefinitionConditionsLaunch getLaunch() {
    return launch;
  }

  public void setLaunch(OrganizationNotifyRuleDefinitionConditionsLaunch launch) {
    this.launch = launch;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationNotifyRuleDefinitionConditions organizationNotifyRuleDefinitionConditions = (OrganizationNotifyRuleDefinitionConditions) o;
    return Objects.equals(this.launch, organizationNotifyRuleDefinitionConditions.launch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(launch);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationNotifyRuleDefinitionConditions {\n");
    
    sb.append("    launch: ").append(toIndentedString(launch)).append("\n");
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
