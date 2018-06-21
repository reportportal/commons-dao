package com.epam.ta.reportportal.database.entity;

import com.epam.ta.reportportal.database.dao.aggregation.GroupingOperation;
import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Pavel Bortnik
 */
public class GroupingPeriodEnumTest {

	@Test
	public void groupingEnums() {
		ImmutableMap<String, String> expected = ImmutableMap.<String, String>builder().put("by_day", "$dayOfYear")
				.put("by_week", "$week")
				.put("by_month", "$month")
				.build();
		Map<String, String> actual = Arrays.stream(GroupingOperation.GroupingPeriod.values())
				.collect(Collectors.toMap(GroupingOperation.GroupingPeriod::getValue, GroupingOperation.GroupingPeriod::getOperation));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void positiveGet() {
		Optional<GroupingOperation.GroupingPeriod> period = GroupingOperation.GroupingPeriod.getByValue("by_day");
		Assert.assertTrue(period.isPresent());
		Assert.assertEquals(GroupingOperation.GroupingPeriod.BY_DAY, period.get());
	}

	@Test
	public void negativeGet() {
		Optional<GroupingOperation.GroupingPeriod> notExists = GroupingOperation.GroupingPeriod.getByValue("not_exists");
		Assert.assertFalse(notExists.isPresent());
	}

}
