/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.dao.util;

import com.epam.ta.reportportal.entity.widget.content.LaunchesStatisticsContent;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

/**
 * @author Ivan Budaev
 */
//TODO check this stuff and remove
public final class ResultSorter {

	private ResultSorter() {
		//static only
	}

	public static final Comparator<Map.Entry<String, List<LaunchesStatisticsContent>>> TAG_SORT_COMPARATOR = (prevEntry, currEntry) -> {
		String prevKey = ofNullable(prevEntry.getKey()).orElseGet(String::new);
		String currKey = ofNullable(currEntry.getKey()).orElseGet(String::new);

		int prevKeyLength = prevKey.length();
		int currKeyLength = currKey.length();

//		String[] array =prevKey.trim().split("\\D+");
//		String[] resultArray = Arrays.stream(array).filter(StringUtils::isNotEmpty).toArray(String[]::new);

		return prevKeyLength == currKeyLength ? prevKey.compareTo(currKey) : prevKeyLength - currKeyLength;
	};
}
