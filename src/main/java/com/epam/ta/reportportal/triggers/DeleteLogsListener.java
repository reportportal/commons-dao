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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.QueryMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Service;

import com.epam.ta.reportportal.database.DataStorage;
import com.epam.ta.reportportal.database.entity.Log;
import com.mongodb.DBObject;

/**
 * @author Dzmitry Kavalets
 */
@Service
public class DeleteLogsListener extends AbstractMongoEventListener<Log> {

	private MongoTemplate mongoTemplate;
	private DataStorage dataStorage;
	private MongoMappingContext mongoMappingContext;
	private QueryMapper queryMapper;

	@Autowired
	public DeleteLogsListener(MongoTemplate mongoTemplate, DataStorage dataStorage, MongoMappingContext mongoMappingContext) {
		this.mongoTemplate = mongoTemplate;
		this.dataStorage = dataStorage;
		this.mongoMappingContext = mongoMappingContext;
		this.queryMapper = new QueryMapper(mongoTemplate.getConverter());
	}

	@Override
	public void onBeforeDelete(BeforeDeleteEvent<Log> event) {
		List<String> ids = new ArrayList<>();
		for (DBObject dbObject : mongoTemplate.getCollection(event.getCollectionName())
				.find(queryMapper.getMappedObject(event.getDBObject(), mongoMappingContext.getPersistentEntity(Log.class)))) {
			if (dbObject.containsField("binary_content")) {
				Map<String, Object> binaries = (Map<String, Object>) dbObject.get("binary_content");
				if (binaries.containsKey("id")) {
					ids.add(binaries.get("id").toString());
				}
				if (binaries.containsKey("thumbnail_id")) {
					ids.add(binaries.get("thumbnail_id").toString());
				}
			}
		}
		dataStorage.delete(ids);
	}
}
