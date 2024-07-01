package com.epam.ta.reportportal.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * Offset-based pagination
 */
@Schema(description = "Offset-based pagination")
@Validated



public class OffsetRequest   {
  @JsonProperty("offset")
  private Integer offset = 0;

  @JsonProperty("limit")
  private Integer limit = 300;

  @JsonProperty("sort")
  private String sort = "id";

  /**
   * To indicate sorting direction. Ascending or Descending.
   */
  public enum OrderEnum {
    ASC("ASC"),
    
    DESC("DESC");

    private String value;

    OrderEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static OrderEnum fromValue(String text) {
      for (OrderEnum b : OrderEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("order")
  private OrderEnum order = OrderEnum.ASC;

  public OffsetRequest offset(Integer offset) {
    this.offset = offset;
    return this;
  }

  /**
   * The offset used for this page of results.
   * minimum: 0
   * @return offset
   **/
  @Schema(description = "The offset used for this page of results.")
      @NotNull

  @Min(0)  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public OffsetRequest limit(Integer limit) {
    this.limit = limit;
    return this;
  }

  /**
   * The limit used for this page of results. This will be the same as the limit query parameter unless it exceeded the maximum value allowed for this API endpoint.
   * minimum: 0
   * @return limit
   **/
  @Schema(description = "The limit used for this page of results. This will be the same as the limit query parameter unless it exceeded the maximum value allowed for this API endpoint.")
      @NotNull

  @Min(0)  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public OffsetRequest sort(String sort) {
    this.sort = sort;
    return this;
  }

  /**
   * Field to define the sort field.
   * @return sort
   **/
  @Schema(description = "Field to define the sort field.")
      @NotNull

    public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public OffsetRequest order(OrderEnum order) {
    this.order = order;
    return this;
  }

  /**
   * To indicate sorting direction. Ascending or Descending.
   * @return order
   **/
  @Schema(description = "To indicate sorting direction. Ascending or Descending.")
      @NotNull

    public OrderEnum getOrder() {
    return order;
  }

  public void setOrder(OrderEnum order) {
    this.order = order;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OffsetRequest offsetRequest = (OffsetRequest) o;
    return Objects.equals(this.offset, offsetRequest.offset) &&
        Objects.equals(this.limit, offsetRequest.limit) &&
        Objects.equals(this.sort, offsetRequest.sort) &&
        Objects.equals(this.order, offsetRequest.order);
  }

  @Override
  public int hashCode() {
    return Objects.hash(offset, limit, sort, order);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OffsetRequest {\n");
    
    sb.append("    offset: ").append(toIndentedString(offset)).append("\n");
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
    sb.append("    sort: ").append(toIndentedString(sort)).append("\n");
    sb.append("    order: ").append(toIndentedString(order)).append("\n");
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
