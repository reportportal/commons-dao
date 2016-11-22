package com.epam.ta.reportportal.triggers;

import static java.util.Collections.singletonList;
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

import com.epam.ta.reportportal.database.dao.LogRepository;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

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
			final String id = dbObject.get("_id").toString();
			final BasicDBObject itemDescendantsQuery = new BasicDBObject("path", new BasicDBObject("$in", singletonList(id)));
			final List<String> itemIds = stream(
					mongoTemplate.getCollection(event.getCollectionName()).find(itemDescendantsQuery).spliterator(), false)
							.map(it -> it.get("_id").toString()).collect(toList());
			mongoTemplate.getCollection(event.getCollectionName()).remove(itemDescendantsQuery);
			itemIds.add(id);
			logRepository.deleteByItemRef(itemIds);
		}
	}
}
