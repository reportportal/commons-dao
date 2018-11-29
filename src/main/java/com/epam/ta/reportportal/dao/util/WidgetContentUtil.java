
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

import com.epam.ta.reportportal.commons.querygen.CriteriaHolder;
import com.epam.ta.reportportal.commons.querygen.FilterTarget;
import com.epam.ta.reportportal.entity.widget.content.*;
import com.google.common.base.CaseFormat;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.sql.Timestamp;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.ta.reportportal.commons.querygen.QueryBuilder.STATISTICS_KEY;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.jooq.tables.JItemAttribute.ITEM_ATTRIBUTE;
import static com.epam.ta.reportportal.jooq.tables.JLaunch.LAUNCH;
import static com.epam.ta.reportportal.jooq.tables.JStatistics.STATISTICS;
import static com.epam.ta.reportportal.jooq.tables.JStatisticsField.STATISTICS_FIELD;
import static java.util.Optional.ofNullable;

/**
 * Util class for widget content repository.
 *
 * @author Pavel Bortnik
 */
public class WidgetContentUtil {

	private WidgetContentUtil() {
		//static only
	}

	private static final Function<Result<? extends Record>, Map<Long, LaunchesStatisticsContent>> STATISTICS_FETCHER = result -> {

		Map<Long, LaunchesStatisticsContent> resultMap = new LinkedHashMap<>();

		result.stream().forEach(record -> {

			if (resultMap.containsKey(record.get(LAUNCH.ID))) {
				LaunchesStatisticsContent content = resultMap.get(record.get(LAUNCH.ID));
				content.getValues().put(record.get(STATISTICS_FIELD.NAME), String.valueOf(record.get(STATISTICS.S_COUNTER)));
			} else {
				LaunchesStatisticsContent content = record.into(LaunchesStatisticsContent.class);
				content.getValues().put(record.get(STATISTICS_FIELD.NAME), String.valueOf(record.get(STATISTICS.S_COUNTER)));
				resultMap.put(record.get(LAUNCH.ID), content);
			}

		});

		return resultMap;
	};

	public static final Function<Result<? extends Record>, OverallStatisticsContent> OVERALL_STATISTICS_FETCHER = result -> {
		Map<String, Long> values = new HashMap<>();

		result.stream().forEach(record -> values.put(record.get(STATISTICS_FIELD.NAME), record.get(fieldName(SUM), Long.class)));

		return new OverallStatisticsContent(values);
	};

	public static final BiFunction<Result<? extends Record>, List<String>, List<LaunchesTableContent>> LAUNCHES_TABLE_FETCHER = (result, contentFields) -> {

		List<String> nonStatisticsFields = contentFields.stream().filter(cf -> !cf.startsWith(STATISTICS_KEY)).collect(Collectors.toList());

		nonStatisticsFields.removeAll(Stream.of(LAUNCH.ID, LAUNCH.NAME, LAUNCH.NUMBER, LAUNCH.START_TIME)
				.map(cf -> CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, cf.getQualifiedName().last()))
				.collect(Collectors.toList()));

		Map<Long, LaunchesTableContent> resultMap = new LinkedHashMap<>();

		Map<String, String> criteria = FilterTarget.LAUNCH_TARGET.getCriteriaHolders()
				.stream()
				.collect(Collectors.toMap(CriteriaHolder::getFilterCriteria, CriteriaHolder::getQueryCriteria));

		Optional<Field<?>> statisticsField = ofNullable(result.field(fieldName(STATISTICS_TABLE, SF_NAME)));
		Optional<Field<Timestamp>> startTimeField = ofNullable(result.field(LAUNCH.START_TIME));

		result.stream().forEach(record -> {
			LaunchesTableContent content;
			if (resultMap.containsKey(record.get(LAUNCH.ID))) {

				content = resultMap.get(record.get(LAUNCH.ID));
			} else {
				content = new LaunchesTableContent();
				content.setId(record.get(LAUNCH.ID));
				content.setName(record.get(DSL.field(LAUNCH.NAME.getQualifiedName().toString()), String.class));
				content.setNumber(record.get(DSL.field(LAUNCH.NUMBER.getQualifiedName().toString()), Integer.class));

				startTimeField.ifPresent(f -> content.setStartTime(record.get(f)));

			}

			statisticsField.ifPresent(sf -> content.getValues()
					.put(record.get(sf, String.class), String.valueOf(record.get(fieldName(STATISTICS_TABLE, STATISTICS_COUNTER)))));
			resultMap.put(record.get(LAUNCH.ID), content);

			nonStatisticsFields.forEach(cf -> content.getValues().put(cf, String.valueOf(record.get(criteria.get(cf)))));

		});

