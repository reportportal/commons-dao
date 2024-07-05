package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;

/**
 * OrganizationProjectInfo
 */
@Validated



public class OrganizationProjectInfo extends ProjectMetadata  {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("slug")
  private String slug = null;

  public OrganizationProjectInfo name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Display project name.
   * @return name
   **/
  @Schema(required = true, description = "Display project name.")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OrganizationProjectInfo slug(String slug) {
    this.slug = slug;
    return this;
  }

  /**
   * Get slug
   * @return slug
   **/
  @Schema(description = "")
      @NotNull

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
    OrganizationProjectInfo organizationProjectInfo = (OrganizationProjectInfo) o;
    return Objects.equals(this.name, organizationProjectInfo.name) &&
        Objects.equals(this.slug, organizationProjectInfo.slug) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, slug, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationProjectInfo {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
