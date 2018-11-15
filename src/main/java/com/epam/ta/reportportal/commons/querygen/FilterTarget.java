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

import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.launch.Launch;
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

import static com.epam.ta.reportportal.commons.querygen.constant.LaunchCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant.*;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.PROJECT_ID;
import static com.epam.ta.reportportal.dao.constant.WidgetRepositoryConstants.DESCRIPTION;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static org.jooq.impl.DSL.field;

public enum FilterTarget {

	LAUNCH_TARGET(Launch.class, Arrays.asList(

			new CriteriaHolder("id", LAUNCH.ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder("uuid", LAUNCH.UUID.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_LAUNCH_NAME, LAUNCH.NAME.getQualifiedName().toString(), String.class),
			new CriteriaHolder(DESCRIPTION, LAUNCH.DESCRIPTION.getQualifiedName().toString(), String.class),
			new CriteriaHolder("start_time", LAUNCH.START_TIME.getQualifiedName().toString(), Timestamp.class),
			new CriteriaHolder("end_time", LAUNCH.END_TIME.getQualifiedName().toString(), Timestamp.class),
			new CriteriaHolder(PROJECT_ID, LAUNCH.PROJECT_ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder("user_id", LAUNCH.USER_ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder("number", LAUNCH.NUMBER.getQualifiedName().toString(), Integer.class),
			new CriteriaHolder("last_modified", LAUNCH.LAST_MODIFIED.getQualifiedName().toString(), Timestamp.class),
			new CriteriaHolder(CRITERIA_LAUNCH_MODE, LAUNCH.MODE.getQualifiedName().toString(), JLaunchModeEnum.class),
			new CriteriaHolder(CRITERIA_LAUNCH_STATUS, LAUNCH.STATUS.getQualifiedName().toString(), JStatusEnum.class),
			new CriteriaHolder(CRITERIA_LAUNCH_TAG, LAUNCH_TAG.VALUE.getQualifiedName().toString(), String.class),
			new CriteriaHolder("statistics_field", STATISTICS_FIELD.NAME.getQualifiedName().toString(), String.class),
			new CriteriaHolder("statistics_count", STATISTICS.S_COUNTER.getQualifiedName().toString(), Long.class),
			new CriteriaHolder("login", USERS.LOGIN.getQualifiedName().toString(), String.class)
	)) {
		@Override
		public SelectQuery<? extends Record> getQuery() {
			SelectQuery<? extends Record> query = DSL.selectDistinct(LAUNCH.ID.as(FILTERED_ID))
					.orderBy(LAUNCH.ID.as(FILTERED_ID))
					.getQuery();
			joinTables(query);
			return query;
		}

		@Override
		protected Collection<? extends SelectFieldOrAsterisk> selectFields() {
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
					LAUNCH.STATUS,
					LAUNCH_TAG.VALUE,
					STATISTICS.S_COUNTER,
					STATISTICS_FIELD.NAME,
					USERS.ID,
					USERS.LOGIN
			);
		}

		@Override
		protected void joinTables(SelectQuery<? extends Record> query) {
			query.addFrom(LAUNCH);
			query.addJoin(LAUNCH_TAG, JoinType.LEFT_OUTER_JOIN, LAUNCH.ID.eq(LAUNCH_TAG.LAUNCH_ID));
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
			Arrays.asList(new CriteriaHolder(PROJECT_ID, LAUNCH.PROJECT_ID.getQualifiedName().toString(), Long.class),

					new CriteriaHolder(CRITERIA_NAME, TEST_ITEM.NAME.getQualifiedName().toString(), String.class),
					new CriteriaHolder(CRITERIA_TYPE, TEST_ITEM.TYPE.getQualifiedName().toString(), JTestItemTypeEnum.class),
					new CriteriaHolder("start_time", TEST_ITEM.START_TIME.getQualifiedName().toString(), Timestamp.class),
					new CriteriaHolder("description", TEST_ITEM.DESCRIPTION.getQualifiedName().toString(), String.class),
					new CriteriaHolder("last_modified", TEST_ITEM.LAST_MODIFIED.getQualifiedName().toString(), String.class),
					new CriteriaHolder(CRITERIA_PATH, TEST_ITEM.PATH.getQualifiedName().toString(), Long.class),
					new CriteriaHolder("unique_id", TEST_ITEM.UNIQUE_ID.getQualifiedName().toString(), Long.class),
					new CriteriaHolder("parent_id", TEST_ITEM.PARENT_ID.getQualifiedName().toString(), Long.class),
					new CriteriaHolder(CRITERIA_HAS_CHILDREN, TEST_ITEM.HAS_CHILDREN.getQualifiedName().toString(), Boolean.class),

					new CriteriaHolder(CRITERIA_TI_STATUS, TEST_ITEM_RESULTS.STATUS.getQualifiedName().toString(), JStatusEnum.class),
					new CriteriaHolder("end_time", TEST_ITEM_RESULTS.END_TIME.getQualifiedName().toString(), Timestamp.class),
					new CriteriaHolder("duration", TEST_ITEM_RESULTS.DURATION.getQualifiedName().toString(), Long.class),

					new CriteriaHolder("key", PARAMETER.KEY.getQualifiedName().toString(), String.class),
					new CriteriaHolder("value", PARAMETER.VALUE.getQualifiedName().toString(), String.class),
					new CriteriaHolder("statistics_field", STATISTICS_FIELD.NAME.getQualifiedName().toString(), String.class),
					new CriteriaHolder("statistics_count", STATISTICS.S_COUNTER.getQualifiedName().toString(), Long.class),
					new CriteriaHolder("auto_analyzed", ISSUE.AUTO_ANALYZED.getQualifiedName().toString(), Boolean.class),
					new CriteriaHolder("ignore_analyzer", ISSUE.IGNORE_ANALYZER.getQualifiedName().toString(), Boolean.class),
					new CriteriaHolder("locator", ISSUE_TYPE.LOCATOR.getQualifiedName().toString(), String.class),

					new CriteriaHolder(CRITERIA_LAUNCH_ID, TEST_ITEM.LAUNCH_ID.getQualifiedName().toString(), Long.class),
					new CriteriaHolder(CRITERIA_LAUNCH_MODE, LAUNCH.MODE.getQualifiedName().toString(), JLaunchModeEnum.class),
					new CriteriaHolder(CRITERIA_PARENT_ID, TEST_ITEM.PARENT_ID.getQualifiedName().toString(), Long.class),
					new CriteriaHolder(CRITERIA_ITEM_TAG, ITEM_TAG.VALUE.getQualifiedName().toString(), String.class),
					new CriteriaHolder(CRITERIA_ISSUE_TYPE, ISSUE_TYPE.LOCATOR.getQualifiedName().toString(), String.class)
			)
	) {
		@Override
		public SelectQuery<? extends Record> getQuery() {
			SelectQuery<? extends Record> query = DSL.selectDistinct(TEST_ITEM.ITEM_ID.as(FILTERED_ID))
					.orderBy(TEST_ITEM.ITEM_ID.as(FILTERED_ID))
					.getQuery();
			joinTables(query);
			return query;
		}

		@Override
		protected Collection<? extends SelectFieldOrAsterisk> selectFields() {
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
					TEST_ITEM.LAUNCH_ID,
					TEST_ITEM_RESULTS.STATUS,
					TEST_ITEM_RESULTS.END_TIME,
					TEST_ITEM_RESULTS.DURATION,
					ITEM_TAG.VALUE,
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
			query.addJoin(ITEM_TAG, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(ITEM_TAG.ITEM_ID));
			query.addJoin(PARAMETER, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(PARAMETER.ITEM_ID));
			query.addJoin(STATISTICS, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(STATISTICS.ITEM_ID));
			query.addJoin(STATISTICS_FIELD, JoinType.JOIN, STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID));
			query.addJoin(LAUNCH, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID));
			query.addJoin(TEST_ITEM_RESULTS, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID));
			query.addJoin(ISSUE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID));
			query.addJoin(ISSUE_TYPE, JoinType.LEFT_OUTER_JOIN, ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID));
			query.addJoin(ISSUE_GROUP, JoinType.LEFT_OUTER_JOIN, ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID));

		}
	};
	//	ACTIVITY(Activity.class, Arrays.asList(
	//			new CriteriaHolder(ID, "a.id", Long.class)
	//			new CriteriaHolder(PROJECT_ID, "a.project_id", Long.class),
	//			new CriteriaHolder(CRITERIA_LOGIN, "u.login", String.class),
	//			new CriteriaHolder(CRITERIA_ACTION, "a.action", String.class)
	//			new CriteriaHolder(CRITERIA_OBJECT_ID, "a.object_id", Long.class)
	//	)) {
	//		@Override
	//		public SelectQuery<? extends Record> getQuery() {
	//
	//			JActivity a = JActivity.ACTIVITY.as("a");
	//			JUsers u = JUsers.USERS.as("u");
	//			JProject p = JProject.PROJECT.as("p");
	//			return DSL.select(a.ID, a.PROJECT_ID, a.USER_ID, a.ENTITY, a.ACTION, a.CREATION_DATE, a.DETAILS, a.OBJECT_ID, u.LOGIN, p.NAME)
	//					.from(a)
	//					.join(u)
	//					.on(a.USER_ID.eq(u.ID))
	//					.join(p)
	//					.on(a.PROJECT_ID.eq(p.ID))
	//					.getQuery();
	//		}
	//	};

	//	INTEGRATION(Integration.class, Arrays.asList(
	//			//@formatter:off
