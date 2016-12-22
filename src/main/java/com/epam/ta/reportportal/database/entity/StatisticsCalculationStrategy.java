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

package com.epam.ta.reportportal.database.entity;

import java.util.Arrays;
import java.util.Optional;

/**
 * Statistics calculation strategy
 *
 * @author Andrei Varabyeu
 */
public enum StatisticsCalculationStrategy {

	/**
	 * Test based strategy. Only tests should be caclulated as statistics items
	 */
	STEP_BASED,

	/**
	 * All (including befores and afters) should be calculated as statistics items
	 */
	ALL_ITEMS_BASED,

	/**
	 * Optimized for BDD-based launches. Does NOT calculates stats for step/scenario level, only starting from TEST level
	 */
	TEST_BASED;

	/**
	 * Loads strategy by it's string name. Case matters.
	 *
	 * @param strategy Strategy string
	 * @return Optional of found enum value
	 */
	public static Optional<StatisticsCalculationStrategy> fromString(String strategy) {
		return Arrays.stream(values()).filter(s -> s.name().equals(strategy)).findAny();
	}

}