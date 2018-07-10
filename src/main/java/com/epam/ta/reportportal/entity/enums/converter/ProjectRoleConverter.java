package com.epam.ta.reportportal.entity.enums.converter;

import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.exception.ReportPortalException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter(autoApply = true)
public class ProjectRoleConverter implements AttributeConverter<ProjectRole, String> {
	@Override
	public String convertToDatabaseColumn(ProjectRole attribute) {
		return attribute.toString();
	}

	@Override
	public ProjectRole convertToEntityAttribute(String dbProjectRoleName) {
		Optional<ProjectRole> projectRole = ProjectRole.forName(dbProjectRoleName);
		return projectRole.orElseThrow(() -> {
			throw new ReportPortalException("Can not convert project role name from database.");
		});
	}
}
