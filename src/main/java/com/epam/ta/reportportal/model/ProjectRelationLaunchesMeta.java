package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * ProjectRelationLaunchesMeta
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class ProjectRelationLaunchesMeta   {
  @JsonProperty("last_occurred_at")
  private Instant lastOccurredAt = null;

  public ProjectRelationLaunchesMeta lastOccurredAt(Instant lastOccurredAt) {
    this.lastOccurredAt = lastOccurredAt;
    return this;
  }

  /**
   * The date and time of the last launch occurred in the project.
   * @return lastOccurredAt
   **/
  @Schema(description = "The date and time of the last launch occurred in the project.")
  
    @Valid
    public Instant getLastOccurredAt() {
    return lastOccurredAt;
  }

  public void setLastOccurredAt(Instant lastOccurredAt) {
    this.lastOccurredAt = lastOccurredAt;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectRelationLaunchesMeta projectRelationLaunchesMeta = (ProjectRelationLaunchesMeta) o;
    return Objects.equals(this.lastOccurredAt, projectRelationLaunchesMeta.lastOccurredAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastOccurredAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectRelationLaunchesMeta {\n");
    
    sb.append("    lastOccurredAt: ").append(toIndentedString(lastOccurredAt)).append("\n");
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
