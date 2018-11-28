/*
 * Copyright (C) 2018 EPAM Systems
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

package com.epam.ta.reportportal.dao.util;

import com.epam.ta.reportportal.entity.widget.content.LaunchesStatisticsContent;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.dao.util.ResultSorter.TAG_SORT_COMPARATOR;

/**
 * @author Ivan Budaev
 */
public class ResultSorterTest {

	@Test
	public void tagSortComparatorTest() {
		List<String> tags = generateTags();
		Map<String, List<LaunchesStatisticsContent>> map = new HashMap<>();
		tags.forEach(tag -> map.put(tag, new ArrayList<>()));
		Map<String, List<LaunchesStatisticsContent>> resultMap = map.entrySet()
				.stream()
				.sorted(TAG_SORT_COMPARATOR)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

	}

	private List<String> generateTags() {
		return Lists.newArrayList(
				"13.build:1.1.2",
				"build:1.2.1",
				"build:1.2.1",
				"build:1.1.11",
				"build:1.1.0",
				"build:1.1.001",
				"build:1.1.011",
				"build:1.011.2",
				"build:01.1.2",
				"build:sd1.1.2",
				"build:12.1",
				"build:12.1.2"
		);
	}
}