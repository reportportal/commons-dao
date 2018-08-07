package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ivan Budayeu
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class WidgetContentRepositoryTest {

//	@Before
//	public void init() throws SQLException, ClassNotFoundException, IOException, SqlToolError {
//		initMocks(this);
//		Class.forName("org.hsqldb.jdbc.JDBCDriver");
//		runSqlScript("/test-create-script.sql");
//	}
//
//
//	@After
//	public void destroy() throws SQLException, IOException, SqlToolError {
//		runSqlScript("/test-dropall-script.sql");
//	}
//
//	private void runSqlScript(String scriptPath) throws SQLException, IOException, SqlToolError {
//		try (Connection connection = getConnection()) {
//			new SqlRunner().runSqlScript(connection, scriptPath);
//		}
//	}
//
//	private static Connection getConnection() throws SQLException {
//		return DriverManager.getConnection("jdbc:hsqldb:mem:reportportal", "rpuser", "rppass");
//	}

	@Test
	public void overallStatisticsContent() {
	}

	@Test
	public void mostFailedByExecutionCriteria() {
	}

	@Test
	public void mostFailedByDefectCriteria() {
	}

	@Test
	public void launchStatistics() {
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
}