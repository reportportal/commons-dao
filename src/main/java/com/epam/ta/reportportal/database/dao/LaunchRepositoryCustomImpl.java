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

import static com.epam.ta.reportportal.database.entity.Status.IN_PROGRESS;
import static com.epam.ta.reportportal.database.search.ModifiableQueryBuilder.findModifiedLaterThanPeriod;
import static com.epam.ta.reportportal.database.search.UpdateStatisticsQueryBuilder.*;
import static java.util.stream.Collectors.toList;
import static org.bson.types.ObjectId.isValid;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.*;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.epam.ta.reportportal.config.CacheConfiguration;
import com.epam.ta.reportportal.database.Time;
import com.epam.ta.reportportal.database.entity.Launch;
import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.Status;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.database.entity.item.issue.TestItemIssueType;
import com.epam.ta.reportportal.database.entity.statistics.StatisticSubType;
import com.epam.ta.reportportal.database.search.Filter;
import com.epam.ta.reportportal.database.search.QueryBuilder;

/**
 * Implementations of custom methods which are cannot be generated via default
 * Spring's repositories mechanism
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
		Query query = query(where(PROJECT_ID_REFERENCE).is(isValid(project.getId()) ? new ObjectId(project.getId()) : project.getId()));
		query.fields().include(ID_REFERENCE);
		return mongoTemplate.find(query, Launch.class);
	}

	@Override
	public void updateExecutionStatistics(TestItem item) {
		mongoTemplate.updateMulti(getLaunchQuery(item.getLaunchRef()), fromItemStatusAware(item.getStatus(), 1, 1), Launch.class);
	}

	@Override
	public void updateIssueStatistics(TestItem item, Project.Configuration settings) {
		mongoTemplate.updateMulti(getLaunchQuery(item.getLaunchRef()),
				fromIssueTypeAware(settings.getByLocator(item.getIssue().getIssueType()), 1), Launch.class);
	}

	@Override
	public void dropIssueStatisticsType(String id, StatisticSubType type) {
		mongoTemplate.updateMulti(getLaunchQuery(id), dropIssueTypeAware(type), Launch.class);
	}

	@Override
	public List<String> findLaunchIdsByProjectIds(List<String> ids) {
		Aggregation aggregation = newAggregation(match(where("projectRef").in(ids)), group("id"));
		AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, Launch.class, Map.class);
		return results.getMappedResults().stream().map(it -> it.get("_id").toString()).collect(toList());
	}

	@Override
	public void resetIssueStatistics(TestItem item, Project.Configuration settings) {
		mongoTemplate.updateMulti(getLaunchQuery(item.getLaunchRef()),
				fromIssueTypeAware(settings.getByLocator(item.getIssue().getIssueType()), -1), Launch.class);
	}

	@Override
	public void resetExecutionStatistics(TestItem item) {
		mongoTemplate.updateMulti(getLaunchQuery(item.getLaunchRef()), fromItemStatusAware(item.getStatus(), -1, -1), Launch.class);
	}

	@Override
	public void deleteIssueStatistics(TestItem item) {
		mongoTemplate.updateMulti(getLaunchQuery(item.getLaunchRef()), fromIssueTypeAware(item, true), Launch.class);
	}

	// Probably unnecessary method (launch delete remove object from DB
	// completely)
	@Override
	public void deleteExecutionStatistics(TestItem item) {
		mongoTemplate.updateMulti(getLaunchQuery(item.getLaunchRef()), fromItemStatusAware(item, true), Launch.class);
	}

	// Probably unnecessary method (launch delete remove object from DB
	// completely)
	@Override
	public List<Launch> findModifiedLaterAgo(Time time, Status status) {
		return mongoTemplate.find(findModifiedLaterThanPeriod(time, status), Launch.class);
	}

	// Probably unnecessary method (launch delete remove object from DB
	// completely)
	@Override
	public List<Launch> findModifiedLaterAgo(Time time, Status status, String project) {
		return mongoTemplate.find(findModifiedLaterThanPeriod(time, status).addCriteria(where(PROJECT_ID_REFERENCE).is(project)),
				Launch.class);
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
		return hasItems(new Query().addCriteria(where(LAUNCH_ID_REFERENCE).is(launch.getId())));
	}

	@Override
	public boolean hasItems(Launch launch, Status status) {
		return hasItems(new Query().addCriteria(where(LAUNCH_ID_REFERENCE).is(launch.getId())).addCriteria(where("status").is(status)));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<String> findDistinctValues(String projectName, String containsValue, String distinctBy) {
		Aggregation aggregation = newAggregation(match(where(PROJECT_ID_REFERENCE).is(projectName)), unwind(distinctBy),
				match(where(distinctBy).regex("(?i).*" + Pattern.quote(containsValue) + ".*")), group(distinctBy),
				limit(AUTOCOMPLETE_LIMITATION));
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
		Aggregation aggregation = newAggregation(match(where(PROJECT_ID_REFERENCE).is(projectName)), match(where(MODE).is(mode)),
				match(where(distinctBy).regex("(?i).*" + containsValue + ".*")), group(distinctBy), limit(AUTOCOMPLETE_LIMITATION));
		AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, Launch.class, Map.class);
		return result.getMappedResults().stream().map(entry -> entry.get("_id").toString()).collect(toList());
	}

	@Cacheable(value = { CacheConfiguration.PROJECT_INFO_CACHE })
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, Integer> findGroupedLaunchesByOwner(String projectName, String mode, Date from) {
		Map<String, Integer> output = new HashMap<>();
		Aggregation aggregation = newAggregation(match(where(PROJECT_ID_REFERENCE).is(projectName)), match(where(MODE).is(mode)),
				match(where(STATUS).ne(IN_PROGRESS.name())), match(where(START_TIME).gt(from)), group("$userRef").count().as("count"));

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
		Query query = query(where(PROJECT_ID_REFERENCE).is(projectId)).addCriteria(where(STATUS).ne(IN_PROGRESS.name()))
				.addCriteria(where(MODE).is(mode)).addCriteria(where(START_TIME).gt(from)).with(new Sort(Sort.Direction.ASC, START_TIME));
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
		Query query = query(where(USER_ID_REFERENCE).is(userRef));
		return mongoTemplate.find(query, Launch.class);
	}

	@Override
	public void updateUserRef(String oldOwner, String newOwner) {
		mongoTemplate.updateMulti(query(where(USER_ID_REFERENCE).is(oldOwner)), Update.update(USER_ID_REFERENCE, null), Launch.class);
	}

	@Override
	public Long findLaunchesQuantity(String projectId, String mode, Date from) {
		Query query = query(where(PROJECT_ID_REFERENCE).is(projectId)).addCriteria(where(STATUS).ne(IN_PROGRESS.name()))
				.addCriteria(where(MODE).is(mode));
		if (null != from) {
			query = query.addCriteria(where(START_TIME).gt(from));
		}
		return mongoTemplate.count(query, Launch.class);
	}

	@Override
	public Optional<Launch> findLastLaunch(String projectId, String mode) {
		Query query = query(where(PROJECT_ID_REFERENCE).is(projectId)).addCriteria(where(STATUS).ne(IN_PROGRESS))
				.addCriteria(where(MODE).is(mode)).limit(1).with(new Sort(Sort.Direction.DESC, START_TIME));
		List<Launch> launches = mongoTemplate.find(query, Launch.class);
		return !launches.isEmpty() ? Optional.of(launches.get(0)) : Optional.empty();
	}

	@Override
	public Optional<Launch> findLastLaunch(String projectId, String launchName, String mode) {
		Query query = query(where(PROJECT_ID_REFERENCE).is(projectId)).addCriteria(where(NAME).is(launchName))
				.addCriteria(where(MODE).is(mode)).limit(1).with(new Sort(Sort.Direction.DESC, START_TIME));
		List<Launch> launches = mongoTemplate.find(query, Launch.class);
		return !launches.isEmpty() ? Optional.of(launches.get(0)) : Optional.empty();
	}

	@Override
	public List<Launch> findByFilterWithSortingAndLimit(Filter filter, Sort sort, int limit) {
		final Query query = QueryBuilder.newBuilder().with(filter).with(sort).with(limit).build();
		return mongoTemplate.find(query, Launch.class);
	}

	private Query getLaunchQuery(String id) {
		return new Query().addCriteria(where("_id").is(new ObjectId(id)));
	}

	private boolean hasItems(Query query) {
		return mongoTemplate.count(query, TestItem.class) > 0;
	}

	@Override
	public List<Launch> findLaunchesWithSpecificStat(String projectRef, StatisticSubType type) {
		String issueField = "statistics.issueCounter." + TestItemIssueType.valueOf(type.getTypeRef()).awareStatisticsField() + "."
				+ type.getLocator();
		Query query = query(where(PROJECT_ID_REFERENCE).is(projectRef)).addCriteria(where(issueField).exists(true));
		return mongoTemplate.find(query, Launch.class);
	}
}