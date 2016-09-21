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

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;

import com.epam.ta.reportportal.database.DataStorage;
import com.epam.ta.reportportal.database.dao.LogRepository;
import com.epam.ta.reportportal.database.entity.Log;
import com.mongodb.BasicDBObject;

public class CascadeDeleteLogTriggerTest {

	private CascadeDeleteLogTrigger cascadeDeleteLogTrigger;
	private LogRepository logRepository;

	@Before
	public void setUp() {
		this.logRepository = mock(LogRepository.class);
		this.cascadeDeleteLogTrigger = new CascadeDeleteLogTrigger(mock(DataStorage.class), logRepository);
	}

	@Test
	public void eventUnexpectedQuery() {
		cascadeDeleteLogTrigger.onBeforeDelete(new BeforeDeleteEvent<>(new BasicDBObject(), Log.class, "log"));
		verify(logRepository, never()).findOne(anyString());
	}
}