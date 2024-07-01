package com.epam.ta.reportportal.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * A relationship object contains members that represent a relationship of the organization. Provided only for &#x60;Administrator&#x60; and &#x60;Manager&#x60;.  Represents [JSON:API relationships object](https://jsonapi.org/format/#document-resource-object-relationships). We use only &#x60;links&#x60; and &#x60;meta&#x60; objects.
 */
@Schema(description = "A relationship object contains members that represent a relationship of the organization. Provided only for `Administrator` and `Manager`.  Represents [JSON:API relationships object](https://jsonapi.org/format/#document-resource-object-relationships). We use only `links` and `meta` objects.")
@Validated



public class OrganizationRelation   {
  @JsonProperty("users")
  private OrganizationRelationUsers users = null;

  @JsonProperty("projects")
  private OrganizationRelationProjects projects = null;

  @JsonProperty("launches")
  private OrganizationRelationLaunches launches = null;

  @JsonProperty("billing")
  private OrganizationRelationBilling billing = null;

  public OrganizationRelation users(OrganizationRelationUsers users) {
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
    public OrganizationRelationUsers getUsers() {
    return users;
  }

  public void setUsers(OrganizationRelationUsers users) {
    this.users = users;
  }

  public OrganizationRelation projects(OrganizationRelationProjects projects) {
    this.projects = projects;
    return this;
  }

  /**
   * Get projects
   * @return projects
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public OrganizationRelationProjects getProjects() {
    return projects;
  }

  public void setProjects(OrganizationRelationProjects projects) {
    this.projects = projects;
  }

  public OrganizationRelation launches(OrganizationRelationLaunches launches) {
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
    public OrganizationRelationLaunches getLaunches() {
    return launches;
  }

  public void setLaunches(OrganizationRelationLaunches launches) {
    this.launches = launches;
  }

  public OrganizationRelation billing(OrganizationRelationBilling billing) {
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
    public OrganizationRelationBilling getBilling() {
    return billing;
  }

  public void setBilling(OrganizationRelationBilling billing) {
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
    OrganizationRelation organizationRelation = (OrganizationRelation) o;
    return Objects.equals(this.users, organizationRelation.users) &&
        Objects.equals(this.projects, organizationRelation.projects) &&
        Objects.equals(this.launches, organizationRelation.launches) &&
        Objects.equals(this.billing, organizationRelation.billing);
  }

  @Override
  public int hashCode() {
    return Objects.hash(users, projects, launches, billing);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationRelation {\n");
    
    sb.append("    users: ").append(toIndentedString(users)).append("\n");
    sb.append("    projects: ").append(toIndentedString(projects)).append("\n");
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
