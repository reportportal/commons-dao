/*
 * Copyright (C) 2018 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.filter.Queryable;
import com.epam.ta.reportportal.entity.launch.LaunchTag;
import com.epam.ta.reportportal.entity.widget.content.*;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

/**
 * @author Pavel Bortnik
 */
public interface WidgetContentRepository {

	/**
	 * Overall statistics content loading.
	 *
	 * @param filter        {@link Filter}
	 * @param sort          {@link Sort}
	 * @param contentFields Content fields to load
	 * @param latest        Load only for latest launches
	 * @param limit         Limit of loaded launches
	 * @return List of {@link LaunchesStatisticsContent}
	 */
	List<LaunchesStatisticsContent> overallStatisticsContent(Filter filter, Sort sort, List<String> contentFields, boolean latest,
			int limit);

	/**
	 * Loads top limit history of items sorted in descending order by provided criteria
	 * for specified launch. Criteria could be one of statistics fields.
	 * For example if criteria is 'statistics$execution$failed' and launchName is 'DefaultLaunch'
	 * that is specified in the filter and limit is 20 the result will be top 20 grouped steps
	 * by uniqueId of the whole launch history with 'DefaultLaunch' name
	 * sorted by count of steps with existed statistics of 'statistics$execution$failed'
	 *
	 * @param filter         Launches filter
	 * @param criteria       Criteria for example 'statistics$execution$failed'
	 * @param limit          Limit of items
	 * @param includeMethods Include or not test item types that have 'METHOD' or 'CLASS'
	 * @return List of items, one represents history of concrete step
	 */
	List<CriteraHistoryItem> topItemsByCriteria(Filter filter, String criteria, int limit, boolean includeMethods);

	/**
	 * Launch statistics content loading
	 *
	 * @param filter        {@link Filter}
	 * @param contentFields Custom fields for select query building
	 * @param sort          {@link Sort}
	 * @param limit         Results limit
	 * @return List of {@link LaunchesStatisticsContent}
	 */
	List<LaunchesStatisticsContent> launchStatistics(Filter filter, List<String> contentFields, Sort sort, int limit);

	/**
	 * Investigated statistics loading
	 *
	 * @param filter {@link Filter}
	 * @param sort   {@link Sort}
	 * @param limit  Results limit
	 * @return List of{@link InvestigatedStatisticsResult}
	 */
	List<InvestigatedStatisticsResult> investigatedStatistics(Filter filter, Sort sort, int limit);

	/**
	 * Launches passing rate result for launch or for all launches depending on the filter conditions
	 *
	 * @param filter {@link Filter}
	 * @param sort   {@link Sort}
	 * @param limit  Results limit
	 * @return {@link PassingRateStatisticsResult}
	 */
	PassingRateStatisticsResult passingRateStatistics(Filter filter, Sort sort, int limit);

	/**
	 * Test cases' count trend loading
	 *
	 * @param filter                {@link Filter}
	 * @param executionContentField Content field with table column name
	 * @param sort                  {@link Sort}
	 * @param limit                 Results limit
	 * @return List of{@link CasesTrendContent}
	 */
	List<CasesTrendContent> casesTrendStatistics(Filter filter, String executionContentField, Sort sort, int limit);

	/**
	 * Bug trend loading
	 *
	 * @param filter        {@link Filter}
	 * @param contentFields Custom fields for select query building
	 * @param sort          {@link Sort}
	 * @param limit         Results limit
	 * @return List of{@link LaunchesStatisticsContent}
	 */
	List<LaunchesStatisticsContent> bugTrendStatistics(Filter filter, List<String> contentFields, Sort sort, int limit);

	/**
	 * Comparison statistics content loading for launches with specified Ids
	 *
	 * @param filter        {@link Filter}
	 * @param contentFields Custom fields for select query building
	 * @param sort          {@link Sort}
	 * @param limit         Results limit
	 * @return List of{@link LaunchesStatisticsContent}
	 */
	List<LaunchesStatisticsContent> launchesComparisonStatistics(Filter filter, List<String> contentFields, Sort sort, int limit);

	/**
	 * Launches duration content loading
	 *
	 * @param filter   {@link Filter}
	 * @param sort     {@link Sort}
	 * @param isLatest Flag for retrieving only latest launches
	 * @param limit    Results limit
	 * @return List of{@link LaunchesDurationContent}
	 */
	List<LaunchesDurationContent> launchesDurationStatistics(Filter filter, Sort sort, boolean isLatest, int limit);

