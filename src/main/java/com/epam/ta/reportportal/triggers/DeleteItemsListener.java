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

import com.epam.ta.reportportal.database.dao.LogRepository;
import com.epam.ta.reportportal.database.entity.item.RetryType;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.QueryMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * @author Dzmitry Kavalets
 */
@Service
public class DeleteItemsListener extends AbstractMongoEventListener<TestItem> {

	private MongoTemplate mongoTemplate;
	private LogRepository logRepository;
	private QueryMapper queryMapper;
	private MongoMappingContext mappingContext;

	@Autowired
	public DeleteItemsListener(MongoTemplate mongoTemplate, LogRepository logRepository, MongoMappingContext mappingContext) {
		this.mongoTemplate = mongoTemplate;
		this.logRepository = logRepository;
		this.queryMapper = new QueryMapper(mongoTemplate.getConverter());
		this.mappingContext = mappingContext;
	}

	@Override
	public void onBeforeDelete(BeforeDeleteEvent<TestItem> event) {

		DBObject dbqo = queryMapper.getMappedObject(event.getDBObject(), mappingContext.getPersistentEntity(TestItem.class));

		for (DBObject dbObject : mongoTemplate.getCollection(event.getCollectionName()).find(dbqo)) {
			String retryType = (String) dbObject.get("retryType");
			if (retryType == null || RetryType.LAST.getValue().equals(retryType)) {
				final String id = dbObject.get("_id").toString();
				Query itemDescendantsQuery = Query.query(Criteria.where("path").in(singletonList(id)));
				List<TestItem> itemsForDelete = mongoTemplate.find(itemDescendantsQuery, TestItem.class);
				List<String> ids = getDeletingItemsIds(itemsForDelete);
				ids.add(id);

				BasicDBObject query = new BasicDBObject("_id", new BasicDBObject("$in", ids));
				mongoTemplate.getCollection(event.getCollectionName()).remove(query);
				logRepository.deleteByItemRef(ids);
			}
		}
	}

	private List<String> getDeletingItemsIds(List<TestItem> itemsForDelete) {
		List<String> ids = new ArrayList<>();
		itemsForDelete.forEach(item -> {
			ids.add(item.getId());
			if (null != item.getRetries()) {
				item.getRetries().forEach(it -> ids.add(it.getId()));
			}
		});
		return ids;
	}
}
