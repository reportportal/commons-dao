package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * OrganizationNotifyRulesList
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class OrganizationNotifyRulesList extends Offset  {
  @JsonProperty("items")
  @Valid
  private List<OrganizationNotifyRule> organizationNotifyRulesListItems = null;

  public OrganizationNotifyRulesList organizationNotifyRulesListItems(List<OrganizationNotifyRule> organizationNotifyRulesListItems) {
    this.organizationNotifyRulesListItems = organizationNotifyRulesListItems;
    return this;
  }

  public OrganizationNotifyRulesList addOrganizationNotifyRulesListItemsItem(OrganizationNotifyRule organizationNotifyRulesListItemsItem) {
    if (this.organizationNotifyRulesListItems == null) {
      this.organizationNotifyRulesListItems = new ArrayList<>();
    }
    this.organizationNotifyRulesListItems.add(organizationNotifyRulesListItemsItem);
    return this;
  }

  /**
   * Get organizationNotifyRulesListItems
   * @return organizationNotifyRulesListItems
   **/
  @Schema(description = "")
      @Valid
    public List<OrganizationNotifyRule> getOrganizationNotifyRulesListItems() {
    return organizationNotifyRulesListItems;
  }

  public void setOrganizationNotifyRulesListItems(List<OrganizationNotifyRule> organizationNotifyRulesListItems) {
    this.organizationNotifyRulesListItems = organizationNotifyRulesListItems;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationNotifyRulesList organizationNotifyRulesList = (OrganizationNotifyRulesList) o;
    return Objects.equals(this.organizationNotifyRulesListItems, organizationNotifyRulesList.organizationNotifyRulesListItems) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(organizationNotifyRulesListItems, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationNotifyRulesList {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    organizationNotifyRulesListItems: ").append(toIndentedString(organizationNotifyRulesListItems)).append("\n");
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
