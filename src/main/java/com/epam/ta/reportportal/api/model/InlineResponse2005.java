package com.epam.ta.reportportal.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * InlineResponse2005
 */
@Validated



public class InlineResponse2005   {
  @JsonProperty("retention_policy")
  private OrganizationRetentionPolicy retentionPolicy = null;

  @JsonProperty("notification_policy")
  private OrganizationNotificationPolicy notificationPolicy = null;

  public InlineResponse2005 retentionPolicy(OrganizationRetentionPolicy retentionPolicy) {
    this.retentionPolicy = retentionPolicy;
    return this;
  }

  /**
   * Get retentionPolicy
   * @return retentionPolicy
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public OrganizationRetentionPolicy getRetentionPolicy() {
    return retentionPolicy;
  }

  public void setRetentionPolicy(OrganizationRetentionPolicy retentionPolicy) {
    this.retentionPolicy = retentionPolicy;
  }

  public InlineResponse2005 notificationPolicy(OrganizationNotificationPolicy notificationPolicy) {
    this.notificationPolicy = notificationPolicy;
    return this;
  }

  /**
   * Get notificationPolicy
   * @return notificationPolicy
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public OrganizationNotificationPolicy getNotificationPolicy() {
    return notificationPolicy;
  }

  public void setNotificationPolicy(OrganizationNotificationPolicy notificationPolicy) {
    this.notificationPolicy = notificationPolicy;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2005 inlineResponse2005 = (InlineResponse2005) o;
    return Objects.equals(this.retentionPolicy, inlineResponse2005.retentionPolicy) &&
        Objects.equals(this.notificationPolicy, inlineResponse2005.notificationPolicy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(retentionPolicy, notificationPolicy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2005 {\n");
    
    sb.append("    retentionPolicy: ").append(toIndentedString(retentionPolicy)).append("\n");
    sb.append("    notificationPolicy: ").append(toIndentedString(notificationPolicy)).append("\n");
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
