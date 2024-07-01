package com.epam.ta.reportportal.api.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* InlineResponse2002
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = OrganizationUserProfile.class, name = "OrganizationUserProfile"),
  @JsonSubTypes.Type(value = EventStatus.class, name = "EventStatus")
})
public interface InlineResponse2002 {

}
