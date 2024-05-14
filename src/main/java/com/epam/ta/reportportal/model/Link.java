package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * Allows clients to dynamically follow the hypermedia links to quickly navigate to the relevant resources.  Uses for Hypermedia as the Engine of Application State (HATEOAS).  Represents the [JSON:API link object](https://jsonapi.org/format/#auto-id--link-objects) and accepts &#x60;Web linking&#x60; as the de-facto datamodel.
 */
@Schema(description = "Allows clients to dynamically follow the hypermedia links to quickly navigate to the relevant resources.  Uses for Hypermedia as the Engine of Application State (HATEOAS).  Represents the [JSON:API link object](https://jsonapi.org/format/#auto-id--link-objects) and accepts `Web linking` as the de-facto datamodel.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class Link   {
  @JsonProperty("href")
  private String href = null;

  @JsonProperty("rel")
  private String rel = null;

  public Link href(String href) {
    this.href = href;
    return this;
  }

  /**
   * The target resource URI reference.
   * @return href
   **/
  @Schema(required = true, description = "The target resource URI reference.")
      @NotNull

    public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public Link rel(String rel) {
    this.rel = rel;
    return this;
  }

  /**
   * The link relation type describes how the current context is related to the target resource.
   * @return rel
   **/
  @Schema(description = "The link relation type describes how the current context is related to the target resource.")
  
    public String getRel() {
    return rel;
  }

  public void setRel(String rel) {
    this.rel = rel;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Link link = (Link) o;
    return Objects.equals(this.href, link.href) &&
        Objects.equals(this.rel, link.rel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(href, rel);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Link {\n");
    
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    rel: ").append(toIndentedString(rel)).append("\n");
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
