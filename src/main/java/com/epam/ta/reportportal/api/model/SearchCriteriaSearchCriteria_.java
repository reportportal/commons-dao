package com.epam.ta.reportportal.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * SearchCriteriaSearchCriteria_
 */
@Validated



public class SearchCriteriaSearchCriteria_   {
  @JsonProperty("filter_key")
  private String filterKey = null;

  /**
   * operation to perform for the filter
   */
  public enum OperationEnum {
    EQ("EQ"),
    
    NE("NE"),
    
    CNT("CNT"),
    
    NON_CNT("NON_CNT"),
    
    BTW("BTW"),
    
    IN("IN");

    private String value;

    OperationEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static OperationEnum fromValue(String text) {
      for (OperationEnum b : OperationEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("operation")
  private OperationEnum operation = null;

  @JsonProperty("value")
  private String value = null;

  public SearchCriteriaSearchCriteria_ filterKey(String filterKey) {
    this.filterKey = filterKey;
    return this;
  }

  /**
   * search by field
   * @return filterKey
   **/
  @Schema(description = "search by field")
      @NotNull

    public String getFilterKey() {
    return filterKey;
  }

  public void setFilterKey(String filterKey) {
    this.filterKey = filterKey;
  }

  public SearchCriteriaSearchCriteria_ operation(OperationEnum operation) {
    this.operation = operation;
    return this;
  }

  /**
   * operation to perform for the filter
   * @return operation
   **/
  @Schema(description = "operation to perform for the filter")
      @NotNull

    public OperationEnum getOperation() {
    return operation;
  }

  public void setOperation(OperationEnum operation) {
    this.operation = operation;
  }

  public SearchCriteriaSearchCriteria_ value(String value) {
    this.value = value;
    return this;
  }

  /**
   * search value
   * @return value
   **/
  @Schema(description = "search value")
      @NotNull

    public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchCriteriaSearchCriteria_ searchCriteriaSearchCriteria_ = (SearchCriteriaSearchCriteria_) o;
    return Objects.equals(this.filterKey, searchCriteriaSearchCriteria_.filterKey) &&
        Objects.equals(this.operation, searchCriteriaSearchCriteria_.operation) &&
        Objects.equals(this.value, searchCriteriaSearchCriteria_.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filterKey, operation, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchCriteriaSearchCriteria_ {\n");
    
    sb.append("    filterKey: ").append(toIndentedString(filterKey)).append("\n");
    sb.append("    operation: ").append(toIndentedString(operation)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