	/**
	 * Not passed cases content loading
	 *
	 * @param filter {@link Filter}
	 * @param sort   {@link Sort}
	 * @param limit  Results limit
	 * @return List of{@link NotPassedCasesContent}
	 */
	List<NotPassedCasesContent> notPassedCasesStatistics(Filter filter, Sort sort, int limit);

	/**
	 * Launches table content loading
	 *
	 * @param filter        {@link Filter}
	 * @param contentFields Custom fields for select query building
	 * @param sort          {@link Sort}
	 * @param limit         Results limit
	 * @return List of{@link LaunchesStatisticsContent}
	 */
	List<LaunchesStatisticsContent> launchesTableStatistics(Filter filter, List<String> contentFields, Sort sort, int limit);

	/**
	 * User activity content loading
	 *
	 * @param filter {@link Filter}
	 * @param sort   {@link Sort}
	 * @param limit  Results limit
	 * @return List of{@link ActivityContent}
	 */
	List<ActivityContent> activityStatistics(Filter filter, Sort sort, int limit);

	/**
	 * Loading unique bugs content that was produced by Bug Tracking System
	 *
	 * @param filter   {@link Filter}
	 * @param sort     {@link Sort}
	 * @param isLatest Flag for retrieving only latest launches
	 * @param limit    Results limit
	 * @return Map grouped by ticket id as key and List of {@link UniqueBugContent} as value
	 */
	Map<String, List<UniqueBugContent>> uniqueBugStatistics(Filter filter, Sort sort, boolean isLatest, int limit);

	/**
	 * Loading the most "flaky" test cases content
	 *
	 * @param filter {@link Filter}
	 * @param limit  Results limit
	 * @return List of {@link FlakyCasesTableContent}
	 */
	List<FlakyCasesTableContent> flakyCasesStatistics(Filter filter, int limit);

	//	/**
	//	 * Loading cumulative trend statistics grouped by {@link LaunchTag#getValue()}
	//	 *
	//	 * @param filter        {@link Filter}
	//	 * @param contentFields Custom fields for select query building
	//	 * @param sort          {@link Sort}
	//	 * @param tagPrefix     Prefix of the {@link LaunchTag#getValue()}
	//	 * @param limit         Results limit
	//	 * @return Map with {@link LaunchTag#getValue()} as key and list of {@link LaunchesStatisticsContent} as value
	//	 */
	//	Map<String, List<LaunchesStatisticsContent>> cumulativeTrendStatistics(Filter filter, List<String> contentFields, Sort sort,
	//			String tagPrefix, int limit);

	/**
	 * Loading the product status statistics grouped by one or more {@link Filter}
	 *
	 * @param filterSortMapping Map of {@link Filter} as key and {@link Sort} as value to implement multiple filters logic with own sorting
	 * @param contentFields     Custom fields for select query building
	 * @param tags              List of the prefixes of the {@link LaunchTag#getValue()}
	 * @param isLatest          Flag for retrieving only latest launches
	 * @param limit             Results limit
	 * @return Map grouped by filter name with {@link Queryable#getName()} as key and list of {@link LaunchesStatisticsContent} as value
	 */
	Map<String, List<LaunchesStatisticsContent>> productStatusGroupedByFilterStatistics(Map<Filter, Sort> filterSortMapping,
			List<String> contentFields, List<String> tags, boolean isLatest, int limit);

	/**
	 * Loading the product status statistics grouped by {@link com.epam.ta.reportportal.entity.launch.Launch} with combined {@link Filter}
	 *
	 * @param filter        {@link Filter}
	 * @param contentFields Custom fields for select query building
	 * @param tags          List of the prefixes of the {@link LaunchTag#getValue()}
	 * @param sort          {@link Sort}
	 * @param isLatest      Flag for retrieving only latest launches
	 * @param limit         Results limit
	 * @return list of {@link LaunchesStatisticsContent}
	 */
	List<LaunchesStatisticsContent> productStatusGroupedByLaunchesStatistics(Filter filter, List<String> contentFields, List<String> tags,
			Sort sort, boolean isLatest, int limit);

	/**
	 * Loading the TOP-20 most time consuming test cases
	 *
	 * @param filter {@link Filter}
	 * @return list of {@link MostTimeConsumingTestCasesContent}
	 */
	List<MostTimeConsumingTestCasesContent> mostTimeConsumingTestCasesStatistics(Filter filter);
}
