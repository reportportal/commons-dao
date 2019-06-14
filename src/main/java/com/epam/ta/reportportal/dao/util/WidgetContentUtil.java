
/*
 * Copyright 2018 EPAM Systems
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
import com.epam.ta.reportportal.entity.activity.ActivityDetails;
import com.epam.ta.reportportal.entity.widget.content.*;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ActivityResource;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.epam.ta.reportportal.ws.model.attribute.ItemAttributeResource;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.ta.reportportal.commons.EntityUtils.TO_DATE;
import static com.epam.ta.reportportal.commons.querygen.QueryBuilder.STATISTICS_KEY;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.jooq.tables.JActivity.ACTIVITY;
import static com.epam.ta.reportportal.jooq.tables.JItemAttribute.ITEM_ATTRIBUTE;
import static com.epam.ta.reportportal.jooq.tables.JLaunch.LAUNCH;
import static com.epam.ta.reportportal.jooq.tables.JProject.PROJECT;
import static com.epam.ta.reportportal.jooq.tables.JStatisticsField.STATISTICS_FIELD;
import static com.epam.ta.reportportal.jooq.tables.JTestItem.TEST_ITEM;
import static com.epam.ta.reportportal.jooq.tables.JTicket.TICKET;
import static com.epam.ta.reportportal.jooq.tables.JUsers.USERS;
import static java.util.Optional.ofNullable;

/**
 * Util class for widget content repository.
 *
 * @author Pavel Bortnik
 */
public class WidgetContentUtil {

	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	private WidgetContentUtil() {
		//static only
	}

	private static final Function<Result<? extends Record>, Map<Long, ChartStatisticsContent>> STATISTICS_FETCHER = result -> {

		Map<Long, ChartStatisticsContent> resultMap = new LinkedHashMap<>();

		result.forEach(record -> {
			ChartStatisticsContent content;
			if (resultMap.containsKey(record.get(LAUNCH.ID))) {
				content = resultMap.get(record.get(LAUNCH.ID));
			} else {
				content = record.into(ChartStatisticsContent.class);
				resultMap.put(record.get(LAUNCH.ID), content);
			}

			ofNullable(record.get(fieldName(STATISTICS_TABLE, SF_NAME), String.class)).ifPresent(v -> content.getValues()
					.put(v, ofNullable(record.get(fieldName(STATISTICS_TABLE, STATISTICS_COUNTER), String.class)).orElse("0")));

		});

		return resultMap;
	};

	public static final Function<Result<? extends Record>, OverallStatisticsContent> OVERALL_STATISTICS_FETCHER = result -> {
		Map<String, Long> values = new HashMap<>();

		result.forEach(record -> ofNullable(record.get(STATISTICS_FIELD.NAME)).ifPresent(v -> values.put(v,
				ofNullable(record.get(fieldName(SUM), Long.class)).orElse(0L)
		)));

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
		Optional<Field<?>> startTimeField = ofNullable(result.field(LAUNCH.START_TIME.getQualifiedName().toString()));
		Optional<Field<Long>> itemAttributeIdField = ofNullable(result.field(ITEM_ATTRIBUTE.ID));

		result.forEach(record -> {
			LaunchesTableContent content;
			if (resultMap.containsKey(record.get(LAUNCH.ID))) {

				content = resultMap.get(record.get(LAUNCH.ID));
			} else {
				content = new LaunchesTableContent();
				content.setId(record.get(LAUNCH.ID));
				content.setName(record.get(DSL.field(LAUNCH.NAME.getQualifiedName().toString()), String.class));
				content.setNumber(record.get(DSL.field(LAUNCH.NUMBER.getQualifiedName().toString()), Integer.class));

				startTimeField.ifPresent(f -> content.setStartTime(record.get(f, Timestamp.class)));
				itemAttributeIdField.ifPresent(f -> ofNullable(record.get(f)).ifPresent(id -> {
					Set<ItemAttributeResource> attributes = ofNullable(content.getAttributes()).orElseGet(Sets::newLinkedHashSet);

					ItemAttributeResource attributeResource = new ItemAttributeResource();
					attributeResource.setKey(record.get(ITEM_ATTRIBUTE.KEY));
					attributeResource.setValue(record.get(ITEM_ATTRIBUTE.VALUE));

					attributes.add(attributeResource);

					content.setAttributes(attributes);
				}));

			}

			statisticsField.ifPresent(sf -> ofNullable(record.get(sf, String.class)).ifPresent(v -> content.getValues()
					.put(v, ofNullable(record.get(fieldName(STATISTICS_TABLE, STATISTICS_COUNTER), String.class)).orElse("0"))));

			resultMap.put(record.get(LAUNCH.ID), content);

			nonStatisticsFields.forEach(cf -> content.getValues().put(cf, String.valueOf(record.get(criteria.get(cf)))));

		});

		return new ArrayList<>(resultMap.values());

	};

