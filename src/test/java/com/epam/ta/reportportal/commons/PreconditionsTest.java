/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.commons;

import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static com.epam.ta.reportportal.commons.querygen.constant.LaunchCriteriaConstant.CRITERIA_LAUNCH_MODE;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
class PreconditionsTest {

	@Test
	void hasModePositive() {

		Mode mode = Mode.DEFAULT;

		Assertions.assertTrue(Preconditions.hasMode(mode).test(filterConditionWithMode(mode)));
	}

	@Test
	void hasModeNegative() {

		Mode mode = Mode.DEFAULT;
		Mode anotherMode = Mode.DEBUG;

		Assertions.assertFalse(Preconditions.hasMode(mode).test(filterConditionWithMode(anotherMode)));
	}

	@Test
	void sameTime() {

		Date date = new Date();

		LocalDateTime sameTime = LocalDateTime.from(date.toInstant().atZone(ZoneOffset.UTC));

		Assertions.assertTrue(Preconditions.sameTimeOrLater(sameTime).test(date));
	}

	@Test
	void laterTime() {

		Date date = new Date();

		LocalDateTime laterTime = LocalDateTime.from(date.toInstant().atZone(ZoneOffset.UTC)).minusSeconds(1L);

		Assertions.assertTrue(Preconditions.sameTimeOrLater(laterTime).test(date));
	}

	@Test
	void beforeTime() {

		Date date = new Date();

		LocalDateTime beforeTime = LocalDateTime.from(date.toInstant().atZone(ZoneOffset.UTC)).plusSeconds(1L);

		Assertions.assertFalse(Preconditions.sameTimeOrLater(beforeTime).test(date));
	}

	@Test
	void validateNullValue() {

		assertThrows(NullPointerException.class, () -> Preconditions.sameTimeOrLater(null).test(new Date()));
	}

	private FilterCondition filterConditionWithMode(Mode mode) {

		return new FilterCondition(Condition.EQUALS, false, mode.name(), CRITERIA_LAUNCH_MODE);
	}

}