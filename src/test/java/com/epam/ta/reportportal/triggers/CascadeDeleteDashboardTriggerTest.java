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

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;

import com.epam.ta.reportportal.database.dao.DashboardRepository;
import com.epam.ta.reportportal.database.dao.WidgetRepository;
import com.epam.ta.reportportal.database.entity.Dashboard;
import com.mongodb.BasicDBObject;

public class CascadeDeleteDashboardTriggerTest {

	private CascadeDeleteDashboardTrigger cascadeDeleteDashboardTrigger;
	private DashboardRepository dashboardRepository;

	@Before
	public void setUp() {
		this.dashboardRepository = mock(DashboardRepository.class);
		this.cascadeDeleteDashboardTrigger = new CascadeDeleteDashboardTrigger(mock(WidgetRepository.class), dashboardRepository);
	}

	@Test
	public void eventUnexpectedQuery() {
		cascadeDeleteDashboardTrigger.onBeforeDelete(new BeforeDeleteEvent<>(new BasicDBObject(), Dashboard.class, "dashboard"));
		verify(dashboardRepository, never()).findOne(anyString());
	}

}