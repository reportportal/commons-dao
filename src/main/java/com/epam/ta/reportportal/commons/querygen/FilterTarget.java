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

package com.epam.ta.reportportal.commons.querygen;

import com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant;
import com.epam.ta.reportportal.entity.Activity;
import com.epam.ta.reportportal.entity.enums.IntegrationGroupEnum;
import com.epam.ta.reportportal.entity.enums.LogLevel;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.log.Log;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.jooq.enums.JActivityEntityEnum;
import com.epam.ta.reportportal.jooq.enums.JLaunchModeEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.enums.JTestItemTypeEnum;
import com.google.common.collect.Lists;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.commons.querygen.constant.ActivityCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.IntegrationCriteriaConstant.CRITERIA_INTEGRATION_TYPE;
import static com.epam.ta.reportportal.commons.querygen.constant.IssueCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.ItemAttributeConstant.CRITERIA_ITEM_ATTRIBUTE_KEY;
import static com.epam.ta.reportportal.commons.querygen.constant.ItemAttributeConstant.CRITERIA_ITEM_ATTRIBUTE_VALUE;
import static com.epam.ta.reportportal.commons.querygen.constant.ItemAttributeConstant.CRITERIA_ITEM_ATTRIBUTE_KEY;
import static com.epam.ta.reportportal.commons.querygen.constant.ItemAttributeConstant.CRITERIA_ITEM_ATTRIBUTE_VALUE;
import static com.epam.ta.reportportal.commons.querygen.constant.LaunchCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.LogCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.ProjectCriteriaConstant.CRITERIA_ATTRIBUTE_NAME;
import static com.epam.ta.reportportal.commons.querygen.constant.ProjectCriteriaConstant.CRITERIA_ATTRIBUTE_NAME;
import static com.epam.ta.reportportal.commons.querygen.constant.ProjectCriteriaConstant.CRITERIA_PROJECT_NAME;
import static com.epam.ta.reportportal.commons.querygen.constant.ProjectCriteriaConstant.CRITERIA_PROJECT_TYPE;
import static com.epam.ta.reportportal.commons.querygen.constant.StatisticsCriteriaConstant.CRITERIA_STATISTICS_COUNT;
import static com.epam.ta.reportportal.commons.querygen.constant.StatisticsCriteriaConstant.CRITERIA_STATISTICS_FIELD;
import static com.epam.ta.reportportal.commons.querygen.constant.StatisticsCriteriaConstant.CRITERIA_STATISTICS_COUNT;
import static com.epam.ta.reportportal.commons.querygen.constant.StatisticsCriteriaConstant.CRITERIA_STATISTICS_FIELD;
import static com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.CRITERIA_TYPE;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.PROJECT_ID;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static org.jooq.impl.DSL.field;

public enum FilterTarget {

