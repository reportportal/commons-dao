package com.epam.ta.reportportal.entity.enums.converter;

import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.exception.ReportPortalException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Ivan Budayeu
 */
@Converter(autoApply = true)
public class ProjectRoleConverter implements AttributeConverter<ProjectRole, String> {
	@Override
	public String convertToDatabaseColumn(ProjectRole attribute) {
		return attribute.toString();
	}

	@Override
	public ProjectRole convertToEntityAttribute(String dbProjectRoleName) {
		return ProjectRole.forName(dbProjectRoleName)
				.orElseThrow(() -> new ReportPortalException("Can not convert project role name from database."));
	}
}
