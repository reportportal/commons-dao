package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

/**
 * Various information about the organization information.
 */
@Schema(description = "Various information about the organization information.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class OrganizationInfo extends OrganizationDetails  {
  @JsonProperty("id")
  private Long id = null;

  /**
   * Type for automatization purposes.  There are three types: - INTERNAL: created by API request. - PERSONAL: created for a user after the user creating. Only Enterprise edition feature. - EXTERNAL: created for external integrations.
   */
  public enum TypeEnum {
    INTERNAL("INTERNAL"),
    
    PERSONAL("PERSONAL"),
    
    EXTERNAL("EXTERNAL");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("type")
  private TypeEnum type = null;

  @JsonProperty("external_id")
  private String externalId = null;

  @JsonProperty("created_at")
  private Instant createdAt = null;

  @JsonProperty("updated_at")
  private Instant updatedAt = null;

  public OrganizationInfo id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Organization ReportPortal identifier.
   * minimum: 0
   * @return id
   **/
  @Schema(description = "Organization ReportPortal identifier.")
  
  @Min(0L)  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public OrganizationInfo type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Type for automatization purposes.  There are three types: - INTERNAL: created by API request. - PERSONAL: created for a user after the user creating. Only Enterprise edition feature. - EXTERNAL: created for external integrations.
   * @return type
   **/
  @Schema(description = "Type for automatization purposes.  There are three types: - INTERNAL: created by API request. - PERSONAL: created for a user after the user creating. Only Enterprise edition feature. - EXTERNAL: created for external integrations.")
  
    public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public OrganizationInfo externalId(String externalId) {
    this.externalId = externalId;
    return this;
  }

  /**
   * External organization identifier. It is created for integration.
   * @return externalId
   **/
  @Schema(description = "External organization identifier. It is created for integration.")
  
    public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  public OrganizationInfo createdAt(Instant createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Creation date.
   * @return createdAt
   **/
  @Schema(description = "Creation date.")
  
    @Valid
    public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public OrganizationInfo updatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Last update date.
   * @return updatedAt
   **/
  @Schema(description = "Last update date.")
  
    @Valid
    public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationInfo organizationInfo = (OrganizationInfo) o;
    return Objects.equals(this.id, organizationInfo.id) &&
        Objects.equals(this.type, organizationInfo.type) &&
        Objects.equals(this.externalId, organizationInfo.externalId) &&
        Objects.equals(this.createdAt, organizationInfo.createdAt) &&
        Objects.equals(this.updatedAt, organizationInfo.updatedAt) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, externalId, createdAt, updatedAt, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationInfo {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    externalId: ").append(toIndentedString(externalId)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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
