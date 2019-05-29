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

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.dao.util.TimestampUtils;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.log.Log;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.ws.model.ErrorType;
import org.jooq.DSLContext;
import org.jooq.OrderField;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.epam.ta.reportportal.commons.querygen.constant.LogCriteriaConstant.CRITERIA_LOG_TIME;
import static com.epam.ta.reportportal.dao.constant.LogRepositoryConstants.DISTINCT_LOGS_TABLE;
import static com.epam.ta.reportportal.dao.constant.LogRepositoryConstants.ROW_NUMBER;
import static com.epam.ta.reportportal.dao.constant.WidgetRepositoryConstants.ID;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.dao.util.RecordMappers.LOG_MAPPER;
import static com.epam.ta.reportportal.dao.util.ResultFetchers.LOG_FETCHER;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static com.epam.ta.reportportal.jooq.tables.JAttachment.ATTACHMENT;
import static com.epam.ta.reportportal.jooq.tables.JTestItem.TEST_ITEM;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.jooq.impl.DSL.field;

/**
 * @author Pavel Bortnik
 */
@Repository
public class LogRepositoryCustomImpl implements LogRepositoryCustom {

	private DSLContext dsl;

	@Autowired
	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public boolean hasLogs(Long itemId) {
		return dsl.fetchExists(dsl.selectOne().from(LOG).where(LOG.ITEM_ID.eq(itemId)));
	}

	@Override
	public List<Log> findByTestItemId(Long itemId, int limit) {
		if (itemId == null || limit <= 0) {
			return new ArrayList<>();
		}

		return dsl.select()
				.from(LOG)
				.leftJoin(ATTACHMENT)
				.on(LOG.ATTACHMENT_ID.eq(ATTACHMENT.ID))
				.where(LOG.ITEM_ID.eq(itemId))
				.orderBy(LOG.LOG_TIME.asc())
				.limit(limit)
				.fetch()
				.map(LOG_MAPPER);
	}

	@Override
	public List<Log> findByTestItemId(Long itemId) {
		if (itemId == null) {
			return new ArrayList<>();
		}

		return dsl.select()
				.from(LOG)
				.leftJoin(ATTACHMENT)
				.on(LOG.ATTACHMENT_ID.eq(ATTACHMENT.ID))
				.where(LOG.ITEM_ID.eq(itemId))
				.orderBy(LOG.LOG_TIME.asc())
				.fetch()
				.map(LOG_MAPPER);
	}

	@Override
	public List<Long> findIdsByTestItemId(Long testItemId) {
		return dsl.select(LOG.ID).from(LOG).where(LOG.ITEM_ID.eq(testItemId)).fetchInto(Long.class);
	}

	@Override
	public List<Long> findIdsByTestItemIds(List<Long> itemIds) {
		return dsl.select().from(LOG).where(LOG.ITEM_ID.in(itemIds)).fetch(LOG.ID, Long.class);
	}

	@Override
	public List<Long> findItemLogIdsByLaunchId(Long launchId) {
		return dsl.select()
				.from(LOG)
				.leftJoin(TEST_ITEM)
				.onKey()
				.join(LAUNCH).on(TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID))
				.where(LAUNCH.ID.eq(launchId))
				.fetch(LOG.ID, Long.class);
	}

	@Override
	public List<Long> findItemLogIdsByLaunchIds(List<Long> launchIds) {
		return dsl.select()
				.from(LOG)
				.leftJoin(TEST_ITEM)
				.onKey()
				.join(LAUNCH).on(TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID))
				.where(LAUNCH.ID.in(launchIds))
				.fetch(LOG.ID, Long.class);
	}

	@Override
	public List<Log> findLogsWithThumbnailByTestItemIdAndPeriod(Long itemId, Duration period) {
		return dsl.select(LOG.ID, ATTACHMENT.FILE_ID, ATTACHMENT.THUMBNAIL_ID)
				.from(LOG)
				.join(ATTACHMENT)
				.on(LOG.ID.eq(ATTACHMENT.ID))
				.where(LOG.ITEM_ID.eq(itemId).and(LOG.LAST_MODIFIED.lt(TimestampUtils.getTimestampBackFromNow(period))))
				.and(ATTACHMENT.FILE_ID.isNotNull().or(ATTACHMENT.THUMBNAIL_ID.isNotNull()))
				.fetchInto(Log.class);
	}

	@Override
	public List<Log> findByFilter(Queryable filter) {
		return LOG_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).wrap().build()));
	}

	@Override
	public Page<Log> findByFilter(Queryable filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(LOG_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter)
				.with(pageable)
				.wrap()
				.withWrapperSort(pageable.getSort())
				.build())), pageable, () -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build()));
	}

	@Override
	public Integer getPageNumber(Long id, Filter filter, Pageable pageable) {

		Sort.Order order = ofNullable(pageable.getSort().getOrderFor(CRITERIA_LOG_TIME)).orElseThrow(() -> new ReportPortalException(
				ErrorType.INCORRECT_SORTING_PARAMETERS));

		OrderField<?> sortField = order.getDirection().isAscending() ? LOG.LOG_TIME.asc() : LOG.LOG_TIME.desc();

		return ofNullable(dsl.select(fieldName(ROW_NUMBER))
				.from(dsl.select(LOG.ID, DSL.rowNumber().over(DSL.orderBy(sortField)).as(ROW_NUMBER))
						.from(LOG)
						.join(QueryBuilder.newBuilder(filter).with(pageable.getSort()).build().asTable(DISTINCT_LOGS_TABLE))
						.on(LOG.ID.eq(fieldName(DISTINCT_LOGS_TABLE, ID).cast(Long.class))))
				.where(fieldName(ID).cast(Long.class).eq(id))
				.fetchAny()).map(r -> {
			Long rowNumber = r.into(Long.class);
			return BigDecimal.valueOf(rowNumber).divide(BigDecimal.valueOf(pageable.getPageSize()), RoundingMode.CEILING).intValue();
		}).orElseThrow(() -> new ReportPortalException(ErrorType.LOG_NOT_FOUND, id));

	}

	@Override
	public boolean hasLogsAddedLately(Duration period, Long launchId, StatusEnum... statuses) {
		List<JStatusEnum> jStatuses = Arrays.stream(statuses).map(it -> JStatusEnum.valueOf(it.name())).collect(toList());
		return dsl.fetchExists(dsl.selectOne()
				.from(LOG)
				.join(TEST_ITEM)
				.on(LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID))
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId))
				.and(TEST_ITEM_RESULTS.STATUS.in(jStatuses))
				.and(LOG.LAST_MODIFIED.gt(TimestampUtils.getTimestampBackFromNow(period)))
				.limit(1));
	}

	@Override
	public int deleteByPeriodAndTestItemIds(Duration period, Collection<Long> testItemIds) {

		return dsl.deleteFrom(LOG)
				.where(LOG.ITEM_ID.in(testItemIds).and(LOG.LAST_MODIFIED.lt(TimestampUtils.getTimestampBackFromNow(period))))
				.execute();
	}

	private List<SortField<Object>> buildSortFields(Sort sort) {
		return ofNullable(sort).map(s -> StreamSupport.stream(s.spliterator(), false)
				.map(order -> field(order.getProperty()).sort(order.getDirection().isDescending() ? SortOrder.DESC : SortOrder.ASC))
				.collect(Collectors.toList())).orElseGet(Collections::emptyList);
	}
}
