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

import static com.epam.ta.reportportal.database.search.UpdateStatisticsQueryBuilder.*;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.*;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.hateoas.Identifiable;

import com.epam.ta.reportportal.commons.DbUtils;
import com.epam.ta.reportportal.database.Time;
import com.epam.ta.reportportal.database.entity.*;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.database.entity.item.issue.TestItemIssueType;
import com.epam.ta.reportportal.database.entity.statistics.StatisticSubType;
import com.epam.ta.reportportal.database.search.ModifiableQueryBuilder;

/**
 * Implementation of Test Items repository routines
 *
 * @author Andrei Varabyeu
 * @author Andrei_Ramanchuk
 *
 */
public class TestItemRepositoryCustomImpl implements TestItemRepositoryCustom {

	private static final String ID_REFERENCE = "id";
	private static final String LAUNCH_REFERENCE = "launchRef";
	private static final String ITEM_REFERENCE = "testItemRef";
	private static final String ISSUE_TYPE = "issue.issueType";
	private static final String ISSUE_TICKET = "issue.externalSystemIssues";
	private static final String ISSUE_DESCRIPTION = "issue.issueDescription";
	private static final String HAS_CHILD = "has_childs";
	private static final String START_TIME = "start_time";

	public static final int HISTORY_LIMIT = 2000;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void updateExecutionStatistics(TestItem item) {
		mongoTemplate.updateMulti(getItemQuery(item), fromItemStatusAware(item.getStatus(), 1, 1), TestItem.class);
	}

	@Override
	public void updateIssueStatistics(TestItem item, ProjectSettings settings) {
		mongoTemplate.updateMulti(getItemQuery(item), fromIssueTypeAware(settings.getByLocator(item.getIssue().getIssueType()), 1),
				TestItem.class);
	}

	@Override
	public void dropIssueStatisticsType(String id, StatisticSubType type) {
		mongoTemplate.updateMulti(query(where(ID_REFERENCE).is(id)), dropIssueTypeAware(type), TestItem.class);
	}

	@Override
	public boolean hasDescendants(Object... id) {
		return mongoTemplate.count(getItemDescendantsQuery(id), TestItem.class) > 0;
	}

	@Override
	public List<TestItem> findDescendants(String... id) {
		return mongoTemplate.find(getItemDescendantsQuery((Object[]) id), TestItem.class);
	}

	@Override
	public void resetIssueStatistics(TestItem item, ProjectSettings settings) {
		mongoTemplate.updateMulti(getItemQuery(item), fromIssueTypeAware(settings.getByLocator(item.getIssue().getIssueType()), -1),
				TestItem.class);
	}

	@Override
	public void resetExecutionStatistics(TestItem item) {
		mongoTemplate.updateMulti(getItemQuery(item), fromItemStatusAware(item.getStatus(), -1, -1), TestItem.class);
	}

	@Override
	public void deleteIssueStatistics(TestItem item) {
		mongoTemplate.updateMulti(getItemQuery(item), fromIssueTypeAware(item, true), TestItem.class);
	}

	@Override
	public void deleteExecutionStatistics(TestItem item) {
		mongoTemplate.updateMulti(getItemQuery(item), fromItemStatusAware(item, true), TestItem.class);
	}

	private Query getItemQuery(TestItem item) {
		List<String> fullPath = new ArrayList<>(item.getPath());
		fullPath.add(item.getId());
		return query(where(LAUNCH_REFERENCE).is(item.getLaunchRef())).addCriteria(where("_id").in(fullPath))
				.addCriteria(where("statistics").exists(true));
	}

	private Query getItemDescendantsQuery(Object... id) {
		return new Query().addCriteria(where("path").in(id));
	}

	@Override
	public List<TestItem> findByLaunch(Launch launch) {
		return mongoTemplate.find(query(where(LAUNCH_REFERENCE).is(launch.getId())), TestItem.class);
	}

	@Override
	public List<TestItem> findIdsByLaunch(Iterable<Launch> launches) {
		Query q = query(where(LAUNCH_REFERENCE).in(obtainIds(launches)));
		q.fields().include(ID_REFERENCE);
		return mongoTemplate.find(q, TestItem.class);
	}

	@Override
	public Map<String, String> findPathNames(Iterable<String> path) {
		Query q = query(where("_id").in(toObjId(path)));
		q.fields().include("name");
		List<TestItem> testItems = mongoTemplate.find(q, TestItem.class);
		LinkedHashMap<String, String> pathNames = new LinkedHashMap<>(testItems.size());
		for (TestItem testItem : testItems) {
			pathNames.put(testItem.getId(), testItem.getName());
		}
		return pathNames;
	}

	private Collection<ObjectId> toObjId(Iterable<String> path) {
		List<ObjectId> ids = new ArrayList<>();
		for (String id : path) {
			ids.add(new ObjectId(id));
		}
		return ids;
	}

