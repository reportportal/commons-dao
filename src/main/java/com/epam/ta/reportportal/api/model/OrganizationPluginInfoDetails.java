package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * #TODO Legasy
 */
@Schema(description = "#TODO Legasy")
@Validated



public class OrganizationPluginInfoDetails   {
  @JsonProperty("meta")
  private Object meta = null;

  public OrganizationPluginInfoDetails meta(Object meta) {
    this.meta = meta;
    return this;
  }

  /**
   * #TODO
   * @return meta
   **/
  @Schema(description = "#TODO")
      @NotNull

    public Object getMeta() {
    return meta;
  }

  public void setMeta(Object meta) {
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
    OrganizationPluginInfoDetails organizationPluginInfoDetails = (OrganizationPluginInfoDetails) o;
    return Objects.equals(this.meta, organizationPluginInfoDetails.meta);
  }

  @Override
  public int hashCode() {
    return Objects.hash(meta);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationPluginInfoDetails {\n");
    
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
