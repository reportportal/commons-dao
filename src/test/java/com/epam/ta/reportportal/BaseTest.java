package com.epam.ta.reportportal;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.InputSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Pavel Bortnik
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Ignore
public class BaseTest {

	@Autowired
	private DataSource dataSource;

	@Autowired
	@Value("classpath:dataset.xml")
	private Resource dataset;

	@Before
	public void contextLoads() throws SQLException, DatabaseUnitException, IOException {
		DatabaseConnection connection = new DatabaseConnection(dataSource.getConnection());
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory() {
			@Override
			public boolean isEnumType(String sqlTypeName) {
				return sqlTypeName.endsWith("enum");
			}

			@Override
			public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
				if (isEnumType(sqlTypeName)) {
					sqlType = Types.OTHER;
				}
				return super.createDataType(sqlType, sqlTypeName);
			}
		});

		FlatXmlDataSet dataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(dataset.getInputStream())));
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}

}
