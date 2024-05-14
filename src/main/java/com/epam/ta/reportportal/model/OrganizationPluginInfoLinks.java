package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * #TODO
 */
@Schema(description = "#TODO")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T07:48:32.405970064+03:00[Europe/Istanbul]")


public class OrganizationPluginInfoLinks   {
  @JsonProperty("source")
  private Link source = null;

  @JsonProperty("documentation")
  private Link documentation = null;

  public OrganizationPluginInfoLinks source(Link source) {
    this.source = source;
    return this;
  }

  /**
   * Get source
   * @return source
   **/
  @Schema(description = "")
  
    @Valid
    public Link getSource() {
    return source;
  }

  public void setSource(Link source) {
    this.source = source;
  }

  public OrganizationPluginInfoLinks documentation(Link documentation) {
    this.documentation = documentation;
    return this;
  }

  /**
   * Get documentation
   * @return documentation
   **/
  @Schema(description = "")
  
    @Valid
    public Link getDocumentation() {
    return documentation;
  }

  public void setDocumentation(Link documentation) {
    this.documentation = documentation;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationPluginInfoLinks organizationPluginInfoLinks = (OrganizationPluginInfoLinks) o;
    return Objects.equals(this.source, organizationPluginInfoLinks.source) &&
        Objects.equals(this.documentation, organizationPluginInfoLinks.documentation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(source, documentation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationPluginInfoLinks {\n");
    
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    documentation: ").append(toIndentedString(documentation)).append("\n");
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
