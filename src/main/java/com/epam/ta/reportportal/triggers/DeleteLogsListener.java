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
