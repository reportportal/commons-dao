package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * Comprehensive information about a project.
 */
@Schema(description = "Comprehensive information about a project.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T07:48:32.405970064+03:00[Europe/Istanbul]")


public class ProjectProfile extends ProjectInfo  {
  @JsonProperty("relationships")
  private ProjectRelation relationships = null;

  public ProjectProfile relationships(ProjectRelation relationships) {
    this.relationships = relationships;
    return this;
  }

  /**
   * Get relationships
   * @return relationships
   **/
  @Schema(description = "")
  
    @Valid
    public ProjectRelation getRelationships() {
    return relationships;
  }

  public void setRelationships(ProjectRelation relationships) {
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
    return Objects.equals(this.relationships, projectProfile.relationships) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(relationships, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectProfile {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
