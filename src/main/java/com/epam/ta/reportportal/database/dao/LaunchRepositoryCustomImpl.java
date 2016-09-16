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

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.epam.ta.reportportal.config.CacheConfiguration;
import com.epam.ta.reportportal.database.Time;
import com.epam.ta.reportportal.database.entity.Launch;
import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.ProjectSettings;
import com.epam.ta.reportportal.database.entity.Status;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.database.entity.item.issue.TestItemIssueType;
import com.epam.ta.reportportal.database.entity.statistics.StatisticSubType;
import com.epam.ta.reportportal.database.search.Filter;
import com.epam.ta.reportportal.database.search.ModifiableQueryBuilder;
import com.epam.ta.reportportal.database.search.QueryBuilder;
import com.epam.ta.reportportal.database.search.UpdateStatisticsQueryBuilder;

/**
 * Implementations of custom methods which are cannot be generated via default Spring's repositories
 * mechanism
 * 
 * @author Andrei Varabyeu
 * @author Andrei_Ramanchuk
 */
public class LaunchRepositoryCustomImpl implements LaunchRepositoryCustom {

	public static final String PROJECT_ID_REFERENCE = "projectRef";
	public static final String LAUNCH_ID_REFERENCE = "launchRef";
	public static final String USER_ID_REFERENCE = "userRef";
	public static final String ID_REFERENCE = "id";
	public static final String NUMBER = "number";
	public static final String MODE = "mode";
	public static final String STATISTICS = "statistics";
	public static final String START_TIME = "start_time";
	public static final String STATUS = "status";
	public static final String NAME = "name";
	public static final int AUTOCOMPLETE_LIMITATION = 50;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Launch> findLaunchIdsByProject(Project project) {
		Query query = Query.query(Criteria.where(PROJECT_ID_REFERENCE)
				.is(ObjectId.isValid(project.getId()) ? new ObjectId(project.getId()) : project.getId()));
		query.fields().include(ID_REFERENCE);
		return mongoTemplate.find(query, Launch.class);
	}

	@Override
	public void updateExecutionStatistics(TestItem item) {
		mongoTemplate.updateMulti(getLaunchQuery(item.getLaunchRef()),
				UpdateStatisticsQueryBuilder.fromItemStatusAware(item.getStatus(), 1, 1), Launch.class);
	}

	@Override
	public void updateIssueStatistics(TestItem item, ProjectSettings settings) {
		mongoTemplate.updateMulti(getLaunchQuery(item.getLaunchRef()),
				UpdateStatisticsQueryBuilder.fromIssueTypeAware(settings.getByLocator(item.getIssue().getIssueType()), 1), Launch.class);
	}

	@Override
	public void dropIssueStatisticsType(String id, StatisticSubType type) {
		mongoTemplate.updateMulti(getLaunchQuery(id), UpdateStatisticsQueryBuilder.dropIssueTypeAware(type), Launch.class);
	}

	@Override
	public void resetIssueStatistics(TestItem item, ProjectSettings settings) {
		mongoTemplate.updateMulti(getLaunchQuery(item.getLaunchRef()),
				UpdateStatisticsQueryBuilder.fromIssueTypeAware(settings.getByLocator(item.getIssue().getIssueType()), -1), Launch.class);
	}

	@Override
	public void resetExecutionStatistics(TestItem item) {
		mongoTemplate.updateMulti(getLaunchQuery(item.getLaunchRef()),
				UpdateStatisticsQueryBuilder.fromItemStatusAware(item.getStatus(), -1, -1), Launch.class);
	}

	@Override
	public void deleteIssueStatistics(TestItem item) {
		mongoTemplate.updateMulti(getLaunchQuery(item.getLaunchRef()), UpdateStatisticsQueryBuilder.fromIssueTypeAware(item, true),
				Launch.class);
	}

	// Probably unnecessary method (launch delete remove object from DB
	// completely)
	@Override
	public void deleteExecutionStatistics(TestItem item) {
		mongoTemplate.updateMulti(getLaunchQuery(item.getLaunchRef()), UpdateStatisticsQueryBuilder.fromItemStatusAware(item, true),
				Launch.class);
	}

