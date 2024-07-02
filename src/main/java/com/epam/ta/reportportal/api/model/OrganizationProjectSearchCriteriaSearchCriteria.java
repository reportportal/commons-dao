package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * OrganizationProjectSearchCriteriaSearchCriteria
 */
@Validated



public class OrganizationProjectSearchCriteriaSearchCriteria   {
  /**
   * Gets or Sets filterKey
   */
  public enum FilterKeyEnum {
    NAME("name"),
    
    SLUG("slug"),
    
    KEY("key"),
    
    CREATED_AT("created_at"),
    
    UPDATED_AT("updated_at"),
    
    USERS("users"),
    
    LAUNCHES("launches"),
    
    LAST_LAUNCH_OCCURRED("last_launch_occurred");

    private String value;

    FilterKeyEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static FilterKeyEnum fromValue(String text) {
      for (FilterKeyEnum b : FilterKeyEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("filter_key")
  private FilterKeyEnum filterKey = null;

  public OrganizationProjectSearchCriteriaSearchCriteria filterKey(FilterKeyEnum filterKey) {
    this.filterKey = filterKey;
    return this;
  }

  /**
   * Get filterKey
   * @return filterKey
   **/
  @Schema(description = "")
      @NotNull

    public FilterKeyEnum getFilterKey() {
    return filterKey;
  }

  public void setFilterKey(FilterKeyEnum filterKey) {
    this.filterKey = filterKey;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationProjectSearchCriteriaSearchCriteria organizationProjectSearchCriteriaSearchCriteria = (OrganizationProjectSearchCriteriaSearchCriteria) o;
    return Objects.equals(this.filterKey, organizationProjectSearchCriteriaSearchCriteria.filterKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filterKey);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationProjectSearchCriteriaSearchCriteria {\n");
    
    sb.append("    filterKey: ").append(toIndentedString(filterKey)).append("\n");
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
