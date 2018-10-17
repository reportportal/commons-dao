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

import com.epam.ta.reportportal.config.TestConfiguration;
import com.epam.ta.reportportal.config.util.SqlRunner;
import com.epam.ta.reportportal.entity.item.TestItem;
import org.assertj.core.util.Lists;
import org.hsqldb.cmdline.SqlToolError;
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
public class TestItemRepositoryTest {

	@Autowired
	private TestItemRepository testItemRepository;

	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException, IOException, SqlToolError {
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		//		runSqlScript("/test-dropall-script.sql");
		//		runSqlScript("/test-create-script.sql");
		//		runSqlScript("/test-fill-script.sql");
	}

	//	@AfterClass
	//	public static void destroy() throws SQLException, IOException, SqlToolError {
	//		runSqlScript("/test-dropall-script.sql");
	//	}

	private static void runSqlScript(String scriptPath) throws SQLException, IOException, SqlToolError {
		try (Connection connection = getConnection()) {
			new SqlRunner().runSqlScript(connection, scriptPath);
		}
	}

	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/reportportal", "rpuser", "rppass");
	}

	@Test
	public void testLoadItemsHistory() {
		List<TestItem> testItems = testItemRepository.loadItemsHistory(Lists.newArrayList("auto:3d3ef012c6687480d6fb9b4a3fa9471d"),
				Lists.newArrayList(9L, 10L, 11L, 12L, 13L)
		);
		System.out.println();
	}

	@Test
	public void findTestItemsByLaunchId() {

		List<TestItem> testItemList = testItemRepository.findTestItemsByLaunchId(1L);

		Assert.assertNotNull(testItemList);
	}
}