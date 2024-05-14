package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;

/**
 * A slug is used to identify a resource. It should be unique and contain only lowercase letters, numbers, and hyphens. It should not start or end with a hyphen.
 */
@Schema(description = "A slug is used to identify a resource. It should be unique and contain only lowercase letters, numbers, and hyphens. It should not start or end with a hyphen.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class Slug   {
  @JsonProperty("slug")
  private String slug = null;

  public Slug slug(String slug) {
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
    Slug slug = (Slug) o;
    return Objects.equals(this.slug, slug.slug);
  }

  @Override
  public int hashCode() {
    return Objects.hash(slug);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Slug {\n");
    
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
