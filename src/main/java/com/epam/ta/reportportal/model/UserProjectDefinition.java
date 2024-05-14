package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;

/**
 * Information about user&#x27;s project.
 */
@Schema(description = "Information about user's project.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class UserProjectDefinition   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("slug")
  private String slug = null;

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

  public UserProjectDefinition id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Project internal identifier.
   * minimum: 0
   * @return id
   **/
  @Schema(description = "Project internal identifier.")
  
  @Min(0L)  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserProjectDefinition slug(String slug) {
    this.slug = slug;
    return this;
  }

  /**
   * A slug is used to identify a resource. It should be unique and contain only lowercase letters, numbers, and hyphens. It should not start or end with a hyphen.
   * @return slug
   **/
  @Schema(description = "A slug is used to identify a resource. It should be unique and contain only lowercase letters, numbers, and hyphens. It should not start or end with a hyphen.")
  
  @Pattern(regexp="^[a-z0-9]+(?:-[a-z0-9]+)*$")   public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public UserProjectDefinition role(RoleEnum role) {
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
    UserProjectDefinition userProjectDefinition = (UserProjectDefinition) o;
    return Objects.equals(this.id, userProjectDefinition.id) &&
        Objects.equals(this.slug, userProjectDefinition.slug) &&
        Objects.equals(this.role, userProjectDefinition.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, slug, role);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserProjectDefinition {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    slug: ").append(toIndentedString(slug)).append("\n");
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
