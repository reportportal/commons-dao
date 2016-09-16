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

import com.epam.ta.reportportal.database.dao.TestItemRepository;
import com.epam.ta.reportportal.database.entity.Launch;
import com.epam.ta.reportportal.database.entity.item.TestItem;

/**
 * Aspect for Cascade deleting launches. Related binary data will be deleted as
 * well.
 *
 * @author Andrei Varabyeu
 */
@Component
public class CascadeDeleteLaunchTrigger extends AbstractMongoEventListener<Launch> {

	@Autowired
	private TestItemRepository testItemRepository;

	@Override
	public void onBeforeDelete(BeforeDeleteEvent<Launch> event) {

		List<TestItem> testItems = testItemRepository.findIdsByLaunch(event.getDBObject().get("id").toString());
		testItemRepository.delete(testItems);
	}

}