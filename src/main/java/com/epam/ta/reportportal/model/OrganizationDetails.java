package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

/**
 * Basic information provided by the client.  A &#x60;slug&#x60; is a unique identifying part of an organization. Generated according to the organization name if not specified by the user. 
 */
@Schema(description = "Basic information provided by the client.  A `slug` is a unique identifying part of an organization. Generated according to the organization name if not specified by the user. ")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class OrganizationDetails   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("slug")
  private String slug = null;

  public OrganizationDetails name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Organization display name.
   * @return name
   **/
  @Schema(required = true, description = "Organization display name.")
      @NotNull

  @Size(min=1)   public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OrganizationDetails slug(String slug) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationDetails organizationDetails = (OrganizationDetails) o;
    return Objects.equals(this.name, organizationDetails.name) &&
        Objects.equals(this.slug, organizationDetails.slug);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, slug);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationDetails {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    slug: ").append(toIndentedString(slug)).append("\n");
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
