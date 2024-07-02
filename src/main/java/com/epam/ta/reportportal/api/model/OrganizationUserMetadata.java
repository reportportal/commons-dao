package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * User&#x27;s organization metadata. Represent generated fields.
 */
@Schema(description = "User's organization metadata. Represent generated fields.")
@Validated



public class OrganizationUserMetadata   {
  @JsonProperty("assigned_at")
  private Instant assignedAt = null;

  public OrganizationUserMetadata assignedAt(Instant assignedAt) {
    this.assignedAt = assignedAt;
    return this;
  }

  /**
   * A time when a user was assigned to the organization.
   * @return assignedAt
   **/
  @Schema(description = "A time when a user was assigned to the organization.")
      @NotNull

    @Valid
    public Instant getAssignedAt() {
    return assignedAt;
  }

  public void setAssignedAt(Instant assignedAt) {
    this.assignedAt = assignedAt;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationUserMetadata organizationUserMetadata = (OrganizationUserMetadata) o;
    return Objects.equals(this.assignedAt, organizationUserMetadata.assignedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(assignedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationUserMetadata {\n");
    
    sb.append("    assignedAt: ").append(toIndentedString(assignedAt)).append("\n");
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
