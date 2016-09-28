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

package com.epam.ta.reportportal.database.dao;

import static com.epam.ta.reportportal.database.search.ModifiableQueryBuilder.*;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;

import com.epam.ta.reportportal.commons.DbUtils;
import com.epam.ta.reportportal.database.Time;
import com.epam.ta.reportportal.database.entity.Log;
import com.epam.ta.reportportal.database.entity.LogLevel;
import com.epam.ta.reportportal.database.entity.item.TestItem;

/**
 * Log Repository Custom implementation. Adds several custom methods to default
 * interface
 * 
 * @author Andrei Varabyeu
 * @author Andrei_Ramanchuk
 */
class LogRepositoryCustomImpl implements LogRepositoryCustom {

	public static final Sort SORT_DESC_LOG_TIME = new Sort(Direction.ASC, "logTime");
	public static final String BINARY_CONTENT = "binary_content";
	public static final String BINARY_CONTENT_ID = "binary_content.id";
	private static final String ITEM_REFERENCE = "testItemRef";
	private static final String LOG_LEVEL = "level.log_level";
	private static final String ID_REFERENCE = "id";

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public long getNumberOfLogByTestItem(TestItem testStep) {
		Query q = query(where(ITEM_REFERENCE).is(testStep.getId()));
		return mongoTemplate.count(q, Log.class);
	}

	@Override
	public List<Log> findLogsByTestItem(Iterable<TestItem> testItems) {
		Query q = query(where(ITEM_REFERENCE).in(DbUtils.toIds(testItems)));
		return mongoTemplate.find(q, Log.class);
	}

	@Override
	public List<Log> findModifiedBeforeThan(Date date) {
		return mongoTemplate.find(findModifiedLaterThan(date), Log.class);
	}

	@Override
	public List<Log> findModifiedLaterAgo(Time time) {
		return mongoTemplate.find(findModifiedLaterThanPeriod(time), Log.class);
	}

	@Override
	public List<Log> findModifiedLaterAgo(Time time, Iterable<TestItem> testItems) {
		return mongoTemplate.find(findModifiedLaterThanPeriod(time).addCriteria(where(ITEM_REFERENCE).in(DbUtils.toIds(testItems))),
				Log.class);
	}

	@Override
	public List<Log> findByTestItemRef(String itemRef, int limit, boolean isLoadBinaryData) {
		if (itemRef == null || limit <= 0) {
			return new ArrayList<>();
		}
		Query query = query(where(ITEM_REFERENCE).is(itemRef)).with(SORT_DESC_LOG_TIME);
		if (!isLoadBinaryData) {
			query.fields().exclude(BINARY_CONTENT);
		}
		long count = mongoTemplate.count(query, Log.class);
		long max = Math.max(0, count - limit);
		if (max > 0) {
			query.skip((int) max);
		}
		return mongoTemplate.find(query, Log.class);
	}

	@Override
	public boolean hasLogsAddedLately(Time time, TestItem testItem) {
		return mongoTemplate.count(findModifiedLately(time, testItem), Log.class) > 0;
	}

	@Override
	public List<Log> findLogsByFileId(String fileId) {
		Query query = query(where(BINARY_CONTENT_ID).is(fileId));
		return mongoTemplate.find(query, Log.class);
	}

	@Override
	public List<Log> findTestItemErrorLogs(String testItemId) {
		Query query = query(where(ITEM_REFERENCE).is(testItemId)).addCriteria(where(LOG_LEVEL).gte(LogLevel.ERROR_INT));
		return mongoTemplate.find(query, Log.class);
	}

	@Override
	public List<String> findLogIdsByItemRefs(List<String> ids) {
		Aggregation aggregation = newAggregation(match(where("testItemRef").in(ids)), group("id"));
		AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, Log.class, Map.class);
		return results.getMappedResults().stream().map(it -> it.get("_id").toString()).collect(toList());
	}

	@Override
	public List<String> findBinaryIdsByLogRefs(List<String> ids) {
		Aggregation aggregation = newAggregation(match(where("id").in(ids).andOperator(where("binaryContent").exists(true))),
				group("binaryContent.binaryDataId", "binaryContent.thumbnailId"));
		AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, Log.class, Map.class);
		return results.getMappedResults().stream().flatMap(it -> (Stream<String>) it.values().stream()).collect(toList());
	}
}