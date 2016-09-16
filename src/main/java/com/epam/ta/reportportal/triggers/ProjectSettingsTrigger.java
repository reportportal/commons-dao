/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
 * 
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */ 
package com.epam.ta.reportportal.triggers;

import com.epam.ta.reportportal.database.dao.ProjectSettingsRepository;
import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.ProjectSettings;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

/**
 * @author Andrei Varabyeu
 */
@Component
public class ProjectSettingsTrigger extends AbstractMongoEventListener<Project> {

	@Autowired
	private ProjectSettingsRepository projectSettingsRepository;

	@Override
	public void onAfterSave(AfterSaveEvent<Project> event) {
		String projectId = event.getSource().getId();
		if (!Strings.isNullOrEmpty(projectId) && !projectSettingsRepository.exists(projectId)) {
			/* add empty project settings if there is no any attached to this project */
			projectSettingsRepository.save(new ProjectSettings(projectId));
		}
	}
}