
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
import com.epam.ta.reportportal.entity.widget.content.NotPassedCasesContent;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;

/**
 * Util class for widget content repository.
 *
 * @author Pavel Bortnik
 */
public class WidgetContentUtil {

	private WidgetContentUtil() {
		//static only
	}

	public static final BiFunction<Result<? extends Record>, List<String>, List<LaunchesStatisticsContent>> LAUNCHES_STATISTICS_FETCHER = (result, contentFields) -> result
			.stream()
			.map(record -> {
				LaunchesStatisticsContent statisticsContent = record.into(LaunchesStatisticsContent.class);

				Map<String, String> statisticsMap = new LinkedHashMap<>();

				Optional.ofNullable(record.field(fieldName(TOTAL)))
						.ifPresent(field -> statisticsMap.put(TOTAL, String.valueOf(field.getValue(record))));

				contentFields.forEach(contentField -> statisticsMap.put(contentField,
						record.getValue(fieldName(contentField), String.class)
				));

				statisticsContent.setValues(statisticsMap);

				return statisticsContent;
			})
			.collect(Collectors.toList());

	public static final RecordMapper<? super Record, NotPassedCasesContent> NOT_PASSED_CASES_CONTENT_RECORD_MAPPER = r -> {
		NotPassedCasesContent res = r.into(NotPassedCasesContent.class);
		res.setValues(Collections.singletonMap(NOT_PASSED_STATISTICS_KEY, r.getValue(fieldName(PERCENTAGE), String.class)));
		return res;
	};

}
