package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.config.TestConfiguration;
import com.epam.ta.reportportal.config.util.SqlRunner;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.widget.content.*;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.epam.ta.reportportal.ws.model.launch.Mode;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ivan Budayeu
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional("transactionManager")
public class WidgetContentRepositoryTest {

	public static final String EXECUTIONS_KEY = "executions";
	public static final String DEFECTS_KEY = "defects";

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
		Map<String, List<String>> contentFields = buildDefaultContentFields();

		List<LaunchStatisticsContent> launchStatisticsContents = widgetContentRepository.launchStatistics(filter, contentFields, 10);
		Assert.assertNotNull(launchStatisticsContents);
		Assert.assertEquals(launchStatisticsContents.size(), 10);
	}

	@Test
	public void investigatedStatistics() {
		Filter filter = buildDefaultFilter(1L);
		Map<String, List<String>> contentFields = buildDefaultContentFields();

		List<InvestigatedStatisticsResult> investigatedStatisticsResults = widgetContentRepository.investigatedStatistics(filter,
				contentFields,
				11
		);
		Assert.assertNotNull(investigatedStatisticsResults);
		Assert.assertEquals(investigatedStatisticsResults.size(), 11);
	}

	@Test
	public void launchPassPerLaunchStatistics() {
		Filter filter = buildDefaultFilter(1L);
		Map<String, List<String>> contentFields = buildDefaultContentFields();

		Launch launch = new Launch();
		launch.setId(1L);
		launch.setNumber(1L);
		launch.setProjectId(1L);
		launch.setName("launch name");

		Launch launchName = launchRepository.findLatestByNameAndFilter("launch name", filter)
				.orElseThrow(() -> new ReportPortalException(ErrorType.INCORRECT_AUTHENTICATION_TYPE, "asdasd"));

		System.out.println(launch.getId().equals(launchName.getId()));
		System.out.println(launch.getNumber().equals(launchName.getNumber()));
		System.out.println(launch.getName().equals(launchName.getName()));
		System.out.println(launch.getProjectId().equals(launchName.getProjectId()));

		PassStatisticsResult passStatisticsResult = widgetContentRepository.launchPassPerLaunchStatistics(filter,
				contentFields,
				launchName,
				12
		);

		Assert.assertNotNull(passStatisticsResult);
	}

	@Test
	public void summaryPassStatistics() {
		Filter filter = buildDefaultFilter(1L);
		Map<String, List<String>> contentFields = buildDefaultContentFields();

		PassStatisticsResult passStatisticsResult = widgetContentRepository.summaryPassStatistics(filter, contentFields, 10);

		Assert.assertNotNull(passStatisticsResult);
	}

	@Test
	public void casesTrendStatistics() {
		Filter filter = buildDefaultFilter(1L);
		Map<String, List<String>> contentFields = buildDefaultContentFields();

		List<CasesTrendContent> casesTrendContents = widgetContentRepository.casesTrendStatistics(filter, contentFields, 10);

		Assert.assertNotNull(casesTrendContents);
		Assert.assertEquals(casesTrendContents.size(), 10);

	}

	@Test
	public void bugTrendStatistics() {
		Filter filter = buildDefaultFilter(1L);
		Map<String, List<String>> contentFields = buildDefaultContentFields();

		List<LaunchStatisticsContent> launchStatisticsContents = widgetContentRepository.bugTrendStatistics(filter, contentFields, 12);

		Assert.assertNotNull(launchStatisticsContents);
		Assert.assertEquals(launchStatisticsContents.size(), 12);

	}

	@Test
	public void launchesComparisonStatistics() {
		Filter filter = buildDefaultFilter(1L);
		Map<String, List<String>> contentFields = buildDefaultContentFields();

		List<ComparisonStatisticsContent> comparisonStatisticsContents = widgetContentRepository.launchesComparisonStatistics(filter,
				contentFields,
				100,
				1L,
				2L
		);

		Assert.assertNotNull(comparisonStatisticsContents);
		Assert.assertEquals(comparisonStatisticsContents.size(), 2);

	}

	@Test
	public void launchesDurationStatistics() {
		Filter filter = buildDefaultFilter(1L);
		Map<String, List<String>> contentFields = buildDefaultContentFields();

		List<LaunchesDurationContent> launchesDurationContents = widgetContentRepository.launchesDurationStatistics(filter,
				contentFields,
				10
		);

		Assert.assertNotNull(launchesDurationContents);
		Assert.assertEquals(launchesDurationContents.size(), 10);

	}

	@Test
	public void notPassedCasesStatistics() {
		Filter filter = buildDefaultFilter(1L);
		Map<String, List<String>> contentFields = buildDefaultContentFields();

		List<NotPassedCasesContent> notPassedCasesContents = widgetContentRepository.notPassedCasesStatistics(filter, contentFields, 10);

		Assert.assertNotNull(notPassedCasesContents);
		Assert.assertEquals(notPassedCasesContents.size(), 10);

	}

	@Test
	public void launchesTableStatistics() {
		Filter filter = buildDefaultFilter(1L);
		Map<String, List<String>> contentFields = buildDefaultContentFields();

		List<LaunchStatisticsContent> launchStatisticsContents = widgetContentRepository.launchesTableStatistics(filter, contentFields, 10);

		Assert.assertNotNull(launchStatisticsContents);
		Assert.assertEquals(launchStatisticsContents.size(), 10);

	}

	@Test
	public void activityStatistics() {
	}

	@Test
	public void uniqueBugStatistics() {
	}

	private Filter buildDefaultFilter(Long projectId) {
		return Filter.builder()
				.withTarget(Launch.class)
				.withCondition(new FilterCondition(Condition.EQUALS, false, String.valueOf(projectId), "project_id"))
				.withCondition(new FilterCondition(Condition.NOT_EQUALS, false, StatusEnum.IN_PROGRESS.name(), "status"))
				.withCondition(new FilterCondition(Condition.EQUALS, false, Mode.DEFAULT.toString(), "mode"))
				.build();
	}

	private Map<String, List<String>> buildDefaultContentFields() {
		Map<String, List<String>> contentFields = new HashMap<>();
		contentFields.put(DEFECTS_KEY,
				Arrays.stream(new String[] { "ND001", "PB001", "AB001", "AB002", "SI001", "TI001" }).collect(Collectors.toList())
		);
		contentFields.put(EXECUTIONS_KEY, Arrays.stream(new String[] { "FAILED", "SKIPPED", "PASSED" }).collect(Collectors.toList()));

		return contentFields;
	}
}