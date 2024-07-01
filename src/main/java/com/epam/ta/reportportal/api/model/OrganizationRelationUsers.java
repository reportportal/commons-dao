package com.epam.ta.reportportal.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * General information about the organization users.
 */
@Schema(description = "General information about the organization users.")
@Validated



public class OrganizationRelationUsers   {
  @JsonProperty("meta")
  private OrganizationRelationUsersMeta meta = null;

  public OrganizationRelationUsers meta(OrganizationRelationUsersMeta meta) {
    this.meta = meta;
    return this;
  }

  /**
   * Get meta
   * @return meta
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public OrganizationRelationUsersMeta getMeta() {
    return meta;
  }

  public void setMeta(OrganizationRelationUsersMeta meta) {
    this.meta = meta;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationRelationUsers organizationRelationUsers = (OrganizationRelationUsers) o;
    return Objects.equals(this.meta, organizationRelationUsers.meta);
  }

  @Override
  public int hashCode() {
    return Objects.hash(meta);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationRelationUsers {\n");
    
    sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
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
