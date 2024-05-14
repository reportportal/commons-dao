package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * Base information about a user in an organization.
 */
@Schema(description = "Base information about a user in an organization.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T07:48:32.405970064+03:00[Europe/Istanbul]")


public class OrganizationUserDefinition extends OrganizationUserDetails  {
  @JsonProperty("projects")
  @Valid
  private List<UserProjectDefinition> projects = null;

  public OrganizationUserDefinition projects(List<UserProjectDefinition> projects) {
    this.projects = projects;
    return this;
  }

  public OrganizationUserDefinition addProjectsItem(UserProjectDefinition projectsItem) {
    if (this.projects == null) {
      this.projects = new ArrayList<>();
    }
    this.projects.add(projectsItem);
    return this;
  }

  /**
   * List of projects for user assignment. It's optional; you can assign the user  to an organization without projects.
   * @return projects
   **/
  @Schema(description = "List of projects for user assignment. It's optional; you can assign the user  to an organization without projects.")
      @Valid
    public List<UserProjectDefinition> getProjects() {
    return projects;
  }

  public void setProjects(List<UserProjectDefinition> projects) {
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
    OrganizationUserDefinition organizationUserDefinition = (OrganizationUserDefinition) o;
    return Objects.equals(this.projects, organizationUserDefinition.projects) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(projects, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationUserDefinition {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
