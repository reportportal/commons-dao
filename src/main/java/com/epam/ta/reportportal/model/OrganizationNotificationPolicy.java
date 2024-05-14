package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;

/**
 * OrganizationNotificationPolicy
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class OrganizationNotificationPolicy  implements SettingsSettingNameBody, SettingsSettingNameBody1, SettingsSettingNameBody2 {
  @JsonProperty("auto_notification")
  private Boolean autoNotification = null;

  public OrganizationNotificationPolicy autoNotification(Boolean autoNotification) {
    this.autoNotification = autoNotification;
    return this;
  }

  /**
   * Get autoNotification
   * @return autoNotification
   **/
  @Schema(description = "")
  
    public Boolean isAutoNotification() {
    return autoNotification;
  }

  public void setAutoNotification(Boolean autoNotification) {
    this.autoNotification = autoNotification;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationNotificationPolicy organizationNotificationPolicy = (OrganizationNotificationPolicy) o;
    return Objects.equals(this.autoNotification, organizationNotificationPolicy.autoNotification);
  }

  @Override
  public int hashCode() {
    return Objects.hash(autoNotification);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationNotificationPolicy {\n");
    
    sb.append("    autoNotification: ").append(toIndentedString(autoNotification)).append("\n");
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
