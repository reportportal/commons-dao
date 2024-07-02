package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * OrganizationRelationBillingMeta
 */
@Validated



public class OrganizationRelationBillingMeta   {
  @JsonProperty("plan_name")
  private String planName = null;

  @JsonProperty("storage_usage")
  private Integer storageUsage = null;

  public OrganizationRelationBillingMeta planName(String planName) {
    this.planName = planName;
    return this;
  }

  /**
   * Plan name.
   * @return planName
   **/
  @Schema(description = "Plan name.")
      @NotNull

    public String getPlanName() {
    return planName;
  }

  public void setPlanName(String planName) {
    this.planName = planName;
  }

  public OrganizationRelationBillingMeta storageUsage(Integer storageUsage) {
    this.storageUsage = storageUsage;
    return this;
  }

  /**
   * Storage usage in percents.
   * minimum: 0
   * maximum: 100
   * @return storageUsage
   **/
  @Schema(description = "Storage usage in percents.")
      @NotNull

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
    OrganizationRelationBillingMeta organizationRelationBillingMeta = (OrganizationRelationBillingMeta) o;
    return Objects.equals(this.planName, organizationRelationBillingMeta.planName) &&
        Objects.equals(this.storageUsage, organizationRelationBillingMeta.storageUsage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(planName, storageUsage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationRelationBillingMeta {\n");
    
    sb.append("    planName: ").append(toIndentedString(planName)).append("\n");
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
