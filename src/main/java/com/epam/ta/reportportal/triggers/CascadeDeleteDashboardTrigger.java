/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
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

import com.epam.ta.reportportal.commons.Preconditions;
import com.epam.ta.reportportal.database.dao.DashboardRepository;
import com.epam.ta.reportportal.database.dao.WidgetRepository;
import com.epam.ta.reportportal.database.entity.Dashboard;
import com.epam.ta.reportportal.database.entity.widget.Widget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;

/**
 * Deletes all dashboard's binary data related to specified {@link Dashboard}
 *
 * @author Andrei Varabyeu
 * @author Aliaksei Makayed
 */
@Component
public class CascadeDeleteDashboardTrigger extends AbstractMongoEventListener<Dashboard> {

	private final WidgetRepository widgetRepository;
	private final DashboardRepository dashBoardRepository;

	@Autowired
	public CascadeDeleteDashboardTrigger(WidgetRepository widgetRepository, DashboardRepository dashboardRepository) {
		this.widgetRepository = widgetRepository;
		this.dashBoardRepository = dashboardRepository;
	}

	@Override
	public void onBeforeDelete(BeforeDeleteEvent<Dashboard> event) {
		Object id = event.getDBObject().get("id");
		if (id == null) {
			return;
		}
		Dashboard dashboard = dashBoardRepository.findOne(id.toString());
		if (dashboard != null) {
			deleteWidgets(dashboard.getWidgets(), dashboard.getAcl().getOwnerUserId());
		}
	}

	private void deleteWidgets(List<Dashboard.WidgetObject> widgets, String owner) {
		if (!Preconditions.NOT_EMPTY_COLLECTION.test(widgets)) {
			return;
		}
		List<Widget> ownedWidgets = widgetRepository
				.findOnlyOwnedEntities(newHashSet(widgets.stream().map(Dashboard.WidgetObject::getWidgetId).collect(
						Collectors.toList())), owner);
		widgetRepository.delete(ownedWidgets);
	}

}
