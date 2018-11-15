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

import com.epam.ta.reportportal.entity.item.Parameter;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.item.TestItemTag;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.launch.LaunchTag;
import com.google.common.collect.Maps;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.epam.ta.reportportal.jooq.Tables.LAUNCH;
import static com.epam.ta.reportportal.jooq.Tables.TEST_ITEM;

/**
 * Fetches results from db by JOOQ queries into Java objects.
 *
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public class ResultFetchers {

	private ResultFetchers() {
		//static only
	}

	/**
	 * Fetches records from db results into list of {@link TestItem} objects.
	 */
	public static final Function<Result<? extends Record>, List<TestItem>> TEST_ITEM_FETCHER = records -> {
		Map<Long, TestItem> testItems = Maps.newLinkedHashMap();
		records.forEach(record -> {
			Long id = record.get(TEST_ITEM.ITEM_ID);
			TestItem testItem;
			if (!testItems.containsKey(id)) {
				testItem = RecordMappers.TEST_ITEM_RECORD_MAPPER.map(record);
			} else {
				testItem = testItems.get(id);
			}
			testItem.getTags().add(record.into(TestItemTag.class));
			testItem.getParameters().add(record.into(Parameter.class));
			testItem.getItemResults().getStatistics().add(RecordMappers.STATISTICS_RECORD_MAPPER.map(record));
			testItems.put(id, testItem);
		});
		return new ArrayList<>(testItems.values());
	};

	/**
	 * Fetches records from db results into list of {@link Launch} objects.
	 */
	public static final Function<Result<? extends Record>, List<Launch>> LAUNCH_FETCHER = records -> {
		Map<Long, Launch> launches = Maps.newLinkedHashMap();
		records.forEach(record -> {
			Long id = record.get(LAUNCH.ID);
			Launch launch;
			if (!launches.containsKey(id)) {
				launch = RecordMappers.LAUNCH_RECORD_MAPPER.map(record);
			} else {
				launch = launches.get(id);
			}
			launch.getTags().add(record.into(LaunchTag.class));
			launch.getStatistics().add(RecordMappers.STATISTICS_RECORD_MAPPER.map(record));
			launches.put(id, launch);
		});
		return new ArrayList<>(launches.values());
	};

}
