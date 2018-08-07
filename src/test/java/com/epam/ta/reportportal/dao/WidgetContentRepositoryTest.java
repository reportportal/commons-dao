package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.config.TestConfiguration;
import com.epam.ta.reportportal.config.util.SqlRunner;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.widget.content.LaunchStatisticsContent;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.AfterClass;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException, IOException, SqlToolError {
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		runSqlScript("/test-create-script.sql");
		runSqlScript("/test-fill-script.sql");
	}

	@AfterClass
	public static void destroy() throws SQLException, IOException, SqlToolError {
		//runSqlScript("/test-dropall-script.sql");
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

		List<LaunchStatisticsContent> launchStatisticsContents = widgetContentRepository.launchStatistics(filter, contentFields, 1000);
		System.out.println(launchStatisticsContents);
	}

	@Test
	public void investigatedStatistics() {
	}

	@Test
	public void launchPassPerLaunchStatistics() {
	}

	@Test
	public void summaryPassStatistics() {
	}

	@Test
	public void casesTrendStatistics() {
	}

	@Test
	public void bugTrendStatistics() {
	}

	@Test
	public void launchesComparisonStatistics() {
	}

	@Test
	public void launchesDurationStatistics() {
	}

	@Test
	public void notPassedCasesStatistics() {
	}

	@Test
	public void launchesTableStatistics() {
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
		contentFields.put(
				DEFECTS_KEY,
				Arrays.stream(new String[] { "ND001", "PB001", "AB001", "AB002", "SI001", "TI001" }).collect(Collectors.toList())
		);
		contentFields.put(EXECUTIONS_KEY, Arrays.stream(new String[] { "FAILED", "SKIPPED", "PASSED" }).collect(Collectors.toList()));

		return contentFields;
	}
}