/*
 * Copyright 2017 EPAM Systems
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

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;

/**
 * @author Andrei Varabyeu
 */
public class ConditionTest {

	@Test
	public void testFindByMarker() throws Exception {
		Assert.assertThat("Condition find error", Condition.findByMarker("cnt").get(), equalTo(Condition.CONTAINS));
		Assert.assertThat("Negative condition find error", Condition.findByMarker("!cnt").get(), equalTo(Condition.CONTAINS));
		Assert.assertThat("Non existing condition find error", Condition.findByMarker("non_existing"), equalTo(Optional.empty()));
	}

	@Test
	public void isNegative() throws Exception {
		Assert.assertThat("Negative check error", Condition.isNegative("cnt"), equalTo(false));
		Assert.assertThat("Negative check error", Condition.isNegative("!cnt"), equalTo(true));
	}

	@Test
	public void testMakeNegative(){
		Assert.assertThat("Make negative filter error", Condition.makeNegative("cnt"), equalTo("!cnt"));
		Assert.assertThat("Make negative of already negative filter error", Condition.makeNegative("!cnt"), equalTo("!cnt"));
	}

}