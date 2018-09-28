package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.config.TestConfiguration;
import com.epam.ta.reportportal.config.util.SqlRunner;
import com.epam.ta.reportportal.entity.item.TestItem;
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

/**
 * @author Ivan Budaev
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional("transactionManager")
public class TestItemRepositoryCustomImplTest {

	@Autowired
	private TestItemRepository testItemRepository;

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
	public void selectAllDescendantsWithChildren() {

		List<TestItem> testItems = testItemRepository.selectAllDescendantsWithChildren(33L);

		Assert.assertNotNull(testItems);
	}
}