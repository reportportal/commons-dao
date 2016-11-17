/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
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
 
package com.epam.ta.reportportal.database.entity.item;

import java.io.Serializable;
import java.util.Comparator;

public enum TestItemType implements Comparable<TestItemType> {

//@formatter:off
SUITE(Constants.SUITE_LEVEL, true),
STORY(Constants.SUITE_LEVEL, true),
TEST(Constants.TEST_LEVEL, true),
SCENARIO(Constants.TEST_LEVEL, true),
STEP(Constants.STEP_LEVEL, true),
BEFORE_CLASS(Constants.STEP_LEVEL, false),
BEFORE_GROUPS(Constants.STEP_LEVEL, false),
BEFORE_METHOD(Constants.STEP_LEVEL, false),
BEFORE_SUITE(Constants.TEST_LEVEL, false),
BEFORE_TEST(Constants.STEP_LEVEL, false),
AFTER_CLASS(Constants.STEP_LEVEL, false),
AFTER_GROUPS(Constants.STEP_LEVEL, false),
AFTER_METHOD(Constants.STEP_LEVEL, false),
AFTER_SUITE(Constants.TEST_LEVEL, false),
AFTER_TEST(Constants.STEP_LEVEL, false);
//@formatter:on

	private int level;
	private boolean awareStatistics;

	TestItemType(int level, boolean awareStatistics) {
		this.level = level;
		this.awareStatistics = awareStatistics;
	}

	public static TestItemType fromValue(String value) {
		TestItemType[] values = TestItemType.values();
		for (TestItemType type : values) {
			if (type.name().equals(value)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * Is level of current item higher than level of specified
	 * 
	 * @param type
	 * @return
	 */
	public boolean higherThan(TestItemType type) {
		return LEVEL_COMPARATOR.compare(this, type) > 0;
	}

	/**
	 * Is level of current item lower than level of specified
	 * 
	 * @param type
	 * @return
	 */
	public boolean lowerThan(TestItemType type) {
		return LEVEL_COMPARATOR.compare(this, type) < 0;
	}

	public boolean awareStatistics() {
		return awareStatistics;
	}

	private static final LevelComparator LEVEL_COMPARATOR = new LevelComparator();

	/**
	 * Level Comparator for TestItem types. Returns TRUE of level of first
	 * object is <b>lower</b> than level of second object
	 * 
	 * @author Andrei Varabyeu
	 * 
	 */
	private static class LevelComparator implements Comparator<TestItemType>, Serializable {

		@Override
		public int compare(TestItemType o1, TestItemType o2) {
			return (o1.level == o2.level) ? 1 : (o1.level < o2.level) ? 1 : -1;
		}
	}

	public static class Constants {
		public static final int SUITE_LEVEL = 0;
		public static final int TEST_LEVEL = 1;
		public static final int STEP_LEVEL = 2;
	}
}
