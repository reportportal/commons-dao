package com.epam.ta.reportportal.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Information about the relationships of a project.
 */
@Schema(description = "Information about the relationships of a project.")
@Validated



public class ProjectRelation   {
  @JsonProperty("users")
  private ProjectRelationUsers users = null;

  @JsonProperty("launches")
  private ProjectRelationLaunches launches = null;

  @JsonProperty("billing")
  private ProjectRelationBilling billing = null;

  public ProjectRelation users(ProjectRelationUsers users) {
    this.users = users;
    return this;
  }

  /**
   * Get users
   * @return users
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public ProjectRelationUsers getUsers() {
    return users;
  }

  public void setUsers(ProjectRelationUsers users) {
    this.users = users;
  }

  public ProjectRelation launches(ProjectRelationLaunches launches) {
    this.launches = launches;
    return this;
  }

  /**
   * Get launches
   * @return launches
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public ProjectRelationLaunches getLaunches() {
    return launches;
  }

  public void setLaunches(ProjectRelationLaunches launches) {
    this.launches = launches;
  }

  public ProjectRelation billing(ProjectRelationBilling billing) {
    this.billing = billing;
    return this;
  }

  /**
   * Get billing
   * @return billing
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public ProjectRelationBilling getBilling() {
    return billing;
  }

  public void setBilling(ProjectRelationBilling billing) {
    this.billing = billing;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectRelation projectRelation = (ProjectRelation) o;
    return Objects.equals(this.users, projectRelation.users) &&
        Objects.equals(this.launches, projectRelation.launches) &&
        Objects.equals(this.billing, projectRelation.billing);
  }

  @Override
  public int hashCode() {
    return Objects.hash(users, launches, billing);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectRelation {\n");
    
    sb.append("    users: ").append(toIndentedString(users)).append("\n");
    sb.append("    launches: ").append(toIndentedString(launches)).append("\n");
    sb.append("    billing: ").append(toIndentedString(billing)).append("\n");
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
