/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.entity.project.email;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enumerator with project information widget types
 *
 * @author Andrei_Ramanchuk
 */
public enum ProjectInfoWidget {
	INVESTIGATED("investigated"),
	CASES_STATISTIC("cases_stats"),
	LAUNCHES_QUANTITY("launches_quantity"),
	ISSUES_CHART("issues_chart"),
	BUGS_PERCENTAGE("bugs_percentage"),
	ACTIVITIES("activities"),
	LAST_LAUNCH("last_launch");

	private String widgetCode;

	ProjectInfoWidget(String code) {
		this.widgetCode = code;
	}

	public String getWidgetCode() {
		return widgetCode;
	}

	public static Optional<ProjectInfoWidget> findByCode(String code) {
		return Arrays.stream(ProjectInfoWidget.values()).filter(type -> type.getWidgetCode().equalsIgnoreCase(code)).findAny();
	}
}
