/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.entity.enums.converter;

import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.reportportal.rules.exception.ReportPortalException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

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
        .orElseThrow(
            () -> new ReportPortalException("Can not convert project role name from database."));
  }
}
