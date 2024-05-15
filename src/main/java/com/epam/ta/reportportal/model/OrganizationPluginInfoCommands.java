package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * #TODO
 */
@Schema(description = "#TODO")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class OrganizationPluginInfoCommands   {
  @JsonProperty("common")
  @Valid
  private List<String> common = null;

  @JsonProperty("allowed")
  @Valid
  private List<String> allowed = null;

  public OrganizationPluginInfoCommands common(List<String> common) {
    this.common = common;
    return this;
  }

  public OrganizationPluginInfoCommands addCommonItem(String commonItem) {
    if (this.common == null) {
      this.common = new ArrayList<>();
    }
    this.common.add(commonItem);
    return this;
  }

  /**
   * Get common
   * @return common
   **/
  @Schema(description = "")
  
    public List<String> getCommon() {
    return common;
  }

  public void setCommon(List<String> common) {
    this.common = common;
  }

  public OrganizationPluginInfoCommands allowed(List<String> allowed) {
    this.allowed = allowed;
    return this;
  }

  public OrganizationPluginInfoCommands addAllowedItem(String allowedItem) {
    if (this.allowed == null) {
      this.allowed = new ArrayList<>();
    }
    this.allowed.add(allowedItem);
    return this;
  }

  /**
   * Get allowed
   * @return allowed
   **/
  @Schema(description = "")
  
    public List<String> getAllowed() {
    return allowed;
  }

  public void setAllowed(List<String> allowed) {
    this.allowed = allowed;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationPluginInfoCommands organizationPluginInfoCommands = (OrganizationPluginInfoCommands) o;
    return Objects.equals(this.common, organizationPluginInfoCommands.common) &&
        Objects.equals(this.allowed, organizationPluginInfoCommands.allowed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(common, allowed);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationPluginInfoCommands {\n");
    
    sb.append("    common: ").append(toIndentedString(common)).append("\n");
    sb.append("    allowed: ").append(toIndentedString(allowed)).append("\n");
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
