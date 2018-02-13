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

import com.epam.ta.reportportal.database.entity.item.TestItem;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.hamcrest.Matchers.is;

/**
 * @author Andrei Varabyeu
 */
public class ConditionBuilderTest {

	@Test(expected = IllegalArgumentException.class)
	public void checkEmptyValue() {
		FilterCondition.builder().withCondition(Condition.EQUALS).withSearchCriteria("xxx").build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkEmptyCondition() {
		FilterCondition.builder().withValue("hello").withSearchCriteria("xxx").build();
	}

	@Test
	public void checkConditionPositive() {
		FilterCondition condition = FilterCondition.builder()
				.withValue("hello")
				.withSearchCriteria("xxx")
				.withCondition(Condition.EQUALS)
				.build();
		Assert.assertThat("Incorrect condition", condition.getCondition(), is(Condition.EQUALS));
		Assert.assertThat("Incorrect value", condition.getValue(), is("hello"));
		Assert.assertThat("Incorrect search criteria", condition.getSearchCriteria(), is("xxx"));
	}

	@Test
	public void checkEquals() {
		FilterCondition condition = FilterCondition.builder().eq("xxx", "hello").build();
		Assert.assertThat("Incorrect condition", condition.getCondition(), is(Condition.EQUALS));
		Assert.assertThat("Incorrect value", condition.getValue(), is("hello"));
		Assert.assertThat("Incorrect search criteria", condition.getSearchCriteria(), is("xxx"));
	}

	@Test
	public void checkDateStartingFrom() {

		FilterCondition condition = FilterCondition.builder()
				.withSearchCriteria("start_time")
				.withValue("0;1439;-6")
				.withCondition(Condition.BETWEEN)
				.build();
		Assert.assertThat("Incorrect condition", condition.getCondition(), is(Condition.BETWEEN));

		Criteria criteria = new Criteria();
		condition.getCondition().addCondition(
				criteria,
				condition,
				CriteriaMapFactory.DEFAULT_INSTANCE_SUPPLIER.get().getCriteriaMap(TestItem.class).getCriteriaHolder("start_time")
		);

		long expected = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate().atStartOfDay(ZoneId.of("UTC")).plusHours(6).toEpochSecond();
		long calculated = ((Date) (criteria.getCriteriaObject().get("$gte"))).toInstant().getEpochSecond();
		Assert.assertThat("Incorrect between range processing", calculated, is(expected));

	}

}
