package com.epam.ta.reportportal.entity.organization;

import com.epam.reportportal.rules.exception.ErrorType;
import com.epam.reportportal.rules.exception.ReportPortalException;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    private MembershipDetailsBuilder() {
    }

    public MembershipDetails.MembershipDetailsBuilder withOrgId(Long orgId) {
      this.orgId = orgId;
      return this;
    }

    public MembershipDetails.MembershipDetailsBuilder withOrgName(String orgName) {
      this.orgName = orgName;
      return this;
    }

    public MembershipDetails.MembershipDetailsBuilder withOrgRole(String orgRole) {
      this.orgRole = OrganizationRole.forName(orgRole)
          .orElseThrow(() -> new ReportPortalException(ErrorType.ROLE_NOT_FOUND, orgRole));
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


    public MembershipDetails.MembershipDetailsBuilder withProjectRole(String projectRole) {
      this.projectRole = ProjectRole.forName(projectRole)
          .orElseThrow(() -> new ReportPortalException(ErrorType.ROLE_NOT_FOUND, projectRole));
      return this;
    }

    public MembershipDetails build() {
      return new MembershipDetails(orgId, orgName, orgRole, projectId, projectName, projectKey,
          projectRole);
    }
  }


}
