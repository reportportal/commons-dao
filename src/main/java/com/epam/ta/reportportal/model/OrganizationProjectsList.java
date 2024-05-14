package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * OrganizationProjectsList
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T07:48:32.405970064+03:00[Europe/Istanbul]")


public class OrganizationProjectsList extends Offset  {
  @JsonProperty("items")
  @Valid
  private List<ProjectProfile> organizationProjectsListItems = null;

  public OrganizationProjectsList organizationProjectsListItems(List<ProjectProfile> organizationProjectsListItems) {
    this.organizationProjectsListItems = organizationProjectsListItems;
    return this;
  }

  public OrganizationProjectsList addOrganizationProjectsListItemsItem(ProjectProfile organizationProjectsListItemsItem) {
    if (this.organizationProjectsListItems == null) {
      this.organizationProjectsListItems = new ArrayList<>();
    }
    this.organizationProjectsListItems.add(organizationProjectsListItemsItem);
    return this;
  }

  /**
   * Get organizationProjectsListItems
   * @return organizationProjectsListItems
   **/
  @Schema(description = "")
      @Valid
    public List<ProjectProfile> getOrganizationProjectsListItems() {
    return organizationProjectsListItems;
  }

  public void setOrganizationProjectsListItems(List<ProjectProfile> organizationProjectsListItems) {
    this.organizationProjectsListItems = organizationProjectsListItems;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationProjectsList organizationProjectsList = (OrganizationProjectsList) o;
    return Objects.equals(this.organizationProjectsListItems, organizationProjectsList.organizationProjectsListItems) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(organizationProjectsListItems, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationProjectsList {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    organizationProjectsListItems: ").append(toIndentedString(organizationProjectsListItems)).append("\n");
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
