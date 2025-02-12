package com.epam.ta.reportportal.entity.group.dto;

public record GroupProjectDetailsRecord(
    Long projectId,
    String projectName,
    String[] projectRoles
) {}
