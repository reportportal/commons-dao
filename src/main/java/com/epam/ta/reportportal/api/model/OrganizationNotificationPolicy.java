package com.epam.ta.reportportal.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * OrganizationNotificationPolicy
 */
@Validated



public class OrganizationNotificationPolicy  implements InlineResponse2006, SettingsSettingNameBody, SettingsSettingNameBody1, SettingsSettingNameBody2 {
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
      @NotNull

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