//			new CriteriaHolder(GeneralCriteriaConstant.CRITERIA_PROJECT, "p.name", String.class),
//			new CriteriaHolder(CRITERIA_TYPE, "it.name", String.class)
//			//@formatter:on
	//	)) {
	//		public SelectQuery<? extends Record> getQuery() {
	//			JIntegration i = JIntegration.INTEGRATION.as("i");
	//			JIntegrationType it = JIntegrationType.INTEGRATION_TYPE.as("it");
	//			JProject p = JProject.PROJECT.as("p");
	//
	//			return DSL.select(i.ID, i.PROJECT_ID, i.TYPE, i.PARAMS, i.CREATION_DATE)
	//					.from(i)
	//					.leftJoin(it)
	//					.on(i.TYPE.eq(it.ID))
	//					.leftJoin(p)
	//					.on(i.PROJECT_ID.eq(p.ID))
	//					.groupBy(i.ID, i.PROJECT_ID, i.TYPE, i.PARAMS, i.CREATION_DATE)
	//					.getQuery();
	//		}
	//	},
	//
	//	PROJECT(
	//			Project.class,
	//			Arrays.asList(new CriteriaHolder(NAME, "name", String.class), new CriteriaHolder("projectType", "project_type", String.class))
	//	) {
	//		public SelectQuery<? extends Record> getQuery() {
	//			JProject p = JProject.PROJECT;
	//			return DSL.select(p.ID, p.NAME, p.PROJECT_TYPE, p.CREATION_DATE, p.METADATA).from(p).getQuery();
	//		}
	//	},
	//
	//	LOG(Log.class, Arrays.asList(
	//			new CriteriaHolder(CRITERIA_LOG_MESSAGE, "l.log_message", String.class),
	//			new CriteriaHolder(CRITERIA_TEST_ITEM_ID, "l.item_id", Long.class),
	//			new CriteriaHolder(CRITERIA_LOG_LEVEL, "l.log_level", LogLevel.class),
	//			new CriteriaHolder(CRITERIA_LOG_ID, "l.id", Long.class)
	//	)) {
	//		@Override
	//		public SelectQuery<? extends Record> getQuery() {
	//			JLog l = JLog.LOG.as("l");
	//			JTestItem ti = JTestItem.TEST_ITEM.as("ti");
	//
	//			return DSL.select(
	//					l.ID,
	//					l.LOG_TIME,
	//					l.LOG_MESSAGE,
	//					l.LAST_MODIFIED,
	//					l.LOG_LEVEL,
	//					l.ITEM_ID,
	//					l.ATTACHMENT,
	//					l.ATTACHMENT_THUMBNAIL,
	//					l.CONTENT_TYPE
	//			)
	//					.from(l)
	//					.leftJoin(ti)
	//					.on(l.ITEM_ID.eq(ti.ITEM_ID))
	//					.groupBy(l.ID, l.LOG_TIME, l.LOG_MESSAGE, l.LAST_MODIFIED, l.LOG_LEVEL, l.ITEM_ID)
	//					.getQuery();
	//		}
	//	},
	//
	//	USER(User.class, Arrays.asList(
	//			new CriteriaHolder(ID, ID, Long.class),
	//			new CriteriaHolder(UserCriteriaConstant.CRITERIA_LOGIN, UserCriteriaConstant.CRITERIA_LOGIN, String.class),
	//			new CriteriaHolder(CRITERIA_EMAIL, CRITERIA_EMAIL, String.class),
	//			new CriteriaHolder(CRITERIA_FULL_NAME, CRITERIA_FULL_NAME, String.class),
	//			new CriteriaHolder(CRITERIA_ROLE, CRITERIA_ROLE, String.class),
	//			new CriteriaHolder(UserCriteriaConstant.CRITERIA_TYPE, UserCriteriaConstant.CRITERIA_TYPE, String.class),
	//			new CriteriaHolder(CRITERIA_EXPIRED, CRITERIA_EXPIRED, Boolean.class),
	//			new CriteriaHolder(CRITERIA_PROJECT_ID, CRITERIA_PROJECT_ID, Long.class)
	//	)) {
	//		@Override
	//		public SelectQuery<? extends Record> getQuery() {
	//			JUsers u = JUsers.USERS;
	//			JProjectUser pu = JProjectUser.PROJECT_USER;
	//			JProject p = JProject.PROJECT;
	//			return DSL.select(
	//					u.ID,
	//					u.LOGIN,
	//					u.DEFAULT_PROJECT_ID,
	//					u.FULL_NAME,
	//					u.ATTACHMENT,
	//					u.ATTACHMENT_THUMBNAIL,
	//					u.EMAIL,
	//					u.EXPIRED,
	//					u.PASSWORD,
	//					u.ROLE,
	//					u.TYPE,
	//					u.METADATA,
	//					pu.PROJECT_ID,
	//					pu.PROJECT_ROLE,
	//					p.PROJECT_TYPE,
	//					p.NAME
	//			).from(u).leftJoin(pu).on(u.ID.eq(pu.USER_ID)).leftJoin(p).on(pu.PROJECT_ID.eq(p.ID)).getQuery();
	//		}
	//	},
	//
	//	USER_FILTER(UserFilter.class, Arrays.asList(new CriteriaHolder(NAME, NAME, String.class))) {
	//		@Override
	//		public SelectQuery<? extends Record> getQuery() {
	//			return DSL.select(
	//					JUserFilter.USER_FILTER.ID,
	//					FILTER.NAME,
	//					FILTER.PROJECT_ID,
	//					FILTER.TARGET,
	//					FILTER.DESCRIPTION,
	//					FILTER_CONDITION.SEARCH_CRITERIA,
	//					FILTER_CONDITION.CONDITION,
	//					FILTER_CONDITION.VALUE,
	//					FILTER_CONDITION.NEGATIVE,
	//					FILTER_SORT.FIELD,
	//					FILTER_SORT.DIRECTION
	//			)
	//					.from(JUserFilter.USER_FILTER)
	//					.join(ACL_OBJECT_IDENTITY)
	//					.on(JUserFilter.USER_FILTER.ID.cast(String.class).eq(ACL_OBJECT_IDENTITY.OBJECT_ID_IDENTITY))
	//					.join(ACL_CLASS)
	//					.on(ACL_CLASS.ID.eq(ACL_OBJECT_IDENTITY.OBJECT_ID_CLASS))
	//					.join(ACL_ENTRY)
	//					.on(ACL_ENTRY.ACL_OBJECT_IDENTITY.eq(ACL_OBJECT_IDENTITY.ID))
	//					.join(FILTER)
	//					.on(JUserFilter.USER_FILTER.ID.eq(FILTER.ID))
	//					.join(FILTER_CONDITION)
	//					.on(FILTER.ID.eq(FILTER_CONDITION.FILTER_ID))
	//					.join(FILTER_SORT)
	//					.on(FILTER.ID.eq(FILTER_SORT.FILTER_ID))
	//					.getQuery();
	//		}
	//	};

	public static final String FILTERED_QUERY = "filtered";
	public static final String FILTERED_ID = "id";

	private Class<?> clazz;
	private List<CriteriaHolder> criteriaHolders;

	FilterTarget(Class<?> clazz, List<CriteriaHolder> criteriaHolders) {
		this.clazz = clazz;
		this.criteriaHolders = criteriaHolders;
	}

	public abstract SelectQuery<? extends Record> getQuery();

	protected abstract Collection<? extends SelectFieldOrAsterisk> selectFields();

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
