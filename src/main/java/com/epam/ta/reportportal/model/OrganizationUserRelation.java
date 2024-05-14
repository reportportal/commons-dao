package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * A relationship object contains members that represent a relationship of the user in the organization.  Represents [JSON:API relationships object](https://jsonapi.org/format/#document-resource-object-relationships). We use only &#x60;links&#x60; and &#x60;meta&#x60; objects.
 */
@Schema(description = "A relationship object contains members that represent a relationship of the user in the organization.  Represents [JSON:API relationships object](https://jsonapi.org/format/#document-resource-object-relationships). We use only `links` and `meta` objects.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T07:48:32.405970064+03:00[Europe/Istanbul]")


public class OrganizationUserRelation   {
  @JsonProperty("projects")
  private OrganizationUserRelationProjects projects = null;

  public OrganizationUserRelation projects(OrganizationUserRelationProjects projects) {
    this.projects = projects;
    return this;
  }

  /**
   * Get projects
   * @return projects
   **/
  @Schema(description = "")
  
    @Valid
    public OrganizationUserRelationProjects getProjects() {
    return projects;
  }

  public void setProjects(OrganizationUserRelationProjects projects) {
    this.projects = projects;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationUserRelation organizationUserRelation = (OrganizationUserRelation) o;
    return Objects.equals(this.projects, organizationUserRelation.projects);
  }

  @Override
  public int hashCode() {
    return Objects.hash(projects);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationUserRelation {\n");
    
    sb.append("    projects: ").append(toIndentedString(projects)).append("\n");
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
