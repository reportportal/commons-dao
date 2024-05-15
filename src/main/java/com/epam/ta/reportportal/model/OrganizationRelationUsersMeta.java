package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

/**
 * OrganizationRelationUsersMeta
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class OrganizationRelationUsersMeta   {
  @JsonProperty("count")
  private Integer count = null;

  public OrganizationRelationUsersMeta count(Integer count) {
    this.count = count;
    return this;
  }

  /**
   * Total count of users in the organization.
   * minimum: 0
   * @return count
   **/
  @Schema(description = "Total count of users in the organization.")
  
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
    OrganizationRelationUsersMeta organizationRelationUsersMeta = (OrganizationRelationUsersMeta) o;
    return Objects.equals(this.count, organizationRelationUsersMeta.count);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationRelationUsersMeta {\n");
    
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
