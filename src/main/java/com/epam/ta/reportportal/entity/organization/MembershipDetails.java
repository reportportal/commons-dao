package com.epam.ta.reportportal.entity.organization;

import com.epam.ta.reportportal.entity.project.ProjectRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MembershipDetails {

  private Long orgId;
  private String orgName;
  private OrganizationRole orgRole;
  private Long projectId;
  private String projectName;
  private String projectKey;
  private ProjectRole projectRole;

  public static MembershipDetailsBuilder builder() {
    return new MembershipDetailsBuilder();
  }

  public static class MembershipDetailsBuilder {

    private Long orgId;
    private String orgName;
    private OrganizationRole orgRole;
    private Long projectId;
    private String projectName;
    private String projectKey;
    private ProjectRole projectRole;

    public MembershipDetailsBuilder() {
    }

    public MembershipDetails.MembershipDetailsBuilder withOrgId(Long orgId) {
      this.orgId = orgId;
      return this;
    }

    public MembershipDetails.MembershipDetailsBuilder withOrgName(String orgName) {
      this.orgName = orgName;
      return this;
    }

    public MembershipDetails.MembershipDetailsBuilder withOrgRole(OrganizationRole orgRole) {
      this.orgRole = orgRole;
      return this;
    }

    public MembershipDetails.MembershipDetailsBuilder withProjectId(Long projectId) {
      this.projectId = projectId;
      return this;
    }

    public MembershipDetails.MembershipDetailsBuilder withProjectName(String projectName) {
      this.projectName = projectName;
      return this;
    }

    public MembershipDetails.MembershipDetailsBuilder withProjectKey(String projectKey) {
      this.projectKey = projectKey;
      return this;
    }


    public MembershipDetails.MembershipDetailsBuilder withProjectRole(ProjectRole projectRole) {
      this.projectRole = projectRole;
      return this;
    }

    public MembershipDetails build() {
      return new MembershipDetails(orgId, orgName, orgRole, projectId, projectName, projectKey,
          projectRole);
    }
  }


}
