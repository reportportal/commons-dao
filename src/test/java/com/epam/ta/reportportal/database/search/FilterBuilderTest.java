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

import com.epam.ta.reportportal.database.entity.user.User;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.hasSize;

/**
 * @author Andrei Varabyeu
 */
public class FilterBuilderTest {

	@Test(expected = IllegalArgumentException.class)
	public void checkFilterNoConditions() {
		Filter.builder().build();
	}

	@Test
	public void checkFilterPositive() {
		Filter filter = Filter.builder().withTarget(User.class).withCondition(
				FilterCondition.builder().withCondition(Condition.EQUALS).withSearchCriteria("login").withValue("xxx").build())
				.build();
		Assert.assertThat("Incorrect conditions count", filter.toCriteria(), hasSize(1));
	}

}
