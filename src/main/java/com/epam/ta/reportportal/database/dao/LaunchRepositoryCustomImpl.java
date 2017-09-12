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

import com.epam.ta.reportportal.config.CacheConfiguration;
import com.epam.ta.reportportal.database.dao.aggregation.AggregationUtils;
import com.epam.ta.reportportal.database.entity.Launch;
import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.Status;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.database.entity.item.issue.TestItemIssueType;
import com.epam.ta.reportportal.database.entity.statistics.StatisticSubType;
import com.epam.ta.reportportal.database.search.Filter;
import com.epam.ta.reportportal.database.search.QueryBuilder;
import com.epam.ta.reportportal.database.search.Queryable;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.database.dao.aggregation.AggregationUtils.matchOperationFromFilter;
import static com.epam.ta.reportportal.database.entity.Status.IN_PROGRESS;
import static com.epam.ta.reportportal.database.search.ModifiableQueryBuilder.findModifiedLaterThanPeriod;
import static com.epam.ta.reportportal.database.search.UpdateStatisticsQueryBuilder.*;
import static java.util.stream.Collectors.toList;
import static org.bson.types.ObjectId.isValid;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

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

	//useful constants for latest launches
	private static final String ORIGINAL = "original";
	private static final String RESULT = "result";

	//useful constants for cumulative
	private static final String TAGS = "tags";
	private static final String REGEX_POSTFIX = ":.+";

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
		Aggregation aggregation = newAggregation(match(where(PROJECT_ID_REFERENCE).in(ids)), group(ID_REFERENCE));
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
		mongoTemplate.updateMulti(getLaunchQuery(item.getLaunchRef()), fromIssueTypeAware(item), Launch.class);
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
	public List<Launch> findModifiedLaterAgo(Duration time, Status status) {
		return mongoTemplate.find(findModifiedLaterThanPeriod(time, status), Launch.class);
	}

	// Probably unnecessary method (launch delete remove object from DB
	// completely)
	@Override
	public List<Launch> findModifiedLaterAgo(Duration time, Status status, String project) {
		return mongoTemplate
				.find(findModifiedLaterThanPeriod(time, status).addCriteria(where(PROJECT_ID_REFERENCE).is(project)), Launch.class);
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
		//@formatter:off
		Aggregation aggregation = newAggregation(
				match(where(PROJECT_ID_REFERENCE).is(projectName)),
				unwind(distinctBy),
				match(where(distinctBy).regex("(?i).*" + Pattern.quote(containsValue) + ".*")),
				group(distinctBy),
				limit(AUTOCOMPLETE_LIMITATION));
		//@formatter:on
		AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, Launch.class, Map.class);
		return result.getMappedResults().stream().map(it -> (String) it.get("_id")).collect(Collectors.toList());
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<String> findValuesWithMode(String projectName, String containsValue, String distinctBy, String mode) {
		Aggregation aggregation = newAggregation(match(where(PROJECT_ID_REFERENCE).is(projectName)), match(where(MODE).is(mode)),
				match(where(distinctBy).regex("(?i).*" + Pattern.quote(containsValue) + ".*")), group(distinctBy),
				limit(AUTOCOMPLETE_LIMITATION));
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
	public Optional<Launch> findLatestLaunch(String projectName, String launchName, String mode) {
		Query query = query(where(PROJECT_ID_REFERENCE).is(projectName)).addCriteria(where(NAME).is(launchName))
				.addCriteria(where(STATUS).ne(Status.IN_PROGRESS)).addCriteria(where(MODE).is(mode)).limit(1)
				.with(new Sort(Sort.Direction.DESC, NUMBER));
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
		String issueField =
				"statistics.issueCounter." + TestItemIssueType.valueOf(type.getTypeRef()).awareStatisticsField() + "." + type.getLocator();
		Query query = query(where(PROJECT_ID_REFERENCE).is(projectRef)).addCriteria(where(issueField).exists(true));
		return mongoTemplate.find(query, Launch.class);
	}

	@Override
	public void findLatestWithCallback(Queryable filter, Sort sort, List<String> contentFields, long limit,
			DocumentCallbackHandler callbackHandler) {
		List<AggregationOperation> operations = latestLaunchesAggregationOperationsList(filter);
		operations.add(sort(sort));
		operations.add(limit(limit));
		DBObject results = mongoTemplate.aggregate(newAggregation(operations), mongoTemplate.getCollectionName(Launch.class), Launch.class)
				.getRawResults();
		BasicDBList result = (BasicDBList) results.get(RESULT);
		result.stream().map(it -> (DBObject) it).forEach(callbackHandler::processDocument);
	}

	@Override
	public Page<Launch> findLatestLaunches(Queryable filter, Pageable pageable) {
		Long totalCount = countLatestLaunches(filter);
		List<Launch> launches = Collections.emptyList();
		if (totalCount > 0) {
			launches = findLatest(filter, pageable);
		}
		return new PageImpl<>(launches, pageable, totalCount);
	}

	/*
		db.launch.aggregate([
			{ $match : { "$and" : [ { projectRef : "default_personal" } ] } },
			{ $match : { "$and" : [ { tags : { $regex : "run:.+" } },{ metadata : { $exists : true } } ] } },
			{ $unwind : "$tags"},
			{ $match : {tags : {$regex : "run:.+"}}},
			{ $group : { _id : "$metadata.build", "statistics$executionCounter$passed" : { "$sum" : "$statistics.executionCounter.passed"}}},
			{ $sort : {"_id" : -1 }},
			{ $limit : 10 }
		 ])
      */
	@Override
	public void cumulativeStatisticsGroupedByTag(Queryable filter, List<String> contentFields, long limit, String tagPrefix,
			DocumentCallbackHandler callbackHandler) {
		//@formatter:off
		Aggregation aggregation = newAggregation(
				AggregationUtils.matchOperationFromFilter(filter, mongoTemplate, Launch.class),
				match(Criteria.where(TAGS).regex(tagPrefix + REGEX_POSTFIX)
						.andOperator(Criteria.where("metadata").exists(true))),
				unwind("$tags"),
				match(Criteria.where(TAGS).regex(tagPrefix + REGEX_POSTFIX)),
				groupByFieldWithStatisticsSumming(TAGS, contentFields),
				sort(Sort.Direction.DESC, "_id"),
				limit(limit)
		);
		//@formatter:on
		List<DBObject> mappedResults = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(Launch.class), DBObject.class)
				.getMappedResults();
		mappedResults.forEach(callbackHandler::processDocument);
	}

	/**
	 * Creates a group operation by specified field with sum of contentFields values
	 *
	 * @param field         Grouping field
	 * @param contentFields Fields for summing
	 * @return Group operation
	 */
	private GroupOperation groupByFieldWithStatisticsSumming(String field, List<String> contentFields) {
		GroupOperation groupOperation = Aggregation.group(field);
		for (String contentField : contentFields) {
			groupOperation = groupOperation.sum(contentField).as(contentField.replace('.', '$'));
		}
		return groupOperation;
	}

	private Long countLatestLaunches(Queryable filter) {
		Long total = 0L;
		final String countKey = "count";
		List<AggregationOperation> operations = latestLaunchesAggregationOperationsList(filter);
		operations.add(Aggregation.count().as(countKey));
		Map result = mongoTemplate.aggregate(newAggregation(operations), Launch.class, Map.class).getUniqueMappedResult();
		if (null != result && result.containsKey(countKey)) {
			total = Long.valueOf(result.get(countKey).toString());
		}
		return total;
	}

	/*
	 *     db.launch.aggregate([
	 *        { $match : { "$and" : [ { <filter query> } ], "projectRef" : "projectName"},
	 *        { $sort : { number : -1 }}
	 *        { $group : { "_id" : "$name", "original" : {
	 *              $first : "$$ROOT"
	 *        }}},
	 *        { $replaceRoot : { newRoot : "$original" } } ,
	 *        { $sort : { "start_time" : -1 } },
	 *        { $skip : skip },
	 *        { $limit : limit }
	 *     ])
	*/
	private List<Launch> findLatest(Queryable filter, Pageable pageable) {
		List<AggregationOperation> operations = latestLaunchesAggregationOperationsList(filter);
		operations.add(sort(pageable.getSort()));
		operations.add(skip((long) pageable.getPageNumber() * pageable.getPageSize()));
		operations.add(limit(pageable.getPageSize()));
		return mongoTemplate.aggregate(newAggregation(operations), mongoTemplate.getCollectionName(Launch.class), Launch.class)
				.getMappedResults();
	}

	/*
	 *     db.launch.aggregate([
	 *        { $match : { "$and" : [ { <filter query> } ], "projectRef" : "projectName" },
	 *        { $sort : { number : -1 }}
	 *        { $group : { "_id" : "$name", "original" : {
	 *              $first : "$$ROOT"
	 *        }}},
	 *        { $replaceRoot : { newRoot : "$original" }
	 *     ])
	*/
	private List<AggregationOperation> latestLaunchesAggregationOperationsList(Queryable filter) {
		return Lists.newArrayList(matchOperationFromFilter(filter, mongoTemplate, Launch.class), sort(Sort.Direction.DESC, NUMBER),
				group("$name").first(ROOT).as(ORIGINAL), replaceRoot(ORIGINAL));
	}

}