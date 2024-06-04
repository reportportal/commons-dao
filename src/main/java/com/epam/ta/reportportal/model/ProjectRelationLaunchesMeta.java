package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * ProjectRelationLaunchesMeta
 */
@Validated



public class ProjectRelationLaunchesMeta   {
  @JsonProperty("last_occurred_at")
  private Instant lastOccurredAt = null;

  @JsonProperty("count")
  private Integer count = null;

  public ProjectRelationLaunchesMeta lastOccurredAt(Instant lastOccurredAt) {
    this.lastOccurredAt = lastOccurredAt;
    return this;
  }

  /**
   * The date and time of the last launch occurred in the project.
   * @return lastOccurredAt
   **/
  @Schema(description = "The date and time of the last launch occurred in the project.")
      @NotNull

    @Valid
    public Instant getLastOccurredAt() {
    return lastOccurredAt;
  }

  public void setLastOccurredAt(Instant lastOccurredAt) {
    this.lastOccurredAt = lastOccurredAt;
  }

  public ProjectRelationLaunchesMeta count(Integer count) {
    this.count = count;
    return this;
  }

  /**
   * Total number of launches in the project.
   * minimum: 0
   * @return count
   **/
  @Schema(description = "Total number of launches in the project.")
      @NotNull

  @Min(0)  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
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
    return Objects.equals(this.lastOccurredAt, projectRelationLaunchesMeta.lastOccurredAt) &&
        Objects.equals(this.count, projectRelationLaunchesMeta.count);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastOccurredAt, count);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectRelationLaunchesMeta {\n");
    
    sb.append("    lastOccurredAt: ").append(toIndentedString(lastOccurredAt)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
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
