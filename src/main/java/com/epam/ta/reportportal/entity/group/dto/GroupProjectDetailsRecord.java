package com.epam.ta.reportportal.entity.group.dto;

import com.epam.ta.reportportal.commons.ReportPortalUser.ProjectDetails;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record GroupProjectDetailsRecord(
    Long projectId,
    String projectName,
    String[] projectRoles
) {
  public ProjectDetails toProjectDetails() {
    List<ProjectRole> projectRoles = Arrays.stream(this.projectRoles)
        .map(ProjectRole::valueOf)
        .collect(Collectors.toList());
    return new ProjectDetails(projectId, projectName, projectRoles);
  }
}
