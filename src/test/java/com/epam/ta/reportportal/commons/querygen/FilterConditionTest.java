package com.epam.ta.reportportal.commons.querygen;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
class FilterConditionTest {

	@Test
	public void testInBuilder() {
		FilterCondition condition = FilterCondition.builder().in("criteria", Lists.newArrayList(1, 2, 3)).build();
		Assert.assertEquals("criteria", condition.getSearchCriteria());
		Assert.assertEquals(Condition.IN, condition.getCondition());
		Assert.assertEquals("1,2,3", condition.getValue());
	}

	@Test
	public void testEqBuilder() {
		FilterCondition condition = FilterCondition.builder().eq("criteria", "value").build();
		Assert.assertEquals("criteria", condition.getSearchCriteria());
		Assert.assertEquals(Condition.EQUALS, condition.getCondition());
		Assert.assertEquals("value", condition.getValue());
	}

}