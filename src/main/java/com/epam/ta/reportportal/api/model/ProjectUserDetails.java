package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;

/**
 * There are the following roles:  - &#x60;EDITOR&#x60; is a full-access role for the project. By default, all users with a &#x60;MANAGER&#x60; role have the &#x60;EDITOR&#x60; role. - &#x60;VIEWER&#x60; is a base role for members. Provides a read-only permission. - &#x60;null&#x60; is a state you can use to unassign a user from the project.
 */
@Schema(description = "There are the following roles:  - `EDITOR` is a full-access role for the project. By default, all users with a `MANAGER` role have the `EDITOR` role. - `VIEWER` is a base role for members. Provides a read-only permission. - `null` is a state you can use to unassign a user from the project.")
@Validated



public class ProjectUserDetails   {
  /**
   * User role in the project.
   */
  public enum RoleEnum {
    EDITOR("EDITOR"),
    
    VIEWER("VIEWER");

    private String value;

    RoleEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static RoleEnum fromValue(String text) {
      for (RoleEnum b : RoleEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("role")
  private RoleEnum role = null;

  public ProjectUserDetails role(RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * User role in the project.
   * @return role
   **/
  @Schema(description = "User role in the project.")
  
    public RoleEnum getRole() {
    return role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectUserDetails projectUserDetails = (ProjectUserDetails) o;
    return Objects.equals(this.role, projectUserDetails.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectUserDetails {\n");
    
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
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
