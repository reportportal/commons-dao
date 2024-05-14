package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * OrganizationProfilesList
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T07:48:32.405970064+03:00[Europe/Istanbul]")


public class OrganizationProfilesList extends Offset  {
  @JsonProperty("items")
  @Valid
  private List<OrganizationProfile> organizationProfilesListItems = null;

  public OrganizationProfilesList organizationProfilesListItems(List<OrganizationProfile> organizationProfilesListItems) {
    this.organizationProfilesListItems = organizationProfilesListItems;
    return this;
  }

  public OrganizationProfilesList addOrganizationProfilesListItemsItem(OrganizationProfile organizationProfilesListItemsItem) {
    if (this.organizationProfilesListItems == null) {
      this.organizationProfilesListItems = new ArrayList<>();
    }
    this.organizationProfilesListItems.add(organizationProfilesListItemsItem);
    return this;
  }

  /**
   * Get organizationProfilesListItems
   * @return organizationProfilesListItems
   **/
  @Schema(description = "")
      @Valid
    public List<OrganizationProfile> getOrganizationProfilesListItems() {
    return organizationProfilesListItems;
  }

  public void setOrganizationProfilesListItems(List<OrganizationProfile> organizationProfilesListItems) {
    this.organizationProfilesListItems = organizationProfilesListItems;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationProfilesList organizationProfilesList = (OrganizationProfilesList) o;
    return Objects.equals(this.organizationProfilesListItems, organizationProfilesList.organizationProfilesListItems) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(organizationProfilesListItems, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationProfilesList {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    organizationProfilesListItems: ").append(toIndentedString(organizationProfilesListItems)).append("\n");
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
