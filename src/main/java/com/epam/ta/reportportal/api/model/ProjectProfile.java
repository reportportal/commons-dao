package com.epam.ta.reportportal.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Comprehensive information about a project.
 */
@Schema(description = "Comprehensive information about a project.")
@Validated



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
      @NotNull

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
