package com.epam.ta.reportportal.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * #TODO
 */
@Schema(description = "#TODO")
@Validated



public class OrganizationRetentionPolicy  implements InlineResponse2006, SettingsSettingNameBody, SettingsSettingNameBody1, SettingsSettingNameBody2 {
  @JsonProperty("log_retention_period")
  private String logRetentionPeriod = null;

  @JsonProperty("launch_retention_period")
  private String launchRetentionPeriod = null;

  @JsonProperty("attachment_retention_period")
  private String attachmentRetentionPeriod = null;

  public OrganizationRetentionPolicy logRetentionPeriod(String logRetentionPeriod) {
    this.logRetentionPeriod = logRetentionPeriod;
    return this;
  }

  /**
   * #TODO
   * @return logRetentionPeriod
   **/
  @Schema(description = "#TODO")
      @NotNull

    public String getLogRetentionPeriod() {
    return logRetentionPeriod;
  }

  public void setLogRetentionPeriod(String logRetentionPeriod) {
    this.logRetentionPeriod = logRetentionPeriod;
  }

  public OrganizationRetentionPolicy launchRetentionPeriod(String launchRetentionPeriod) {
    this.launchRetentionPeriod = launchRetentionPeriod;
    return this;
  }

  /**
   * #TODO
   * @return launchRetentionPeriod
   **/
  @Schema(description = "#TODO")
      @NotNull

    public String getLaunchRetentionPeriod() {
    return launchRetentionPeriod;
  }

  public void setLaunchRetentionPeriod(String launchRetentionPeriod) {
    this.launchRetentionPeriod = launchRetentionPeriod;
  }

  public OrganizationRetentionPolicy attachmentRetentionPeriod(String attachmentRetentionPeriod) {
    this.attachmentRetentionPeriod = attachmentRetentionPeriod;
    return this;
  }

  /**
   * #TODO
   * @return attachmentRetentionPeriod
   **/
  @Schema(description = "#TODO")
      @NotNull

    public String getAttachmentRetentionPeriod() {
    return attachmentRetentionPeriod;
  }

  public void setAttachmentRetentionPeriod(String attachmentRetentionPeriod) {
    this.attachmentRetentionPeriod = attachmentRetentionPeriod;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationRetentionPolicy organizationRetentionPolicy = (OrganizationRetentionPolicy) o;
    return Objects.equals(this.logRetentionPeriod, organizationRetentionPolicy.logRetentionPeriod) &&
        Objects.equals(this.launchRetentionPeriod, organizationRetentionPolicy.launchRetentionPeriod) &&
        Objects.equals(this.attachmentRetentionPeriod, organizationRetentionPolicy.attachmentRetentionPeriod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logRetentionPeriod, launchRetentionPeriod, attachmentRetentionPeriod);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationRetentionPolicy {\n");
    
    sb.append("    logRetentionPeriod: ").append(toIndentedString(logRetentionPeriod)).append("\n");
    sb.append("    launchRetentionPeriod: ").append(toIndentedString(launchRetentionPeriod)).append("\n");
    sb.append("    attachmentRetentionPeriod: ").append(toIndentedString(attachmentRetentionPeriod)).append("\n");
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
