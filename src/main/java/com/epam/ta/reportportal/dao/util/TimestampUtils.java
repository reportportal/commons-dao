package com.epam.ta.reportportal.dao.util;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public final class TimestampUtils {

	private TimestampUtils() {
		//static only
	}

	public static Timestamp getTimestampBackFromNow(Duration period) {
		return Timestamp.from(Instant.now().minusSeconds(period.getSeconds()));
	}
}
