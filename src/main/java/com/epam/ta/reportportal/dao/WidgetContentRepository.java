/*
 * Copyright 2019 EPAM Systems
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
import com.epam.ta.reportportal.entity.ItemAttribute;
import com.epam.ta.reportportal.entity.widget.content.*;
import com.epam.ta.reportportal.ws.model.ActivityResource;
import org.springframework.data.domain.Sort;

import javax.annotation.Nullable;
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
	 * @return {@link OverallStatisticsContent}
	 */
	OverallStatisticsContent overallStatisticsContent(Filter filter, Sort sort, List<String> contentFields, boolean latest, int limit);

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
	List<CriteriaHistoryItem> topItemsByCriteria(Filter filter, String criteria, int limit, boolean includeMethods);

	/**
	 * Launch statistics content loading
	 *
	 * @param filter        {@link Filter}
	 * @param contentFields Custom fields for select query building
	 * @param sort          {@link Sort}
	 * @param limit         Results limit
	 * @return List of {@link ChartStatisticsContent}
	 */
	List<ChartStatisticsContent> launchStatistics(Filter filter, List<String> contentFields, Sort sort, int limit);

	/**
	 * Investigated statistics loading
	 *
	 * @param filter {@link Filter}
	 * @param sort   {@link Sort}
	 * @param limit  Results limit
	 * @return List of{@link ChartStatisticsContent}
	 */
	List<ChartStatisticsContent> investigatedStatistics(Filter filter, Sort sort, int limit);

	/**
	 * Investigated statistics loading for timeline view
	 *
	 * @param filter {@link Filter}
	 * @param sort   {@link Sort}
	 * @param limit  Results limit
	 * @return List of{@link ChartStatisticsContent}
	 */
	List<ChartStatisticsContent> timelineInvestigatedStatistics(Filter filter, Sort sort, int limit);

	/**
	 * Launch passing rate result for depending on the filter conditions
	 *
	 * @param filter {@link Filter}
	 * @param sort   {@link Sort}
	 * @param limit  Results limit
	 * @return {@link PassingRateStatisticsResult}
	 */
	PassingRateStatisticsResult passingRatePerLaunchStatistics(Filter filter, Sort sort, int limit);

	/**
	 * Summary passing rate result for launches depending on the filter conditions
	 *
	 * @param filter {@link Filter}
	 * @param sort   {@link Sort}
	 * @param limit  Results limit
	 * @return {@link PassingRateStatisticsResult}
	 */
	PassingRateStatisticsResult summaryPassingRateStatistics(Filter filter, Sort sort, int limit);

	/**
	 * Test cases' count trend loading
	 *
	 * @param filter                {@link Filter}
	 * @param executionContentField Content field with table column name
	 * @param sort                  {@link Sort}
	 * @param limit                 Results limit
	 * @return List of{@link ChartStatisticsContent}
	 */
	List<ChartStatisticsContent> casesTrendStatistics(Filter filter, String executionContentField, Sort sort, int limit);

	/**
	 * Bug trend loading
	 *
	 * @param filter        {@link Filter}
	 * @param contentFields Custom fields for select query building
	 * @param sort          {@link Sort}
	 * @param limit         Results limit
	 * @return List of{@link ChartStatisticsContent}
	 */
	List<ChartStatisticsContent> bugTrendStatistics(Filter filter, List<String> contentFields, Sort sort, int limit);

	/**
	 * Comparison statistics content loading for launches with specified Ids
	 *
	 * @param filter        {@link Filter}
	 * @param contentFields Custom fields for select query building
	 * @param sort          {@link Sort}
	 * @param limit         Results limit
	 * @return List of{@link ChartStatisticsContent}
	 */
	List<ChartStatisticsContent> launchesComparisonStatistics(Filter filter, List<String> contentFields, Sort sort, int limit);

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
	 * @return List of{@link LaunchesTableContent}
	 */
	List<LaunchesTableContent> launchesTableStatistics(Filter filter, List<String> contentFields, Sort sort, int limit);

	/**
	 * User activity content loading
	 *
	 * @param filter {@link Filter}
	 * @param sort   {@link Sort}
	 * @param limit  Results limit
	 * @return List of{@link ActivityResource}
	 */
	List<ActivityResource> activityStatistics(Filter filter, Sort sort, int limit);

	/**
	 * Loading unique bugs content that was produced by Bug Tracking System
	 *
	 * @param filter   {@link Filter}
	 * @param sort     {@link Sort}
	 * @param isLatest Flag for retrieving only latest launches
	 * @param limit    Results limit
	 * @return Map grouped by ticket id as key and List of {@link UniqueBugContent} as value
	 */
	Map<String, UniqueBugContent> uniqueBugStatistics(Filter filter, Sort sort, boolean isLatest, int limit);

	/**
	 * Loading the most "flaky" test cases content
	 *
	 * @param filter         {@link Filter}
	 * @param includeMethods Include or not test item types that have 'METHOD' or 'CLASS'
	 * @param limit          Results limit
	 * @return List of {@link FlakyCasesTableContent}
	 */
	List<FlakyCasesTableContent> flakyCasesStatistics(Filter filter, boolean includeMethods, int limit);

	/**
	 * Loading cumulative trend statistics grouped by {@link com.epam.ta.reportportal.entity.ItemAttribute#getValue()}
	 *
	 * @param filter              {@link Filter}
	 * @param contentFields       Custom fields for select query building
	 * @param sort                {@link Sort}
	 * @param primaryAttributeKey {@link ItemAttribute#getKey()} ()}
	 * @param limit               Attributes limit
	 * @return Map with {@link com.epam.ta.reportportal.entity.ItemAttribute#getValue()} as key and list of {@link CumulativeTrendChartContent} as value
	 */
	List<CumulativeTrendChartEntry> cumulativeTrendStatistics(Filter filter, List<String> contentFields, Sort sort,
			String primaryAttributeKey, String subAttributeKey, int limit);

	/**
	 * Loading the product status statistics grouped by one or more {@link Filter}
	 *
	 * @param filterSortMapping Map of {@link Filter} as key and {@link Sort} as value to implement multiple filters logic with own sorting
	 * @param contentFields     Custom fields for select query building
	 * @param customColumns     Map of the custom column name as key and {@link com.epam.ta.reportportal.entity.ItemAttribute#key} as value
	 * @param isLatest          Flag for retrieving only latest launches
	 * @param limit             Results limit
	 * @return Map grouped by filter name with {@link com.epam.ta.reportportal.entity.filter.UserFilter#getName()} as key and list of {@link ProductStatusStatisticsContent} as value
	 */
	Map<String, List<ProductStatusStatisticsContent>> productStatusGroupedByFilterStatistics(Map<Filter, Sort> filterSortMapping,
			List<String> contentFields, Map<String, String> customColumns, boolean isLatest, int limit);

	/**
	 * Loading the product status statistics grouped by {@link com.epam.ta.reportportal.entity.launch.Launch} with combined {@link Filter}
	 *
	 * @param filter        {@link Filter}
	 * @param contentFields Custom fields for select query building
	 * @param customColumns Map of the custom column name as key and {@link com.epam.ta.reportportal.entity.ItemAttribute#key} as value
	 * @param sort          {@link Sort}
	 * @param isLatest      Flag for retrieving only latest launches
	 * @param limit         Results limit
	 * @return list of {@link ProductStatusStatisticsContent}
	 */
	List<ProductStatusStatisticsContent> productStatusGroupedByLaunchesStatistics(Filter filter, List<String> contentFields,
			Map<String, String> customColumns, Sort sort, boolean isLatest, int limit);

	/**
	 * Loading the most time consuming test cases
	 *
	 * @param filter {@link Filter}
	 * @param limit  Results limit
	 * @return list of {@link MostTimeConsumingTestCasesContent}
	 */
	List<MostTimeConsumingTestCasesContent> mostTimeConsumingTestCasesStatistics(Filter filter, int limit);

	/**
	 * Load TOP-20 most matched {@link com.epam.ta.reportportal.entity.pattern.PatternTemplate} entities with matched items count,
	 * grouped by {@link ItemAttribute#value} and {@link com.epam.ta.reportportal.entity.pattern.PatternTemplate#name}
	 *
	 * @param filter       {@link Filter}
	 * @param sort         {@link Sort}
	 * @param attributeKey {@link ItemAttribute#key}
	 * @param patternName  {@link com.epam.ta.reportportal.entity.pattern.PatternTemplate#name}
	 * @param isLatest     Flag for retrieving only latest launches
	 * @param limit        Attributes count limit
	 * @return The {@link List} of the {@link TopPatternTemplatesContent}
	 */
	List<TopPatternTemplatesContent> patternTemplate(Filter filter, Sort sort, String attributeKey, @Nullable String patternName,
			boolean isLatest, int limit);
}
