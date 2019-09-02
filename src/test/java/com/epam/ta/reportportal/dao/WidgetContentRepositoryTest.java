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

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.ConvertibleCondition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.entity.activity.Activity;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.widget.content.*;
import com.epam.ta.reportportal.entity.widget.content.healthcheck.ComponentHealthCheckContent;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.ws.model.ActivityResource;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.commons.querygen.constant.ActivityCriteriaConstant.CRITERIA_ACTION;
import static com.epam.ta.reportportal.commons.querygen.constant.ActivityCriteriaConstant.CRITERIA_CREATION_DATE;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.ItemAttributeConstant.CRITERIA_COMPOSITE_ATTRIBUTE;
import static com.epam.ta.reportportal.commons.querygen.constant.ItemAttributeConstant.CRITERIA_ITEM_ATTRIBUTE_KEY;
import static com.epam.ta.reportportal.commons.querygen.constant.LaunchCriteriaConstant.CRITERIA_LAUNCH_MODE;
import static com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant.CRITERIA_STATUS;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.CRITERIA_USER;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.jooq.enums.JTestItemTypeEnum.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Budayeu
 */
@Sql("/db/fill/widget-content/widget-content-fill.sql")
class WidgetContentRepositoryTest extends BaseTest {

	@Autowired
	private WidgetContentRepository widgetContentRepository;

