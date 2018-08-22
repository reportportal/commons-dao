package com.epam.ta.reportportal.config.util;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

public class SqlRunner {
    public void runSqlScript(Connection connection, String scriptPath) throws IOException, SqlToolError, SQLException {
        try (InputStream inputStream = getClass().getResourceAsStream(scriptPath)) {
            SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", false, new File("."));
            sqlFile.setConnection(connection);
            sqlFile.execute();
        }
    }
}
