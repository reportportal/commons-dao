package com.epam.ta.reportportal.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * #TODO
 */
@Schema(description = "#TODO")
@Validated



public class OrganizationPluginInfo   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("version")
  private String version = null;

  /**
   * Gets or Sets groups
   */
  public enum GroupsEnum {
    BTS("BTS"),
    
    NOTIFICATION("NOTIFICATION"),
    
    OTHER("OTHER"),
    
    AUTH("AUTH");

    private String value;

    GroupsEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static GroupsEnum fromValue(String text) {
      for (GroupsEnum b : GroupsEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("groups")
  @Valid
  private List<GroupsEnum> groups = null;

  @JsonProperty("configured")
  private Boolean configured = null;

  /**
   * #TODO
   */
  public enum ConfigurationTypeEnum {
    SINGLE("SINGLE"),
    
    MULTIPLE("MULTIPLE");

    private String value;

    ConfigurationTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ConfigurationTypeEnum fromValue(String text) {
      for (ConfigurationTypeEnum b : ConfigurationTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("configuration_type")
  private ConfigurationTypeEnum configurationType = null;

  @JsonProperty("created_at")
  private Instant createdAt = null;

  @JsonProperty("assets")
  private OrganizationPluginInfoAssets assets = null;

  @JsonProperty("links")
  private OrganizationPluginInfoLinks links = null;

  @JsonProperty("commands")
  private OrganizationPluginInfoCommands commands = null;

  @JsonProperty("details")
  private OrganizationPluginInfoDetails details = null;

  public OrganizationPluginInfo id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * #TODO
   * minimum: 0
   * @return id
   **/
  @Schema(description = "#TODO")
      @NotNull

  @Min(0L)  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public OrganizationPluginInfo name(String name) {
    this.name = name;
    return this;
  }

  /**
   * #TODO
   * @return name
   **/
  @Schema(description = "#TODO")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OrganizationPluginInfo description(String description) {
    this.description = description;
    return this;
  }

  /**
   * #TODO
   * @return description
   **/
  @Schema(description = "#TODO")
      @NotNull

    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public OrganizationPluginInfo version(String version) {
    this.version = version;
    return this;
  }

  /**
   * #TODO
   * @return version
   **/
  @Schema(description = "#TODO")
      @NotNull

    public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public OrganizationPluginInfo groups(List<GroupsEnum> groups) {
    this.groups = groups;
    return this;
  }

  public OrganizationPluginInfo addGroupsItem(GroupsEnum groupsItem) {
    if (this.groups == null) {
      this.groups = new ArrayList<>();
    }
    this.groups.add(groupsItem);
    return this;
  }

  /**
   * #TODO
   * @return groups
   **/
  @Schema(description = "#TODO")
      @NotNull

    public List<GroupsEnum> getGroups() {
    return groups;
  }

  public void setGroups(List<GroupsEnum> groups) {
    this.groups = groups;
  }

  public OrganizationPluginInfo configured(Boolean configured) {
    this.configured = configured;
    return this;
  }

  /**
   * #TODO
   * @return configured
   **/
  @Schema(description = "#TODO")
      @NotNull

    public Boolean isConfigured() {
    return configured;
  }

  public void setConfigured(Boolean configured) {
    this.configured = configured;
  }

  public OrganizationPluginInfo configurationType(ConfigurationTypeEnum configurationType) {
    this.configurationType = configurationType;
    return this;
  }

  /**
   * #TODO
   * @return configurationType
   **/
  @Schema(description = "#TODO")
      @NotNull

    public ConfigurationTypeEnum getConfigurationType() {
    return configurationType;
  }

  public void setConfigurationType(ConfigurationTypeEnum configurationType) {
    this.configurationType = configurationType;
  }

  public OrganizationPluginInfo createdAt(Instant createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * #TODO
   * @return createdAt
   **/
  @Schema(description = "#TODO")
      @NotNull

    @Valid
    public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public OrganizationPluginInfo assets(OrganizationPluginInfoAssets assets) {
    this.assets = assets;
    return this;
  }

  /**
   * Get assets
   * @return assets
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public OrganizationPluginInfoAssets getAssets() {
    return assets;
  }

  public void setAssets(OrganizationPluginInfoAssets assets) {
    this.assets = assets;
  }

  public OrganizationPluginInfo links(OrganizationPluginInfoLinks links) {
    this.links = links;
    return this;
  }

  /**
   * Get links
   * @return links
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public OrganizationPluginInfoLinks getLinks() {
    return links;
  }

  public void setLinks(OrganizationPluginInfoLinks links) {
    this.links = links;
  }

  public OrganizationPluginInfo commands(OrganizationPluginInfoCommands commands) {
    this.commands = commands;
    return this;
  }

  /**
   * Get commands
   * @return commands
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public OrganizationPluginInfoCommands getCommands() {
    return commands;
  }

  public void setCommands(OrganizationPluginInfoCommands commands) {
    this.commands = commands;
  }

  public OrganizationPluginInfo details(OrganizationPluginInfoDetails details) {
    this.details = details;
    return this;
  }

  /**
   * Get details
   * @return details
   **/
  @Schema(description = "")
      @NotNull

    @Valid
    public OrganizationPluginInfoDetails getDetails() {
    return details;
  }

  public void setDetails(OrganizationPluginInfoDetails details) {
    this.details = details;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationPluginInfo organizationPluginInfo = (OrganizationPluginInfo) o;
    return Objects.equals(this.id, organizationPluginInfo.id) &&
        Objects.equals(this.name, organizationPluginInfo.name) &&
        Objects.equals(this.description, organizationPluginInfo.description) &&
        Objects.equals(this.version, organizationPluginInfo.version) &&
        Objects.equals(this.groups, organizationPluginInfo.groups) &&
        Objects.equals(this.configured, organizationPluginInfo.configured) &&
        Objects.equals(this.configurationType, organizationPluginInfo.configurationType) &&
        Objects.equals(this.createdAt, organizationPluginInfo.createdAt) &&
        Objects.equals(this.assets, organizationPluginInfo.assets) &&
        Objects.equals(this.links, organizationPluginInfo.links) &&
        Objects.equals(this.commands, organizationPluginInfo.commands) &&
        Objects.equals(this.details, organizationPluginInfo.details);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, version, groups, configured, configurationType, createdAt, assets, links, commands, details);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationPluginInfo {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    groups: ").append(toIndentedString(groups)).append("\n");
    sb.append("    configured: ").append(toIndentedString(configured)).append("\n");
    sb.append("    configurationType: ").append(toIndentedString(configurationType)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    assets: ").append(toIndentedString(assets)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    commands: ").append(toIndentedString(commands)).append("\n");
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
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
