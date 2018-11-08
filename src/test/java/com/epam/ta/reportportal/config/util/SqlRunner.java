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

package com.epam.ta.reportportal.config.util;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public final class SqlRunner {

	private SqlRunner() {
		//static only
	}

	public static void runSqlScripts(String... scriptPaths) throws SQLException {
		try (Connection connection = getConnection()) {
			runSqlScript(connection, scriptPaths);
		}
	}

	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/reportportal", "rpuser", "rppass");
	}

	private static void runSqlScript(Connection connection, String... scriptPaths) {
		Arrays.stream(scriptPaths).forEachOrdered(scriptPath -> {
			try (InputStream inputStream = SqlRunner.class.getResourceAsStream(scriptPath)) {
				SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", false, new File("."));
				sqlFile.setConnection(connection);
				sqlFile.execute();
			} catch (IOException | SqlToolError | SQLException e) {
				e.printStackTrace();
			}
		});

	}
}
