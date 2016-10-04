/*
 * Copyright 2016 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.epam.ta.reportportal.database.search;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

/**
 * @author Andrei Varabyeu
 */
public class ConditionBuilderTest {

	@Test(expected = IllegalArgumentException.class)
	public void checkEmptyValue() {
		FilterCondition.builder()
				.withCondition(Condition.EQUALS)
				.withSearchCriteria("xxx").createCondition();
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkEmptyCondition() {
		FilterCondition.builder()
				.withValue("hello")
				.withSearchCriteria("xxx").createCondition();
	}

	@Test
	public void checkConditionPositive() {
		FilterCondition condition = FilterCondition.builder().withValue("hello").withSearchCriteria("xxx").withCondition(Condition.EQUALS)
				.createCondition();
		Assert.assertThat("Incorrect condition", condition.getCondition(), is(Condition.EQUALS));
		Assert.assertThat("Incorrect value", condition.getValue(), is("hello"));
		Assert.assertThat("Incorrect search criteria", condition.getSearchCriteria(), is("xxx"));
	}


	@Test
	public void checkEquals() {
		FilterCondition condition = FilterCondition.builder().eq("xxx", "hello").createCondition();
		Assert.assertThat("Incorrect condition", condition.getCondition(), is(Condition.EQUALS));
		Assert.assertThat("Incorrect value", condition.getValue(), is("hello"));
		Assert.assertThat("Incorrect search criteria", condition.getSearchCriteria(), is("xxx"));
	}

}