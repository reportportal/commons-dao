package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * Contains details associated with a user account.  User has a unique email address, full name, and account type.  Account type can be either &#x60;ADMIN&#x60; or &#x60;USER&#x60;. Only &#x60;ADMIN&#x60; users have access to change account type.
 */
@Schema(description = "Contains details associated with a user account.  User has a unique email address, full name, and account type.  Account type can be either `ADMIN` or `USER`. Only `ADMIN` users have access to change account type.")
@Validated



public class UserDetails   {
  @JsonProperty("email")
  private String email = null;

  @JsonProperty("full_name")
  private String fullName = null;

  /**
   * Instance account role.
   */
  public enum InstanceRoleEnum {
    ADMINISTRATOR("ADMINISTRATOR"),
    
    USER("USER");

    private String value;

    InstanceRoleEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static InstanceRoleEnum fromValue(String text) {
      for (InstanceRoleEnum b : InstanceRoleEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("instance_role")
  private InstanceRoleEnum instanceRole = null;

  @JsonProperty("external_id")
  private String externalId = null;

  public UserDetails email(String email) {
    this.email = email;
    return this;
  }

  /**
   * User email.
   * @return email
   **/
  @Schema(description = "User email.")
      @NotNull

    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserDetails fullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  /**
   * User full name.
   * @return fullName
   **/
  @Schema(description = "User full name.")
      @NotNull

    public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public UserDetails instanceRole(InstanceRoleEnum instanceRole) {
    this.instanceRole = instanceRole;
    return this;
  }

  /**
   * Instance account role.
   * @return instanceRole
   **/
  @Schema(description = "Instance account role.")
      @NotNull

    public InstanceRoleEnum getInstanceRole() {
    return instanceRole;
  }

  public void setInstanceRole(InstanceRoleEnum instanceRole) {
    this.instanceRole = instanceRole;
  }

  public UserDetails externalId(String externalId) {
    this.externalId = externalId;
    return this;
  }

  /**
   * User external identifier. Provided by external systems.
   * @return externalId
   **/
  @Schema(description = "User external identifier. Provided by external systems.")
      @NotNull

    public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDetails userDetails = (UserDetails) o;
    return Objects.equals(this.email, userDetails.email) &&
        Objects.equals(this.fullName, userDetails.fullName) &&
        Objects.equals(this.instanceRole, userDetails.instanceRole) &&
        Objects.equals(this.externalId, userDetails.externalId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, fullName, instanceRole, externalId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserDetails {\n");
    
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    instanceRole: ").append(toIndentedString(instanceRole)).append("\n");
    sb.append("    externalId: ").append(toIndentedString(externalId)).append("\n");
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
