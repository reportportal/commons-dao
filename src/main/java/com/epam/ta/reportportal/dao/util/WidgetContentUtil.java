
/*
 * Copyright 2019 EPAM Systems
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
import com.epam.ta.reportportal.entity.widget.content.healthcheck.ComponentHealthCheckContent;
import com.epam.ta.reportportal.entity.widget.content.healthcheck.HealthCheckTableStatisticsContent;
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
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_END_TIME;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_LAST_MODIFIED;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.jooq.tables.JActivity.ACTIVITY;
import static com.epam.ta.reportportal.jooq.tables.JItemAttribute.ITEM_ATTRIBUTE;
import static com.epam.ta.reportportal.jooq.tables.JLaunch.LAUNCH;
import static com.epam.ta.reportportal.jooq.tables.JPatternTemplate.PATTERN_TEMPLATE;
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

	public static <K, V> void consumeIfNotNull(K key, V value, BiConsumer<K, V> consumer) {
		ofNullable(key).ifPresent(k -> ofNullable(value).ifPresent(v -> consumer.accept(k, v)));
	}

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
		Optional<Field<?>> itemAttributeIdField = ofNullable(result.field(ATTR_ID));

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
			}

			statisticsField.flatMap(sf -> ofNullable(record.get(sf, String.class)))
					.ifPresent(v -> content.getValues()
							.put(v, ofNullable(record.get(fieldName(STATISTICS_TABLE, STATISTICS_COUNTER), String.class)).orElse("0")));

			resultMap.put(record.get(LAUNCH.ID), content);

			nonStatisticsFields.forEach(cf -> {
				if (CRITERIA_END_TIME.equalsIgnoreCase(cf) || CRITERIA_LAST_MODIFIED.equalsIgnoreCase(cf)) {
					consumeIfNotNull(cf, record.get(criteria.get(cf), Timestamp.class), (k, v) -> content.getValues().put(k, v));
				} else {
					consumeIfNotNull(cf, record.get(criteria.get(cf)), (k, v) -> content.getValues().put(k, String.valueOf(v)));
				}
			});

			itemAttributeIdField.flatMap(f -> ofNullable(record.get(f))).ifPresent(id -> {
				Set<ItemAttributeResource> attributes = ofNullable(content.getAttributes()).orElseGet(Sets::newLinkedHashSet);

				ItemAttributeResource attributeResource = new ItemAttributeResource();
				attributeResource.setKey(record.get(ITEM_ATTRIBUTE.KEY));
				attributeResource.setValue(record.get(ITEM_ATTRIBUTE.VALUE));

				attributes.add(attributeResource);

				content.setAttributes(attributes);
			});
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
		Optional<Field<?>> startTimeField = ofNullable(result.field(LAUNCH.START_TIME.getQualifiedName().toString()));
		Optional<Field<?>> statusField = ofNullable(result.field(LAUNCH.STATUS.getQualifiedName().toString()));

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
			startTimeField.ifPresent(f -> content.setStartTime(record.get(f, Timestamp.class)));
			statusField.ifPresent(f -> content.setStatus(record.get(f, String.class)));
			if (attributeField.isPresent()) {
				String attributeKey = record.get(fieldName(ATTR_TABLE, ATTRIBUTE_KEY), String.class);
				attributes.entrySet()
						.stream()
						.filter(attributeName -> attributeKey == null || (StringUtils.isNotBlank(attributeKey) && attributeKey.startsWith(
								attributeName.getValue())))
						.forEach(attribute -> proceedProductStatusAttributes(record, attribute.getKey(), content));

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
		Optional<Field<?>> startTimeField = ofNullable(result.field(LAUNCH.START_TIME.getQualifiedName().toString()));
		Optional<Field<?>> statusField = ofNullable(result.field(LAUNCH.STATUS.getQualifiedName().toString()));

		result.forEach(record -> {
			ProductStatusStatisticsContent content = PRODUCT_STATUS_WITHOUT_ATTRIBUTES_MAPPER.apply(productStatusMapping, record);
			startTimeField.ifPresent(f -> content.setStartTime(record.get(f, Timestamp.class)));
			statusField.ifPresent(f -> content.setStatus(record.get(f, String.class)));
			if (attributeField.isPresent()) {
				String attributeKey = record.get(fieldName(ATTR_TABLE, ATTRIBUTE_KEY), String.class);
				attributes.entrySet()
						.stream()
						.filter(attributeName -> attributeKey == null || (StringUtils.isNotBlank(attributeKey) && attributeKey.startsWith(
								attributeName.getValue())))
						.forEach(attribute -> proceedProductStatusAttributes(record, attribute.getKey(), content));

			}
		});

		return new ArrayList<>(productStatusMapping.values());
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

	private static final RecordMapper<Record, UniqueBugContent> UNIQUE_BUG_CONTENT_RECORD_MAPPER = record -> {
		UniqueBugContent uniqueBugContent = new UniqueBugContent();
		uniqueBugContent.setUrl(record.get(TICKET.URL));
		uniqueBugContent.setSubmitDate(record.get(TICKET.SUBMIT_DATE));
		uniqueBugContent.setSubmitter(record.get(TICKET.SUBMITTER));
		return uniqueBugContent;
	};

	private static final RecordMapper<Record, UniqueBugContent.ItemInfo> UNIQUE_BUG_ITEM_MAPPER = record -> {
		UniqueBugContent.ItemInfo itemInfo = new UniqueBugContent.ItemInfo();
		itemInfo.setTestItemId(record.get(TEST_ITEM.ITEM_ID));
		itemInfo.setTestItemName(record.get(TEST_ITEM.NAME));
		itemInfo.setLaunchId(record.get(TEST_ITEM.LAUNCH_ID));
		itemInfo.setPath(record.get(TEST_ITEM.PATH, String.class));
		ITEM_ATTRIBUTE_RESOURCE_MAPPER.map(record).ifPresent(attribute -> itemInfo.getItemAttributeResources().add(attribute));
		return itemInfo;
	};

	public static final Function<Result<? extends Record>, Map<String, UniqueBugContent>> UNIQUE_BUG_CONTENT_FETCHER = result -> {
		Map<String, UniqueBugContent> content = Maps.newLinkedHashMap();
		Map<Long, UniqueBugContent.ItemInfo> itemsMap = Maps.newHashMap();

		result.forEach(record -> {
			String ticketId = record.get(TICKET.TICKET_ID);
			Long itemId = record.get(TEST_ITEM.ITEM_ID);
			content.computeIfPresent(ticketId, (ticketID, bugContent) -> {
				itemsMap.computeIfPresent(itemId, (itemID, itemInfo) -> {
					ITEM_ATTRIBUTE_RESOURCE_MAPPER.map(record).ifPresent(attribute -> itemInfo.getItemAttributeResources().add(attribute));
					return itemInfo;
				});
				itemsMap.computeIfAbsent(itemId, id -> {
					UniqueBugContent.ItemInfo itemInfo = UNIQUE_BUG_ITEM_MAPPER.map(record);
					bugContent.getItems().add(itemInfo);
					return itemInfo;
				});
				return bugContent;
			});
			content.computeIfAbsent(ticketId, key -> {
				UniqueBugContent.ItemInfo itemInfo = UNIQUE_BUG_ITEM_MAPPER.map(record);
				itemsMap.put(itemId, itemInfo);
				UniqueBugContent uniqueBugContent = UNIQUE_BUG_CONTENT_RECORD_MAPPER.map(record);
				uniqueBugContent.getItems().add(itemInfo);
				return uniqueBugContent;
			});
		});

		return content;
	};

	public static final Function<Result<? extends Record>, List<CumulativeTrendChartEntry>> CUMULATIVE_TREND_CHART_FETCHER = result -> {
		Map<String, CumulativeTrendChartContent> attributesMapping = Maps.newLinkedHashMap();

		result.forEach(record -> {
			String attributeValue = record.get(fieldName(LAUNCHES_TABLE, ATTRIBUTE_VALUE), String.class);

			String statistics = record.get(STATISTICS_FIELD.NAME, String.class);
			Integer counter = record.get(STATISTICS_COUNTER, Integer.class);

			CumulativeTrendChartContent content = attributesMapping.getOrDefault(attributeValue, new CumulativeTrendChartContent());

			Long[] launchIds = record.get(LAUNCHES, Long[].class);
			if (ArrayUtils.isNotEmpty(launchIds)) {
				content.getLaunchIds().addAll(Arrays.stream(launchIds).collect(Collectors.toSet()));
			}

			content.getStatistics().computeIfPresent(statistics, (k, v) -> v + counter);
			content.getStatistics().putIfAbsent(statistics, counter);

			attributesMapping.put(attributeValue, content);
		});

		return attributesMapping.entrySet()
				.stream()
				.map(entry -> new CumulativeTrendChartEntry(entry.getKey(), entry.getValue()))
				.collect(Collectors.toCollection(LinkedList::new));
	};

	public static final BiConsumer<CumulativeTrendChartEntry, Result<? extends Record>> CUMULATIVE_TOOLTIP_FETCHER = (cumulative, tooltipResult) -> {
		tooltipResult.forEach(record -> {
			String attributeKey = record.get(ATTRIBUTE_KEY, String.class);
			String attributeValue = record.get(ATTRIBUTE_VALUE, String.class);
			cumulative.getContent().getTooltipContent().add(attributeKey + ":" + attributeValue);
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

	public static final Function<Result<? extends Record>, List<FlakyCasesTableContent>> FLAKY_CASES_TABLE_FETCHER = result -> result.stream()
			.map(record -> {
				FlakyCasesTableContent entry = new FlakyCasesTableContent();
				entry.setStatuses((String[]) record.get(DSL.field(fieldName(STATUSES))));
				entry.setFlakyCount(record.get(DSL.field(fieldName(FLAKY_COUNT)), Long.class));
				entry.setTotal(record.get(DSL.field(fieldName(TOTAL)), Long.class));
				entry.setItemName(record.get(DSL.field(fieldName(ITEM_NAME)), String.class));
				entry.setUniqueId(record.get(DSL.field(fieldName(UNIQUE_ID)), String.class));
				entry.setStartTime(Collections.singletonList(record.get(DSL.field(fieldName(START_TIME_HISTORY)), Date.class)));
				return entry;
			})
			.collect(Collectors.toList());

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

	public static final BiFunction<Result<? extends Record>, Boolean, Map<String, List<Long>>> PATTERN_TEMPLATES_AGGREGATION_FETCHER = (result, isLatest) -> {
		Map<String, List<Long>> content;
		if (isLatest) {
			content = Maps.newLinkedHashMap();
			result.forEach(record -> {
				String attribute = record.get(ITEM_ATTRIBUTE.VALUE, String.class);
				List<Long> launchIds = content.computeIfAbsent(attribute, k -> Lists.newArrayList());
				launchIds.add(record.get(fieldName(ID), Long.class));
			});
		} else {
			content = Maps.newLinkedHashMapWithExpectedSize(result.size());
			result.forEach(record -> {
				String attribute = record.get(ITEM_ATTRIBUTE.VALUE, String.class);
				content.put(attribute, Lists.newArrayList(record.get(fieldName(ID), Long[].class)));
			});
		}
		return content;
	};

	public static final Function<Result<? extends Record>, List<TopPatternTemplatesContent>> TOP_PATTERN_TEMPLATES_FETCHER = result -> {

		Map<String, TopPatternTemplatesContent> content = Maps.newLinkedHashMap();

		result.forEach(record -> {

			String attributeValue = record.get(fieldName(ATTRIBUTE_VALUE), String.class);
			TopPatternTemplatesContent patternTemplatesContent = content.computeIfAbsent(attributeValue,
					k -> new TopPatternTemplatesContent(attributeValue)
			);
			patternTemplatesContent.getPatternTemplates()
					.add(new PatternTemplateStatistics(record.get(PATTERN_TEMPLATE.NAME), record.get(fieldName(TOTAL), Long.class)));
		});

		return new ArrayList<>(content.values());
	};

	public static final Function<Result<? extends Record>, List<TopPatternTemplatesContent>> TOP_PATTERN_TEMPLATES_GROUPED_FETCHER = result -> {

		Map<String, TopPatternTemplatesContent> content = Maps.newLinkedHashMap();

		result.forEach(record -> {

			String attributeValue = record.get(fieldName(ATTRIBUTE_VALUE), String.class);
			TopPatternTemplatesContent patternTemplatesContent = content.computeIfAbsent(attributeValue,
					k -> new TopPatternTemplatesContent(attributeValue)
			);
			patternTemplatesContent.getPatternTemplates()
					.add(new PatternTemplateLaunchStatistics(record.get(LAUNCH.NAME),
							record.get(LAUNCH.NUMBER),
							record.get(fieldName(TOTAL), Long.class),
							record.get(LAUNCH.ID)
					));
		});

		return new ArrayList<>(content.values());
	};

	public static final Function<Result<? extends Record>, List<ComponentHealthCheckContent>> COMPONENT_HEALTH_CHECK_FETCHER = result -> result.stream()
			.map(record -> {
				String attributeValue = record.get(fieldName(VALUE), String.class);
				Long total = record.get(fieldName(TOTAL), Long.class);
				Double passingRate = record.get(fieldName(PASSING_RATE), Double.class);
				return new ComponentHealthCheckContent(attributeValue, total, passingRate);
			})
			.collect(Collectors.toList());

	public static final Function<Result<? extends Record>, Map<String, HealthCheckTableStatisticsContent>> COMPONENT_HEALTH_CHECK_TABLE_STATS_FETCHER = result -> {

		Map<String, HealthCheckTableStatisticsContent> resultMap = new LinkedHashMap<>();

		result.forEach(record -> {
			String attributeValue = record.get(fieldName(VALUE), String.class);
			String statisticsField = record.get(STATISTICS_FIELD.NAME, String.class);
			Integer counter = record.get(fieldName(SUM), Integer.class);

			HealthCheckTableStatisticsContent content;
			if (resultMap.containsKey(attributeValue)) {
				content = resultMap.get(attributeValue);
			} else {
				content = new HealthCheckTableStatisticsContent();
				resultMap.put(attributeValue, content);
			}
			content.getStatistics().put(statisticsField, counter);

		});

		resultMap.forEach((key, content) -> {
			double passingRate = 100.0 * content.getStatistics().getOrDefault(EXECUTIONS_PASSED, 0) / content.getStatistics()
					.getOrDefault(EXECUTIONS_TOTAL, 1);
			content.setPassingRate(new BigDecimal(passingRate).setScale(2, RoundingMode.HALF_UP).doubleValue());
		});

		return resultMap;
	};

	public static final Function<Result<? extends Record>, Map<String, List<String>>> COMPONENT_HEALTH_CHECK_TABLE_COLUMN_FETCHER = result -> {

		Map<String, List<String>> resultMap = Maps.newLinkedHashMapWithExpectedSize(result.size());

		result.forEach(record -> resultMap.put(record.get(fieldName(VALUE), String.class),
				ofNullable(record.get(fieldName(AGGREGATED_VALUES),
						String[].class
				)).map(values -> (List<String>) Lists.newArrayList(values)).orElseGet(Collections::emptyList)
		));
		return resultMap;
	};

}
