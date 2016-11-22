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
