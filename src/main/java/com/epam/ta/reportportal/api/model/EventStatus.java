package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * Event status object used to represent the status of a request item in bulk operations.
 */
@Schema(description = "Event status object used to represent the status of a request item in bulk operations.")
@Validated



public class EventStatus   {
  @JsonProperty("href")
  private String href = null;

  @JsonProperty("status")
  private EventStatusStatus status = null;

  public EventStatus href(String href) {
    this.href = href;
    return this;
  }

  /**
   * Identifier of a request item.
   * @return href
   **/
  @Schema(required = true, description = "Identifier of a request item.")
      @NotNull

    public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public EventStatus status(EventStatusStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public EventStatusStatus getStatus() {
    return status;
  }

  public void setStatus(EventStatusStatus status) {
    this.status = status;
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
    return Objects.equals(this.href, eventStatus.href) &&
        Objects.equals(this.status, eventStatus.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(href, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventStatus {\n");
    
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
