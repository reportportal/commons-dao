package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * Information about the relationships of a project.
 */
@Schema(description = "Information about the relationships of a project.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T07:48:32.405970064+03:00[Europe/Istanbul]")


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
