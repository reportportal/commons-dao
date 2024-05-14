package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

/**
 * UserAccountInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class UserAccountInfo extends UserDetails  {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("created_at")
  private Instant createdAt = null;

  @JsonProperty("updated_at")
  private Instant updatedAt = null;

  @JsonProperty("last_login_at")
  private Instant lastLoginAt = null;

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
  
  @Min(0L)  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
  
    @Valid
    public Instant getLastLoginAt() {
    return lastLoginAt;
  }

  public void setLastLoginAt(Instant lastLoginAt) {
    this.lastLoginAt = lastLoginAt;
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
        Objects.equals(this.createdAt, userAccountInfo.createdAt) &&
        Objects.equals(this.updatedAt, userAccountInfo.updatedAt) &&
        Objects.equals(this.lastLoginAt, userAccountInfo.lastLoginAt) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, updatedAt, lastLoginAt, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserAccountInfo {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    lastLoginAt: ").append(toIndentedString(lastLoginAt)).append("\n");
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