	public static final RecordMapper<? super Record, ActivityResource> ACTIVITY_MAPPER = r -> {

		ActivityResource activityResource = new ActivityResource();
		activityResource.setId(r.get(ACTIVITY.ID));
		activityResource.setUser(r.get(USERS.LOGIN));
		activityResource.setProjectId(r.get(ACTIVITY.PROJECT_ID));
		activityResource.setProjectName(r.get(PROJECT.NAME));
		activityResource.setActionType(r.get(ACTIVITY.ACTION));
		activityResource.setObjectType(r.get(ACTIVITY.ENTITY));
		activityResource.setLastModified(TO_DATE.apply(r.get(ACTIVITY.CREATION_DATE, LocalDateTime.class)));
		activityResource.setLoggedObjectId(r.get(ACTIVITY.OBJECT_ID));
		String detailsJson = r.get(ACTIVITY.DETAILS, String.class);
		ofNullable(detailsJson).ifPresent(s -> {
			try {
				ActivityDetails details = objectMapper.readValue(s, ActivityDetails.class);
				activityResource.setDetails(details);
			} catch (IOException e) {
				throw new ReportPortalException(ErrorType.OBJECT_RETRIEVAL_ERROR, "Activity details");
			}
		});
		return activityResource;

	};

	private static final BiFunction<Map<Long, ProductStatusStatisticsContent>, Record, ProductStatusStatisticsContent> PRODUCT_STATUS_WITHOUT_ATTRIBUTES_MAPPER = (mapping, record) -> {
		ProductStatusStatisticsContent content;
		if (mapping.containsKey(record.get(LAUNCH.ID))) {
			content = mapping.get(record.get(LAUNCH.ID));
		} else {
			content = record.into(ProductStatusStatisticsContent.class);
			mapping.put(record.get(LAUNCH.ID), content);
		}

		ofNullable(record.get(fieldName(STATISTICS_TABLE, SF_NAME), String.class)).ifPresent(v -> content.getValues()
				.put(v, ofNullable(record.get(fieldName(STATISTICS_TABLE, STATISTICS_COUNTER), String.class)).orElse("0")));

		return content;
	};

	private static void proceedProductStatusAttributes(Record record, String columnName, ProductStatusStatisticsContent content) {

		ofNullable(record.get(fieldName(ATTR_TABLE, ATTRIBUTE_VALUE), String.class)).ifPresent(value -> {
			Map<String, Set<String>> attributesMapping = ofNullable(content.getAttributes()).orElseGet(LinkedHashMap::new);
			Set<String> attributeValues = attributesMapping.get(columnName);
			if (ofNullable(attributeValues).isPresent()) {
				attributeValues.add(value);
			} else {
				attributesMapping.put(columnName, Sets.newHashSet(value));
			}
			content.setAttributes(attributesMapping);
		});

	}

	public static final BiFunction<Result<? extends Record>, Map<String, String>, Map<String, List<ProductStatusStatisticsContent>>> PRODUCT_STATUS_FILTER_GROUPED_FETCHER = (result, attributes) -> {
		Map<String, Map<Long, ProductStatusStatisticsContent>> filterMapping = new LinkedHashMap<>();

		Optional<? extends Field<?>> attributeField = ofNullable(result.field(fieldName(ATTR_TABLE, ATTRIBUTE_VALUE)));

		result.forEach(record -> {
			String filterName = record.get(fieldName(FILTER_NAME), String.class);
			Map<Long, ProductStatusStatisticsContent> productStatusMapping;
			if (filterMapping.containsKey(filterName)) {
				productStatusMapping = filterMapping.get(filterName);
			} else {
				productStatusMapping = new LinkedHashMap<>();
				filterMapping.put(filterName, productStatusMapping);
			}

			ProductStatusStatisticsContent content = PRODUCT_STATUS_WITHOUT_ATTRIBUTES_MAPPER.apply(productStatusMapping, record);
			if (attributeField.isPresent()) {
				ofNullable(record.get(fieldName(ATTR_TABLE, ATTRIBUTE_KEY), String.class)).ifPresent(key -> attributes.entrySet()
						.stream()
						.filter(attributeName -> StringUtils.isNotBlank(key) && key.startsWith(attributeName.getValue()))
						.forEach(attribute -> proceedProductStatusAttributes(record, attribute.getKey(), content)));

			}
		});

		return filterMapping.entrySet()
				.stream()
				.collect(LinkedHashMap::new,
						(res, filterMap) -> res.put(filterMap.getKey(), new ArrayList<>(filterMap.getValue().values())),
						LinkedHashMap::putAll
				);
	};