	// Probably unnecessary method (launch delete remove object from DB
	// completely)
	@Override
	public List<Launch> findModifiedLaterAgo(Time time, Status status) {
		return mongoTemplate.find(ModifiableQueryBuilder.findModifiedLaterThanPeriod(time, status), Launch.class);
	}

	// Probably unnecessary method (launch delete remove object from DB
	// completely)
	@Override
	public List<Launch> findModifiedLaterAgo(Time time, Status status, String project) {
		return mongoTemplate.find(ModifiableQueryBuilder.findModifiedLaterThanPeriod(time, status)
				.addCriteria(Criteria.where(PROJECT_ID_REFERENCE).is(project)), Launch.class);
	}

	@Override
	public List<Launch> findIdsByFilter(Filter filter, Sort sort, int quantity) {
		Query query = QueryBuilder.newBuilder().with(filter).with(sort).with(quantity).build();
		query.fields().include(ID_REFERENCE);
		query.fields().include(NUMBER);
		query.fields().include(START_TIME);
		query.fields().include(STATUS);
		return mongoTemplate.find(query, Launch.class);
	}

	@Override
	public boolean hasItems(Launch launch) {
		return hasItems(new Query().addCriteria(Criteria.where(LAUNCH_ID_REFERENCE).is(launch.getId())));
	}

