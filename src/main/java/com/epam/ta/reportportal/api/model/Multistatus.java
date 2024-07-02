package com.epam.ta.reportportal.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * Multistatus
 */
@Validated



public class Multistatus   {
  @JsonProperty("multistatus")
  private MultistatusMultistatus multistatus = null;

  public Multistatus multistatus(MultistatusMultistatus multistatus) {
    this.multistatus = multistatus;
    return this;
  }

  /**
   * Get multistatus
   * @return multistatus
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public MultistatusMultistatus getMultistatus() {
    return multistatus;
  }

  public void setMultistatus(MultistatusMultistatus multistatus) {
    this.multistatus = multistatus;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Multistatus multistatus = (Multistatus) o;
    return Objects.equals(this.multistatus, multistatus.multistatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(multistatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Multistatus {\n");
    
    sb.append("    multistatus: ").append(toIndentedString(multistatus)).append("\n");
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
