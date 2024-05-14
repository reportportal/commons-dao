package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * Event status object used to represent the status of a request item in bulk operations.  The status can be one of the following:  * \&quot;SUCCESS\&quot; - The request item was successfully processed. * \&quot;FAILURE\&quot; - The request item failed to be processed. * \&quot;PENDING\&quot; - The request item is still pending processing.
 */
@Schema(description = "Event status object used to represent the status of a request item in bulk operations.  The status can be one of the following:  * \"SUCCESS\" - The request item was successfully processed. * \"FAILURE\" - The request item failed to be processed. * \"PENDING\" - The request item is still pending processing.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class EventStatus {
  @JsonProperty("id")
  private String id = null;

  /**
   * Field indicating the execution status of the request item.  The status can be one of the following:  * \"SUCCESS\"  * \"FAILURE\"  * \"PENDING\"
   */
  public enum StatusEnum {
    SUCCESS("SUCCESS"),
    
    FAILURE("FAILURE"),
    
    PENDING("PENDING");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("status")
  private StatusEnum status = null;

  @JsonProperty("description")
  private String description = null;

  public EventStatus id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of a request item.
   * @return id
   **/
  @Schema(required = true, description = "Identifier of a request item.")
      @NotNull

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public EventStatus status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Field indicating the execution status of the request item.  The status can be one of the following:  * \"SUCCESS\"  * \"FAILURE\"  * \"PENDING\"
   * @return status
   **/
  @Schema(required = true, description = "Field indicating the execution status of the request item.  The status can be one of the following:  * \"SUCCESS\"  * \"FAILURE\"  * \"PENDING\"")
      @NotNull

    public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public EventStatus description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Human readable status description and containing additional               context information about failures etc.
   * @return description
   **/
  @Schema(description = "Human readable status description and containing additional               context information about failures etc.")
  
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
    EventStatus eventStatus = (EventStatus) o;
    return Objects.equals(this.id, eventStatus.id) &&
        Objects.equals(this.status, eventStatus.status) &&
        Objects.equals(this.description, eventStatus.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventStatus {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
