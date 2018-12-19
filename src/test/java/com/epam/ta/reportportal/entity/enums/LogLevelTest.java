package com.epam.ta.reportportal.entity.enums;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class LogLevelTest {

	public static final int FATAL_INT = 55000;
	public static final int ERROR_INT = 45000;
	public static final int WARN_INT = 35000;
	public static final int INFO_INT = 25000;
	public static final int DEBUG_INT = 15000;
	public static final int TRACE_INT = 7500;

	@Test
	public void testFatal() {

		Assert.assertEquals(FATAL_INT, LogLevel.toCustomLogLevel(String.valueOf(FATAL_INT)));
		Assert.assertEquals(LogLevel.FATAL_INT, LogLevel.toCustomLogLevel("FATAL"));
		Assert.assertEquals(LogLevel.FATAL, LogLevel.toLevel(FATAL_INT));
	}

	@Test
	public void testError() {

		Assert.assertEquals(ERROR_INT, LogLevel.toCustomLogLevel(String.valueOf(ERROR_INT)));
		Assert.assertEquals(LogLevel.ERROR_INT, LogLevel.toCustomLogLevel("ERROR"));
		Assert.assertEquals(LogLevel.ERROR, LogLevel.toLevel(ERROR_INT));
	}

	@Test
	public void testWarn() {

		Assert.assertEquals(WARN_INT, LogLevel.toCustomLogLevel(String.valueOf(WARN_INT)));
		Assert.assertEquals(LogLevel.WARN_INT, LogLevel.toCustomLogLevel("WARN"));
		Assert.assertEquals(LogLevel.WARN, LogLevel.toLevel(WARN_INT));
	}

	@Test
	public void testInfo() {

		Assert.assertEquals(INFO_INT, LogLevel.toCustomLogLevel(String.valueOf(INFO_INT)));
		Assert.assertEquals(LogLevel.INFO_INT, LogLevel.toCustomLogLevel("INFO"));
		Assert.assertEquals(LogLevel.INFO, LogLevel.toLevel(INFO_INT));
	}

	@Test
	public void testDebug() {

		Assert.assertEquals(DEBUG_INT, LogLevel.toCustomLogLevel(String.valueOf(DEBUG_INT)));
		Assert.assertEquals(LogLevel.DEBUG_INT, LogLevel.toCustomLogLevel("DEBUG"));
		Assert.assertEquals(LogLevel.DEBUG, LogLevel.toLevel(DEBUG_INT));
	}

	@Test
	public void testTrace() {

		Assert.assertEquals(TRACE_INT, LogLevel.toCustomLogLevel(String.valueOf(TRACE_INT)));
		Assert.assertEquals(LogLevel.TRACE_INT, LogLevel.toCustomLogLevel("TRACE"));
		Assert.assertEquals(LogLevel.TRACE, LogLevel.toLevel(TRACE_INT));
	}
}