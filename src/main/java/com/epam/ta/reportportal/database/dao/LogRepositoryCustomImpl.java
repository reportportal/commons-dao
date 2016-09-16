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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.epam.ta.reportportal.commons.DbUtils;
import com.epam.ta.reportportal.database.Time;
import com.epam.ta.reportportal.database.entity.Log;
import com.epam.ta.reportportal.database.entity.LogLevel;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.database.search.ModifiableQueryBuilder;

/**
 * Log Repository Custom implementation. Adds several custom methods to default interface
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
	public List<Log> findLogIdsByTestItem(TestItem testStep) {
		Query q = Query.query(Criteria.where(ITEM_REFERENCE).is(testStep.getId()));
		q.fields().include(ID_REFERENCE);
		return mongoTemplate.find(q, Log.class);
	}

	@Override
	public long getNumberOfLogByTestItem(TestItem testStep) {
		Query q = Query.query(Criteria.where(ITEM_REFERENCE).is(testStep.getId()));
		return mongoTemplate.count(q, Log.class);
	}

	@Override
	public List<Log> findLogsByTestItem(Iterable<TestItem> testItems) {
		Query q = Query.query(Criteria.where(ITEM_REFERENCE).in(DbUtils.toIds(testItems)));
		return mongoTemplate.find(q, Log.class);
	}

	@Override
	public List<Log> findModifiedBeforeThan(Date date) {
		return mongoTemplate.find(ModifiableQueryBuilder.findModifiedLaterThan(date), Log.class);
	}

	@Override
	public List<Log> findModifiedLaterAgo(Time time) {
		return mongoTemplate.find(ModifiableQueryBuilder.findModifiedLaterThanPeriod(time), Log.class);
	}

	@Override
	public List<Log> findModifiedLaterAgo(Time time, Iterable<TestItem> testItems) {
		return mongoTemplate.find(ModifiableQueryBuilder.findModifiedLaterThanPeriod(time)
				.addCriteria(Criteria.where(ITEM_REFERENCE).in(DbUtils.toIds(testItems))), Log.class);
	}

	@Override
	public List<Log> findByTestItemRef(String itemRef, int limit, boolean isLoadBinaryData) {
		if (itemRef == null || limit <= 0) {
			return new ArrayList<>();
		}
		Query query = Query.query(Criteria.where(ITEM_REFERENCE).is(itemRef)).with(SORT_DESC_LOG_TIME);
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
		return mongoTemplate.count(ModifiableQueryBuilder.findModifiedLately(time, testItem), Log.class) > 0;
	}

	@Override
	public List<Log> findLogsByFileId(String fileId) {
		Query query = Query.query(Criteria.where(BINARY_CONTENT_ID).is(fileId));
		return mongoTemplate.find(query, Log.class);
	}

	@Override
	public List<Log> findTestItemErrorLogs(String testItemId) {
		Query query = Query.query(Criteria.where(ITEM_REFERENCE).is(testItemId))
				.addCriteria(Criteria.where(LOG_LEVEL).gte(LogLevel.ERROR_INT));
		return mongoTemplate.find(query, Log.class);
	}
}