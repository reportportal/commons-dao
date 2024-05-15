package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;

/**
 * Contains details associated with a user account.  User has a unique email address, full name, and account type.  Account type can be either &#x60;ADMIN&#x60; or &#x60;USER&#x60;. Only &#x60;ADMIN&#x60; users have access to change account type.
 */
@Schema(description = "Contains details associated with a user account.  User has a unique email address, full name, and account type.  Account type can be either `ADMIN` or `USER`. Only `ADMIN` users have access to change account type.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class UserDetails   {
  @JsonProperty("email")
  private String email = null;

  @JsonProperty("full_name")
  private String fullName = null;

  /**
   * Instance account type.
   */
  public enum TypeEnum {
    ADMIN("ADMIN"),
    
    USER("USER");

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

  public UserDetails email(String email) {
    this.email = email;
    return this;
  }

  /**
   * User email.
   * @return email
   **/
  @Schema(description = "User email.")
  
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
  
    public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public UserDetails type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Instance account type.
   * @return type
   **/
  @Schema(description = "Instance account type.")
  
    public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
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
        Objects.equals(this.type, userDetails.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, fullName, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserDetails {\n");
    
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
