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
package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.config.TestConfiguration;
import com.epam.ta.reportportal.config.util.SqlRunner;
import com.epam.ta.reportportal.entity.Activity;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.widget.content.*;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant.CRITERIA_TI_STATUS;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.jooq.enums.JTestItemTypeEnum.*;

/**
 * @author Ivan Budayeu
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional("transactionManager")
public class WidgetContentRepositoryTest {

	public static final String FILTER_START_TIME = "start_time";
	public static final String FILTER_CREATION_DATE = "creation_date";

	@Autowired
	private WidgetContentRepository widgetContentRepository;

	@Autowired
	private LaunchRepository launchRepository;

	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException, IOException, SqlToolError {
//		Class.forName("org.hsqldb.jdbc.JDBCDriver");
//		runSqlScript("/test-dropall-script.sql");
//		runSqlScript("/test-create-script.sql");
//		runSqlScript("/test-fill-script.sql");
//		runSqlScript("/statistics-filling-script.sql");
	}

	@AfterClass
	public static void destroy() throws SQLException, IOException, SqlToolError {
//		runSqlScript("/test-dropall-script.sql");
	}

	private static void runSqlScript(String scriptPath) throws SQLException, IOException, SqlToolError {
		try (Connection connection = getConnection()) {
			new SqlRunner().runSqlScript(connection, scriptPath);
		}
	}

	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/reportportal", "rpuser", "rppass");
	}

	@Test
	public void overallStatisticsContent() {
		//StatisticsContent statisticsContent = widgetContentRepository.overallStatisticsContent()
	}

	@Test
	public void mostFailedByExecutionCriteria() {
	}

	@Test
	public void mostFailedByDefectCriteria() {
	}

	@Test
	public void launchStatistics() {

		String sortingColumn = "statistics$defects$no_defect$ND001";

		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildContentFields();

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, sortingColumn));

		Sort sort = Sort.by(orderings);

		List<LaunchesStatisticsContent> launchStatisticsContents = widgetContentRepository.launchStatistics(filter, contentFields, sort, 4);
		Assert.assertNotNull(launchStatisticsContents);
		Assert.assertEquals(4, launchStatisticsContents.size());

		Assert.assertEquals(launchStatisticsContents.get(0).getValues().get(sortingColumn), String.valueOf(6));
		Assert.assertEquals(launchStatisticsContents.get(launchStatisticsContents.size() - 1).getValues().get(sortingColumn),
				String.valueOf(1)
		);
	}

	@Test
	public void investigatedStatistics() {

		Map<Long, Map<String, Integer>> statistics = buildTotalDefectsMap();

		String sortingColumn = "statistics$defects$no_defect$ND001";

		Filter filter = buildDefaultFilter(1L);

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, sortingColumn));

		Sort sort = Sort.by(orderings);

		List<InvestigatedStatisticsResult> investigatedStatisticsResults = widgetContentRepository.investigatedStatistics(filter, sort, 4);
		Assert.assertNotNull(investigatedStatisticsResults);
		Assert.assertEquals(4, investigatedStatisticsResults.size());

		investigatedStatisticsResults.forEach(res -> {
			Map<String, Integer> stats = statistics.get(res.getId());
			int sum = stats.values().stream().mapToInt(Integer::intValue).sum();
			Assert.assertEquals(res.getNotInvestigatedPercentage() + res.getInvestigatedPercentage(), 100.0, 0.01);
			Assert.assertEquals(res.getNotInvestigatedPercentage(),
					BigDecimal.valueOf((double) 100 * stats.get("statistics$defects$to_investigate$total") / sum)
							.setScale(2, RoundingMode.HALF_UP)
							.doubleValue(),
					0.01
			);
		});
	}

	@Test
	public void launchPassPerLaunchStatistics() {
		Filter filter = buildDefaultFilter(1L);

		filter.withCondition(new FilterCondition(Condition.EQUALS, false, "launch name test", NAME));

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, FILTER_START_TIME));

		Sort sort = Sort.by(orderings);

		PassingRateStatisticsResult passStatisticsResult = widgetContentRepository.passingRateStatistics(filter, sort, 1);

		Assert.assertNotNull(passStatisticsResult);
		Assert.assertEquals(3, passStatisticsResult.getPassed());
		Assert.assertEquals(12, passStatisticsResult.getTotal());
	}

	@Test
	public void summaryPassStatistics() {
		Filter filter = buildDefaultFilter(1L);

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, ID));

		Sort sort = Sort.by(orderings);

		PassingRateStatisticsResult passStatisticsResult = widgetContentRepository.passingRateStatistics(filter, sort, 4);

		Assert.assertNotNull(passStatisticsResult);

		Assert.assertEquals(13, passStatisticsResult.getPassed());
		Assert.assertEquals(48, passStatisticsResult.getTotal());
	}

	@Test
	public void casesTrendStatistics() {
		Filter filter = buildDefaultFilter(1L);
		String executionContentField = "statistics$executions$total";

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, ID));

		Sort sort = Sort.by(orderings);

		List<CasesTrendContent> casesTrendContents = widgetContentRepository.casesTrendStatistics(filter, executionContentField, sort, 4);

		Assert.assertNotNull(casesTrendContents);
		Assert.assertEquals(4, casesTrendContents.size());

		int firstElementDelta = casesTrendContents.get(0).getDelta();
		int secondElementDelta = casesTrendContents.get(1).getTotal() - casesTrendContents.get(0).getTotal();
		int thirdElementDelta = casesTrendContents.get(2).getTotal() - casesTrendContents.get(1).getTotal();
		int fourthElementDelta = casesTrendContents.get(3).getTotal() - casesTrendContents.get(2).getTotal();

		Assert.assertEquals(0, firstElementDelta);
		Assert.assertEquals(1, secondElementDelta);
		Assert.assertEquals(4, thirdElementDelta);
		Assert.assertEquals(-3, fourthElementDelta);

	}

	@Test
	public void bugTrendStatistics() {

		Map<Long, Map<String, Integer>> statistics = buildTotalDefectsMap();

		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildTotalDefectsContentFields();

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, ID));

		Sort sort = Sort.by(orderings);

		List<LaunchesStatisticsContent> launchStatisticsContents = widgetContentRepository.bugTrendStatistics(filter,
				contentFields,
				sort,
				4
		);

		Assert.assertNotNull(launchStatisticsContents);
		Assert.assertEquals(4, launchStatisticsContents.size());

		launchStatisticsContents.forEach(res -> {
			Map<String, Integer> stats = statistics.get(res.getId());
			Map<String, String> resStatistics = res.getValues();

			long total = stats.values().stream().mapToInt(Integer::intValue).sum();

			stats.keySet().forEach(key -> Assert.assertEquals((long) stats.get(key), (long) Integer.parseInt(resStatistics.get(key))));

			Assert.assertEquals(Long.parseLong(resStatistics.get(TOTAL)), total);
		});
	}

	@Test
	public void launchesComparisonStatistics() {
		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildTotalContentFields();
		Set<FilterCondition> defaultConditions = Sets.newHashSet(new FilterCondition(Condition.EQUALS, false, "launch name", NAME));
		filter = filter.withConditions(defaultConditions);

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, ID));

		Sort sort = Sort.by(orderings);

		List<LaunchesStatisticsContent> comparisonStatisticsContents = widgetContentRepository.launchesComparisonStatistics(filter,
				contentFields,
				sort,
				2
		);

		Assert.assertNotNull(comparisonStatisticsContents);
		Assert.assertEquals(2, comparisonStatisticsContents.size());

		comparisonStatisticsContents.forEach(res -> {
			Map<String, String> currStatistics = res.getValues();
			Map<Long, Map<String, Integer>> preDefinedStatistics = buildLaunchesComparisonStatistics();

			Map<String, Integer> testStatistics = preDefinedStatistics.get(res.getId());
			int executionsSum = testStatistics.entrySet()
					.stream()
					.filter(entry -> entry.getKey().contains(EXECUTIONS_KEY))
					.mapToInt(Map.Entry::getValue)
					.sum();
			int defectsSum = testStatistics.entrySet()
					.stream()
					.filter(entry -> entry.getKey().contains(DEFECTS_KEY))
					.mapToInt(Map.Entry::getValue)
					.sum();

			currStatistics.keySet()
					.stream()
					.filter(key -> key.contains(EXECUTIONS_KEY))
					.forEach(key -> Assert.assertEquals(Double.parseDouble(currStatistics.get(key)),
							BigDecimal.valueOf((double) 100 * testStatistics.get(key) / executionsSum)
									.setScale(2, RoundingMode.HALF_UP)
									.doubleValue(),
							0.01
					));
			currStatistics.keySet()
					.stream()
					.filter(key -> key.contains(DEFECTS_KEY))
					.forEach(key -> Assert.assertEquals(Double.parseDouble(currStatistics.get(key)),
							BigDecimal.valueOf((double) 100 * testStatistics.get(key) / defectsSum)
									.setScale(2, RoundingMode.HALF_UP)
									.doubleValue(),
							0.01
					));
		});

	}

	@Test
	public void launchesDurationStatistics() {
		Filter filter = buildDefaultFilter(1L);
		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, ID));

		Sort sort = Sort.by(orderings);

		List<LaunchesDurationContent> launchesDurationContents = widgetContentRepository.launchesDurationStatistics(filter, sort, false, 4);

		Assert.assertNotNull(launchesDurationContents);
		Assert.assertEquals(4, launchesDurationContents.size());

		launchesDurationContents.forEach(content -> {
			Timestamp endTime = content.getEndTime();
			Timestamp startTime = content.getStartTime();
			if (startTime.before(endTime)) {
				long duration = content.getDuration();
				Assert.assertTrue(duration > 0 && duration == endTime.getTime() - startTime.getTime());
			}
		});

	}

	@Test
	public void notPassedCasesStatistics() {
		Filter filter = buildDefaultFilter(1L);

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, ID));

		Sort sort = Sort.by(orderings);

		List<NotPassedCasesContent> notPassedCasesContents = widgetContentRepository.notPassedCasesStatistics(filter, sort, 3);

		Assert.assertNotNull(notPassedCasesContents);
		Assert.assertEquals(3, notPassedCasesContents.size());

		notPassedCasesContents.forEach(content -> {
			Map<String, String> currentStatistics = content.getValues();
			Map<Long, Map<String, Integer>> preDefinedStatistics = buildNotPassedCasesStatistics();

			Map<String, Integer> testStatistics = preDefinedStatistics.get(content.getId());
			int executionsSum = testStatistics.entrySet().stream().mapToInt(Map.Entry::getValue).sum();

			Assert.assertEquals(Double.parseDouble(currentStatistics.get(NOT_PASSED_STATISTICS_KEY)),
					BigDecimal.valueOf((double) 100 * (testStatistics.get("statistics$executions$skipped") + testStatistics.get(
							"statistics$executions$failed")) / executionsSum).setScale(2, RoundingMode.HALF_UP).doubleValue(),
					0.01
			);

		});

	}

	@Test
	public void launchesTableStatistics() {
		Filter filter = buildDefaultFilter(1L);

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, ID));

		Sort sort = Sort.by(orderings);

		List<String> contentFields = buildLaunchesTableContentFields();

		List<LaunchesStatisticsContent> launchStatisticsContents = widgetContentRepository.launchesTableStatistics(filter,
				contentFields,
				sort,
				3
		);

		Assert.assertNotNull(launchStatisticsContents);
		Assert.assertEquals(3, launchStatisticsContents.size());

		List<String> tableContentFields = contentFields.stream().filter(cf -> !cf.startsWith("statistics$")).collect(Collectors.toList());

		launchStatisticsContents.forEach(content -> {

			Map<String, String> values = content.getValues();
			tableContentFields.forEach(tcf -> {
				Assert.assertTrue(values.containsKey(tcf));
				Assert.assertNotNull(values.get(tcf));
			});
		});

	}

	@Test
	public void activityStatistics() {

		Filter filter = buildDefaultActivityFilter(1L);

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, FILTER_CREATION_DATE));

		Sort sort = Sort.by(orderings);

		List<String> contentFields = buildActivityContentFields();

		filter.withCondition(new FilterCondition(Condition.EQUALS, false, "default", "login"))
				.withCondition(new FilterCondition(Condition.IN, false, String.join(",", contentFields), "action"));

		List<ActivityContent> activityContentList = widgetContentRepository.activityStatistics(filter, sort, 4);

		Assert.assertNotNull(activityContentList);
		Assert.assertEquals(4, activityContentList.size());
	}

	@Test
	public void uniqueBugStatistics() {

		Filter filter = buildDefaultFilter(1L);

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, ID));

		Sort sort = Sort.by(orderings);

		Map<String, List<UniqueBugContent>> uniqueBugStatistics = widgetContentRepository.uniqueBugStatistics(filter, sort, false, 2);

		Assert.assertNotNull(uniqueBugStatistics);
		Assert.assertEquals(3, uniqueBugStatistics.size());

		Assert.assertTrue(uniqueBugStatistics.containsKey("EPMRPP-123"));
		Assert.assertTrue(uniqueBugStatistics.containsKey("EPMRPP-322"));
		Assert.assertTrue(uniqueBugStatistics.containsKey("QWERTY-100"));

	}

	@Test
	public void flakyCasesStatistics() {

		Filter filter = buildDefaultFilter(1L);

		List<FlakyCasesTableContent> flakyCasesStatistics = widgetContentRepository.flakyCasesStatistics(filter, 5);

		Assert.assertNotNull(flakyCasesStatistics);

		flakyCasesStatistics.forEach(content -> {
			long counter = 0;
			List<String> statuses = content.getStatuses();

			for (int i = 0; i < statuses.size() - 1; i++) {
				if (!statuses.get(i).equalsIgnoreCase(statuses.get(i + 1))) {
					counter++;
				}
			}

			Assert.assertEquals(counter, (long) content.getFlakyCount());
			Assert.assertTrue(content.getFlakyCount() < content.getTotal());

		});

		Assert.assertEquals((long) flakyCasesStatistics.get(0).getFlakyCount(),
				flakyCasesStatistics.stream().mapToLong(FlakyCasesTableContent::getFlakyCount).max().orElse(Long.MAX_VALUE)
		);

		Assert.assertEquals((long) flakyCasesStatistics.get(flakyCasesStatistics.size() - 1).getFlakyCount(),
				flakyCasesStatistics.stream().mapToLong(FlakyCasesTableContent::getFlakyCount).min().orElse(Long.MIN_VALUE)
		);
	}

	//	@Test
	//	public void cumulativeTrendChart() {
	//		Filter filter = buildDefaultFilter(1L);
	//		List<String> contentFields = buildContentFields();
	//
	//		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, "statistics$defects$no_defect$ND001"));
	//
	//		Sort sort = Sort.by(orderings);
	//		Map<String, List<LaunchesStatisticsContent>> launchesStatisticsContents = widgetContentRepository.cumulativeTrendStatistics(filter,
	//				contentFields,
	//				sort,
	//				"build",
	//				4
	//		);
	//
	//		Assert.assertNotNull(launchesStatisticsContents);
	//	}

	@Test
	public void productStatusFilterGroupedWidget() {

		List<Sort.Order> firstOrdering = Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, "statistics$defects$product_bug$PB001"));
		List<Sort.Order> secondOrdering = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "statistics$defects$automation_bug$AB001"));

		Sort firstSort = Sort.by(firstOrdering);
		Sort secondSort = Sort.by(secondOrdering);

		Map<Filter, Sort> filterSortMapping = Maps.newLinkedHashMap();
		filterSortMapping.put(buildDefaultFilter(1L), firstSort);
		filterSortMapping.put(buildDefaultTestFilter(1L), secondSort);

		List<String> tagContentFields = buildProductStatusContentFields().stream()
				.filter(s -> s.startsWith("tag"))
				.map(tag -> tag.split("\\$")[1])
				.collect(Collectors.toList());
		List<String> contentFields = buildProductStatusContentFields().stream()
				.filter(s -> !s.startsWith("tag"))
				.collect(Collectors.toList());

		Map<String, List<LaunchesStatisticsContent>> result = widgetContentRepository.productStatusGroupedByFilterStatistics(filterSortMapping,
				contentFields,
				tagContentFields,
				false,
				10
		);

		Assert.assertNotNull(result);
	}

	@Test
	public void productStatusLaunchGroupedWidget() {
		Filter filter = buildDefaultTestFilter(1L);

		List<Sort.Order> orderings = Lists.newArrayList(new Sort.Order(Sort.Direction.DESC, "statistics$defects$product_bug$PB001"));

		Sort sort = Sort.by(orderings);

		List<String> tagContentFields = buildProductStatusContentFields().stream()
				.filter(s -> s.startsWith("tag"))
				.map(tag -> tag.split("\\$")[1])
				.collect(Collectors.toList());
		List<String> contentFields = buildProductStatusContentFields().stream()
				.filter(s -> !s.startsWith("tag"))
				.collect(Collectors.toList());

		List<LaunchesStatisticsContent> result = widgetContentRepository.productStatusGroupedByLaunchesStatistics(filter,
				contentFields,
				tagContentFields,
				sort,
				false,
				10
		);

		Assert.assertNotNull(result);
	}

	@Test
	public void mostTimeConsumingTestCases() {
		Filter filter = buildMostTimeConsumingFilter(1L);
		filter = updateFilter(filter, "launch name", true);
		List<MostTimeConsumingTestCasesContent> mostTimeConsumingTestCasesContents = widgetContentRepository.mostTimeConsumingTestCasesStatistics(
				filter);

		Assert.assertNotNull(mostTimeConsumingTestCasesContents);
	}

	private Filter buildDefaultFilter(Long projectId) {
		Set<FilterCondition> conditionSet = Sets.newHashSet(new FilterCondition(Condition.EQUALS,
						false,
						String.valueOf(projectId),
						"project_id"
				),
				new FilterCondition(Condition.NOT_EQUALS, false, StatusEnum.IN_PROGRESS.name(), "status"),
				new FilterCondition(Condition.EQUALS, false, Mode.DEFAULT.toString(), "mode")
		);
		return new Filter(2L, Launch.class, conditionSet);
	}

	private Filter buildDefaultTestFilter(Long projectId) {
		Set<FilterCondition> conditionSet = Sets.newHashSet(new FilterCondition(Condition.EQUALS,
						false,
						String.valueOf(projectId),
						"project_id"
				),
				new FilterCondition(Condition.NOT_EQUALS, false, StatusEnum.IN_PROGRESS.name(), "status"),
				new FilterCondition(Condition.EQUALS, false, Mode.DEFAULT.toString(), "mode"),
				new FilterCondition(Condition.LOWER_THAN_OR_EQUALS, false, "12", "statistics$executions$total")
		);
		return new Filter(3L, Launch.class, conditionSet);
	}

	private Filter buildDefaultActivityFilter(Long projectId) {
		Set<FilterCondition> conditionSet = Sets.newHashSet(new FilterCondition(Condition.EQUALS,
				false,
				String.valueOf(projectId),
				"project_id"
		));
		return new Filter(1L, Activity.class, conditionSet);
	}

	private Filter buildMostTimeConsumingFilter(Long projectId) {
		Set<FilterCondition> conditionSet = Sets.newHashSet(new FilterCondition(Condition.EQUALS,
				false,
				String.valueOf(projectId),
				"project_id"
		), new FilterCondition(Condition.EQUALS_ANY,
				false,
				String.join(",", JStatusEnum.PASSED.getLiteral(), JStatusEnum.FAILED.getLiteral()),
				CRITERIA_TI_STATUS
		));

		return new Filter(1L, TestItem.class, conditionSet);
	}

	private Filter updateFilter(Filter filter, String launchName, boolean includeMethodsFlag) {
		filter = updateFilterWithLaunchName(filter, launchName);
		filter = updateFilterWithTestItemTypes(filter, includeMethodsFlag);
		return filter;
	}

	private Filter updateFilterWithLaunchName(Filter filter, String launchName) {
		return filter.withCondition(new FilterCondition(Condition.EQUALS,
				false,
				String.valueOf(launchRepository.findLatestByNameAndFilter(launchName, filter)
						.orElseThrow(() -> new ReportPortalException(ErrorType.LAUNCH_NOT_FOUND, "No launch with name: " + launchName))
						.getId()),
				LAUNCH_ID
		));
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
		return Lists.newArrayList("statistics$defects$no_defect$ND001",
				"statistics$defects$product_bug$PB001",
				"statistics$defects$automation_bug$AB001",
				"statistics$defects$system_issue$SI001",
				"statistics$defects$to_investigate$TI001",
				"end_time",
				"description",
				"last_modified",
				"statistics$executions$total",
				"statistics$executions$failed",
				"statistics$executions$passed",
				"statistics$executions$skipped"
		);
	}

	private List<String> buildContentFields() {

		return Lists.newArrayList("statistics$defects$no_defect$ND001",
				"statistics$defects$product_bug$PB001",
				"statistics$defects$automation_bug$AB001",
				"statistics$defects$system_issue$SI001",
				"statistics$defects$to_investigate$TI001",
				"statistics$executions$failed",
				"statistics$executions$skipped",
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
				"statistics$executions$passed"
		);
	}

	private List<String> buildProductStatusContentFields() {
		return Lists.newArrayList("statistics$defects$no_defect$ND001",
				"statistics$defects$product_bug$PB001",
				"statistics$defects$automation_bug$AB001",
				"statistics$defects$system_issue$SI001",
				"statistics$defects$to_investigate$TI001",
				"statistics$executions$failed",
				"statistics$executions$skipped",
				"statistics$executions$total",
				"statistics$defects$no_defect$total",
				"statistics$defects$product_bug$total",
				"statistics$defects$automation_bug$total",
				"statistics$defects$system_issue$total",
				"statistics$defects$to_investigate$total",
				"tag$build",
				"tag$check"

		);
	}

	private List<String> buildActivityContentFields() {
		return Lists.newArrayList("CREATE_LAUNCH", "CREATE_ITEM");
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
		Map<Long, Map<String, Integer>> investigatedTrendMap = Maps.newLinkedHashMap();

		investigatedTrendMap.put(1L,
				ImmutableMap.<String, Integer>builder().put("statistics$defects$to_investigate$total", 2)
						.put("statistics$defects$system_issue$total", 8)
						.put("statistics$defects$automation_bug$total", 7)
						.put("statistics$defects$product_bug$total", 13)
						.put("statistics$defects$no_defect$total", 2)
						.put("statistics$executions$passed", 3)
						.put("statistics$executions$skipped", 4)
						.put("statistics$executions$failed", 3)
						.build()
		);
		investigatedTrendMap.put(2L,
				ImmutableMap.<String, Integer>builder().put("statistics$defects$to_investigate$total", 3)
						.put("statistics$defects$system_issue$total", 3)
						.put("statistics$defects$automation_bug$total", 1)
						.put("statistics$defects$product_bug$total", 1)
						.put("statistics$defects$no_defect$total", 2)
						.put("statistics$executions$passed", 2)
						.put("statistics$executions$skipped", 3)
						.put("statistics$executions$failed", 6)
						.build()
		);

		return investigatedTrendMap;

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

}