package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * InlineResponse2004
 */
@Validated



public class InlineResponse2004   {
  @JsonProperty("retention_policy")
  private OrganizationRetentionPolicy retentionPolicy = null;

  @JsonProperty("notification_policy")
  private OrganizationNotificationPolicy notificationPolicy = null;

  public InlineResponse2004 retentionPolicy(OrganizationRetentionPolicy retentionPolicy) {
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

  public InlineResponse2004 notificationPolicy(OrganizationNotificationPolicy notificationPolicy) {
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
    InlineResponse2004 inlineResponse2004 = (InlineResponse2004) o;
    return Objects.equals(this.retentionPolicy, inlineResponse2004.retentionPolicy) &&
        Objects.equals(this.notificationPolicy, inlineResponse2004.notificationPolicy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(retentionPolicy, notificationPolicy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2004 {\n");
    
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
