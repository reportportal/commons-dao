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
 * InlineResponse207
 */
@Validated



public class InlineResponse207   {
  @JsonProperty("message")
  private String message = "The user's role in the organization has been updated.";

  @JsonProperty("statuses")
  @Valid
  private List<EventStatus> statuses = null;

  public InlineResponse207 message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
   **/
  @Schema(description = "")
      @NotNull

    public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public InlineResponse207 statuses(List<EventStatus> statuses) {
    this.statuses = statuses;
    return this;
  }

  public InlineResponse207 addStatusesItem(EventStatus statusesItem) {
    if (this.statuses == null) {
      this.statuses = new ArrayList<>();
    }
    this.statuses.add(statusesItem);
    return this;
  }

  /**
   * Change statuses for projects. For cases when a user is assigned projects.
   * @return statuses
   **/
  @Schema(description = "Change statuses for projects. For cases when a user is assigned projects.")
      @NotNull
    @Valid
    public List<EventStatus> getStatuses() {
    return statuses;
  }

  public void setStatuses(List<EventStatus> statuses) {
    this.statuses = statuses;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse207 inlineResponse207 = (InlineResponse207) o;
    return Objects.equals(this.message, inlineResponse207.message) &&
        Objects.equals(this.statuses, inlineResponse207.statuses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, statuses);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse207 {\n");
    
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    statuses: ").append(toIndentedString(statuses)).append("\n");
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
