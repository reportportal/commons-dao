package com.epam.ta.reportportal.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SearchCriteria
 */
@Validated



public class SearchCriteria extends OffsetRequest  {
  @JsonProperty("search_criteria ")
  @Valid
  private List<SearchCriteriaSearchCriteria_> searchCriteria_ = null;

  public SearchCriteria searchCriteria_(List<SearchCriteriaSearchCriteria_> searchCriteria_) {
    this.searchCriteria_ = searchCriteria_;
    return this;
  }

  public SearchCriteria addSearchCriteria_Item(SearchCriteriaSearchCriteria_ searchCriteria_Item) {
    if (this.searchCriteria_ == null) {
      this.searchCriteria_ = new ArrayList<>();
    }
    this.searchCriteria_.add(searchCriteria_Item);
    return this;
  }

  /**
   * Get searchCriteria_
   * @return searchCriteria_
   **/
  @Schema(description = "")
      @NotNull
    @Valid
    public List<SearchCriteriaSearchCriteria_> getSearchCriteria_() {
    return searchCriteria_;
  }

  public void setSearchCriteria_(List<SearchCriteriaSearchCriteria_> searchCriteria_) {
    this.searchCriteria_ = searchCriteria_;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchCriteria searchCriteria = (SearchCriteria) o;
    return Objects.equals(this.searchCriteria_, searchCriteria.searchCriteria_) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(searchCriteria_, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchCriteria {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    searchCriteria_: ").append(toIndentedString(searchCriteria_)).append("\n");
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
