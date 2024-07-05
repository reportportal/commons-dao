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
 * OrganizationProjectsList
 */
@Validated



public class OrganizationProjectsList   {
  @JsonProperty("items")
  @Valid
  private List<ProjectProfile> items = new ArrayList<>();

  public OrganizationProjectsList items(List<ProjectProfile> items) {
    this.items = items;
    return this;
  }

  public OrganizationProjectsList addItemsItem(ProjectProfile itemsItem) {
    this.items.add(itemsItem);
    return this;
  }

  /**
   * Get items
   * @return items
   **/
  @Schema(required = true, description = "")
      @NotNull
    @Valid
    public List<ProjectProfile> getItems() {
    return items;
  }

  public void setItems(List<ProjectProfile> items) {
    this.items = items;
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
    return Objects.equals(this.items, organizationProjectsList.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationProjectsList {\n");
    
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
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