	private Collection<String> obtainIds(Iterable<Launch> launches) {
		List<String> ids = new ArrayList<>();
		for (Identifiable<String> launch : launches) {
			ids.add(launch.getId());
		}
		return ids;
	}

	@Override
	public List<TestItem> findModifiedLaterAgo(Time period, Status status, Launch launch, boolean hasChilds) {
		Query q = ModifiableQueryBuilder.findModifiedLaterThanPeriod(period, status).addCriteria(where(LAUNCH_REFERENCE).is(launch.getId()))
				.addCriteria(where("has_childs").is(hasChilds));
		return mongoTemplate.find(q, TestItem.class);
	}

	@Override
	public List<TestItem> findModifiedLaterAgo(Time period, Status status, Launch launch) {
		Query q = ModifiableQueryBuilder.findModifiedLaterThanPeriod(period, status)
				.addCriteria(where(LAUNCH_REFERENCE).is(launch.getId()));
		return mongoTemplate.find(q, TestItem.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<String> findDistinctValues(String launchId, String containsValue, String distinctBy) {
		Aggregation aggregation = newAggregation(match(where(LAUNCH_REFERENCE).is(launchId)), unwind(distinctBy),
				match(where(distinctBy).regex("(?i).*" + Pattern.quote(containsValue) + ".*")), group(distinctBy));
		AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, TestItem.class, Map.class);
		return result.getMappedResults().stream().map(entry -> entry.get("_id").toString()).collect(toList());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<String> getUniqueTicketsCount(List<Launch> launches) {
		List<String> launchIds = launches.stream().map(Launch::getId).collect(toList());
		Aggregation aggregation = newAggregation(match(where(LAUNCH_REFERENCE).in(launchIds)), match(where(ISSUE_TICKET).exists(true)),
				unwind(ISSUE_TICKET), group(ISSUE_TICKET));
		// Count be as
		// Aggregation.group("issue.externalSystemIssues").count().as("count");
		// but keep a whole
		AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, TestItem.class, Map.class);
		return result.getMappedResults().stream().map(entry -> entry.get("ticketId").toString()).collect(toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.epam.ta.reportportal.database.dao.TestItemRepositoryCustom#
	 * getMostFailedTestCases(java.util.List, java.lang.String)
	 * 
	 * Most Failed Test Cases widget related method
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, String> getMostFailedTestCases(List<Launch> launches, String criteria) {
		Map<String, String> output = new HashMap<>();
		List<String> launchIds = launches.stream().map(Launch::getId).collect(toList());

		GroupOperation operationTotal = new GroupOperation(Fields.fields("$name")).count().as("count");
		Aggregation aggregationTotal = newAggregation(match(where(LAUNCH_REFERENCE).in(launchIds)), match(where(HAS_CHILD).is(false)),
				operationTotal);
		AggregationResults<Map> resultTotal = mongoTemplate.aggregate(aggregationTotal, TestItem.class, Map.class);
		Map<String, String> values = resultTotal.getMappedResults().stream()
				.collect(toMap(key -> key.get("_id").toString(), value -> value.get("count").toString()));

		GroupOperation operation = new GroupOperation(Fields.fields("$name")).count().as("count").last("$startTime").as("last");
		Aggregation aggregation = newAggregation(match(where(LAUNCH_REFERENCE).in(launchIds)), match(where(criteria).is(1)),
				match(where(HAS_CHILD).is(false)), operation);

		AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, TestItem.class, Map.class);
		for (Map<String, ?> entry : result.getMappedResults()) {
			String itemName = String.valueOf(entry.get("_id"));
			String count = String.valueOf(entry.get("count"));
			Date date = (Date) entry.get("last");
			String total = values.get(itemName);
			// FIXME Update dirty # separator with common case
			// And update after {@link MostFailedTestCasesFilterStrategy}
			output.put(itemName, count + "#" + date.getTime() + "#" + total);
		}
		return output;
	}

	@Override
	public boolean hasLogs(Iterable<TestItem> items) {
		Query query = query(where(ITEM_REFERENCE).in(DbUtils.toIds(items)));
		return mongoTemplate.count(query, Log.class) > 0;
	}

	@Override
	public List<TestItem> loadItemsHistory(List<TestItem> items, List<String> launchesIds, List<String> parentIds) {
		if (items == null || launchesIds == null) {
			return new ArrayList<>();
		}
		Query query = query(getHistoryLaunchPathCriteria(launchesIds, items.get(0))).addCriteria(getItemsHistoryCriteria(items));

		if (parentIds != null) {
			query.addCriteria(where("parent").in(parentIds));
		}
		query.limit(HISTORY_LIMIT);
		query.fields().include("name");
		query.fields().include("start_time");
		query.fields().include("end_time");
		/* For UI: links level detector */
		query.fields().include("has_childs");
		query.fields().include("launchRef");
		query.fields().include("issue");
		query.fields().include("status");
		query.fields().include("tags");
		query.fields().include("itemDescription");
		query.fields().include("statistics");
		query.fields().include("type");
		query.fields().include(ID_REFERENCE);
		return mongoTemplate.find(query, TestItem.class);
	}

	@Override
	public List<TestItem> findTestItemWithInvestigated(String launchId) {
		Criteria internalIssues = new Criteria().andOperator(where(LAUNCH_REFERENCE).is(launchId),
				where(ISSUE_TYPE).ne(TestItemIssueType.TO_INVESTIGATE.name()), where(ISSUE_DESCRIPTION).exists(true));

		Criteria externalIssues = new Criteria().andOperator(where(LAUNCH_REFERENCE).is(launchId), where(ISSUE_TYPE).exists(true),
				where(ISSUE_TICKET).exists(true));

		Query query = query(new Criteria().orOperator(internalIssues, externalIssues));

		query.limit(HISTORY_LIMIT);
		query.fields().include("name");
		query.fields().include("launchRef");
		query.fields().include("issue");
		query.fields().include("status");
		query.fields().include(ID_REFERENCE);

		query.fields().include("start_time");

		return mongoTemplate.find(query, TestItem.class);
	}

	@Override
	public boolean hasTestItemsAddedLately(Time period, Launch launch, Status status) {
		Query query = ModifiableQueryBuilder.findModifiedLately(period).addCriteria(where(LAUNCH_REFERENCE).is(launch.getId()))
				.addCriteria(where(HasStatus.STATUS).is(status.name()));
		return (mongoTemplate.count(query, TestItem.class) > 0);
	}

	@Override
	public List<TestItem> findInIssueTypeItems(String issueType, String launchId) {
		Query query = query(where(LAUNCH_REFERENCE).is(launchId)).addCriteria(where(ISSUE_TYPE).regex(issueType));
		return mongoTemplate.find(query, TestItem.class);
	}

	@Override
	public List<String> findItemIdsByLaunchRef(List<String> launchRef) {
		Aggregation aggregation = newAggregation(match(where("launchRef").in(launchRef)), group("id"));
		AggregationResults<Map> aggregationResults = mongoTemplate.aggregate(aggregation, TestItem.class, Map.class);
		return aggregationResults.getMappedResults().stream().map(it -> it.get("_id").toString()).collect(toList());
	}

	/**
	 * Create part of history criteria. Define launch id, and path size
	 * conditions.
	 *
	 * @param launchesIds
	 * @param item
	 * @return
	 */
	private Criteria getHistoryLaunchPathCriteria(List<String> launchesIds, TestItem item) {
		return where(LAUNCH_REFERENCE).in(launchesIds).and("path").size(item.getPath().size());
	}

	/**
	 * Create {@link Criteria} object for items history selecting Define name
	 * type conditions.
	 *
	 * @param testItems
	 * @return
	 */
	private Criteria getItemsHistoryCriteria(List<TestItem> testItems) {
		Criteria criteria = new Criteria();
		Criteria[] itemCriteries = new Criteria[testItems.size()];

		for (int i = 0; i < testItems.size(); i++) {
			TestItem testItem = testItems.get(i);
			Criteria one = where("name").is(testItem.getName()).and("type").is(testItem.getType().toString());
			if (null != testItem.getItemDescription())
				one.and("itemDescription").is(testItem.getItemDescription());
			if (null != testItem.getTags())
				one.and("tags").is(testItem.getTags());
			itemCriteries[i] = one;
		}

		criteria.orOperator(itemCriteries);
		return criteria;
	}

	@Override
	public List<TestItem> findByHasChildStatus(boolean hasChildren, String launch) {
		Query query = query(where(LAUNCH_REFERENCE).is(launch)).addCriteria(where(HAS_CHILD).is(hasChildren))
				.with(new Sort(Sort.Direction.ASC, START_TIME));
		return mongoTemplate.find(query, TestItem.class);
	}

	@Override
	public List<TestItem> findForSpecifiedSubType(List<String> launchesIds, boolean hasChild, StatisticSubType type) {
		String issueField = "statistics.issueCounter." + TestItemIssueType.valueOf(type.getTypeRef()).awareStatisticsField() + "."
				+ type.getLocator();
		Query query = query(where(LAUNCH_REFERENCE).in(launchesIds)).addCriteria(where(HAS_CHILD).is(hasChild))
				.addCriteria(where(issueField).exists(true)).with(new Sort(Sort.Direction.ASC, START_TIME));
		return mongoTemplate.find(query, TestItem.class);
	}
}