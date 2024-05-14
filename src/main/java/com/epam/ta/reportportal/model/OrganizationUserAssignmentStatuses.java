package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * OrganizationUserAssignmentStatuses
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T07:48:32.405970064+03:00[Europe/Istanbul]")


public class OrganizationUserAssignmentStatuses extends EventStatus  {
  @JsonProperty("project_statuses")
  @Valid
  private List<EventStatus> projectStatuses = null;

  public OrganizationUserAssignmentStatuses projectStatuses(List<EventStatus> projectStatuses) {
    this.projectStatuses = projectStatuses;
    return this;
  }

  public OrganizationUserAssignmentStatuses addProjectStatusesItem(EventStatus projectStatusesItem) {
    if (this.projectStatuses == null) {
      this.projectStatuses = new ArrayList<>();
    }
    this.projectStatuses.add(projectStatusesItem);
    return this;
  }

  /**
   * Get projectStatuses
   * @return projectStatuses
   **/
  @Schema(description = "")
      @Valid
    public List<EventStatus> getProjectStatuses() {
    return projectStatuses;
  }

  public void setProjectStatuses(List<EventStatus> projectStatuses) {
    this.projectStatuses = projectStatuses;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationUserAssignmentStatuses organizationUserAssignmentStatuses = (OrganizationUserAssignmentStatuses) o;
    return Objects.equals(this.projectStatuses, organizationUserAssignmentStatuses.projectStatuses) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(projectStatuses, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationUserAssignmentStatuses {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    projectStatuses: ").append(toIndentedString(projectStatuses)).append("\n");
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
