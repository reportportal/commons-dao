/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.dao.util;

import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.item.TestItemResults;
import com.epam.ta.reportportal.entity.item.issue.IssueEntity;
import com.epam.ta.reportportal.entity.item.issue.IssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.statistics.Statistics;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.LAUNCH_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant.PARENT_ID;
import static com.epam.ta.reportportal.jooq.Tables.LAUNCH;

/**
 * Set of record mappers that helps to convert the result of jooq queries into
 * Java objects
 *
 * @author Pavel Bortnik
 */
public class RecordMappers {

	private static final String STATISTICS = "statistics";

	/**
	 * Maps crosstab results statistics into set of {@link Statistics} objects
	 */
	public static final RecordMapper<? super Record, Set<Statistics>> CROSSTAB_RECORD_STATISTICS_MAPPER = r -> Arrays.stream(r.fields())
			.filter(f -> f.getName().startsWith(STATISTICS))
			.map(f -> new Statistics(f.getName(), r.get(f.getName(), Integer.class)))
			.collect(Collectors.toSet());

	/**
	 * Maps record into {@link IssueType} object
	 */
	public static final RecordMapper<? super Record, IssueType> ISSUE_TYPE_RECORD_MAPPER = r -> {
		IssueType type = r.into(IssueType.class);
		type.setIssueGroup(r.into(IssueGroup.class));
		return type;
	};

	/**
	 * Maps record into {@link IssueEntity} object
	 */
	public static final RecordMapper<? super Record, IssueEntity> ISSUE_RECORD_MAPPER = r -> {
		IssueEntity issueEntity = r.into(IssueEntity.class);
		issueEntity.setIssueType(ISSUE_TYPE_RECORD_MAPPER.map(r));
		return issueEntity;
	};

	/**
	 * Maps record into {@link TestItemResults} object
	 */
	public static final RecordMapper<? super Record, TestItemResults> TEST_ITEM_RESULTS_RECORD_MAPPER = r -> {
		TestItemResults results = r.into(TestItemResults.class);
		results.setIssue(ISSUE_RECORD_MAPPER.map(r));
		results.setStatistics(CROSSTAB_RECORD_STATISTICS_MAPPER.map(r));
		return results;
	};

	/**
	 * Maps record with crosstab into {@link TestItem} object
	 */
	public static final RecordMapper<? super Record, TestItem> TEST_ITEM_RECORD_MAPPER = r -> {
		TestItem testItem = r.into(TestItem.class);
		testItem.setItemResults(TEST_ITEM_RESULTS_RECORD_MAPPER.map(r));
		testItem.setLaunch(new Launch(r.get(LAUNCH_ID, Long.class)));
		testItem.setParent(new TestItem(r.get(PARENT_ID, Long.class)));
		return testItem;
	};

	/**
	 * Maps record with crosstab into {@link Launch} object
	 */
	public static final RecordMapper<? super Record, Launch> LAUNCH_RECORD_MAPPER = r -> {
		Launch launch = r.into(Launch.class);
		launch.setStatistics(CROSSTAB_RECORD_STATISTICS_MAPPER.map(r));
		return launch;
	};

	/**
	 * Maps result of records without crosstab into list of launches
	 */
	public static final Function<Result<? extends Record>, List<Launch>> LAUNCH_FETCHER = result -> new ArrayList<>(result.stream()
			.collect(Collectors.toMap(r -> r.get(LAUNCH.ID), r -> r.into(Launch.class)))
			.values());

}
