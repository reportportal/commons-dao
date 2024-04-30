package com.epam.ta.reportportal.entity.organization;

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

}
