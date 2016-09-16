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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

import com.epam.ta.reportportal.database.DataStorage;
import com.epam.ta.reportportal.database.dao.LogRepository;
import com.epam.ta.reportportal.database.entity.Log;

/**
 * Deletes all log binary data related to specified {@link Log}
 *
 * @author Andrei Varabyeu
 */
@Component
public class CascadeDeleteLogTrigger extends AbstractMongoEventListener<Log> {

	@Autowired
	private DataStorage dataStorage;

	@Autowired
	private LogRepository logRepository;

	@Override
	public void onBeforeDelete(BeforeDeleteEvent<Log> event) {

		Log log = logRepository.findOne(event.getDBObject().get("id").toString());

		if (null != log.getBinaryContent()) {
			dataStorage.deleteData(log.getBinaryContent().getBinaryDataId());
			/* Thumbnail remove (if exists) */
			if (null != log.getBinaryContent().getThumbnailId())
				dataStorage.deleteData(log.getBinaryContent().getThumbnailId());
		}
	}

}