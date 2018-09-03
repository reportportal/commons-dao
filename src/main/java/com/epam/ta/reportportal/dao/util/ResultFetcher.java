package com.epam.ta.reportportal.dao.util;

import com.epam.ta.reportportal.entity.widget.content.LaunchesStatisticsContent;
import com.epam.ta.reportportal.entity.widget.content.NotPassedCasesContent;
import org.jooq.Record;
import org.jooq.Result;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.dao.util.FieldNameTransformer.fieldName;

public final class ResultFetcher {

	private ResultFetcher() {
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

	public static final Function<Result<? extends Record>, List<NotPassedCasesContent>> NOT_PASSED_CASES_FETCHER = result -> result.stream()
			.map(r -> {
				NotPassedCasesContent res = r.into(NotPassedCasesContent.class);

				Map<String, String> executionMap = new LinkedHashMap<>();
				executionMap.put(NOT_PASSED_STATISTICS_KEY, r.getValue(fieldName(PERCENTAGE), String.class));
				res.setValues(executionMap);
				return res;
			})
			.collect(Collectors.toList());

}