		return new ArrayList<>(resultMap.values());

	};

	private static final BiFunction<Map<Long, ProductStatusStatisticsContent>, Record, ProductStatusStatisticsContent> PRODUCT_STATUS_WITHOUT_ATTRIBUTES_MAPPER = (mapping, record) -> {
		ProductStatusStatisticsContent content;
		if (mapping.containsKey(record.get(LAUNCH.ID))) {
			content = mapping.get(record.get(LAUNCH.ID));
		} else {
			content = record.into(ProductStatusStatisticsContent.class);
			mapping.put(record.get(LAUNCH.ID), content);
		}
		content.getValues().put(
				record.get(fieldName(STATISTICS_TABLE, SF_NAME), String.class),
				record.get(fieldName(STATISTICS_TABLE, STATISTICS_COUNTER), String.class)
		);

		return content;
	};

	private static final BiConsumer<Map<Long, ProductStatusStatisticsContent>, Record> PRODUCT_STATUS_WITH_ATTRIBUTES_MAPPER = (mapping, record) -> {
		PRODUCT_STATUS_WITHOUT_ATTRIBUTES_MAPPER.apply(mapping, record)
				.getTags()
				.add(record.get(fieldName(ATTRIBUTE_TABLE, ATTRIBUTE_VALUE), String.class));
	};

	public static final Function<Result<? extends Record>, Map<String, List<ProductStatusStatisticsContent>>> PRODUCT_STATUS_FILTER_GROUPED_FETCHER = result -> {
		Map<String, Map<Long, ProductStatusStatisticsContent>> filterMapping = new LinkedHashMap<>();

		Optional<? extends Field<?>> attributeField = ofNullable(result.field(fieldName(ATTRIBUTE_TABLE, ATTRIBUTE_VALUE)));

		result.forEach(record -> {
			String filterName = record.get(fieldName(FILTER_NAME), String.class);
			Map<Long, ProductStatusStatisticsContent> productStatusMapping;
			if (filterMapping.containsKey(filterName)) {
				productStatusMapping = filterMapping.get(filterName);
			} else {
				productStatusMapping = new LinkedHashMap<>();
				filterMapping.put(filterName, productStatusMapping);
			}
			if (attributeField.isPresent()) {
				PRODUCT_STATUS_WITH_ATTRIBUTES_MAPPER.accept(productStatusMapping, record);
			} else {
				PRODUCT_STATUS_WITHOUT_ATTRIBUTES_MAPPER.apply(productStatusMapping, record);
			}
		});

		return filterMapping.entrySet().stream().collect(
				LinkedHashMap::new,
				(res, filterMap) -> res.put(filterMap.getKey(), new ArrayList<>(filterMap.getValue().values())),
				LinkedHashMap::putAll
		);
	};

	public static final Function<Result<? extends Record>, List<ProductStatusStatisticsContent>> PRODUCT_STATUS_LAUNCH_GROUPED_FETCHER = result -> {
		Map<Long, ProductStatusStatisticsContent> resultMap = new LinkedHashMap<>();

		Optional<? extends Field<?>> attributeField = ofNullable(result.field(fieldName(ATTRIBUTE_TABLE, ATTRIBUTE_VALUE)));

		result.stream().forEach(record -> {
			if (attributeField.isPresent()) {
				PRODUCT_STATUS_WITH_ATTRIBUTES_MAPPER.accept(resultMap, record);
			} else {
				PRODUCT_STATUS_WITHOUT_ATTRIBUTES_MAPPER.apply(resultMap, record);
			}
		});

		return new ArrayList<>(resultMap.values());
	};

	public static final Function<Result<? extends Record>, Map<String, List<CumulativeTrendChartContent>>> CUMULATIVE_TREND_CHART_FETCHER = result -> {
		Map<String, Map<Long, CumulativeTrendChartContent>> tagMapper = new LinkedHashMap<>();

		result.stream().forEach(record -> {

			Map<Long, CumulativeTrendChartContent> cumulativeTrendMapper;
			String tag = record.get(ITEM_ATTRIBUTE.VALUE);
			if (tagMapper.containsKey(tag)) {
				cumulativeTrendMapper = tagMapper.get(tag);
			} else {
				cumulativeTrendMapper = new LinkedHashMap<>();
				tagMapper.put(tag, cumulativeTrendMapper);
			}

			CumulativeTrendChartContent content;
			Long launchId = record.get(LAUNCH.ID);
			if (cumulativeTrendMapper.containsKey(launchId)) {
				content = cumulativeTrendMapper.get(launchId);
			} else {
				content = record.into(CumulativeTrendChartContent.class);
				content.setId(launchId);
				cumulativeTrendMapper.put(launchId, content);
			}
			content.getValues().put(
					record.get(fieldName(STATISTICS_TABLE, SF_NAME), String.class),
					record.get(fieldName(STATISTICS_TABLE, STATISTICS_COUNTER), String.class)
			);

		});

		return tagMapper.entrySet().stream().collect(
				LinkedHashMap::new,
				(res, filterMap) -> res.put(filterMap.getKey(), new ArrayList<>(filterMap.getValue().values())),
				LinkedHashMap::putAll
		);
	};

	public static final Function<Result<? extends Record>, List<LaunchesStatisticsContent>> LAUNCHES_STATISTICS_FETCHER = result -> new ArrayList<>(
			STATISTICS_FETCHER.apply(result).values());

	public static final Function<Result<? extends Record>, List<LaunchesStatisticsContent>> BUG_TREND_STATISTICS_FETCHER = result -> {
		Map<Long, LaunchesStatisticsContent> resultMap = STATISTICS_FETCHER.apply(result);

		resultMap.values()
				.stream()
				.forEach(content -> content.getValues()
						.put(TOTAL, String.valueOf(content.getValues().values().stream().mapToInt(Integer::parseInt).sum())));

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
