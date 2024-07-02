package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * OrganizationUsersList
 */
@Validated



public class OrganizationUsersList extends Offset  {
  @JsonProperty("items")
  @Valid
  private List<OrganizationUserProfile> organizationUsersListItems = null;

  public OrganizationUsersList organizationUsersListItems(List<OrganizationUserProfile> organizationUsersListItems) {
    this.organizationUsersListItems = organizationUsersListItems;
    return this;
  }

  public OrganizationUsersList addOrganizationUsersListItemsItem(OrganizationUserProfile organizationUsersListItemsItem) {
    if (this.organizationUsersListItems == null) {
      this.organizationUsersListItems = new ArrayList<>();
    }
    this.organizationUsersListItems.add(organizationUsersListItemsItem);
    return this;
  }

  /**
   * Get organizationUsersListItems
   * @return organizationUsersListItems
   **/
  @Schema(description = "")
      @NotNull
    @Valid
    public List<OrganizationUserProfile> getOrganizationUsersListItems() {
    return organizationUsersListItems;
  }

  public void setOrganizationUsersListItems(List<OrganizationUserProfile> organizationUsersListItems) {
    this.organizationUsersListItems = organizationUsersListItems;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationUsersList organizationUsersList = (OrganizationUsersList) o;
    return Objects.equals(this.organizationUsersListItems, organizationUsersList.organizationUsersListItems) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(organizationUsersListItems, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationUsersList {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    organizationUsersListItems: ").append(toIndentedString(organizationUsersListItems)).append("\n");
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