	PROJECT_TARGET(Project.class, Arrays.asList(

			new CriteriaHolder(CRITERIA_PROJECT_NAME, PROJECT.NAME, String.class),
			new CriteriaHolder(CRITERIA_PROJECT_TYPE, PROJECT.PROJECT_TYPE, String.class),
			new CriteriaHolder(CRITERIA_ATTRIBUTE_NAME, ATTRIBUTE.NAME, String.class)
	)) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(PROJECT.ID,
					PROJECT.NAME,
					PROJECT.PROJECT_TYPE,
					PROJECT.CREATION_DATE,
					PROJECT.METADATA,
					PROJECT_ATTRIBUTE.VALUE,
					ATTRIBUTE.NAME,
					PROJECT_USER.PROJECT_ID,
					PROJECT_USER.PROJECT_ROLE,
					PROJECT_USER.USER_ID,
					USERS.LOGIN
			);
		}

		@Override
		protected void joinTables(SelectQuery<? extends Record> query) {
			query.addFrom(PROJECT);
			query.addJoin(PROJECT_USER, JoinType.LEFT_OUTER_JOIN, PROJECT.ID.eq(PROJECT_USER.PROJECT_ID));
			query.addJoin(USERS, JoinType.LEFT_OUTER_JOIN, PROJECT_USER.USER_ID.eq(USERS.ID));
			query.addJoin(PROJECT_ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, PROJECT.ID.eq(PROJECT_ATTRIBUTE.PROJECT_ID));
			query.addJoin(ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, PROJECT_ATTRIBUTE.ATTRIBUTE_ID.eq(ATTRIBUTE.ID));
		}

		@Override
		protected Field<Long> idField() {
			return PROJECT.ID;
		}
	},

	USER_TARGET(User.class, Arrays.asList(

			new CriteriaHolder(CRITERIA_ID, USERS.ID, Long.class),
			new CriteriaHolder(CRITERIA_USER, USERS.LOGIN, String.class),
			new CriteriaHolder(CRITERIA_EMAIL, USERS.EMAIL, String.class),
			new CriteriaHolder(CRITERIA_FULL_NAME, USERS.FULL_NAME, String.class),
			new CriteriaHolder(CRITERIA_ROLE, USERS.ROLE, String.class),
			new CriteriaHolder(CRITERIA_TYPE, USERS.TYPE, String.class),
			new CriteriaHolder(CRITERIA_EXPIRED, USERS.EXPIRED, Boolean.class),
			new CriteriaHolder(CRITERIA_PROJECT_ID, PROJECT_USER.PROJECT_ID, Long.class)

	)) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(USERS.ID,
					USERS.LOGIN,
					USERS.DEFAULT_PROJECT_ID,
					USERS.FULL_NAME,
					USERS.TYPE,
					USERS.ATTACHMENT,
					USERS.ATTACHMENT_THUMBNAIL,
					USERS.EMAIL,
					USERS.EXPIRED,
					USERS.PASSWORD,
					USERS.ROLE,
					USERS.METADATA,
					PROJECT.NAME,
					PROJECT.PROJECT_TYPE,
					PROJECT_USER.PROJECT_ID,
					PROJECT_USER.PROJECT_ROLE,
					PROJECT_USER.USER_ID
			);
		}

		@Override
		protected void joinTables(SelectQuery<? extends Record> query) {
			query.addFrom(USERS);
			query.addJoin(PROJECT_USER, JoinType.LEFT_OUTER_JOIN, USERS.ID.eq(PROJECT_USER.USER_ID));
			query.addJoin(PROJECT, JoinType.LEFT_OUTER_JOIN, PROJECT_USER.PROJECT_ID.eq(PROJECT.ID));
		}

		@Override
		protected Field<Long> idField() {
			return USERS.ID;
		}
	},

	LAUNCH_TARGET(Launch.class, Arrays.asList(

			new CriteriaHolder(CRITERIA_ID, LAUNCH.ID, Long.class),
			new CriteriaHolder(CRITERIA_NAME, LAUNCH.NAME, String.class),
			new CriteriaHolder(CRITERIA_DESCRIPTION, LAUNCH.DESCRIPTION, String.class),
			new CriteriaHolder(CRITERIA_LAUNCH_UUID, LAUNCH.UUID, String.class),
			new CriteriaHolder(CRITERIA_START_TIME, LAUNCH.START_TIME, Timestamp.class),
			new CriteriaHolder(CRITERIA_END_TIME, LAUNCH.END_TIME, Timestamp.class),
			new CriteriaHolder(CRITERIA_PROJECT_ID, LAUNCH.PROJECT_ID, Long.class),
			new CriteriaHolder(CRITERIA_USER_ID, LAUNCH.USER_ID, Long.class),
			new CriteriaHolder(CRITERIA_LAUNCH_NUMBER, LAUNCH.NUMBER, Integer.class),
			new CriteriaHolder(CRITERIA_LAST_MODIFIED, LAUNCH.LAST_MODIFIED, Timestamp.class),
			new CriteriaHolder(CRITERIA_LAUNCH_MODE, LAUNCH.MODE, JLaunchModeEnum.class),
			new CriteriaHolder(CRITERIA_LAUNCH_STATUS, LAUNCH.STATUS, JStatusEnum.class),
			new CriteriaHolder(CRITERIA_ITEM_ATTRIBUTE_KEY, ITEM_ATTRIBUTE.KEY, List.class),
			new CriteriaHolder(CRITERIA_ITEM_ATTRIBUTE_VALUE, ITEM_ATTRIBUTE.VALUE, List.class),
			new CriteriaHolder(CRITERIA_STATISTICS_FIELD, STATISTICS_FIELD.NAME, String.class),
			new CriteriaHolder(CRITERIA_STATISTICS_COUNT, STATISTICS.S_COUNTER, Long.class),
			new CriteriaHolder(CRITERIA_USER, USERS.LOGIN, String.class)
	)) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(LAUNCH.ID,
					LAUNCH.UUID,
					LAUNCH.NAME,
					LAUNCH.DESCRIPTION,
					LAUNCH.START_TIME,
					LAUNCH.END_TIME,
					LAUNCH.PROJECT_ID,
					LAUNCH.USER_ID,
					LAUNCH.NUMBER,
					LAUNCH.LAST_MODIFIED,
					LAUNCH.MODE,
					LAUNCH.STATUS, ITEM_ATTRIBUTE.KEY, ITEM_ATTRIBUTE.VALUE, ITEM_ATTRIBUTE.SYSTEM,
					STATISTICS.S_COUNTER,
					STATISTICS_FIELD.NAME,
					USERS.ID,
					USERS.LOGIN
			);
		}

		@Override
		protected void joinTables(SelectQuery<? extends Record> query) {
			query.addFrom(LAUNCH);
			query.addJoin(ITEM_ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, LAUNCH.ID.eq(ITEM_ATTRIBUTE.LAUNCH_ID));
			query.addJoin(USERS, JoinType.LEFT_OUTER_JOIN, LAUNCH.USER_ID.eq(USERS.ID));
			query.addJoin(STATISTICS, JoinType.LEFT_OUTER_JOIN, LAUNCH.ID.eq(STATISTICS.LAUNCH_ID));
			query.addJoin(STATISTICS_FIELD, JoinType.LEFT_OUTER_JOIN, STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID));
		}

		@Override
		protected Field<Long> idField() {
			return LAUNCH.ID;
		}
	},

	TEST_ITEM_TARGET(TestItem.class,
			Arrays.asList(new CriteriaHolder(PROJECT_ID, LAUNCH.PROJECT_ID, Long.class),
					new CriteriaHolder(CRITERIA_ID, TEST_ITEM.ITEM_ID, Long.class),
					new CriteriaHolder(CRITERIA_NAME, TEST_ITEM.NAME, String.class),
					new CriteriaHolder(TestItemCriteriaConstant.CRITERIA_TYPE,
							TEST_ITEM.TYPE,
							JTestItemTypeEnum.class
					),
					new CriteriaHolder(CRITERIA_START_TIME, TEST_ITEM.START_TIME, Timestamp.class),
					new CriteriaHolder(CRITERIA_DESCRIPTION, TEST_ITEM.DESCRIPTION, String.class),
					new CriteriaHolder(CRITERIA_LAST_MODIFIED, TEST_ITEM.LAST_MODIFIED, String.class),
					new CriteriaHolder(CRITERIA_PATH, TEST_ITEM.PATH, Long.class),
					new CriteriaHolder(CRITERIA_UNIQUE_ID, TEST_ITEM.UNIQUE_ID, Long.class),
					new CriteriaHolder(CRITERIA_PARENT_ID, TEST_ITEM.PARENT_ID, Long.class),
					new CriteriaHolder(CRITERIA_HAS_CHILDREN, TEST_ITEM.HAS_CHILDREN, Boolean.class),

					new CriteriaHolder(CRITERIA_TI_STATUS, TEST_ITEM_RESULTS.STATUS, JStatusEnum.class),
					new CriteriaHolder(CRITERIA_END_TIME, TEST_ITEM_RESULTS.END_TIME, Timestamp.class),
					new CriteriaHolder(CRITERIA_TI_DURATION, TEST_ITEM_RESULTS.DURATION, Long.class),

					new CriteriaHolder(CRITERIA_TI_PARAMETER_KEY, PARAMETER.KEY, String.class),
					new CriteriaHolder(CRITERIA_TI_PARAMETER_VALUE, PARAMETER.VALUE, String.class),
					new CriteriaHolder(CRITERIA_ISSUE_AUTO_ANALYZED, ISSUE.AUTO_ANALYZED, Boolean.class),
					new CriteriaHolder(CRITERIA_ISSUE_IGNORE_ANALYZER, ISSUE.IGNORE_ANALYZER, Boolean.class),
					new CriteriaHolder(CRITERIA_ISSUE_LOCATOR, ISSUE_TYPE.LOCATOR, String.class),

					new CriteriaHolder(CRITERIA_LAUNCH_ID, TEST_ITEM.LAUNCH_ID, Long.class),
					new CriteriaHolder(CRITERIA_LAUNCH_MODE, LAUNCH.MODE, JLaunchModeEnum.class),
					new CriteriaHolder(CRITERIA_PARENT_ID, TEST_ITEM.PARENT_ID, Long.class),
					new CriteriaHolder(CRITERIA_ITEM_ATTRIBUTE_KEY, ITEM_ATTRIBUTE.KEY, List.class),
					new CriteriaHolder(CRITERIA_ITEM_ATTRIBUTE_VALUE, ITEM_ATTRIBUTE.VALUE, List.class),
					new CriteriaHolder(CRITERIA_ISSUE_TYPE, ISSUE_TYPE.LOCATOR, String.class)
			)
	) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(TEST_ITEM.ITEM_ID,
					TEST_ITEM.NAME,
					TEST_ITEM.TYPE,
					TEST_ITEM.START_TIME,
					TEST_ITEM.DESCRIPTION,
					TEST_ITEM.LAST_MODIFIED,
					TEST_ITEM.PATH,
					TEST_ITEM.UNIQUE_ID,
					TEST_ITEM.PARENT_ID,
					TEST_ITEM.RETRY_OF,
					TEST_ITEM.HAS_CHILDREN,
					TEST_ITEM.HAS_RETRIES,
					TEST_ITEM.LAUNCH_ID,
					TEST_ITEM_RESULTS.STATUS,
					TEST_ITEM_RESULTS.END_TIME,
					TEST_ITEM_RESULTS.DURATION, ITEM_ATTRIBUTE.KEY, ITEM_ATTRIBUTE.VALUE, ITEM_ATTRIBUTE.SYSTEM,
					PARAMETER.KEY,
					PARAMETER.VALUE,
					STATISTICS_FIELD.NAME,
					STATISTICS.S_COUNTER,
					ISSUE.AUTO_ANALYZED,
					ISSUE.IGNORE_ANALYZER,
					ISSUE.ISSUE_DESCRIPTION,
					ISSUE_TYPE.LOCATOR,
					ISSUE_TYPE.ABBREVIATION,
					ISSUE_TYPE.HEX_COLOR,
					ISSUE_TYPE.ISSUE_NAME,
					ISSUE_GROUP.ISSUE_GROUP_
			);
		}

		@Override
		protected Field<Long> idField() {
			return TEST_ITEM.ITEM_ID;
		}

		@Override
		protected void joinTables(SelectQuery<? extends Record> query) {
			query.addFrom(TEST_ITEM);
			query.addJoin(ITEM_ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(ITEM_ATTRIBUTE.ITEM_ID));
			query.addJoin(PARAMETER, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(PARAMETER.ITEM_ID));
			query.addJoin(STATISTICS, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(STATISTICS.ITEM_ID));
			query.addJoin(STATISTICS_FIELD, JoinType.JOIN, STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID));
			query.addJoin(LAUNCH, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID));
			query.addJoin(TEST_ITEM_RESULTS, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID));
			query.addJoin(ISSUE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID));
			query.addJoin(ISSUE_TYPE, JoinType.LEFT_OUTER_JOIN, ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID));
			query.addJoin(ISSUE_GROUP, JoinType.LEFT_OUTER_JOIN, ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID));

		}
	},

	LOG_TARGET(Log.class, Arrays.asList(

			new CriteriaHolder(CRITERIA_LOG_ID, LOG.ID, Long.class),
			new CriteriaHolder(CRITERIA_LOG_TIME, LOG.LOG_TIME, Timestamp.class),
			new CriteriaHolder(CRITERIA_LAST_MODIFIED, LOG.LAST_MODIFIED, Timestamp.class),
			new CriteriaHolder(CRITERIA_LOG_LEVEL, LOG.LOG_LEVEL, LogLevel.class),
			new CriteriaHolder(CRITERIA_LOG_MESSAGE, LOG.LOG_MESSAGE, String.class),
			new CriteriaHolder(CRITERIA_TEST_ITEM_ID, LOG.ITEM_ID, Long.class)
	)) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(LOG.ID,
					LOG.LOG_TIME,
					LOG.LOG_MESSAGE,
					LOG.LAST_MODIFIED,
					LOG.LOG_LEVEL,
					LOG.ITEM_ID,
					LOG.ATTACHMENT,
					LOG.CONTENT_TYPE
			);
		}

		@Override
		protected void joinTables(SelectQuery<? extends Record> query) {
			query.addFrom(LOG);
		}

		@Override
		protected Field<Long> idField() {
			return LOG.ID;
		}
	},

	ACTIVITY_TARGET(Activity.class, Arrays.asList(

			new CriteriaHolder(CRITERIA_ID, ACTIVITY.ID, Long.class),
			new CriteriaHolder(CRITERIA_PROJECT_ID, ACTIVITY.PROJECT_ID, Long.class),
			new CriteriaHolder(CRITERIA_PROJECT_NAME, PROJECT.NAME, Long.class),
			new CriteriaHolder(CRITERIA_USER_ID, ACTIVITY.USER_ID, Long.class),
			new CriteriaHolder(CRITERIA_ENTITY, ACTIVITY.ENTITY, JActivityEntityEnum.class),
			new CriteriaHolder(CRITERIA_ACTION, ACTIVITY.ACTION, String.class),
			new CriteriaHolder(CRITERIA_CREATION_DATE, ACTIVITY.CREATION_DATE, Timestamp.class),
			new CriteriaHolder(CRITERIA_OBJECT_ID, ACTIVITY.OBJECT_ID, Long.class),
			new CriteriaHolder(CRITERIA_USER, USERS.LOGIN, String.class)
	)) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(ACTIVITY.ID,
					ACTIVITY.PROJECT_ID,
					ACTIVITY.USER_ID,
					ACTIVITY.ENTITY,
					ACTIVITY.ACTION,
					ACTIVITY.CREATION_DATE,
					ACTIVITY.DETAILS,
					ACTIVITY.OBJECT_ID,
					USERS.LOGIN,
					PROJECT.NAME
			);
		}

		@Override
		protected void joinTables(SelectQuery<? extends Record> query) {
			query.addFrom(ACTIVITY);
			query.addJoin(USERS, JoinType.JOIN, ACTIVITY.USER_ID.eq(USERS.ID));
			query.addJoin(PROJECT, JoinType.JOIN, ACTIVITY.PROJECT_ID.eq(PROJECT.ID));
		}

		@Override
		protected Field<Long> idField() {
			return ACTIVITY.ID;
		}
	},

	INTEGRATION_TARGET(Integration.class, Arrays.asList(

			new CriteriaHolder(CRITERIA_PROJECT_ID, INTEGRATION.PROJECT_ID, String.class),
			new CriteriaHolder(CRITERIA_INTEGRATION_TYPE,
					INTEGRATION_TYPE.GROUP_TYPE,
					IntegrationGroupEnum.class
			),
			new CriteriaHolder(CRITERIA_NAME, INTEGRATION_TYPE.NAME, String.class),
			new CriteriaHolder(CRITERIA_PROJECT_NAME, PROJECT.NAME, String.class)
	)) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(INTEGRATION.ID,
					INTEGRATION.PROJECT_ID,
					INTEGRATION.TYPE,
					INTEGRATION.PARAMS,
					INTEGRATION.CREATION_DATE,
					INTEGRATION_TYPE.NAME,
					INTEGRATION_TYPE.GROUP_TYPE,
					PROJECT.NAME
			);
		}

		@Override
		protected void joinTables(SelectQuery<? extends Record> query) {
			query.addFrom(INTEGRATION);
			query.addJoin(INTEGRATION_TYPE, JoinType.JOIN, INTEGRATION.TYPE.eq(INTEGRATION_TYPE.ID));
			query.addJoin(PROJECT, JoinType.JOIN, INTEGRATION.PROJECT_ID.eq(PROJECT.ID));
		}

		@Override
		protected Field<Long> idField() {
			return DSL.cast(INTEGRATION.ID, Long.class);
		}
	},

	USER_FILTER_TARGET(UserFilter.class, Arrays.asList(

			new CriteriaHolder(CRITERIA_NAME, FILTER.NAME, String.class),
			new CriteriaHolder(CRITERIA_NAME, FILTER.NAME, String.class)

	)) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(USER_FILTER.ID,
					FILTER.NAME,
					FILTER.PROJECT_ID,
					FILTER.TARGET,
					FILTER.DESCRIPTION,
					FILTER_CONDITION.SEARCH_CRITERIA,
					FILTER_CONDITION.CONDITION,
					FILTER_CONDITION.VALUE,
					FILTER_CONDITION.NEGATIVE,
					FILTER_SORT.FIELD,
					FILTER_SORT.DIRECTION
			);
		}

		@Override
		protected void joinTables(SelectQuery<? extends Record> query) {
			query.addFrom(USER_FILTER);
			query.addJoin(FILTER, JoinType.JOIN, USER_FILTER.ID.eq(FILTER.ID));
			query.addJoin(FILTER_CONDITION, JoinType.LEFT_OUTER_JOIN, FILTER.ID.eq(FILTER_CONDITION.FILTER_ID));
			query.addJoin(FILTER_SORT, JoinType.LEFT_OUTER_JOIN, FILTER.ID.eq(FILTER_SORT.FILTER_ID));
			query.addJoin(ACL_OBJECT_IDENTITY, JoinType.JOIN, USER_FILTER.ID.cast(String.class).eq(ACL_OBJECT_IDENTITY.OBJECT_ID_IDENTITY));
			query.addJoin(ACL_CLASS, JoinType.JOIN, ACL_CLASS.ID.eq(ACL_OBJECT_IDENTITY.OBJECT_ID_CLASS));
			query.addJoin(ACL_ENTRY, JoinType.JOIN, ACL_ENTRY.ACL_OBJECT_IDENTITY.eq(ACL_OBJECT_IDENTITY.ID));
		}

		@Override
		protected Field<Long> idField() {
			return USER_FILTER.ID;
		}
	};

	public static final String FILTERED_QUERY = "filtered";
	public static final String FILTERED_ID = "id";

	private Class<?> clazz;
	private List<CriteriaHolder> criteriaHolders;

	FilterTarget(Class<?> clazz, List<CriteriaHolder> criteriaHolders) {
		this.clazz = clazz;
		this.criteriaHolders = criteriaHolders;
	}

	public SelectQuery<? extends Record> getQuery() {
		SelectQuery<? extends Record> query = DSL.select(idField().as(FILTERED_ID)).getQuery();
		joinTables(query);
		query.addGroupBy(idField());
		return query;
	}

	protected abstract Collection<? extends SelectField> selectFields();

	protected abstract void joinTables(SelectQuery<? extends Record> query);

	protected abstract Field<Long> idField();

	public SelectQuery<? extends Record> wrapQuery(SelectQuery<? extends Record> query) {
		SelectQuery<Record> wrappedQuery = DSL.with(FILTERED_QUERY).as(query).select(selectFields()).getQuery();
		joinTables(wrappedQuery);
		wrappedQuery.addJoin(DSL.table(DSL.name(FILTERED_QUERY)),
				JoinType.JOIN,
				idField().eq(field(DSL.name(FILTERED_QUERY, FILTERED_ID), Long.class))
		);
		return wrappedQuery;
	}

	public SelectQuery<? extends Record> wrapQuery(SelectQuery<? extends Record> query, String... excluding) {
		List<String> excludingFields = Lists.newArrayList(excluding);
		List<? extends SelectField> fields = selectFields().stream()
				.filter(it -> !excludingFields.contains(it.getName()))
				.collect(Collectors.toList());
		SelectQuery<Record> wrappedQuery = DSL.with(FILTERED_QUERY).as(query).select(fields).getQuery();
		joinTables(wrappedQuery);
		wrappedQuery.addJoin(DSL.table(DSL.name(FILTERED_QUERY)),
				JoinType.JOIN,
				idField().eq(field(DSL.name(FILTERED_QUERY, FILTERED_ID), Long.class))
		);
		return wrappedQuery;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public List<CriteriaHolder> getCriteriaHolders() {
		return criteriaHolders;
	}

	public Optional<CriteriaHolder> getCriteriaByFilter(String filterCriteria) {
		return criteriaHolders.stream().filter(holder -> holder.getFilterCriteria().equals(filterCriteria)).findAny();
	}

	public static FilterTarget findByClass(Class<?> clazz) {
		return Arrays.stream(values())
				.filter(val -> val.clazz.equals(clazz))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException(String.format("No target query builder for clazz %s", clazz)));
	}
}
