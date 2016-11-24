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

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.QueryMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Service;

import com.epam.ta.reportportal.database.dao.TestItemRepository;
import com.epam.ta.reportportal.database.entity.Launch;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author Dzmitry Kavalets
 */
@Service
public class DeleteLaunchesListener extends AbstractMongoEventListener<Launch> {

	private TestItemRepository testItemRepository;
	private MongoTemplate mongoTemplate;
	private QueryMapper queryMapper;
	private MongoMappingContext mappingContext;

	@Autowired
	public DeleteLaunchesListener(TestItemRepository testItemRepository, MongoTemplate mongoTemplate, MongoMappingContext mappingContext) {
		this.mongoTemplate = mongoTemplate;
		this.testItemRepository = testItemRepository;
		this.queryMapper = new QueryMapper(mongoTemplate.getConverter());
		this.mappingContext = mappingContext;
	}

	@Override
	public void onBeforeDelete(BeforeDeleteEvent<Launch> event) {
		DBObject dboq = queryMapper.getMappedObject(event.getDBObject(), mappingContext.getPersistentEntity(Launch.class));
		List<String> ids = stream(mongoTemplate.getCollection(event.getCollectionName()).find(dboq).spliterator(), false)
				.map(dbObject -> dbObject.get("_id").toString()).distinct().collect(toList());
		final BasicDBObject itemsQuery = new BasicDBObject("parent", new BasicDBObject("$exists", false));
		itemsQuery.put("launchRef", new BasicDBObject("$in", ids));
		final List<String> itemIds = stream(mongoTemplate.getCollection("testItem").find(itemsQuery).spliterator(), false)
				.map(dbObject -> dbObject.get("_id").toString()).collect(toList());
		testItemRepository.delete(itemIds);
	}
}