	@Test
	void overallStatisticsContent() {
		String sortingColumn = "statistics$defects$no_defect$nd001";
		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildContentFields();
		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, sortingColumn)));

		OverallStatisticsContent overallStatisticsContent = widgetContentRepository.overallStatisticsContent(filter,
				sort,
				contentFields,
				false,
				4
		);

		assertEquals(48, (long) overallStatisticsContent.getValues().get("statistics$executions$total"));
		assertEquals(13, (long) overallStatisticsContent.getValues().get("statistics$executions$passed"));
		assertEquals(13, (long) overallStatisticsContent.getValues().get("statistics$executions$skipped"));
		assertEquals(22, (long) overallStatisticsContent.getValues().get("statistics$executions$failed"));
		assertEquals(9, (long) overallStatisticsContent.getValues().get("statistics$defects$to_investigate$total"));
		assertEquals(16, (long) overallStatisticsContent.getValues().get("statistics$defects$system_issue$total"));
		assertEquals(11, (long) overallStatisticsContent.getValues().get("statistics$defects$automation_bug$total"));
		assertEquals(17, (long) overallStatisticsContent.getValues().get("statistics$defects$product_bug$total"));
		assertEquals(11, (long) overallStatisticsContent.getValues().get("statistics$defects$no_defect$total"));
		assertEquals(9, (long) overallStatisticsContent.getValues().get("statistics$defects$to_investigate$ti001"));
		assertEquals(16, (long) overallStatisticsContent.getValues().get("statistics$defects$system_issue$si001"));
		assertEquals(11, (long) overallStatisticsContent.getValues().get("statistics$defects$automation_bug$ab001"));
		assertEquals(17, (long) overallStatisticsContent.getValues().get("statistics$defects$product_bug$pb001"));
		assertEquals(11, (long) overallStatisticsContent.getValues().get("statistics$defects$no_defect$nd001"));
	}

	@Test
	void mostFailedByDefectCriteria() {

		String defect = "statistics$executions$failed";

		Filter filter = buildDefaultFilter(1L);

		List<CriteriaHistoryItem> criteriaHistoryItems = widgetContentRepository.topItemsByCriteria(filter, defect, 10, false);

		System.out.println(123);

	}

	@Test
	void launchStatistics() {

		String sortingColumn = "statistics$defects$no_defect$nd001";

		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildContentFields();

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, sortingColumn),
				new Sort.Order(Sort.Direction.DESC, CRITERIA_START_TIME)
		);

		Sort sort = Sort.by(orderings);

		List<ChartStatisticsContent> chartStatisticsContents = widgetContentRepository.launchStatistics(filter, contentFields, sort, 4);
		assertNotNull(chartStatisticsContents);
		assertEquals(4, chartStatisticsContents.size());

		assertEquals(chartStatisticsContents.get(0).getValues().get(sortingColumn), String.valueOf(6));
		assertEquals(chartStatisticsContents.get(chartStatisticsContents.size() - 1).getValues().get(sortingColumn), String.valueOf(1));
	}

	@Test
	void investigatedStatistics() {

		Map<Long, Map<String, Integer>> statistics = buildTotalDefectsMap();

		String sortingColumn = "statistics$defects$no_defect$nd001";

		Filter filter = buildDefaultFilter(1L);

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, sortingColumn));

		Sort sort = Sort.by(orderings);

		List<ChartStatisticsContent> chartStatisticsContents = widgetContentRepository.investigatedStatistics(filter, sort, 4);
		assertNotNull(chartStatisticsContents);
		assertEquals(4, chartStatisticsContents.size());

		chartStatisticsContents.forEach(res -> {
			Map<String, Integer> stats = statistics.get(res.getId());
			int sum = stats.values().stream().mapToInt(Integer::intValue).sum();
			assertEquals(100.0,
					Double.parseDouble(res.getValues().get(TO_INVESTIGATE)) + Double.parseDouble(res.getValues().get(INVESTIGATED)),
					0.01
			);
			assertEquals(Double.parseDouble(res.getValues().get(TO_INVESTIGATE)),
					BigDecimal.valueOf((double) 100 * stats.get("statistics$defects$to_investigate$total") / sum)
							.setScale(2, RoundingMode.HALF_UP)
							.doubleValue(),
					0.01
			);
		});
	}

	@Test
	void timelineInvestigatedStatistics() {

		Filter filter = buildDefaultFilter(1L);

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, CRITERIA_ITEM_ATTRIBUTE_KEY));

		Sort sort = Sort.by(orderings);

		List<ChartStatisticsContent> chartStatisticsContents = widgetContentRepository.timelineInvestigatedStatistics(filter, sort, 4);
		assertNotNull(chartStatisticsContents);
		assertEquals(4, chartStatisticsContents.size());
	}

	@Test
	void launchPassPerLaunchStatistics() {
		Filter filter = buildDefaultFilter(1L);

		filter.withCondition(new FilterCondition(Condition.EQUALS, false, "launch name 1", CRITERIA_NAME));

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, CRITERIA_START_TIME));

		Sort sort = Sort.by(orderings);

		PassingRateStatisticsResult passStatisticsResult = widgetContentRepository.passingRatePerLaunchStatistics(filter, sort, 1);

		assertNotNull(passStatisticsResult);
		assertEquals(3, passStatisticsResult.getPassed());
		assertEquals(12, passStatisticsResult.getTotal());
	}

	@Test
	void summaryPassStatistics() {
		Filter filter = buildDefaultFilter(1L);
		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, CRITERIA_START_TIME)));

		PassingRateStatisticsResult passStatisticsResult = widgetContentRepository.summaryPassingRateStatistics(filter, sort, 4);

		assertNotNull(passStatisticsResult);
		assertEquals(13, passStatisticsResult.getPassed());
		assertEquals(48, passStatisticsResult.getTotal());
	}

	@Test
	void casesTrendStatistics() {
		Filter filter = buildDefaultFilter(1L);
		String executionContentField = "statistics$executions$total";
		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, CRITERIA_START_TIME)));

		List<ChartStatisticsContent> chartStatisticsContents = widgetContentRepository.casesTrendStatistics(filter,
				executionContentField,
				sort,
				4
		);

		assertNotNull(chartStatisticsContents);
		assertEquals(4, chartStatisticsContents.size());

		int firstElementDelta = Integer.parseInt(chartStatisticsContents.get(0).getValues().get(DELTA));
		int secondElementDelta = Integer.parseInt(chartStatisticsContents.get(1).getValues().get(executionContentField)) - Integer.parseInt(
				chartStatisticsContents.get(0).getValues().get(executionContentField));
		int thirdElementDelta = Integer.parseInt(chartStatisticsContents.get(2).getValues().get(executionContentField)) - Integer.parseInt(
				chartStatisticsContents.get(1).getValues().get(executionContentField));
		int fourthElementDelta = Integer.parseInt(chartStatisticsContents.get(3).getValues().get(executionContentField)) - Integer.parseInt(
				chartStatisticsContents.get(2).getValues().get(executionContentField));

		assertEquals(0, firstElementDelta);
		assertEquals(1, secondElementDelta);
		assertEquals(4, thirdElementDelta);
		assertEquals(-3, fourthElementDelta);

	}

	@Test
	void bugTrendStatistics() {
		Map<Long, Map<String, Integer>> statistics = buildTotalDefectsMap();
		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildTotalDefectsContentFields();
		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, CRITERIA_START_TIME)));

		List<ChartStatisticsContent> chartStatisticsContents = widgetContentRepository.bugTrendStatistics(filter, contentFields, sort, 4);

		assertNotNull(chartStatisticsContents);
		assertEquals(4, chartStatisticsContents.size());

		chartStatisticsContents.forEach(res -> {
			Map<String, Integer> stats = statistics.get(res.getId());
			Map<String, String> resStatistics = res.getValues();

			long total = stats.values().stream().mapToInt(Integer::intValue).sum();

			stats.keySet().forEach(key -> assertEquals((long) stats.get(key), (long) Integer.parseInt(resStatistics.get(key))));

			assertEquals(String.valueOf(total), resStatistics.get(TOTAL));
		});
	}

	@Test
	void launchesComparisonStatistics() {
		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildTotalContentFields();
		filter = filter.withConditions(Lists.newArrayList(new FilterCondition(Condition.EQUALS, false, "launch name 1", NAME)));
		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, CRITERIA_START_TIME)));

		List<ChartStatisticsContent> chartStatisticsContents = widgetContentRepository.launchesComparisonStatistics(filter,
				contentFields,
				sort,
				2
		);

		assertNotNull(chartStatisticsContents);
		assertEquals(2, chartStatisticsContents.size());

		chartStatisticsContents.forEach(res -> {
			Map<String, String> currStatistics = res.getValues();
			Map<Long, Map<String, Integer>> preDefinedStatistics = buildLaunchesComparisonStatistics();

			Map<String, Integer> testStatistics = preDefinedStatistics.get(res.getId());
			int executionsSum = testStatistics.entrySet()
					.stream()
					.filter(entry -> entry.getKey().contains(EXECUTIONS_KEY) && !entry.getKey().equalsIgnoreCase(EXECUTIONS_TOTAL))
					.mapToInt(Map.Entry::getValue)
					.sum();
			int defectsSum = testStatistics.entrySet()
					.stream()
					.filter(entry -> entry.getKey().contains(DEFECTS_KEY))
					.mapToInt(Map.Entry::getValue)
					.sum();

			currStatistics.keySet()
					.stream()
					.filter(key -> key.contains(EXECUTIONS_KEY) && !key.equalsIgnoreCase(EXECUTIONS_TOTAL))
					.forEach(key -> assertEquals(Double.parseDouble(currStatistics.get(key)),
							BigDecimal.valueOf((double) 100 * testStatistics.get(key) / executionsSum)
									.setScale(2, RoundingMode.HALF_UP)
									.doubleValue(),
							0.01
					));

			assertEquals((double) testStatistics.get(EXECUTIONS_TOTAL), Double.parseDouble(currStatistics.get(EXECUTIONS_TOTAL)), 0.01);

			currStatistics.keySet()
					.stream()
					.filter(key -> key.contains(DEFECTS_KEY))
					.forEach(key -> assertEquals(Double.parseDouble(currStatistics.get(key)),
							BigDecimal.valueOf((double) 100 * testStatistics.get(key) / defectsSum)
									.setScale(2, RoundingMode.HALF_UP)
									.doubleValue(),
							0.01
					));
		});
	}

	@Test
	void launchesDurationStatistics() {
		Filter filter = buildDefaultFilter(1L);
		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, CRITERIA_START_TIME)));

		List<LaunchesDurationContent> launchesDurationContents = widgetContentRepository.launchesDurationStatistics(filter, sort, false, 4);

		assertNotNull(launchesDurationContents);
		assertEquals(4, launchesDurationContents.size());

		launchesDurationContents.forEach(content -> {
			Timestamp endTime = content.getEndTime();
			Timestamp startTime = content.getStartTime();
			if (startTime.before(endTime)) {
				long duration = content.getDuration();
				assertTrue(duration > 0 && duration == endTime.getTime() - startTime.getTime());
			}
		});

	}

	@Test
	void notPassedCasesStatistics() {
		Filter filter = buildDefaultFilter(1L);
		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, CRITERIA_START_TIME)));

		List<NotPassedCasesContent> notPassedCasesContents = widgetContentRepository.notPassedCasesStatistics(filter, sort, 3);

		assertNotNull(notPassedCasesContents);
		assertEquals(3, notPassedCasesContents.size());

		notPassedCasesContents.forEach(content -> {
			Map<String, String> currentStatistics = content.getValues();
			Map<Long, Map<String, Integer>> preDefinedStatistics = buildNotPassedCasesStatistics();

			Map<String, Integer> testStatistics = preDefinedStatistics.get(content.getId());
			int executionsSum = testStatistics.entrySet().stream().mapToInt(Map.Entry::getValue).sum();

			assertEquals(Double.parseDouble(currentStatistics.get(NOT_PASSED_STATISTICS_KEY)),
					BigDecimal.valueOf((double) 100 * (testStatistics.get("statistics$executions$skipped") + testStatistics.get(
							"statistics$executions$failed")) / executionsSum).setScale(2, RoundingMode.HALF_UP).doubleValue(),
					0.01
			);
		});
	}

	@Test
	void launchesTableStatistics() {
		Filter filter = buildDefaultFilter(1L);
		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, CRITERIA_START_TIME)));
		List<String> contentFields = buildLaunchesTableContentFields();

		List<LaunchesTableContent> launchStatisticsContents = widgetContentRepository.launchesTableStatistics(filter,
				contentFields,
				sort,
				3
		);
		assertNotNull(launchStatisticsContents);
		assertEquals(3, launchStatisticsContents.size());

		List<String> tableContentFields = Lists.newArrayList(CRITERIA_END_TIME,
				CRITERIA_DESCRIPTION,
				CRITERIA_LAST_MODIFIED,
				CRITERIA_USER
		);

		launchStatisticsContents.forEach(content -> {
			Map<String, String> values = content.getValues();
			tableContentFields.forEach(tcf -> {
				assertTrue(values.containsKey(tcf));
				assertNotNull(values.get(tcf));
			});
		});

	}

	@Test
	void activityStatistics() {
		Filter filter = buildDefaultActivityFilter(1L);
		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, CRITERIA_CREATION_DATE)));
		List<String> contentFields = buildActivityContentFields();

		filter.withCondition(new FilterCondition(Condition.EQUALS, false, "superadmin", CRITERIA_USER))
				.withCondition(new FilterCondition(Condition.IN, false, String.join(",", contentFields), CRITERIA_ACTION));

		List<ActivityResource> activityContentList = widgetContentRepository.activityStatistics(filter, sort, 4);

		assertNotNull(activityContentList);
		assertEquals(4, activityContentList.size());
	}

	@Test
	void uniqueBugStatistics() {
		Filter filter = buildDefaultFilter(1L);
		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, CRITERIA_START_TIME));
		Sort sort = Sort.by(orderings);

		Map<String, UniqueBugContent> uniqueBugStatistics = widgetContentRepository.uniqueBugStatistics(filter, sort, true, 5);

		assertNotNull(uniqueBugStatistics);
		assertEquals(2, uniqueBugStatistics.size());

		assertTrue(uniqueBugStatistics.containsKey("EPMRPP-322"));
		assertTrue(uniqueBugStatistics.containsKey("EPMRPP-123"));

		assertEquals(2, uniqueBugStatistics.get("EPMRPP-322").getItems().size());
		assertEquals(1, uniqueBugStatistics.get("EPMRPP-123").getItems().size());
	}

	@Test
	void flakyCasesStatistics() {
		Filter filter = buildDefaultFilter(1L);

		List<FlakyCasesTableContent> flakyCasesStatistics = widgetContentRepository.flakyCasesStatistics(filter, false, 4);

		assertNotNull(flakyCasesStatistics);
		assertTrue(flakyCasesStatistics.isEmpty());
	}

	@Test
	void cumulativeTrendChart() {
		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildContentFields();

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, "statistics$defects$no_defect$nd001"),
				new Sort.Order(Sort.Direction.ASC, CRITERIA_START_TIME)
		);

		Sort sort = Sort.by(orderings);
		List<CumulativeTrendChartEntry> launchesStatisticsContents = widgetContentRepository.cumulativeTrendStatistics(filter,
				contentFields,
				sort,
				"build",
				"level",
				10
		);

		assertNotNull(launchesStatisticsContents);
	}

	@Test
	void productStatusFilterGroupedWidget() {

		List<Sort.Order> firstOrdering = Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, "statistics$defects$product_bug$pb001"));
		List<Sort.Order> secondOrdering = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "statistics$defects$automation_bug$ab001"));

		Sort firstSort = Sort.by(firstOrdering);
		Sort secondSort = Sort.by(secondOrdering);

		Map<Filter, Sort> filterSortMapping = Maps.newLinkedHashMap();
		filterSortMapping.put(buildDefaultFilter(1L), firstSort);
		filterSortMapping.put(buildDefaultTestFilter(1L), secondSort);

		Map<String, String> tags = new LinkedHashMap<>();
		tags.put("firstColumn", "build");
		tags.put("secondColumn", "hello");

		Map<String, List<ProductStatusStatisticsContent>> result = widgetContentRepository.productStatusGroupedByFilterStatistics(
				filterSortMapping,
				buildProductStatusContentFields(),
				tags,
				false,
				10
		);

		assertNotNull(result);
	}

	@Test
	void productStatusLaunchGroupedWidget() {
		Filter filter = buildDefaultTestFilter(1L);
		Sort sort = Sort.by(Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, CRITERIA_START_TIME)));
		Map<String, String> tags = new LinkedHashMap<>();
		tags.put("firstColumn", "build");
		tags.put("secondColumn", "hello");

		List<ProductStatusStatisticsContent> result = widgetContentRepository.productStatusGroupedByLaunchesStatistics(filter,
				buildProductStatusContentFields(),
				tags,
				sort,
				false,
				10
		);

		assertNotNull(result);
	}

	@Test
	void mostTimeConsumingTestCases() {
		Filter filter = buildMostTimeConsumingFilter(1L);
		filter = updateFilter(filter, "launch name 1", 1L, true);
		List<MostTimeConsumingTestCasesContent> mostTimeConsumingTestCasesContents = widgetContentRepository.mostTimeConsumingTestCasesStatistics(
				filter,
				20
		);

		assertNotNull(mostTimeConsumingTestCasesContents);
	}

	@Test
	void patternTemplate() {
		Filter filter = buildDefaultFilter(1L);
		List<TopPatternTemplatesContent> topPatternTemplatesContents = widgetContentRepository.patternTemplate(filter,
				Sort.unsorted(),
				"build",
				"FIRST PATTERN",
				false,
				600,
				15
		);

		assertNotNull(topPatternTemplatesContents);
		assertFalse(topPatternTemplatesContents.isEmpty());
	}

	@Test
	void overallStatisticsContentSorting() {
		String sortingColumn = "statistics$defects$no_defect$nd001";
		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildContentFields();
		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);

		OverallStatisticsContent overallStatisticsContent = widgetContentRepository.overallStatisticsContent(filter,
				sort,
				contentFields,
				false,
				4
		);

		assertNotNull(overallStatisticsContent);
	}

	@Test
	void launchStatisticsSorting() {

		String sortingColumn = "statistics$defects$no_defect$nd001";

		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildContentFields();

		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);

		List<ChartStatisticsContent> chartStatisticsContents = widgetContentRepository.launchStatistics(filter, contentFields, sort, 4);
		assertNotNull(chartStatisticsContents);
		assertEquals(4, chartStatisticsContents.size());
	}

	@Test
	void investigatedStatisticsSorting() {

		String sortingColumn = "statistics$defects$no_defect$nd001";

		Filter filter = buildDefaultFilter(1L);

		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);

		List<ChartStatisticsContent> chartStatisticsContents = widgetContentRepository.investigatedStatistics(filter, sort, 4);
		assertNotNull(chartStatisticsContents);
		assertEquals(4, chartStatisticsContents.size());
	}

	@Test
	void timelineInvestigatedStatisticsSorting() {
		String sortingColumn = "statistics$defects$no_defect$nd001";

		Filter filter = buildDefaultFilter(1L);

		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);

		List<ChartStatisticsContent> chartStatisticsContents = widgetContentRepository.timelineInvestigatedStatistics(filter, sort, 4);
		assertNotNull(chartStatisticsContents);
		assertEquals(4, chartStatisticsContents.size());
	}

	@Test
	void launchPassPerLaunchStatisticsSorting() {
		String sortingColumn = "statistics$defects$no_defect$nd001";
		Filter filter = buildDefaultFilter(1L);

		filter.withCondition(new FilterCondition(Condition.EQUALS, false, "launch name 1", CRITERIA_NAME));

		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);

		PassingRateStatisticsResult passStatisticsResult = widgetContentRepository.passingRatePerLaunchStatistics(filter, sort, 1);

		assertNotNull(passStatisticsResult);
	}

	@Test
	void summaryPassStatisticsSorting() {
		String sortingColumn = "statistics$defects$no_defect$nd001";
		Filter filter = buildDefaultFilter(1L);
		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);

		PassingRateStatisticsResult passStatisticsResult = widgetContentRepository.summaryPassingRateStatistics(filter, sort, 4);

		assertNotNull(passStatisticsResult);
	}

	@Test
	void casesTrendStatisticsSorting() {
		String sortingColumn = "statistics$defects$no_defect$nd001";
		Filter filter = buildDefaultFilter(1L);
		String executionContentField = "statistics$executions$total";
		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);

		List<ChartStatisticsContent> chartStatisticsContents = widgetContentRepository.casesTrendStatistics(filter,
				executionContentField,
				sort,
				4
		);

		assertNotNull(chartStatisticsContents);
		assertEquals(4, chartStatisticsContents.size());

	}

	@Test
	void bugTrendStatisticsSorting() {
		String sortingColumn = "statistics$defects$no_defect$nd001";
		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildTotalDefectsContentFields();
		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);

		List<ChartStatisticsContent> chartStatisticsContents = widgetContentRepository.bugTrendStatistics(filter, contentFields, sort, 4);

		assertNotNull(chartStatisticsContents);
		assertEquals(4, chartStatisticsContents.size());
	}

	@Test
	void launchesComparisonStatisticsSorting() {
		String sortingColumn = "statistics$defects$no_defect$nd001";
		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildTotalContentFields();
		filter = filter.withConditions(Lists.newArrayList(new FilterCondition(Condition.EQUALS, false, "launch name 1", NAME)));
		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);

		List<ChartStatisticsContent> chartStatisticsContents = widgetContentRepository.launchesComparisonStatistics(filter,
				contentFields,
				sort,
				2
		);

		assertNotNull(chartStatisticsContents);
		assertEquals(2, chartStatisticsContents.size());

	}

	@Test
	void launchesDurationStatisticsSorting() {
		String sortingColumn = "statistics$defects$no_defect$nd001";
		Filter filter = buildDefaultFilter(1L);
		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);

		List<LaunchesDurationContent> launchesDurationContents = widgetContentRepository.launchesDurationStatistics(filter, sort, false, 4);

		assertNotNull(launchesDurationContents);
		assertEquals(4, launchesDurationContents.size());

	}

	@Test
	void notPassedCasesStatisticsSorting() {
		String sortingColumn = "statistics$defects$no_defect$nd001";
		Filter filter = buildDefaultFilter(1L);
		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);

		List<NotPassedCasesContent> notPassedCasesContents = widgetContentRepository.notPassedCasesStatistics(filter, sort, 3);

		assertNotNull(notPassedCasesContents);
		assertEquals(3, notPassedCasesContents.size());
	}

	@Test
	void launchesTableStatisticsSorting() {
		String sortingColumn = "statistics$defects$no_defect$nd001";
		Filter filter = buildDefaultFilter(1L);
		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);
		List<String> contentFields = buildLaunchesTableContentFields();

		List<LaunchesTableContent> launchStatisticsContents = widgetContentRepository.launchesTableStatistics(filter,
				contentFields,
				sort,
				3
		);
		assertNotNull(launchStatisticsContents);
		assertEquals(3, launchStatisticsContents.size());

	}

	@Test
	void activityStatisticsSorting() {
		Filter filter = buildDefaultActivityFilter(1L);
		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		Sort sort = Sort.by(orders);
		List<String> contentFields = buildActivityContentFields();

		filter.withCondition(new FilterCondition(Condition.EQUALS, false, "superadmin", CRITERIA_USER))
				.withCondition(new FilterCondition(Condition.IN, false, String.join(",", contentFields), CRITERIA_ACTION));

		List<ActivityResource> activityContentList = widgetContentRepository.activityStatistics(filter, sort, 4);

		assertNotNull(activityContentList);
		assertEquals(4, activityContentList.size());
	}

	@Test
	void uniqueBugStatisticsSorting() {
		String sortingColumn = "statistics$defects$no_defect$nd001";
		Filter filter = buildDefaultFilter(1L);

		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);

		Map<String, UniqueBugContent> uniqueBugStatistics = widgetContentRepository.uniqueBugStatistics(filter, sort, true, 5);

		assertNotNull(uniqueBugStatistics);
		assertEquals(2, uniqueBugStatistics.size());
	}

	@Test
	void cumulativeTrendChartSorting() {
		String sortingColumn = "statistics$defects$no_defect$nd001";
		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildContentFields();

		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);
		List<CumulativeTrendChartEntry> launchesStatisticsContents = widgetContentRepository.cumulativeTrendStatistics(filter,
				contentFields,
				sort,
				"build",
				null,
				10
		);

		assertNotNull(launchesStatisticsContents);
	}

	@Test
	void productStatusFilterGroupedWidgetSorting() {

		String sortingColumn = "statistics$defects$no_defect$nd001";

		Filter filter = buildDefaultFilter(1L);

		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);
		Map<Filter, Sort> filterSortMapping = Maps.newLinkedHashMap();

		filterSortMapping.put(buildDefaultFilter(1L), sort);
		filterSortMapping.put(buildDefaultTestFilter(1L), sort);

		Map<String, String> tags = new LinkedHashMap<>();
		tags.put("firstColumn", "build");
		tags.put("secondColumn", "hello");

		Map<String, List<ProductStatusStatisticsContent>> result = widgetContentRepository.productStatusGroupedByFilterStatistics(
				filterSortMapping,
				buildProductStatusContentFields(),
				tags,
				false,
				10
		);

		assertNotNull(result);
	}

	@Test
	void productStatusLaunchGroupedWidgetSorting() {
		String sortingColumn = "statistics$defects$no_defect$nd001";
		Filter filter = buildDefaultTestFilter(1L);
		List<Sort.Order> orders = filter.getTarget()
				.getCriteriaHolders()
				.stream()
				.map(ch -> new Sort.Order(Sort.Direction.ASC, ch.getFilterCriteria()))
				.collect(Collectors.toList());
		orders.add(new Sort.Order(Sort.Direction.DESC, sortingColumn));
		Sort sort = Sort.by(orders);
		Map<String, String> tags = new LinkedHashMap<>();
		tags.put("firstColumn", "build");
		tags.put("secondColumn", "hello");

		List<ProductStatusStatisticsContent> result = widgetContentRepository.productStatusGroupedByLaunchesStatistics(filter,
				buildProductStatusContentFields(),
				tags,
				sort,
				false,
				10
		);

		assertNotNull(result);
	}

	private Filter buildDefaultFilter(Long projectId) {

		List<ConvertibleCondition> conditionList = Lists.newArrayList(new FilterCondition(Condition.EQUALS,
						false,
						String.valueOf(projectId),
						CRITERIA_PROJECT_ID
				),
				new FilterCondition(Condition.NOT_EQUALS, false, StatusEnum.IN_PROGRESS.name(), CRITERIA_STATUS),
				new FilterCondition(Condition.EQUALS, false, Mode.DEFAULT.toString(), CRITERIA_LAUNCH_MODE)
		);
		return new Filter(1L, Launch.class, conditionList);
	}

	private Filter buildDefaultTestFilter(Long projectId) {
		List<ConvertibleCondition> conditionList = Lists.newArrayList(new FilterCondition(Condition.EQUALS,
						false,
						String.valueOf(projectId),
						CRITERIA_PROJECT_ID
				),
				new FilterCondition(Condition.NOT_EQUALS, false, StatusEnum.IN_PROGRESS.name(), CRITERIA_STATUS),
				new FilterCondition(Condition.EQUALS, false, Mode.DEFAULT.toString(), CRITERIA_LAUNCH_MODE),
				new FilterCondition(Condition.findByMarker("lte").get(), false, "12", "statistics$executions$total")
		);
		return new Filter(2L, Launch.class, conditionList);
	}

	private Filter buildDefaultActivityFilter(Long projectId) {
		List<ConvertibleCondition> conditionList = Lists.newArrayList(new FilterCondition(Condition.EQUALS,
				false,
				String.valueOf(projectId),
				CRITERIA_PROJECT_ID
		));
		return new Filter(1L, Activity.class, conditionList);
	}

	private Filter buildMostTimeConsumingFilter(Long projectId) {
		List<ConvertibleCondition> conditionList = Lists.newArrayList(new FilterCondition(Condition.EQUALS,
						false,
						String.valueOf(projectId),
						CRITERIA_PROJECT_ID
				),
				new FilterCondition(Condition.EQUALS_ANY,
						false,
						String.join(",", JStatusEnum.PASSED.getLiteral(), JStatusEnum.FAILED.getLiteral()),
						CRITERIA_STATUS
				)
		);

		return new Filter(1L, TestItem.class, conditionList);
	}

	private Filter updateFilter(Filter filter, String launchName, Long projectId, boolean includeMethodsFlag) {
		filter = updateFilterWithLaunchName(filter, launchName, projectId);
		filter = updateFilterWithTestItemTypes(filter, includeMethodsFlag);
		return filter;
	}

	private Filter updateFilterWithLaunchName(Filter filter, String launchName, Long projectId) {
		return filter.withCondition(new FilterCondition(Condition.EQUALS, false, String.valueOf(1L), CRITERIA_LAUNCH_ID));
	}

	private Filter updateFilterWithTestItemTypes(Filter filter, boolean includeMethodsFlag) {
		if (includeMethodsFlag) {
			return updateFilterWithStepAndBeforeAfterMethods(filter);
		} else {
			return updateFilterWithStepTestItem(filter);
		}
	}

	private Filter updateFilterWithStepTestItem(Filter filter) {
		return filter.withCondition(new FilterCondition(Condition.EQUALS, false, STEP.getLiteral(), "type"));
	}

	private Filter updateFilterWithStepAndBeforeAfterMethods(Filter filter) {
		return filter.withCondition(new FilterCondition(Condition.EQUALS_ANY,
				false,
				String.join(",", STEP.getLiteral(), BEFORE_METHOD.getLiteral(), AFTER_METHOD.getLiteral()),
				"type"
		));
	}

	private List<String> buildMostTimeConsumingTestCases() {
		return Lists.newArrayList("statistics$executions$failed", "statistics$executions$passed");
	}

	private List<String> buildLaunchesTableContentFields() {
		return Lists.newArrayList("statistics$defects$no_defect$nd001",
				"statistics$defects$product_bug$pb001",
				"statistics$defects$automation_bug$ab001",
				"statistics$defects$system_issue$si001",
				"statistics$defects$to_investigate$ti001",
				CRITERIA_END_TIME,
				CRITERIA_DESCRIPTION,
				CRITERIA_LAST_MODIFIED,
				CRITERIA_USER,
				"number",
				"name",
				"startTime",
				"attributes",
				"statistics$executions$total",
				"statistics$executions$failed",
				"statistics$executions$passed",
				"statistics$executions$skipped"
		);
	}

	private List<String> buildContentFields() {

		return Lists.newArrayList("statistics$defects$no_defect$nd001",
				"statistics$defects$product_bug$pb001",
				"statistics$defects$automation_bug$ab001",
				"statistics$defects$system_issue$si001",
				"statistics$defects$to_investigate$ti001",
				"statistics$executions$failed",
				"statistics$executions$skipped",
				"statistics$executions$passed",
				"statistics$executions$total",
				"statistics$defects$no_defect$total",
				"statistics$defects$product_bug$total",
				"statistics$defects$automation_bug$total",
				"statistics$defects$system_issue$total",
				"statistics$defects$to_investigate$total"

		);
	}

	private List<String> buildTotalDefectsContentFields() {
		return Lists.newArrayList("statistics$defects$to_investigate$total",
				"statistics$defects$product_bug$total",
				"statistics$defects$automation_bug$total",
				"statistics$defects$system_issue$total",
				"statistics$defects$no_defect$total"
		);
	}

	private List<String> buildTotalContentFields() {
		return Lists.newArrayList("statistics$defects$no_defect$total",
				"statistics$defects$product_bug$total",
				"statistics$defects$automation_bug$total",
				"statistics$defects$system_issue$total",
				"statistics$defects$to_investigate$total",
				"statistics$executions$failed",
				"statistics$executions$skipped",
				"statistics$executions$passed",
				"statistics$executions$total"
		);
	}

	private List<String> buildProductStatusContentFields() {
		return Lists.newArrayList("statistics$defects$no_defect$nd001",
				"statistics$defects$product_bug$pb001",
				"statistics$defects$automation_bug$ab001",
				"statistics$defects$system_issue$si001",
				"statistics$defects$to_investigate$ti001",
				"statistics$executions$failed",
				"statistics$executions$skipped",
				"statistics$executions$total",
				"startTime",
				"status",
				"statistics$defects$no_defect$total",
				"statistics$defects$product_bug$total",
				"statistics$defects$automation_bug$total",
				"statistics$defects$system_issue$total",
				"statistics$defects$to_investigate$total"

		);
	}

	private List<String> buildActivityContentFields() {
		return Lists.newArrayList("createLaunch", "createItem");
	}

	private Map<Long, Map<String, Integer>> buildTotalDefectsMap() {
		Map<Long, Map<String, Integer>> investigatedTrendMap = Maps.newLinkedHashMap();

		investigatedTrendMap.put(1L,
				ImmutableMap.<String, Integer>builder().put("statistics$defects$to_investigate$total", 2)
						.put("statistics$defects$system_issue$total", 8)
						.put("statistics$defects$automation_bug$total", 7)
						.put("statistics$defects$product_bug$total", 13)
						.put("statistics$defects$no_defect$total", 2)
						.build()
		);
		investigatedTrendMap.put(2L,
				ImmutableMap.<String, Integer>builder().put("statistics$defects$to_investigate$total", 3)
						.put("statistics$defects$system_issue$total", 3)
						.put("statistics$defects$automation_bug$total", 1)
						.put("statistics$defects$product_bug$total", 1)
						.put("statistics$defects$no_defect$total", 2)
						.build()
		);
		investigatedTrendMap.put(3L,
				ImmutableMap.<String, Integer>builder().put("statistics$defects$to_investigate$total", 1)
						.put("statistics$defects$system_issue$total", 1)
						.put("statistics$defects$automation_bug$total", 1)
						.put("statistics$defects$product_bug$total", 1)
						.put("statistics$defects$no_defect$total", 1)
						.build()
		);
		investigatedTrendMap.put(4L,
				ImmutableMap.<String, Integer>builder().put("statistics$defects$to_investigate$total", 3)
						.put("statistics$defects$system_issue$total", 4)
						.put("statistics$defects$automation_bug$total", 2)
						.put("statistics$defects$product_bug$total", 2)
						.put("statistics$defects$no_defect$total", 6)
						.build()
		);

		return investigatedTrendMap;

	}

	private Map<Long, Map<String, Integer>> buildLaunchesComparisonStatistics() {
		Map<Long, Map<String, Integer>> predefinedLaunchesComparisonStatistics = Maps.newLinkedHashMap();

		predefinedLaunchesComparisonStatistics.put(1L,
				ImmutableMap.<String, Integer>builder().put("statistics$defects$to_investigate$total", 2)
						.put("statistics$defects$system_issue$total", 8)
						.put("statistics$defects$automation_bug$total", 7)
						.put("statistics$defects$product_bug$total", 13)
						.put("statistics$defects$no_defect$total", 2)
						.put("statistics$executions$passed", 3)
						.put("statistics$executions$skipped", 4)
						.put("statistics$executions$failed", 3)
						.put("statistics$executions$total", 10)
						.build()
		);
		predefinedLaunchesComparisonStatistics.put(2L,
				ImmutableMap.<String, Integer>builder().put("statistics$defects$to_investigate$total", 3)
						.put("statistics$defects$system_issue$total", 3)
						.put("statistics$defects$automation_bug$total", 1)
						.put("statistics$defects$product_bug$total", 1)
						.put("statistics$defects$no_defect$total", 2)
						.put("statistics$executions$passed", 2)
						.put("statistics$executions$skipped", 3)
						.put("statistics$executions$failed", 6)
						.put("statistics$executions$total", 11)
						.build()
		);

		return predefinedLaunchesComparisonStatistics;

	}

	private Map<Long, Map<String, Integer>> buildNotPassedCasesStatistics() {
		Map<Long, Map<String, Integer>> investigatedTrendMap = Maps.newLinkedHashMap();

		investigatedTrendMap.put(1L,
				ImmutableMap.<String, Integer>builder().put("statistics$executions$passed", 3)
						.put("statistics$executions$skipped", 4)
						.put("statistics$executions$failed", 3)
						.build()
		);
		investigatedTrendMap.put(2L,
				ImmutableMap.<String, Integer>builder().put("statistics$executions$passed", 2)
						.put("statistics$executions$skipped", 3)
						.put("statistics$executions$failed", 6)
						.build()
		);
		investigatedTrendMap.put(3L,
				ImmutableMap.<String, Integer>builder().put("statistics$executions$passed", 5)
						.put("statistics$executions$skipped", 5)
						.put("statistics$executions$failed", 5)
						.build()
		);

		return investigatedTrendMap;

	}

	@Test
	void componentHealthCheck() {

		String sortingColumn = "statistics$defects$no_defect$nd001";

		Filter launchFilter = buildDefaultFilter(1L);
		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, sortingColumn),
				new Sort.Order(Sort.Direction.DESC, CRITERIA_START_TIME)
		);
		Sort sort = Sort.by(orderings);

		Filter itemsFilter = new Filter(1L,
				TestItem.class,
				Lists.newArrayList(FilterCondition.builder()
						.withCondition(Condition.HAS)
						.withNegative(false)
						.withSearchCriteria(CRITERIA_COMPOSITE_ATTRIBUTE)
						.withValue("new:os")
						.build())
		);

		List<ComponentHealthCheckContent> contents = widgetContentRepository.componentHealthCheck(launchFilter,
				sort,
				600,
				itemsFilter,
				"new"
		);

		assertTrue(contents.isEmpty());
	}
}