package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;

/**
 * Comprehensive information about a project.
 */
@Schema(description = "Comprehensive information about a project.")
@Validated



public class ProjectProfile extends ProjectMetadata  {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("slug")
  private String slug = null;

  @JsonProperty("relationships")
  @Valid
  private List<ProjectRelation> relationships = null;

  public ProjectProfile name(String name) {
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

  public ProjectProfile slug(String slug) {
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

  public ProjectProfile relationships(List<ProjectRelation> relationships) {
    this.relationships = relationships;
    return this;
  }

  public ProjectProfile addRelationshipsItem(ProjectRelation relationshipsItem) {
    if (this.relationships == null) {
      this.relationships = new ArrayList<>();
    }
    this.relationships.add(relationshipsItem);
    return this;
  }

  /**
   * Get relationships
   * @return relationships
   **/
  @Schema(description = "")
      @NotNull
    @Valid
    public List<ProjectRelation> getRelationships() {
    return relationships;
  }

  public void setRelationships(List<ProjectRelation> relationships) {
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
    ProjectProfile projectProfile = (ProjectProfile) o;
    return Objects.equals(this.name, projectProfile.name) &&
        Objects.equals(this.slug, projectProfile.slug) &&
        Objects.equals(this.relationships, projectProfile.relationships) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, slug, relationships, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectProfile {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    slug: ").append(toIndentedString(slug)).append("\n");
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
