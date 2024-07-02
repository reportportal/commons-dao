package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * UserAccountInfo
 */
@Validated



public class UserAccountInfo extends UserDetails  {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("uuid")
  private UUID uuid = null;

  @JsonProperty("created_at")
  private Instant createdAt = null;

  @JsonProperty("updated_at")
  private Instant updatedAt = null;

  @JsonProperty("last_login_at")
  private Instant lastLoginAt = null;

  /**
   * Indicates through which service or authentication method the user account was created.
   */
  public enum AuthProviderEnum {
    INTERNAL("INTERNAL"),
    
    UPSA("UPSA"),
    
    GITHUB("GITHUB"),
    
    LDAP("LDAP"),
    
    SAML("SAML"),
    
    SCIM("SCIM");

    private String value;

    AuthProviderEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AuthProviderEnum fromValue(String text) {
      for (AuthProviderEnum b : AuthProviderEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("auth_provider")
  private AuthProviderEnum authProvider = AuthProviderEnum.INTERNAL;

  public UserAccountInfo id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * User internal identifier.
   * minimum: 0
   * @return id
   **/
  @Schema(description = "User internal identifier.")
      @NotNull

  @Min(0L)  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserAccountInfo uuid(UUID uuid) {
    this.uuid = uuid;
    return this;
  }

  /**
   * User ID for external systems.
   * @return uuid
   **/
  @Schema(description = "User ID for external systems.")
      @NotNull

    @Valid
    public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public UserAccountInfo createdAt(Instant createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * When user's account was created.
   * @return createdAt
   **/
  @Schema(description = "When user's account was created.")
      @NotNull

    @Valid
    public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public UserAccountInfo updatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * When user's data was modifed.
   * @return updatedAt
   **/
  @Schema(description = "When user's data was modifed.")
      @NotNull

    @Valid
    public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public UserAccountInfo lastLoginAt(Instant lastLoginAt) {
    this.lastLoginAt = lastLoginAt;
    return this;
  }

  /**
   * When user last logged in.
   * @return lastLoginAt
   **/
  @Schema(description = "When user last logged in.")
      @NotNull

    @Valid
    public Instant getLastLoginAt() {
    return lastLoginAt;
  }

  public void setLastLoginAt(Instant lastLoginAt) {
    this.lastLoginAt = lastLoginAt;
  }

  public UserAccountInfo authProvider(AuthProviderEnum authProvider) {
    this.authProvider = authProvider;
    return this;
  }

  /**
   * Indicates through which service or authentication method the user account was created.
   * @return authProvider
   **/
  @Schema(description = "Indicates through which service or authentication method the user account was created.")
      @NotNull

    public AuthProviderEnum getAuthProvider() {
    return authProvider;
  }

  public void setAuthProvider(AuthProviderEnum authProvider) {
    this.authProvider = authProvider;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserAccountInfo userAccountInfo = (UserAccountInfo) o;
    return Objects.equals(this.id, userAccountInfo.id) &&
        Objects.equals(this.uuid, userAccountInfo.uuid) &&
        Objects.equals(this.createdAt, userAccountInfo.createdAt) &&
        Objects.equals(this.updatedAt, userAccountInfo.updatedAt) &&
        Objects.equals(this.lastLoginAt, userAccountInfo.lastLoginAt) &&
        Objects.equals(this.authProvider, userAccountInfo.authProvider) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, uuid, createdAt, updatedAt, lastLoginAt, authProvider, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserAccountInfo {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    lastLoginAt: ").append(toIndentedString(lastLoginAt)).append("\n");
    sb.append("    authProvider: ").append(toIndentedString(authProvider)).append("\n");
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
