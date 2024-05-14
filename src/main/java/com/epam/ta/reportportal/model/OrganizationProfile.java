package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * A comprehensive set of organization information.
 */
@Schema(description = "A comprehensive set of organization information.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class OrganizationProfile extends OrganizationInfo  {
  @JsonProperty("relationships")
  private OrganizationRelation relationships = null;

  public OrganizationProfile relationships(OrganizationRelation relationships) {
    this.relationships = relationships;
    return this;
  }

  /**
   * Get relationships
   * @return relationships
   **/
  @Schema(description = "")
  
    @Valid
    public OrganizationRelation getRelationships() {
    return relationships;
  }

  public void setRelationships(OrganizationRelation relationships) {
    this.relationships = relationships;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationProfile organizationProfile = (OrganizationProfile) o;
    return Objects.equals(this.relationships, organizationProfile.relationships) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(relationships, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationProfile {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    relationships: ").append(toIndentedString(relationships)).append("\n");
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
