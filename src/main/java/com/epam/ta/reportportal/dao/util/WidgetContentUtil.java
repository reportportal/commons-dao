
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

import com.epam.ta.reportportal.entity.widget.content.InvestigatedStatisticsResult;
import com.epam.ta.reportportal.entity.widget.content.LaunchesStatisticsContent;
import com.epam.ta.reportportal.entity.widget.content.NotPassedCasesContent;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;

import java.util.*;
import java.util.function.Function;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.jooq.tables.JLaunch.LAUNCH;
import static com.epam.ta.reportportal.jooq.tables.JStatistics.STATISTICS;
import static com.epam.ta.reportportal.jooq.tables.JStatisticsField.STATISTICS_FIELD;

/**
 * Util class for widget content repository.
 *
 * @author Pavel Bortnik
 */
public class WidgetContentUtil {

	private WidgetContentUtil() {
		//static only
	}

	public static final Function<Result<? extends Record>, List<LaunchesStatisticsContent>> LAUNCHES_STATISTICS_FETCHER = result -> {

		Map<Long, LaunchesStatisticsContent> resultMap = new LinkedHashMap<>();

		result.stream().forEach(record -> {

			if (resultMap.containsKey(record.get(LAUNCH.ID))) {
				LaunchesStatisticsContent content = resultMap.get(record.get(LAUNCH.ID));
				content.getValues().put(record.get(STATISTICS_FIELD.NAME), record.get(STATISTICS.S_COUNTER));
			} else {
				LaunchesStatisticsContent content = record.into(LaunchesStatisticsContent.class);
				content.getValues().put(record.get(STATISTICS_FIELD.NAME), record.get(STATISTICS.S_COUNTER));
				resultMap.put(record.get(LAUNCH.ID), content);
			}

		});

		resultMap.values()
				.stream()
				.forEach(content -> content.getValues()
						.put(TOTAL, content.getValues().values().stream().mapToInt(Integer::intValue).sum()));

		return new ArrayList<>(resultMap.values());
	};

	public static final RecordMapper<? super Record, InvestigatedStatisticsResult> INVESTIGATED_STATISTICS_CONTENT_RECORD_MAPPER = r -> {
		InvestigatedStatisticsResult res = r.into(InvestigatedStatisticsResult.class);
		res.setInvestigatedPercentage(100.0 - r.get(TO_INVESTIGATE, Double.class));
		return res;
	};

	public static final RecordMapper<? super Record, NotPassedCasesContent> NOT_PASSED_CASES_CONTENT_RECORD_MAPPER = r -> {
		NotPassedCasesContent res = r.into(NotPassedCasesContent.class);
		res.setValues(Collections.singletonMap(NOT_PASSED_STATISTICS_KEY, r.getValue(fieldName(PERCENTAGE), String.class)));
		return res;
	};

}
