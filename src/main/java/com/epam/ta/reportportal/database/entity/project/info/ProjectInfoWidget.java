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

package com.epam.ta.reportportal.database.entity.project.info;

/**
 * Enumerator with project information widget types
 * 
 * @author Andrei_Ramanchuk
 *
 */
public enum ProjectInfoWidget {
	//@formatter:off
	INVESTIGATED("investigated"),
	CASES_STATISTIC("cases_stats"),
	LAUNCHES_QUANTITY("launches_quantity"),
	ISSUES_CHART("issues_chart"),
	BUGS_PERCENTAGE("bugs_percentage"),
	ACTIVITIES("activities"),
	LAST_LAUNCH("last_launch");
	//@formatter:on

	private String widgetCode;

	ProjectInfoWidget(String code) {
		this.widgetCode = code;
	}

	public String getWidgetCode() {
		return widgetCode;
	}

	public static ProjectInfoWidget findByCode(String code) {
		for (ProjectInfoWidget widget : ProjectInfoWidget.values()) {
			if (widget.getWidgetCode().equals(code))
				return widget;
		}
		return null;
	}
}