	@Override
	public boolean hasItems(Launch launch, Status status) {
		return hasItems(new Query().addCriteria(Criteria.where(LAUNCH_ID_REFERENCE).is(launch.getId()))
				.addCriteria(Criteria.where("status").is(status)));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<String> findDistinctValues(String projectName, String containsValue, String distinctBy) {
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where(PROJECT_ID_REFERENCE).is(projectName)),
				Aggregation.unwind(distinctBy),
				Aggregation.match(Criteria.where(distinctBy).regex("(?i).*" + Pattern.quote(containsValue) + ".*")),
				Aggregation.group(distinctBy), Aggregation.limit(AUTOCOMPLETE_LIMITATION));
		AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, Launch.class, Map.class);
		List<String> tags = new ArrayList<>(result.getMappedResults().size());
		for (Map<String, String> entry : result.getMappedResults()) {
			tags.add(entry.get("_id"));
		}
		return tags;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<String> findValuesWithMode(String projectName, String containsValue, String distinctBy, String mode) {
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where(PROJECT_ID_REFERENCE).is(projectName)),
				Aggregation.match(Criteria.where(MODE).is(mode)),
				Aggregation.match(Criteria.where(distinctBy).regex("(?i).*" + containsValue + ".*")), Aggregation.group(distinctBy),
				Aggregation.limit(AUTOCOMPLETE_LIMITATION));
		AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, Launch.class, Map.class);
		return result.getMappedResults().stream().map(entry -> entry.get("_id").toString()).collect(toList());
	}

	@Cacheable(value = { CacheConfiguration.PROJECT_INFO_CACHE })
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, Integer> findGroupedLaunchesByOwner(String projectName, String mode, Date from) {
		Map<String, Integer> output = new HashMap<>();
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where(PROJECT_ID_REFERENCE).is(projectName)),
				Aggregation.match(Criteria.where(MODE).is(mode)), Aggregation.match(Criteria.where(STATUS).ne(Status.IN_PROGRESS.name())),
				Aggregation.match(Criteria.where(START_TIME).gt(from)), Aggregation.group("$userRef").count().as("count"));

		AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, Launch.class, Map.class);
		for (Map<String, String> entry : result.getMappedResults()) {
			String username = entry.get("_id");
			String count = String.valueOf(entry.get("count"));
			output.put(username, Integer.valueOf(count));
		}
		return output;
	}

	@Override
	public List<Launch> findLaunchesByProjectId(String projectId, Date from, String mode) {
		Query query = Query.query(Criteria.where(PROJECT_ID_REFERENCE).is(projectId))
				.addCriteria(Criteria.where(STATUS).ne(Status.IN_PROGRESS.name())).addCriteria(Criteria.where(MODE).is(mode))
				.addCriteria(Criteria.where(START_TIME).gt(from)).with(new Sort(Sort.Direction.ASC, START_TIME));
		return mongoTemplate.find(query, Launch.class);
	}

	@Override
	public List<Launch> findIdsByFilter(Filter filter) {
		return findIdsByFilter(filter, 0);
	}

	@Override
	public List<Launch> findIdsByFilter(Filter filter, int limit) {
		Query query = QueryBuilder.newBuilder().with(filter).with(limit).build();
		query.fields().include(ID_REFERENCE);
		return mongoTemplate.find(query, Launch.class);
	}

	@Override
	public List<Launch> findByUserRef(String userRef) {
		Query query = Query.query(Criteria.where(USER_ID_REFERENCE).is(userRef));
		return mongoTemplate.find(query, Launch.class);
	}

	@Override
	public void updateUserRef(String oldOwner, String newOwner) {
		mongoTemplate.updateMulti(Query.query(Criteria.where(USER_ID_REFERENCE).is(oldOwner)), Update.update(USER_ID_REFERENCE, null),
				Launch.class);
	}

	@Override
	public List<Launch> findLaunches(List<String> launchIds) {
		Query query = Query.query(Criteria.where(ID_REFERENCE).in(launchIds));
		query.fields().include(PROJECT_ID_REFERENCE);
		query.fields().include(START_TIME);
		query.fields().include(STATISTICS);
		query.fields().include(NUMBER);
		query.fields().include(Launch.NAME);
		query.fields().include(STATUS);
		return mongoTemplate.find(query, Launch.class);
	}

	@Override
	public Long findLaunchesQuantity(String projectId, String mode, Date from) {
		Query query = Query.query(Criteria.where(PROJECT_ID_REFERENCE).is(projectId))
				.addCriteria(Criteria.where(STATUS).ne(Status.IN_PROGRESS.name())).addCriteria(Criteria.where(MODE).is(mode));
		if (null != from) {
			query = query.addCriteria(Criteria.where(START_TIME).gt(from));
		}
		return mongoTemplate.count(query, Launch.class);
	}

	@Override
	public Optional<Launch> findLastLaunch(String projectId, String mode) {
		Query query = Query.query(Criteria.where(PROJECT_ID_REFERENCE).is(projectId))
				.addCriteria(Criteria.where(STATUS).ne(Status.IN_PROGRESS)).addCriteria(Criteria.where(MODE).is(mode)).limit(1)
				.with(new Sort(Sort.Direction.DESC, START_TIME));
		List<Launch> launches = mongoTemplate.find(query, Launch.class);
		return !launches.isEmpty() ? Optional.of(launches.get(0)) : Optional.empty();
	}

	@Override
	public Optional<Launch> findLastLaunch(String projectId, String launchName, String mode) {
		Query query = Query.query(Criteria.where(PROJECT_ID_REFERENCE).is(projectId)).addCriteria(Criteria.where(NAME).is(launchName))
				.addCriteria(Criteria.where(MODE).is(mode)).limit(1).with(new Sort(Sort.Direction.DESC, START_TIME));
		List<Launch> launches = mongoTemplate.find(query, Launch.class);
		return !launches.isEmpty() ? Optional.of(launches.get(0)) : Optional.empty();
	}

	@Override
	public List<Launch> findByFilterWithSortingAndLimit(Filter filter, Sort sort, int limit) {
		final Query query = QueryBuilder.newBuilder().with(filter).with(sort).with(limit).build();
		return mongoTemplate.find(query, Launch.class);
	}

	/**
	 * Retrieves ObjectIDs from project
	 * 
	 * @param projects
	 * @return
	 */
	private List<Object> toIds(Iterable<Project> projects) {
		List<Object> ids = new ArrayList<>();
		for (Project project : projects) {
			ids.add(ObjectId.isValid(project.getId()) ? new ObjectId(project.getId()) : project.getId());
		}
		return ids;
	}

	private Query getLaunchQuery(String id) {
		return new Query().addCriteria(Criteria.where("_id").is(new ObjectId(id)));
	}

	private boolean hasItems(Query query) {
		return mongoTemplate.count(query, TestItem.class) > 0;
	}

	@Override
	public List<Launch> findLaunchesWithSpecificStat(String projectRef, StatisticSubType type) {
		String issueField = "statistics.issueCounter." + TestItemIssueType.valueOf(type.getTypeRef()).awareStatisticsField() + "."
				+ type.getLocator();
		Query query = Query.query(Criteria.where(PROJECT_ID_REFERENCE).is(projectRef)).addCriteria(Criteria.where(issueField).exists(true));
		return mongoTemplate.find(query, Launch.class);
	}
}