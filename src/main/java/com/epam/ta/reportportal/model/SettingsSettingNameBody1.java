package com.epam.ta.reportportal.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* SettingsSettingNameBody1
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = OrganizationRetentionPolicy.class, name = "OrganizationRetentionPolicy"),
  @JsonSubTypes.Type(value = OrganizationNotificationPolicy.class, name = "OrganizationNotificationPolicy")
})
public interface SettingsSettingNameBody1 {

}
