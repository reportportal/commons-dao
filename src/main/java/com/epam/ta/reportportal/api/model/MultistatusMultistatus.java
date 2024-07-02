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
 * MultistatusMultistatus
 */
@Validated



public class MultistatusMultistatus   {
  @JsonProperty("responses")
  @Valid
  private List<EventStatus> responses = null;

  public MultistatusMultistatus responses(List<EventStatus> responses) {
    this.responses = responses;
    return this;
  }

  public MultistatusMultistatus addResponsesItem(EventStatus responsesItem) {
    if (this.responses == null) {
      this.responses = new ArrayList<>();
    }
    this.responses.add(responsesItem);
    return this;
  }

  /**
   * Get responses
   * @return responses
   **/
  @Schema(description = "")
      @NotNull
    @Valid
    public List<EventStatus> getResponses() {
    return responses;
  }

  public void setResponses(List<EventStatus> responses) {
    this.responses = responses;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MultistatusMultistatus multistatusMultistatus = (MultistatusMultistatus) o;
    return Objects.equals(this.responses, multistatusMultistatus.responses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(responses);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MultistatusMultistatus {\n");
    
    sb.append("    responses: ").append(toIndentedString(responses)).append("\n");
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
