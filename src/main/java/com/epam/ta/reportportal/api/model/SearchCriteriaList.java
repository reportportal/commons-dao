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
 * SearchCriteriaList
 */
@Validated



public class SearchCriteriaList   {
  @JsonProperty("search_criteria")
  @Valid
  private List<SearchCriteria> searchCriteria = null;

  public SearchCriteriaList searchCriteria(List<SearchCriteria> searchCriteria) {
    this.searchCriteria = searchCriteria;
    return this;
  }

  public SearchCriteriaList addSearchCriteriaItem(SearchCriteria searchCriteriaItem) {
    if (this.searchCriteria == null) {
      this.searchCriteria = new ArrayList<>();
    }
    this.searchCriteria.add(searchCriteriaItem);
    return this;
  }

  /**
   * Get searchCriteria
   * @return searchCriteria
   **/
  @Schema(description = "")
      @NotNull
    @Valid
    public List<SearchCriteria> getSearchCriteria() {
    return searchCriteria;
  }

  public void setSearchCriteria(List<SearchCriteria> searchCriteria) {
    this.searchCriteria = searchCriteria;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchCriteriaList searchCriteriaList = (SearchCriteriaList) o;
    return Objects.equals(this.searchCriteria, searchCriteriaList.searchCriteria);
  }

  @Override
  public int hashCode() {
    return Objects.hash(searchCriteria);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchCriteriaList {\n");
    
    sb.append("    searchCriteria: ").append(toIndentedString(searchCriteria)).append("\n");
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