	public static final BiFunction<Result<? extends Record>, Map<String, String>, List<ProductStatusStatisticsContent>> PRODUCT_STATUS_LAUNCH_GROUPED_FETCHER = (result, attributes) -> {
		Map<Long, ProductStatusStatisticsContent> productStatusMapping = new LinkedHashMap<>();

		Optional<? extends Field<?>> attributeField = ofNullable(result.field(fieldName(ATTR_TABLE, ATTRIBUTE_VALUE)));

		result.forEach(record -> {
			ProductStatusStatisticsContent content = PRODUCT_STATUS_WITHOUT_ATTRIBUTES_MAPPER.apply(productStatusMapping, record);
			if (attributeField.isPresent()) {
				ofNullable(record.get(fieldName(ATTR_TABLE, ATTRIBUTE_KEY), String.class)).ifPresent(key -> attributes.entrySet()
						.stream()
						.filter(attributeName -> StringUtils.isNotBlank(key) && key.startsWith(attributeName.getValue()))
						.forEach(attribute -> proceedProductStatusAttributes(record, attribute.getKey(), content)));

			}
		});

		return new ArrayList<>(productStatusMapping.values());
	};

	public static final RecordMapper<Record, UniqueBugContent> UNIQUE_BUG_CONTENT_RECORD_MAPPER = record -> {
		UniqueBugContent uniqueBugContent = new UniqueBugContent();
		uniqueBugContent.setTestItemId(record.get(TEST_ITEM.ITEM_ID));
		uniqueBugContent.setTestItemName(record.get(TEST_ITEM.NAME));
		uniqueBugContent.setLaunchId(record.get(TEST_ITEM.LAUNCH_ID));
		uniqueBugContent.setPath(record.get(TEST_ITEM.PATH, String.class));
		uniqueBugContent.setTicketId(record.get(TICKET.TICKET_ID));
		uniqueBugContent.setUrl(record.get(TICKET.URL));
		uniqueBugContent.setSubmitDate(record.get(TICKET.SUBMIT_DATE));
		uniqueBugContent.setSubmitter(record.get(USERS.LOGIN));
		return uniqueBugContent;
	};

	public static final RecordMapper<Record, Optional<ItemAttributeResource>> ITEM_ATTRIBUTE_RESOURCE_MAPPER = record -> {

		String key = record.get(fieldName(ITEM_ATTRIBUTES, KEY), String.class);
		String value = record.get(fieldName(ITEM_ATTRIBUTES, VALUE), String.class);

		if (key != null || value != null) {
			return Optional.of(new ItemAttributeResource(key, value));
		} else {
			return Optional.empty();
		}
	};

	public static final Function<Result<? extends Record>, List<UniqueBugContent>> UNIQUE_BUG_CONTENT_FETCHER = result -> {
		Map<Long, UniqueBugContent> content = Maps.newLinkedHashMap();

		result.forEach(record -> {
			Long itemId = record.get(TEST_ITEM.ITEM_ID);
			UniqueBugContent uniqueBugContent;
			if (content.containsKey(itemId)) {
				uniqueBugContent = content.get(itemId);
			} else {
				uniqueBugContent = UNIQUE_BUG_CONTENT_RECORD_MAPPER.map(record);
				content.put(itemId, uniqueBugContent);
			}

			ITEM_ATTRIBUTE_RESOURCE_MAPPER.map(record).ifPresent(attribute -> uniqueBugContent.getItemAttributeResources().add(attribute));
		});

		return new ArrayList<>(content.values());
	};

