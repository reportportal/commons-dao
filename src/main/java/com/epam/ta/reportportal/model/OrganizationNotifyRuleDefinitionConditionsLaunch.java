package com.epam.ta.reportportal.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * #TODO
 */
@Schema(description = "#TODO")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-14T12:57:43.836661731+03:00[Europe/Istanbul]")


public class OrganizationNotifyRuleDefinitionConditionsLaunch   {
  @JsonProperty("names")
  @Valid
  private List<String> names = null;

  @JsonProperty("attributes")
  @Valid
  private List<OrganizationNotifyRuleDefinitionConditionsLaunchAttributes> attributes = null;

  /**
   * #TODO
   */
  public enum AttributesOperatorEnum {
    AND("AND"),
    
    OR("OR");

    private String value;

    AttributesOperatorEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AttributesOperatorEnum fromValue(String text) {
      for (AttributesOperatorEnum b : AttributesOperatorEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("attributes_operator")
  private AttributesOperatorEnum attributesOperator = AttributesOperatorEnum.AND;

  /**
   * #TODO
   */
  public enum ResultConditionEnum {
    ALWAYS("ALWAYS"),
    
    FAILED("FAILED"),
    
    TO_INVESTIGATE("TO_INVESTIGATE"),
    
    MORE_10("MORE_10"),
    
    MORE_20("MORE_20"),
    
    MORE_50("MORE_50");

    private String value;

    ResultConditionEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ResultConditionEnum fromValue(String text) {
      for (ResultConditionEnum b : ResultConditionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("result_condition")
  private ResultConditionEnum resultCondition = null;

  public OrganizationNotifyRuleDefinitionConditionsLaunch names(List<String> names) {
    this.names = names;
    return this;
  }

  public OrganizationNotifyRuleDefinitionConditionsLaunch addNamesItem(String namesItem) {
    if (this.names == null) {
      this.names = new ArrayList<>();
    }
    this.names.add(namesItem);
    return this;
  }

  /**
   * #TODO
   * @return names
   **/
  @Schema(description = "#TODO")
  
    public List<String> getNames() {
    return names;
  }

  public void setNames(List<String> names) {
    this.names = names;
  }

  public OrganizationNotifyRuleDefinitionConditionsLaunch attributes(List<OrganizationNotifyRuleDefinitionConditionsLaunchAttributes> attributes) {
    this.attributes = attributes;
    return this;
  }

  public OrganizationNotifyRuleDefinitionConditionsLaunch addAttributesItem(OrganizationNotifyRuleDefinitionConditionsLaunchAttributes attributesItem) {
    if (this.attributes == null) {
      this.attributes = new ArrayList<>();
    }
    this.attributes.add(attributesItem);
    return this;
  }

  /**
   * #TODO
   * @return attributes
   **/
  @Schema(description = "#TODO")
      @Valid
    public List<OrganizationNotifyRuleDefinitionConditionsLaunchAttributes> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<OrganizationNotifyRuleDefinitionConditionsLaunchAttributes> attributes) {
    this.attributes = attributes;
  }

  public OrganizationNotifyRuleDefinitionConditionsLaunch attributesOperator(AttributesOperatorEnum attributesOperator) {
    this.attributesOperator = attributesOperator;
    return this;
  }

  /**
   * #TODO
   * @return attributesOperator
   **/
  @Schema(description = "#TODO")
  
    public AttributesOperatorEnum getAttributesOperator() {
    return attributesOperator;
  }

  public void setAttributesOperator(AttributesOperatorEnum attributesOperator) {
    this.attributesOperator = attributesOperator;
  }

  public OrganizationNotifyRuleDefinitionConditionsLaunch resultCondition(ResultConditionEnum resultCondition) {
    this.resultCondition = resultCondition;
    return this;
  }

  /**
   * #TODO
   * @return resultCondition
   **/
  @Schema(description = "#TODO")
  
    public ResultConditionEnum getResultCondition() {
    return resultCondition;
  }

  public void setResultCondition(ResultConditionEnum resultCondition) {
    this.resultCondition = resultCondition;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationNotifyRuleDefinitionConditionsLaunch organizationNotifyRuleDefinitionConditionsLaunch = (OrganizationNotifyRuleDefinitionConditionsLaunch) o;
    return Objects.equals(this.names, organizationNotifyRuleDefinitionConditionsLaunch.names) &&
        Objects.equals(this.attributes, organizationNotifyRuleDefinitionConditionsLaunch.attributes) &&
        Objects.equals(this.attributesOperator, organizationNotifyRuleDefinitionConditionsLaunch.attributesOperator) &&
        Objects.equals(this.resultCondition, organizationNotifyRuleDefinitionConditionsLaunch.resultCondition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(names, attributes, attributesOperator, resultCondition);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationNotifyRuleDefinitionConditionsLaunch {\n");
    
    sb.append("    names: ").append(toIndentedString(names)).append("\n");
    sb.append("    attributes: ").append(toIndentedString(attributes)).append("\n");
    sb.append("    attributesOperator: ").append(toIndentedString(attributesOperator)).append("\n");
    sb.append("    resultCondition: ").append(toIndentedString(resultCondition)).append("\n");
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
