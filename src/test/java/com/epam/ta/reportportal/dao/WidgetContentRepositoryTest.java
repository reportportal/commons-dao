package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.config.TestConfiguration;
import com.epam.ta.reportportal.config.util.SqlRunner;
import com.epam.ta.reportportal.entity.Activity;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.widget.content.*;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.NAME;

/**
 * @author Ivan Budayeu
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional("transactionManager")
public class WidgetContentRepositoryTest {

	@Autowired
	private WidgetContentRepository widgetContentRepository;

	@Autowired
	private LaunchRepository launchRepository;

	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException, IOException, SqlToolError {
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		runSqlScript("/test-dropall-script.sql");
		runSqlScript("/test-create-script.sql");
		runSqlScript("/test-fill-script.sql");
	}

	@AfterClass
	public static void destroy() throws SQLException, IOException, SqlToolError {
		runSqlScript("/test-dropall-script.sql");
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
		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildContentFields();

		List<LaunchesStatisticsContent> launchStatisticsContents = widgetContentRepository.launchStatistics(filter, contentFields, 3);
		Assert.assertNotNull(launchStatisticsContents);
		Assert.assertEquals(3, launchStatisticsContents.size());
	}

	@Test
	public void investigatedStatistics() {
		Filter filter = buildDefaultFilter(1L);

		List<InvestigatedStatisticsResult> investigatedStatisticsResults = widgetContentRepository.investigatedStatistics(filter, 2);
		Assert.assertNotNull(investigatedStatisticsResults);
		Assert.assertEquals(2, investigatedStatisticsResults.size());
	}

	@Test
	public void launchPassPerLaunchStatistics() {
		Filter filter = buildDefaultFilter(1L);

		filter.withCondition(new FilterCondition(Condition.EQUALS, false, "launch name test", NAME));

		PassingRateStatisticsResult passStatisticsResult = widgetContentRepository.passingRateStatistics(filter, 12);

		Assert.assertNotNull(passStatisticsResult);
	}

	@Test
	public void summaryPassStatistics() {
		Filter filter = buildDefaultFilter(1L);

		PassingRateStatisticsResult passStatisticsResult = widgetContentRepository.passingRateStatistics(filter, 10);

		Assert.assertNotNull(passStatisticsResult);
	}

	@Test
	public void casesTrendStatistics() {
		Filter filter = buildDefaultFilter(1L);
		String executionContentField = "statistics$executions$total";

		List<CasesTrendContent> casesTrendContents = widgetContentRepository.casesTrendStatistics(filter, executionContentField, 2);

		Assert.assertNotNull(casesTrendContents);
		Assert.assertEquals(2, casesTrendContents.size());

	}

	@Test
	public void bugTrendStatistics() {
		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildTotalDefectsContentFields();

		List<LaunchesStatisticsContent> launchStatisticsContents = widgetContentRepository.bugTrendStatistics(filter, contentFields, 3);

		Assert.assertNotNull(launchStatisticsContents);
		Assert.assertEquals(3, launchStatisticsContents.size());

	}

	@Test
	public void launchesComparisonStatistics() {
		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildTotalContentFields();
		Set<FilterCondition> defaultConditions = Sets.newHashSet(new FilterCondition(Condition.EQUALS, false, "launch name", NAME));
		filter = filter.withConditions(defaultConditions);
		List<LaunchesStatisticsContent> comparisonStatisticsContents = widgetContentRepository.launchesComparisonStatistics(filter,
				contentFields,
				2
		);

		Assert.assertNotNull(comparisonStatisticsContents);
		Assert.assertEquals(2, comparisonStatisticsContents.size());

	}

	@Test
	public void launchesDurationStatistics() {
		Filter filter = buildDefaultFilter(1L);

		List<LaunchesDurationContent> launchesDurationContents = widgetContentRepository.launchesDurationStatistics(filter, 4);

		Assert.assertNotNull(launchesDurationContents);
		Assert.assertEquals(4, launchesDurationContents.size());

	}

	@Test
	public void notPassedCasesStatistics() {
		Filter filter = buildDefaultFilter(1L);

		List<NotPassedCasesContent> notPassedCasesContents = widgetContentRepository.notPassedCasesStatistics(filter, 3);

		Assert.assertNotNull(notPassedCasesContents);
		Assert.assertEquals(3, notPassedCasesContents.size());

	}

	@Test
	public void launchesTableStatistics() {
		Filter filter = buildDefaultFilter(1L);
		List<String> contentFields = buildLaunchesTableContentFields();

		List<LaunchesStatisticsContent> launchStatisticsContents = widgetContentRepository.launchesTableStatistics(filter,
				contentFields,
				3
		);

		Assert.assertNotNull(launchStatisticsContents);
		Assert.assertEquals(3, launchStatisticsContents.size());

	}

	@Test
	public void activityStatistics() {

		Filter filter = buildDefaultActivityFilter(1L);

		List<String> contentFields = buildActivityContentFields();

		List<ActivityContent> activityContentList = widgetContentRepository.activityStatistics(filter, "default", contentFields, 4);

		Assert.assertNotNull(activityContentList);
		Assert.assertEquals(4, activityContentList.size());
	}

	@Test
	public void uniqueBugStatistics() {
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
		return new Filter(Launch.class, conditionSet);
	}

	private Filter buildDefaultActivityFilter(Long projectId) {
		Set<FilterCondition> conditionSet = Sets.newHashSet(new FilterCondition(Condition.EQUALS,
				false,
				String.valueOf(projectId),
				"project_id"
		));
		return new Filter(Activity.class, conditionSet);
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
				"statistics$executions$total"
		);
	}

	private List<String> buildActivityContentFields() {
		return Lists.newArrayList("CREATE_LAUNCH", "CREATE_ITEM");
	}

}