	public static final Function<Result<? extends Record>, List<CumulativeTrendChartEntry>> CUMULATIVE_TREND_CHART_FETCHER = result -> {
		Map<String, CumulativeTrendChartContent> attributesMapping = Maps.newLinkedHashMap();

		result.forEach(record -> {
			String attributeValue = record.get(fieldName(LAUNCHES_TABLE, ATTRIBUTE_VALUE), String.class);
			Long launchId = record.get(LAUNCH.ID, Long.class);
			String statistics = record.get(STATISTICS_FIELD.NAME, String.class);
			Integer counter = record.get(STATISTICS_COUNTER, Integer.class);

			CumulativeTrendChartContent content = attributesMapping.getOrDefault(attributeValue, new CumulativeTrendChartContent());

			content.getLaunchIds().add(launchId);
			content.getStatistics().computeIfPresent(statistics, (k, v) -> v + counter);
			content.getStatistics().putIfAbsent(statistics, counter);

			attributesMapping.put(attributeValue, content);
		});

		return attributesMapping.entrySet()
				.stream()
				.map(entry -> new CumulativeTrendChartEntry(entry.getKey(), entry.getValue()))
				.collect(Collectors.toCollection(LinkedList::new));
	};

	public static final BiConsumer<List<CumulativeTrendChartEntry>, Result<? extends Record>> CUMULATIVE_TOOLTIP_FETCHER = (cumulative, tooltipResult) -> {
		tooltipResult.forEach(record -> {
			Long launchId = record.get(LAUNCH.ID);
			String attributeValue = record.get(ITEM_ATTRIBUTE.VALUE);
			cumulative.forEach(it -> it.getContent()
					.getLaunchIds()
					.stream()
					.filter(id -> id.equals(launchId))
					.findAny()
					.ifPresent(content -> it.getContent().getTooltipContent().add(attributeValue)));
		});
	};

	public static final BiFunction<Result<? extends Record>, String, List<ChartStatisticsContent>> CASES_GROWTH_TREND_FETCHER = (result, contentField) -> {
		List<ChartStatisticsContent> content = new ArrayList<>(result.size());

		result.forEach(record -> {
			ChartStatisticsContent statisticsContent = record.into(ChartStatisticsContent.class);

			ofNullable(record.get(fieldName(STATISTICS_TABLE, STATISTICS_COUNTER),
					String.class
			)).ifPresent(counter -> statisticsContent.getValues().put(contentField, counter));

			ofNullable(record.get(fieldName(DELTA), String.class)).ifPresent(delta -> statisticsContent.getValues().put(DELTA, delta));

			content.add(statisticsContent);
		});

		return content;
	};

	public static final Function<Result<? extends Record>, List<ChartStatisticsContent>> LAUNCHES_STATISTICS_FETCHER = result -> new ArrayList<>(
			STATISTICS_FETCHER.apply(result).values());

	public static final Function<Result<? extends Record>, List<ChartStatisticsContent>> BUG_TREND_STATISTICS_FETCHER = result -> {
		Map<Long, ChartStatisticsContent> resultMap = STATISTICS_FETCHER.apply(result);

		resultMap.values()
				.forEach(content -> content.getValues()
						.put(TOTAL, String.valueOf(content.getValues().values().stream().mapToInt(Integer::parseInt).sum())));

		return new ArrayList<>(resultMap.values());
	};

	public static final Function<Result<? extends Record>, List<ChartStatisticsContent>> INVESTIGATED_STATISTICS_FETCHER = result -> {
		List<ChartStatisticsContent> statisticsContents = Lists.newArrayListWithExpectedSize(result.size());
		result.forEach(r -> ofNullable(r.get(TO_INVESTIGATE, Double.class)).ifPresent(toInvestigatePercentage -> {
			ChartStatisticsContent content = r.into(ChartStatisticsContent.class);
			content.getValues().put(TO_INVESTIGATE, String.valueOf(toInvestigatePercentage));
			content.getValues().put(INVESTIGATED, String.valueOf(100.0 - toInvestigatePercentage));
			statisticsContents.add(content);
		}));
		return statisticsContents;
	};

	public static final RecordMapper<? super Record, ChartStatisticsContent> TIMELINE_INVESTIGATED_STATISTICS_RECORD_MAPPER = r -> {
		ChartStatisticsContent res = r.into(ChartStatisticsContent.class);
		res.getValues().put(TO_INVESTIGATE, String.valueOf(r.get(TO_INVESTIGATE, Integer.class)));
		res.getValues().put(INVESTIGATED, String.valueOf(r.get(INVESTIGATED, Integer.class)));
		return res;
	};

	public static final RecordMapper<? super Record, NotPassedCasesContent> NOT_PASSED_CASES_CONTENT_RECORD_MAPPER = r -> {
		NotPassedCasesContent res = r.into(NotPassedCasesContent.class);
		res.setValues(Collections.singletonMap(NOT_PASSED_STATISTICS_KEY, r.getValue(fieldName(PERCENTAGE), String.class)));
		return res;
	};

}
