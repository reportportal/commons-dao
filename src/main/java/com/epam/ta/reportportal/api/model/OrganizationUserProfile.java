package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * A comprehensive set of user information related to a user&#x27;s organization.
 */
@Schema(description = "A comprehensive set of user information related to a user's organization.")
@Validated



public class OrganizationUserProfile extends UserAccountInfo  {
  /**
   * Organization user role.
   */
  public enum OrganizationRoleEnum {
    MEMBER("MEMBER"),
    
    MANAGER("MANAGER");

    private String value;

    OrganizationRoleEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static OrganizationRoleEnum fromValue(String text) {
      for (OrganizationRoleEnum b : OrganizationRoleEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("organization_role")
  private OrganizationRoleEnum organizationRole = null;

  @JsonProperty("relationships")
  private OrganizationUserRelation relationships = null;

  public OrganizationUserProfile organizationRole(OrganizationRoleEnum organizationRole) {
    this.organizationRole = organizationRole;
    return this;
  }

  /**
   * Organization user role.
   * @return organizationRole
   **/
  @Schema(required = true, description = "Organization user role.")
      @NotNull

    public OrganizationRoleEnum getOrganizationRole() {
    return organizationRole;
  }

  public void setOrganizationRole(OrganizationRoleEnum organizationRole) {
    this.organizationRole = organizationRole;
  }

  public OrganizationUserProfile relationships(OrganizationUserRelation relationships) {
    this.relationships = relationships;
    return this;
  }

  /**
   * Get relationships
   * @return relationships
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public OrganizationUserRelation getRelationships() {
    return relationships;
  }

  public void setRelationships(OrganizationUserRelation relationships) {
    this.relationships = relationships;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationUserProfile organizationUserProfile = (OrganizationUserProfile) o;
    return Objects.equals(this.organizationRole, organizationUserProfile.organizationRole) &&
        Objects.equals(this.relationships, organizationUserProfile.relationships) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(organizationRole, relationships, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationUserProfile {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    organizationRole: ").append(toIndentedString(organizationRole)).append("\n");
    sb.append("    relationships: ").append(toIndentedString(relationships)).append("\n");
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
