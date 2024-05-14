package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

/**
 * ProjectRelationBillingMeta
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T07:48:32.405970064+03:00[Europe/Istanbul]")


public class ProjectRelationBillingMeta   {
  @JsonProperty("storage_usage")
  private Integer storageUsage = null;

  public ProjectRelationBillingMeta storageUsage(Integer storageUsage) {
    this.storageUsage = storageUsage;
    return this;
  }

  /**
   * The percentage of storage used by the project.
   * minimum: 0
   * maximum: 100
   * @return storageUsage
   **/
  @Schema(description = "The percentage of storage used by the project.")
  
  @Min(0) @Max(100)   public Integer getStorageUsage() {
    return storageUsage;
  }

  public void setStorageUsage(Integer storageUsage) {
    this.storageUsage = storageUsage;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectRelationBillingMeta projectRelationBillingMeta = (ProjectRelationBillingMeta) o;
    return Objects.equals(this.storageUsage, projectRelationBillingMeta.storageUsage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(storageUsage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectRelationBillingMeta {\n");
    
    sb.append("    storageUsage: ").append(toIndentedString(storageUsage)).append("\n");
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
