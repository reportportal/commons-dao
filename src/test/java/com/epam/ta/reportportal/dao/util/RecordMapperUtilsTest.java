package com.epam.ta.reportportal.dao.util;

import org.junit.Assert;
import org.junit.Test;

import static com.epam.ta.reportportal.jooq.tables.JUsers.USERS;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class RecordMapperUtilsTest {

	@Test
	public void fieldExcludingPredicate() {

		Assert.assertFalse(RecordMapperUtils.fieldExcludingPredicate(USERS.LOGIN, USERS.EMAIL).test(USERS.LOGIN));
		Assert.assertTrue(RecordMapperUtils.fieldExcludingPredicate(USERS.LOGIN, USERS.EMAIL).test(USERS.FULL_NAME));
	}
}