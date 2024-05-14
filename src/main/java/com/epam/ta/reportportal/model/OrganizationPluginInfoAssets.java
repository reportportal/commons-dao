package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;

/**
 * #TODO
 */
@Schema(description = "#TODO")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class OrganizationPluginInfoAssets   {
  @JsonProperty("icon")
  private String icon = "plugin-icon.svg";

  @JsonProperty("main")
  private String main = null;

  @JsonProperty("descriptor")
  private String descriptor = "metadata.json";

  public OrganizationPluginInfoAssets icon(String icon) {
    this.icon = icon;
    return this;
  }

  /**
   * Get icon
   * @return icon
   **/
  @Schema(description = "")
  
    public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public OrganizationPluginInfoAssets main(String main) {
    this.main = main;
    return this;
  }

  /**
   * Get main
   * @return main
   **/
  @Schema(description = "")
  
    public String getMain() {
    return main;
  }

  public void setMain(String main) {
    this.main = main;
  }

  public OrganizationPluginInfoAssets descriptor(String descriptor) {
    this.descriptor = descriptor;
    return this;
  }

  /**
   * Get descriptor
   * @return descriptor
   **/
  @Schema(description = "")
  
    public String getDescriptor() {
    return descriptor;
  }

  public void setDescriptor(String descriptor) {
    this.descriptor = descriptor;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationPluginInfoAssets organizationPluginInfoAssets = (OrganizationPluginInfoAssets) o;
    return Objects.equals(this.icon, organizationPluginInfoAssets.icon) &&
        Objects.equals(this.main, organizationPluginInfoAssets.main) &&
        Objects.equals(this.descriptor, organizationPluginInfoAssets.descriptor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(icon, main, descriptor);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationPluginInfoAssets {\n");
    
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
    sb.append("    main: ").append(toIndentedString(main)).append("\n");
    sb.append("    descriptor: ").append(toIndentedString(descriptor)).append("\n");
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
