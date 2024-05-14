package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * Definition for a user assignment to the organization.
 */
@Schema(description = "Definition for a user assignment to the organization.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T07:48:32.405970064+03:00[Europe/Istanbul]")


public class OrganizationUserAssignment   {
  @JsonProperty("email")
  private String email = null;

  @JsonProperty("projects")
  @Valid
  private List<UserProjectDefinition> projects = null;

  public OrganizationUserAssignment email(String email) {
    this.email = email;
    return this;
  }

  /**
   * User email for assignment.
   * @return email
   **/
  @Schema(required = true, description = "User email for assignment.")
      @NotNull

    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public OrganizationUserAssignment projects(List<UserProjectDefinition> projects) {
    this.projects = projects;
    return this;
  }

  public OrganizationUserAssignment addProjectsItem(UserProjectDefinition projectsItem) {
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
    OrganizationUserAssignment organizationUserAssignment = (OrganizationUserAssignment) o;
    return Objects.equals(this.email, organizationUserAssignment.email) &&
        Objects.equals(this.projects, organizationUserAssignment.projects);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, projects);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationUserAssignment {\n");
    
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
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
