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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

import com.epam.ta.reportportal.database.dao.LogRepository;
import com.epam.ta.reportportal.database.dao.TestItemRepository;
import com.epam.ta.reportportal.database.entity.Log;
import com.epam.ta.reportportal.database.entity.item.TestItem;

/**
 * Delete test items from database by cascading with logs and binary data
 *
 * @author Andrei Varabyeu
 */
@Component
public class CascadeDeleteTestItemTrigger extends AbstractMongoEventListener<TestItem> {

	private final LogRepository logRepository;
	private final TestItemRepository testItemRepository;

	@Autowired
	public CascadeDeleteTestItemTrigger(LogRepository logRepository, TestItemRepository testItemRepository) {
		this.logRepository = logRepository;
		this.testItemRepository = testItemRepository;
	}

	@Override
	public void onBeforeDelete(BeforeDeleteEvent<TestItem> event) {

		final Object id = event.getDBObject().get("id");
		if (id == null) {
			return;
		}
		String itemId = id.toString();

		/* Delete logs */
		List<Log> logs = logRepository.findByTestItemRef(itemId);
		logRepository.delete(logs);

		/* Delete descendants */
		List<TestItem> items = testItemRepository.findDescendants(itemId);
		testItemRepository.delete(items);

	}
}