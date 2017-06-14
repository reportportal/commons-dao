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

package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.Modifiable;
import com.epam.ta.reportportal.database.entity.item.Activity;
import com.epam.ta.reportportal.database.search.Filter;
import com.epam.ta.reportportal.database.search.ModifiableQueryBuilder;
import com.epam.ta.reportportal.database.search.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.time.Duration;
import java.util.List;

import static com.epam.ta.reportportal.database.entity.item.Activity.LOGGED_OBJECT_REF;
import static com.epam.ta.reportportal.database.entity.item.Activity.PROJECT_REF;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Activity repository custom implementation
 *
 * @author Dzmitry_Kavalets
 */
public class ActivityRepositoryCustomImpl implements ActivityRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Activity> findActivitiesByTestItemId(String testItemId, Filter filter, Pageable pageable) {
		Sort sort = new Sort(Sort.Direction.DESC, Modifiable.LAST_MODIFIED);
		Query query = QueryBuilder.newBuilder().with(filter).with(pageable).with(sort).build();
		query.addCriteria(where(LOGGED_OBJECT_REF).is(testItemId));
		return mongoTemplate.find(query, Activity.class);
	}

	@Override
	public List<Activity> findActivitiesByProjectId(String projectId, Filter filter, Pageable pageable) {
		Query query = QueryBuilder.newBuilder().with(filter).with(pageable).build();
		query.addCriteria(where(PROJECT_REF).is(projectId));
		return mongoTemplate.find(query, Activity.class);
	}

	@Override
	public void deleteModifiedLaterAgo(String projectId, Duration period) {
		Query query = ModifiableQueryBuilder.findModifiedLaterThanPeriod(period).addCriteria(where(PROJECT_REF).is(projectId));
		mongoTemplate.remove(query, Activity.class);
	}

	@Override
	public List<Activity> findByFilterWithSortingAndLimit(Filter filter, Sort sort, int limit) {
		Query query = QueryBuilder.newBuilder().with(filter).with(sort).with(limit).build();
		return mongoTemplate.find(query, Activity.class);
	}

	@Override
	public List<Activity> findByLoggedObjectRef(String objectRef) {
		final Query query = query(where(LOGGED_OBJECT_REF).is(objectRef));
		return mongoTemplate.find(query, Activity.class);
	}

}