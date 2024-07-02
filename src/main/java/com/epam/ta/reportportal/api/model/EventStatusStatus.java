package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * Object indicating the execution status of the request item.
 */
@Schema(description = "Object indicating the execution status of the request item.")
@Validated



public class EventStatusStatus   {
  @JsonProperty("code")
  private Integer code = null;

  @JsonProperty("description")
  private String description = null;

  public EventStatusStatus code(Integer code) {
    this.code = code;
    return this;
  }

  /**
   * HTTP status code indicating the resource's status.
   * minimum: 100
   * maximum: 600
   * @return code
   **/
  @Schema(description = "HTTP status code indicating the resource's status.")
      @NotNull

  @Min(100) @Max(600)   public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public EventStatusStatus description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Human readable status description and containing additional               context information about failures etc.
   * @return description
   **/
  @Schema(description = "Human readable status description and containing additional               context information about failures etc.")
      @NotNull

    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventStatusStatus eventStatusStatus = (EventStatusStatus) o;
    return Objects.equals(this.code, eventStatusStatus.code) &&
        Objects.equals(this.description, eventStatusStatus.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventStatusStatus {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
