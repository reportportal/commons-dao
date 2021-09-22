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

package com.epam.ta.reportportal.commons.querygen;

import com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant;
import com.epam.ta.reportportal.commons.querygen.query.JoinEntity;
import com.epam.ta.reportportal.commons.querygen.query.QuerySupplier;
import com.epam.ta.reportportal.entity.activity.Activity;
import com.epam.ta.reportportal.entity.dashboard.Dashboard;
import com.epam.ta.reportportal.entity.enums.LogLevel;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.log.Log;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectInfo;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.widget.Widget;
import com.epam.ta.reportportal.jooq.enums.JIntegrationGroupEnum;
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

import static com.epam.ta.reportportal.commons.querygen.QueryBuilder.STATISTICS_KEY;
import static com.epam.ta.reportportal.commons.querygen.constant.ActivityCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.IntegrationCriteriaConstant.CRITERIA_INTEGRATION_TYPE;
import static com.epam.ta.reportportal.commons.querygen.constant.IssueCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.ItemAttributeConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.LaunchCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.LogCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.ProjectCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.CRITERIA_TYPE;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.*;
import static com.epam.ta.reportportal.entity.project.ProjectInfo.*;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static org.jooq.impl.DSL.choose;
import static org.jooq.impl.DSL.field;

public enum FilterTarget {

	PROJECT_TARGET(Project.class,
			Arrays.asList(new CriteriaHolderBuilder().newBuilder(CRITERIA_ID, PROJECT.ID, Long.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_ALLOCATED_STORAGE, PROJECT.ALLOCATED_STORAGE, Long.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_NAME, PROJECT.NAME, String.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_ORGANIZATION, PROJECT.ORGANIZATION, String.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_TYPE, PROJECT.PROJECT_TYPE, String.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_ATTRIBUTE_NAME,
							ATTRIBUTE.NAME,
							String.class,
							Lists.newArrayList(JoinEntity.of(PROJECT_ATTRIBUTE,
									JoinType.LEFT_OUTER_JOIN,
									PROJECT.ID.eq(PROJECT_ATTRIBUTE.PROJECT_ID)
							), JoinEntity.of(ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, PROJECT_ATTRIBUTE.ATTRIBUTE_ID.eq(ATTRIBUTE.ID)))
					).get(),
					new CriteriaHolderBuilder().newBuilder(USERS_QUANTITY,
							USERS_QUANTITY,
							Long.class,
							Lists.newArrayList(JoinEntity.of(PROJECT_USER,
									JoinType.LEFT_OUTER_JOIN,
									PROJECT.ID.eq(PROJECT_USER.PROJECT_ID)
							))
					).withAggregateCriteria(DSL.countDistinct(PROJECT_USER.USER_ID).toString()).get(),
					new CriteriaHolderBuilder().newBuilder(LAUNCHES_QUANTITY,
							LAUNCHES_QUANTITY,
							Long.class,
							Lists.newArrayList(JoinEntity.of(LAUNCH, JoinType.LEFT_OUTER_JOIN, PROJECT.ID.eq(LAUNCH.PROJECT_ID)))
					)
							.withAggregateCriteria(DSL.countDistinct(choose().when(LAUNCH.MODE.eq(JLaunchModeEnum.DEFAULT)
									.and(LAUNCH.STATUS.ne(JStatusEnum.IN_PROGRESS)), LAUNCH.ID)).toString())
							.get()
			)
	) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(PROJECT.ID,
					PROJECT.NAME,
					PROJECT.ORGANIZATION,
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
		protected void addFrom(SelectQuery<? extends Record> query) {
			query.addFrom(PROJECT);
		}

		@Override
		protected void joinTables(QuerySupplier query) {
			query.addJoin(PROJECT_USER, JoinType.LEFT_OUTER_JOIN, PROJECT.ID.eq(PROJECT_USER.PROJECT_ID));
			query.addJoin(USERS, JoinType.LEFT_OUTER_JOIN, PROJECT_USER.USER_ID.eq(USERS.ID));
			query.addJoin(PROJECT_ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, PROJECT.ID.eq(PROJECT_ATTRIBUTE.PROJECT_ID));
			query.addJoin(ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, PROJECT_ATTRIBUTE.ATTRIBUTE_ID.eq(ATTRIBUTE.ID));
			query.addJoin(LAUNCH, JoinType.LEFT_OUTER_JOIN, PROJECT.ID.eq(LAUNCH.PROJECT_ID));
		}

		@Override
		protected void joinTablesForFilter(QuerySupplier query) {

		}

		@Override
		protected Field<Long> idField() {
			return PROJECT.ID;
		}
	},

	PROJECT_INFO(ProjectInfo.class,
			Arrays.asList(new CriteriaHolderBuilder().newBuilder(CRITERIA_ID, PROJECT.ID, Long.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_NAME, PROJECT.NAME, String.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_TYPE, PROJECT.PROJECT_TYPE, String.class).get(),

					new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_ORGANIZATION, PROJECT.ORGANIZATION, String.class).get(),

					new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_CREATION_DATE, PROJECT.CREATION_DATE, Timestamp.class).get(),

					new CriteriaHolderBuilder().newBuilder(USERS_QUANTITY, USERS_QUANTITY, Long.class)
							.withAggregateCriteria(DSL.countDistinct(PROJECT_USER.USER_ID).toString())
							.get(),

					new CriteriaHolderBuilder().newBuilder(LAST_RUN, LAST_RUN, Timestamp.class)
							.withAggregateCriteria(DSL.max(LAUNCH.START_TIME).toString())
							.get(),

					new CriteriaHolderBuilder().newBuilder(LAUNCHES_QUANTITY, LAUNCHES_QUANTITY, Long.class)
							.withAggregateCriteria(DSL.countDistinct(choose().when(LAUNCH.MODE.eq(JLaunchModeEnum.DEFAULT)
									.and(LAUNCH.STATUS.ne(JStatusEnum.IN_PROGRESS)), LAUNCH.ID)).toString())
							.get()
			)
	) {
		@Override
		public QuerySupplier getQuery() {
			SelectQuery<? extends Record> query = DSL.select(selectFields()).getQuery();
			addFrom(query);
			query.addGroupBy(PROJECT.ID, PROJECT.CREATION_DATE, PROJECT.NAME, PROJECT.PROJECT_TYPE);
			QuerySupplier querySupplier = new QuerySupplier(query);
			joinTables(querySupplier);
			return querySupplier;
		}

		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(DSL.countDistinct(PROJECT_USER.USER_ID).as(USERS_QUANTITY),
					DSL.countDistinct(choose().when(LAUNCH.MODE.eq(JLaunchModeEnum.DEFAULT).and(LAUNCH.STATUS.ne(JStatusEnum.IN_PROGRESS)),
							LAUNCH.ID
					)).as(LAUNCHES_QUANTITY),
					DSL.max(LAUNCH.START_TIME).as(LAST_RUN),
					PROJECT.ID,
					PROJECT.CREATION_DATE,
					PROJECT.NAME,
					PROJECT.PROJECT_TYPE,
					PROJECT.ORGANIZATION
			);
		}

		@Override
		protected void addFrom(SelectQuery<? extends Record> query) {
			query.addFrom(PROJECT);
		}

		@Override
		protected void joinTables(QuerySupplier query) {
			query.addJoin(PROJECT_USER, JoinType.LEFT_OUTER_JOIN, PROJECT.ID.eq(PROJECT_USER.PROJECT_ID));
			query.addJoin(LAUNCH, JoinType.LEFT_OUTER_JOIN, PROJECT.ID.eq(LAUNCH.PROJECT_ID));
		}

		@Override
		public QuerySupplier wrapQuery(SelectQuery<? extends Record> query) {
			throw new UnsupportedOperationException("Doesn't supported for Project Info query");
		}

		@Override
		public QuerySupplier wrapQuery(SelectQuery<? extends Record> query, String... excluding) {
			throw new UnsupportedOperationException("Doesn't supported for Project Info query");
		}

		@Override
		protected Field<Long> idField() {
			return PROJECT.ID;
		}
	},

	USER_TARGET(User.class, Arrays.asList(

			new CriteriaHolderBuilder().newBuilder(CRITERIA_ID, USERS.ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_USER, USERS.LOGIN, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_EMAIL, USERS.EMAIL, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_FULL_NAME, USERS.FULL_NAME, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_ROLE, USERS.ROLE, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_TYPE, USERS.TYPE, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_EXPIRED, USERS.EXPIRED, Boolean.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_ID, PROJECT_USER.PROJECT_ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT, PROJECT.NAME, List.class)
					.withAggregateCriteria(DSL.arrayAgg(PROJECT.NAME).toString())
					.get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_LAST_LOGIN,
					"(" + USERS.METADATA + "-> 'metadata' ->> 'last_login')::DOUBLE PRECISION ",
					Long.class
			).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_SYNCHRONIZATION_DATE,
					"(" + USERS.METADATA.getQualifiedName().toString() + "-> 'metadata' ->> 'synchronizationDate')::DOUBLE PRECISION ",
					Long.class
			).get()

	)) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(USERS.ID,
					USERS.LOGIN,
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
		protected void addFrom(SelectQuery<? extends Record> query) {
			query.addFrom(USERS);
		}

		@Override
		protected void joinTables(QuerySupplier query) {
			query.addJoin(PROJECT_USER, JoinType.LEFT_OUTER_JOIN, USERS.ID.eq(PROJECT_USER.USER_ID));
			query.addJoin(PROJECT, JoinType.LEFT_OUTER_JOIN, PROJECT_USER.PROJECT_ID.eq(PROJECT.ID));
		}

		@Override
		protected Field<Long> idField() {
			return USERS.ID;
		}
	},

	LAUNCH_TARGET(Launch.class, Arrays.asList(

			new CriteriaHolderBuilder().newBuilder(CRITERIA_ID, LAUNCH.ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_NAME, LAUNCH.NAME, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_DESCRIPTION, LAUNCH.DESCRIPTION, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_LAUNCH_UUID, LAUNCH.UUID, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_START_TIME, LAUNCH.START_TIME, Timestamp.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_END_TIME, LAUNCH.END_TIME, Timestamp.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_ID, LAUNCH.PROJECT_ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_USER_ID, LAUNCH.USER_ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_LAUNCH_NUMBER, LAUNCH.NUMBER, Integer.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_LAST_MODIFIED, LAUNCH.LAST_MODIFIED, Timestamp.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_LAUNCH_MODE, LAUNCH.MODE, JLaunchModeEnum.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_LAUNCH_STATUS, LAUNCH.STATUS, JStatusEnum.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_HAS_RETRIES, LAUNCH.HAS_RETRIES, Boolean.class).get(),

			new CriteriaHolderBuilder().newBuilder(CRITERIA_ITEM_ATTRIBUTE_KEY,
					ITEM_ATTRIBUTE.KEY,
					String.class,
					Lists.newArrayList(JoinEntity.of(ITEM_ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, LAUNCH.ID.eq(ITEM_ATTRIBUTE.LAUNCH_ID)))
			).withAggregateCriteria(DSL.arrayAggDistinct(ITEM_ATTRIBUTE.KEY).filterWhere(ITEM_ATTRIBUTE.SYSTEM.eq(false)).toString()).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_ITEM_ATTRIBUTE_VALUE,
					ITEM_ATTRIBUTE.VALUE,
					String.class,
					Lists.newArrayList(JoinEntity.of(ITEM_ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, LAUNCH.ID.eq(ITEM_ATTRIBUTE.LAUNCH_ID)))
			)
					.withAggregateCriteria(DSL.arrayAggDistinct(ITEM_ATTRIBUTE.VALUE)
							.filterWhere(ITEM_ATTRIBUTE.SYSTEM.eq(false))
							.toString())
					.get(),
			new CriteriaHolderBuilder().newBuilder(
					CRITERIA_COMPOSITE_ATTRIBUTE,
					ITEM_ATTRIBUTE.KEY,
					List.class,
					Lists.newArrayList(JoinEntity.of(LAUNCH_ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, LAUNCH.ID.eq(LAUNCH_ATTRIBUTE.LAUNCH_ID)))
			).withAggregateCriteria(DSL.field("{0}::varchar[] || {1}::varchar[] || {2}::varchar[]",
					DSL.arrayAggDistinct(DSL.concat(LAUNCH_ATTRIBUTE.KEY, ":")).filterWhere(LAUNCH_ATTRIBUTE.SYSTEM.eq(false)),
					DSL.arrayAggDistinct(DSL.concat(LAUNCH_ATTRIBUTE.VALUE)).filterWhere(LAUNCH_ATTRIBUTE.SYSTEM.eq(false)),
					DSL.arrayAgg(DSL.concat(DSL.coalesce(LAUNCH_ATTRIBUTE.KEY, ""), DSL.val(KEY_VALUE_SEPARATOR), LAUNCH_ATTRIBUTE.VALUE))
							.filterWhere(LAUNCH_ATTRIBUTE.SYSTEM.eq(false))
			).toString()).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_USER,
					USERS.LOGIN,
					String.class,
					Lists.newArrayList(JoinEntity.of(USERS, JoinType.LEFT_OUTER_JOIN, LAUNCH.USER_ID.eq(USERS.ID)))
			).withAggregateCriteria(DSL.max(USERS.LOGIN).toString()).get()
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
					LAUNCH.STATUS,
					LAUNCH.HAS_RETRIES,
					LAUNCH.RERUN,
					LAUNCH.APPROXIMATE_DURATION,
					ITEM_ATTRIBUTE.KEY,
					ITEM_ATTRIBUTE.VALUE,
					ITEM_ATTRIBUTE.SYSTEM,
					STATISTICS.S_COUNTER,
					STATISTICS_FIELD.NAME,
					USERS.ID,
					USERS.LOGIN
			);
		}

		@Override
		protected void addFrom(SelectQuery<? extends Record> query) {
			query.addFrom(LAUNCH);
		}

		@Override
		protected void joinTables(QuerySupplier query) {
			query.addJoin(ITEM_ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, LAUNCH.ID.eq(ITEM_ATTRIBUTE.LAUNCH_ID));
			query.addJoin(USERS, JoinType.LEFT_OUTER_JOIN, LAUNCH.USER_ID.eq(USERS.ID));
			query.addJoin(STATISTICS, JoinType.LEFT_OUTER_JOIN, LAUNCH.ID.eq(STATISTICS.LAUNCH_ID));
			query.addJoin(STATISTICS_FIELD, JoinType.LEFT_OUTER_JOIN, STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID));
		}

		@Override
		protected void joinTablesForFilter(QuerySupplier query) {
		}

		@Override
		protected Field<Long> idField() {
			return LAUNCH.ID;
		}
	},

	TEST_ITEM_TARGET(TestItem.class,
			Arrays.asList(new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_ID, LAUNCH.PROJECT_ID, Long.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_ID, TEST_ITEM.ITEM_ID, Long.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_NAME, TEST_ITEM.NAME, String.class).get(),
					new CriteriaHolderBuilder().newBuilder(TestItemCriteriaConstant.CRITERIA_TYPE, TEST_ITEM.TYPE, JTestItemTypeEnum.class)
							.withAggregateCriteria(DSL.max(TEST_ITEM.TYPE).toString())
							.get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_START_TIME, TEST_ITEM.START_TIME, Timestamp.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_DESCRIPTION, TEST_ITEM.DESCRIPTION, String.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_LAST_MODIFIED, TEST_ITEM.LAST_MODIFIED, String.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_PATH, TEST_ITEM.PATH, Long.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_UNIQUE_ID, TEST_ITEM.UNIQUE_ID, String.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_UUID, TEST_ITEM.UUID, String.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_TEST_CASE_ID, TEST_ITEM.TEST_CASE_ID, String.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_TEST_CASE_HASH, TEST_ITEM.TEST_CASE_HASH, Integer.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_PARENT_ID, TEST_ITEM.PARENT_ID, Long.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_HAS_CHILDREN, TEST_ITEM.HAS_CHILDREN, Boolean.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_HAS_RETRIES, TEST_ITEM.HAS_RETRIES, Boolean.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_HAS_STATS, TEST_ITEM.HAS_STATS, Boolean.class).get(),

					new CriteriaHolderBuilder().newBuilder(CRITERIA_STATUS,
							TEST_ITEM_RESULTS.STATUS,
							JStatusEnum.class,
							Lists.newArrayList(JoinEntity.of(TEST_ITEM_RESULTS,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID)
							))
					).withAggregateCriteria(DSL.max(TEST_ITEM_RESULTS.STATUS).toString()).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_END_TIME,
							TEST_ITEM_RESULTS.END_TIME,
							Timestamp.class,
							Lists.newArrayList(JoinEntity.of(TEST_ITEM_RESULTS,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID)
							))
					).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_DURATION,
							TEST_ITEM_RESULTS.DURATION,
							Long.class,
							Lists.newArrayList(JoinEntity.of(TEST_ITEM_RESULTS,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID)
							))
					).get(),

					new CriteriaHolderBuilder().newBuilder(CRITERIA_PARAMETER_KEY,
							PARAMETER.KEY,
							String.class,
							Lists.newArrayList(JoinEntity.of(PARAMETER, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(PARAMETER.ITEM_ID)))
					).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_PARAMETER_VALUE,
							PARAMETER.VALUE,
							String.class,
							Lists.newArrayList(JoinEntity.of(PARAMETER, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(PARAMETER.ITEM_ID)))
					).get(),

					new CriteriaHolderBuilder().newBuilder(CRITERIA_ISSUE_ID,
							ISSUE.ISSUE_ID,
							Long.class,
							Lists.newArrayList(JoinEntity.of(TEST_ITEM_RESULTS,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID)
							), JoinEntity.of(ISSUE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID)))
					).get(),

					new CriteriaHolderBuilder().newBuilder(CRITERIA_ISSUE_TYPE,
							ISSUE_TYPE.LOCATOR,
							String.class,
							Lists.newArrayList(JoinEntity.of(TEST_ITEM_RESULTS,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID)
									),
									JoinEntity.of(ISSUE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID)),
									JoinEntity.of(ISSUE_TYPE, JoinType.LEFT_OUTER_JOIN, ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
							)
					).get(),

					new CriteriaHolderBuilder().newBuilder(CRITERIA_ISSUE_TYPE_ID,
							ISSUE.ISSUE_TYPE,
							Long.class,
							Lists.newArrayList(JoinEntity.of(TEST_ITEM_RESULTS,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID)
									),
									JoinEntity.of(ISSUE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID))
							)
					).get(),

					new CriteriaHolderBuilder().newBuilder(CRITERIA_ISSUE_AUTO_ANALYZED,
							ISSUE.AUTO_ANALYZED,
							Boolean.class,
							Lists.newArrayList(JoinEntity.of(TEST_ITEM_RESULTS,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID)
							), JoinEntity.of(ISSUE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID)))
					).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_ISSUE_IGNORE_ANALYZER,
							ISSUE.IGNORE_ANALYZER,
							Boolean.class,
							Lists.newArrayList(JoinEntity.of(TEST_ITEM_RESULTS,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID)
							), JoinEntity.of(ISSUE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID)))
					).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_ISSUE_COMMENT,
							ISSUE.ISSUE_DESCRIPTION,
							String.class,
							Lists.newArrayList(JoinEntity.of(TEST_ITEM_RESULTS,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID)
							), JoinEntity.of(ISSUE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID)))
					).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_ISSUE_LOCATOR,
							ISSUE_TYPE.LOCATOR,
							String.class,
							Lists.newArrayList(JoinEntity.of(TEST_ITEM_RESULTS,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID)
									),
									JoinEntity.of(ISSUE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID)),
									JoinEntity.of(ISSUE_TYPE, JoinType.LEFT_OUTER_JOIN, ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
							)
					).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_ISSUE_GROUP_ID,
							ISSUE_TYPE.ISSUE_GROUP_ID,
							Short.class,
							Lists.newArrayList(JoinEntity.of(TEST_ITEM_RESULTS,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID)
									),
									JoinEntity.of(ISSUE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID)),
									JoinEntity.of(ISSUE_TYPE, JoinType.LEFT_OUTER_JOIN, ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID))
							)
					).get(),

					new CriteriaHolderBuilder().newBuilder(CRITERIA_LAUNCH_ID, TEST_ITEM.LAUNCH_ID, Long.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_LAUNCH_MODE, LAUNCH.MODE, JLaunchModeEnum.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_PARENT_ID, TEST_ITEM.PARENT_ID, Long.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_RETRY_PARENT_ID, TEST_ITEM.RETRY_OF, Long.class).get(),

					new CriteriaHolderBuilder().newBuilder(CRITERIA_ITEM_ATTRIBUTE_KEY,
							ITEM_ATTRIBUTE.KEY,
							List.class,
							Lists.newArrayList(JoinEntity.of(ITEM_ATTRIBUTE,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(ITEM_ATTRIBUTE.ITEM_ID)
							))
					)
							.withAggregateCriteria(DSL.arrayAggDistinct(ITEM_ATTRIBUTE.KEY)
									.filterWhere(ITEM_ATTRIBUTE.SYSTEM.eq(false))
									.toString())
							.get(),

					new CriteriaHolderBuilder().newBuilder(CRITERIA_ITEM_ATTRIBUTE_VALUE,
							ITEM_ATTRIBUTE.VALUE,
							List.class,
							Lists.newArrayList(JoinEntity.of(ITEM_ATTRIBUTE,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(ITEM_ATTRIBUTE.ITEM_ID)
							))
					)
							.withAggregateCriteria(DSL.arrayAggDistinct(ITEM_ATTRIBUTE.VALUE)
									.filterWhere(ITEM_ATTRIBUTE.SYSTEM.eq(false))
									.toString())
							.get(),

					new CriteriaHolderBuilder().newBuilder(
							CRITERIA_LEVEL_ATTRIBUTE,
							ITEM_ATTRIBUTE.KEY,
							List.class,
							Lists.newArrayList(JoinEntity.of(ITEM_ATTRIBUTE,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(ITEM_ATTRIBUTE.ITEM_ID)
									),
									JoinEntity.of(LAUNCH_ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, LAUNCH.ID.eq(LAUNCH_ATTRIBUTE.LAUNCH_ID))
							)
					)
							.withAggregateCriteria(DSL.field("array_cat({0}, {1})::varchar[]",
									DSL.arrayAgg(DSL.concat(DSL.coalesce(ITEM_ATTRIBUTE.KEY, ""),
											DSL.val(KEY_VALUE_SEPARATOR),
											ITEM_ATTRIBUTE.VALUE
									)).filterWhere(ITEM_ATTRIBUTE.SYSTEM.eq(false)),
									DSL.arrayAgg(DSL.concat(DSL.coalesce(LAUNCH_ATTRIBUTE.KEY, ""),
											DSL.val(KEY_VALUE_SEPARATOR),
											LAUNCH_ATTRIBUTE.VALUE
									)).filterWhere(LAUNCH_ATTRIBUTE.SYSTEM.eq(false))
							).toString())
							.get(),

					new CriteriaHolderBuilder().newBuilder(CRITERIA_COMPOSITE_ATTRIBUTE, ITEM_ATTRIBUTE.KEY, List.class, Lists.newArrayList(
							JoinEntity.of(ITEM_ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(ITEM_ATTRIBUTE.ITEM_ID)),
							JoinEntity.of(LAUNCH_ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, LAUNCH.ID.eq(LAUNCH_ATTRIBUTE.LAUNCH_ID))
					)).withAggregateCriteria(DSL.field(
							"{0}::varchar[] || {1}::varchar[] || {2}::varchar[] || {3}::varchar[] || {4}::varchar[] || {5}::varchar[]",
							DSL.arrayAggDistinct(DSL.concat(LAUNCH_ATTRIBUTE.KEY, ":")).filterWhere(LAUNCH_ATTRIBUTE.SYSTEM.eq(false)),
							DSL.arrayAggDistinct(DSL.concat(LAUNCH_ATTRIBUTE.VALUE)).filterWhere(LAUNCH_ATTRIBUTE.SYSTEM.eq(false)),
							DSL.arrayAgg(DSL.concat(DSL.coalesce(LAUNCH_ATTRIBUTE.KEY, ""),
									DSL.val(KEY_VALUE_SEPARATOR),
									LAUNCH_ATTRIBUTE.VALUE
							))
									.filterWhere(LAUNCH_ATTRIBUTE.SYSTEM.eq(false)),
							DSL.arrayAggDistinct(DSL.concat(ITEM_ATTRIBUTE.KEY, ":")).filterWhere(ITEM_ATTRIBUTE.SYSTEM.eq(false)),
							DSL.arrayAggDistinct(DSL.concat(ITEM_ATTRIBUTE.VALUE)).filterWhere(ITEM_ATTRIBUTE.SYSTEM.eq(false)),
							DSL.arrayAgg(DSL.concat(DSL.coalesce(ITEM_ATTRIBUTE.KEY, ""),
									DSL.val(KEY_VALUE_SEPARATOR),
									ITEM_ATTRIBUTE.VALUE
							))
									.filterWhere(ITEM_ATTRIBUTE.SYSTEM.eq(false))
					).toString()).get(),

					new CriteriaHolderBuilder().newBuilder(CRITERIA_PATTERN_TEMPLATE_NAME,
							PATTERN_TEMPLATE.NAME,
							List.class,
							Lists.newArrayList(JoinEntity.of(PATTERN_TEMPLATE_TEST_ITEM,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(PATTERN_TEMPLATE_TEST_ITEM.ITEM_ID)
									),
									JoinEntity.of(PATTERN_TEMPLATE,
											JoinType.LEFT_OUTER_JOIN,
											PATTERN_TEMPLATE_TEST_ITEM.PATTERN_ID.eq(PATTERN_TEMPLATE.ID)
									)
							)
					).withAggregateCriteria(DSL.arrayAggDistinct(PATTERN_TEMPLATE.NAME).toString()).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_TICKET_ID,
							TICKET.TICKET_ID,
							String.class,
							Lists.newArrayList(JoinEntity.of(TEST_ITEM_RESULTS,
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID)
									),
									JoinEntity.of(ISSUE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID)),
									JoinEntity.of(ISSUE_TICKET, JoinType.LEFT_OUTER_JOIN, ISSUE.ISSUE_ID.eq(ISSUE_TICKET.ISSUE_ID)),
									JoinEntity.of(TICKET, JoinType.LEFT_OUTER_JOIN, ISSUE_TICKET.TICKET_ID.eq(TICKET.ID))
							)
					).withAggregateCriteria(DSL.arrayAggDistinct(TICKET.TICKET_ID).toString()).get()
			)
	) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(TEST_ITEM.ITEM_ID,
					TEST_ITEM.NAME,
					TEST_ITEM.CODE_REF,
					TEST_ITEM.TYPE,
					TEST_ITEM.START_TIME,
					TEST_ITEM.DESCRIPTION,
					TEST_ITEM.LAST_MODIFIED,
					TEST_ITEM.PATH,
					TEST_ITEM.UNIQUE_ID,
					TEST_ITEM.UUID,
					TEST_ITEM.TEST_CASE_ID,
					TEST_ITEM.TEST_CASE_HASH,
					TEST_ITEM.PARENT_ID,
					TEST_ITEM.RETRY_OF,
					TEST_ITEM.HAS_CHILDREN,
					TEST_ITEM.HAS_STATS,
					TEST_ITEM.HAS_RETRIES,
					TEST_ITEM.LAUNCH_ID,
					TEST_ITEM_RESULTS.STATUS,
					TEST_ITEM_RESULTS.END_TIME,
					TEST_ITEM_RESULTS.DURATION,
					ITEM_ATTRIBUTE.KEY,
					ITEM_ATTRIBUTE.VALUE,
					ITEM_ATTRIBUTE.SYSTEM,
					PARAMETER.ITEM_ID,
					PARAMETER.KEY,
					PARAMETER.VALUE,
					STATISTICS_FIELD.NAME,
					STATISTICS.S_COUNTER,
					ISSUE.ISSUE_ID,
					ISSUE.AUTO_ANALYZED,
					ISSUE.IGNORE_ANALYZER,
					ISSUE.ISSUE_DESCRIPTION,
					ISSUE_TYPE.LOCATOR,
					ISSUE_TYPE.ABBREVIATION,
					ISSUE_TYPE.HEX_COLOR,
					ISSUE_TYPE.ISSUE_NAME,
					ISSUE_GROUP.ISSUE_GROUP_,
					TICKET.ID,
					TICKET.BTS_PROJECT,
					TICKET.BTS_URL,
					TICKET.TICKET_ID,
					TICKET.URL,
					PATTERN_TEMPLATE.ID,
					PATTERN_TEMPLATE.NAME
			);
		}

		@Override
		protected void addFrom(SelectQuery<? extends Record> query) {
			query.addFrom(TEST_ITEM);
		}

		@Override
		protected Field<Long> idField() {
			return TEST_ITEM.ITEM_ID;
		}

		@Override
		protected void joinTables(QuerySupplier query) {
			query.addJoin(LAUNCH, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID));
			query.addJoin(TEST_ITEM_RESULTS, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID));
			query.addJoin(ITEM_ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(ITEM_ATTRIBUTE.ITEM_ID));
			query.addJoin(STATISTICS, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(STATISTICS.ITEM_ID));
			query.addJoin(STATISTICS_FIELD, JoinType.LEFT_OUTER_JOIN, STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID));
			query.addJoin(PATTERN_TEMPLATE_TEST_ITEM, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(PATTERN_TEMPLATE_TEST_ITEM.ITEM_ID));
			query.addJoin(PATTERN_TEMPLATE, JoinType.LEFT_OUTER_JOIN, PATTERN_TEMPLATE_TEST_ITEM.PATTERN_ID.eq(PATTERN_TEMPLATE.ID));
			query.addJoin(ISSUE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID));
			query.addJoin(ISSUE_TYPE, JoinType.LEFT_OUTER_JOIN, ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID));
			query.addJoin(ISSUE_GROUP, JoinType.LEFT_OUTER_JOIN, ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID));
			query.addJoin(ISSUE_TICKET, JoinType.LEFT_OUTER_JOIN, ISSUE.ISSUE_ID.eq(ISSUE_TICKET.ISSUE_ID));
			query.addJoin(TICKET, JoinType.LEFT_OUTER_JOIN, ISSUE_TICKET.TICKET_ID.eq(TICKET.ID));
			query.addJoin(PARAMETER, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(PARAMETER.ITEM_ID));
			query.addJoin(LAUNCH_ATTRIBUTE, JoinType.LEFT_OUTER_JOIN, LAUNCH.ID.eq(LAUNCH_ATTRIBUTE.LAUNCH_ID));
		}

		@Override
		protected void joinTablesForFilter(QuerySupplier query) {
			query.addJoin(LAUNCH, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID));
		}
	},

	LOG_TARGET(Log.class, Arrays.asList(

			new CriteriaHolderBuilder().newBuilder(CRITERIA_ID, LOG.ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_LOG_ID, LOG.ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_LOG_TIME, LOG.LOG_TIME, Timestamp.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_LAST_MODIFIED, LOG.LAST_MODIFIED, Timestamp.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_LOG_LEVEL, LOG.LOG_LEVEL, LogLevel.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_LOG_MESSAGE, LOG.LOG_MESSAGE, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_TEST_ITEM_ID, LOG.ITEM_ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_LOG_LAUNCH_ID, LOG.LAUNCH_ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_LOG_PROJECT_ID, LOG.PROJECT_ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_LOG_BINARY_CONTENT,
					ATTACHMENT.FILE_ID,
					String.class,
					Lists.newArrayList(JoinEntity.of(ATTACHMENT, JoinType.LEFT_OUTER_JOIN, LOG.ATTACHMENT_ID.eq(ATTACHMENT.ID)))
			).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_ITEM_LAUNCH_ID,
					TEST_ITEM.LAUNCH_ID,
					Long.class,
					Lists.newArrayList(JoinEntity.of(TEST_ITEM, JoinType.LEFT_OUTER_JOIN, LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID)))
			).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_RETRY_PARENT_ID,
					TEST_ITEM.RETRY_OF,
					Long.class,
					Lists.newArrayList(JoinEntity.of(TEST_ITEM, JoinType.LEFT_OUTER_JOIN, LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID)))
			).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_RETRY_PARENT_LAUNCH_ID,
					TEST_ITEM.as(RETRY_PARENT).LAUNCH_ID,
					Long.class,
					Lists.newArrayList(JoinEntity.of(TEST_ITEM, JoinType.LEFT_OUTER_JOIN, LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID)),
							JoinEntity.of(TEST_ITEM.as(RETRY_PARENT),
									JoinType.LEFT_OUTER_JOIN,
									TEST_ITEM.RETRY_OF.eq(TEST_ITEM.as(RETRY_PARENT).ITEM_ID)
							)
					)
			).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_PATH,
					TEST_ITEM.PATH,
					String.class,
					Lists.newArrayList(JoinEntity.of(TEST_ITEM, JoinType.LEFT_OUTER_JOIN, LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID)))
			).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_STATUS,
					TEST_ITEM_RESULTS.STATUS,
					JStatusEnum.class,
					Lists.newArrayList(JoinEntity.of(TEST_ITEM, JoinType.LEFT_OUTER_JOIN, LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID)),
							JoinEntity.of(TEST_ITEM_RESULTS, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
					)
			).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_ISSUE_AUTO_ANALYZED,
					ISSUE.AUTO_ANALYZED,
					Boolean.class,
					Lists.newArrayList(JoinEntity.of(TEST_ITEM, JoinType.LEFT_OUTER_JOIN, LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID)),
							JoinEntity.of(TEST_ITEM_RESULTS, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID)),
							JoinEntity.of(ISSUE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID))
					)
			).get()
	)) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(LOG.ID,
					LOG.LOG_TIME,
					LOG.LOG_MESSAGE,
					LOG.LAST_MODIFIED,
					LOG.LOG_LEVEL,
					LOG.ITEM_ID,
					LOG.LAUNCH_ID,
					LOG.ATTACHMENT_ID,
					ATTACHMENT.ID,
					ATTACHMENT.FILE_ID,
					ATTACHMENT.THUMBNAIL_ID,
					ATTACHMENT.CONTENT_TYPE,
					ATTACHMENT.FILE_SIZE,
					ATTACHMENT.PROJECT_ID,
					ATTACHMENT.LAUNCH_ID,
					ATTACHMENT.ITEM_ID
			);
		}

		@Override
		protected void addFrom(SelectQuery<? extends Record> query) {
			query.addFrom(LOG);
		}

		@Override
		protected void joinTables(QuerySupplier query) {
			query.addJoin(TEST_ITEM, JoinType.LEFT_OUTER_JOIN, LOG.ITEM_ID.eq(TEST_ITEM.ITEM_ID));
			query.addJoin(TEST_ITEM_RESULTS, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID));
			query.addJoin(ISSUE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID));
			query.addJoin(ATTACHMENT, JoinType.LEFT_OUTER_JOIN, LOG.ATTACHMENT_ID.eq(ATTACHMENT.ID));
		}

		@Override
		protected void joinTablesForFilter(QuerySupplier query) {

		}

		@Override
		protected Field<Long> idField() {
			return LOG.ID;
		}
	},

	ACTIVITY_TARGET(Activity.class, Arrays.asList(

			new CriteriaHolderBuilder().newBuilder(CRITERIA_ID, ACTIVITY.ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_ID, ACTIVITY.PROJECT_ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_NAME, PROJECT.NAME, Long.class)
					.withAggregateCriteria(DSL.max(PROJECT.NAME).toString())
					.get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_USER_ID, ACTIVITY.USER_ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_ENTITY, ACTIVITY.ENTITY, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_ACTION, ACTIVITY.ACTION, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_LOGIN, ACTIVITY.USERNAME, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_CREATION_DATE, ACTIVITY.CREATION_DATE, Timestamp.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_OBJECT_ID, ACTIVITY.OBJECT_ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_USER, USERS.LOGIN, String.class)
					.withAggregateCriteria(DSL.max(USERS.LOGIN).toString())
					.get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_OBJECT_NAME, ACTIVITY.DETAILS + " ->> 'objectName'", String.class).get()
	)) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(ACTIVITY.ID,
					ACTIVITY.PROJECT_ID,
					ACTIVITY.USERNAME,
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
		protected void addFrom(SelectQuery<? extends Record> query) {
			query.addFrom(ACTIVITY);
		}

		@Override
		protected void joinTables(QuerySupplier query) {
			query.addJoin(USERS, JoinType.LEFT_OUTER_JOIN, ACTIVITY.USER_ID.eq(USERS.ID));
			query.addJoin(PROJECT, JoinType.JOIN, ACTIVITY.PROJECT_ID.eq(PROJECT.ID));
		}

		@Override
		protected Field<Long> idField() {
			return ACTIVITY.ID;
		}
	},

	INTEGRATION_TARGET(Integration.class, Arrays.asList(

			new CriteriaHolderBuilder().newBuilder(CRITERIA_ID, INTEGRATION.ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_ID, INTEGRATION.PROJECT_ID, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_INTEGRATION_TYPE, INTEGRATION_TYPE.GROUP_TYPE, JIntegrationGroupEnum.class)
					.get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_NAME, INTEGRATION_TYPE.NAME, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_NAME, PROJECT.NAME, String.class).get()
	)) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(INTEGRATION.ID,
					INTEGRATION.NAME,
					INTEGRATION.PROJECT_ID,
					INTEGRATION.TYPE,
					INTEGRATION.PARAMS,
					INTEGRATION.CREATOR,
					INTEGRATION.CREATION_DATE,
					INTEGRATION_TYPE.NAME,
					INTEGRATION_TYPE.GROUP_TYPE,
					PROJECT.NAME
			);
		}

		@Override
		protected void addFrom(SelectQuery<? extends Record> query) {
			query.addFrom(INTEGRATION);
		}

		@Override
		protected void joinTables(QuerySupplier query) {
			query.addJoin(INTEGRATION_TYPE, JoinType.JOIN, INTEGRATION.TYPE.eq(INTEGRATION_TYPE.ID));
			query.addJoin(PROJECT, JoinType.JOIN, INTEGRATION.PROJECT_ID.eq(PROJECT.ID));
		}

		@Override
		protected Field<Long> idField() {
			return DSL.cast(INTEGRATION.ID, Long.class);
		}
	},

	DASHBOARD_TARGET(Dashboard.class, Arrays.asList(

			new CriteriaHolderBuilder().newBuilder(CRITERIA_ID, DASHBOARD.ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_NAME, DASHBOARD.NAME, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_SHARED, SHAREABLE_ENTITY.SHARED, Boolean.class)
					.withAggregateCriteria(DSL.boolAnd(SHAREABLE_ENTITY.SHARED).toString())
					.get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_ID, SHAREABLE_ENTITY.PROJECT_ID, Long.class)
					.withAggregateCriteria(DSL.max(SHAREABLE_ENTITY.PROJECT_ID).toString())
					.get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_OWNER, SHAREABLE_ENTITY.OWNER, String.class)
					.withAggregateCriteria(DSL.max(SHAREABLE_ENTITY.OWNER).toString())
					.get()
	)) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(DASHBOARD.ID,
					DASHBOARD.NAME,
					DASHBOARD.DESCRIPTION,
					DASHBOARD.CREATION_DATE,
					DASHBOARD_WIDGET.WIDGET_OWNER,
					DASHBOARD_WIDGET.IS_CREATED_ON,
					DASHBOARD_WIDGET.WIDGET_ID,
					DASHBOARD_WIDGET.WIDGET_NAME,
					DASHBOARD_WIDGET.WIDGET_TYPE,
					DASHBOARD_WIDGET.WIDGET_HEIGHT,
					DASHBOARD_WIDGET.WIDGET_WIDTH,
					DASHBOARD_WIDGET.WIDGET_POSITION_X,
					DASHBOARD_WIDGET.WIDGET_POSITION_Y,
					DASHBOARD_WIDGET.SHARE,
					SHAREABLE_ENTITY.SHARED,
					SHAREABLE_ENTITY.PROJECT_ID,
					SHAREABLE_ENTITY.OWNER
			);
		}

		@Override
		protected void addFrom(SelectQuery<? extends Record> query) {
			query.addFrom(DASHBOARD);
		}

		@Override
		protected void joinTables(QuerySupplier query) {
			query.addJoin(DASHBOARD_WIDGET, JoinType.LEFT_OUTER_JOIN, DASHBOARD.ID.eq(DASHBOARD_WIDGET.DASHBOARD_ID));
			query.addJoin(SHAREABLE_ENTITY, JoinType.JOIN, DASHBOARD.ID.eq(SHAREABLE_ENTITY.ID));
			query.addJoin(ACL_OBJECT_IDENTITY, JoinType.JOIN, DASHBOARD.ID.cast(String.class).eq(ACL_OBJECT_IDENTITY.OBJECT_ID_IDENTITY));
			query.addJoin(ACL_CLASS, JoinType.JOIN, ACL_CLASS.ID.eq(ACL_OBJECT_IDENTITY.OBJECT_ID_CLASS));
			query.addJoin(ACL_ENTRY, JoinType.JOIN, ACL_ENTRY.ACL_OBJECT_IDENTITY.eq(ACL_OBJECT_IDENTITY.ID));
		}

		@Override
		protected Field<Long> idField() {
			return DASHBOARD.ID.cast(Long.class);
		}
	},

	WIDGET_TARGET(Widget.class, Arrays.asList(

			new CriteriaHolderBuilder().newBuilder(CRITERIA_ID, WIDGET.ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_NAME, WIDGET.NAME, String.class)
					.withAggregateCriteria(DSL.max(WIDGET.NAME).toString())
					.get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_DESCRIPTION, WIDGET.DESCRIPTION, String.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_SHARED, SHAREABLE_ENTITY.SHARED, Boolean.class)
					.withAggregateCriteria(DSL.boolAnd(SHAREABLE_ENTITY.SHARED).toString())
					.get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_ID, SHAREABLE_ENTITY.PROJECT_ID, Long.class).get(),
			new CriteriaHolderBuilder().newBuilder(CRITERIA_OWNER, SHAREABLE_ENTITY.OWNER, String.class)
					.withAggregateCriteria(DSL.max(SHAREABLE_ENTITY.OWNER).toString())
					.get()

	)) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(WIDGET.ID,
					WIDGET.NAME,
					WIDGET.WIDGET_TYPE,
					WIDGET.DESCRIPTION,
					WIDGET.ITEMS_COUNT,
					SHAREABLE_ENTITY.PROJECT_ID,
					SHAREABLE_ENTITY.SHARED,
					SHAREABLE_ENTITY.OWNER
			);
		}

		@Override
		protected void addFrom(SelectQuery<? extends Record> query) {
			query.addFrom(WIDGET);
		}

		@Override
		protected void joinTables(QuerySupplier query) {
			query.addJoin(SHAREABLE_ENTITY, JoinType.JOIN, WIDGET.ID.eq(SHAREABLE_ENTITY.ID));
			query.addJoin(ACL_OBJECT_IDENTITY, JoinType.JOIN, WIDGET.ID.cast(String.class).eq(ACL_OBJECT_IDENTITY.OBJECT_ID_IDENTITY));
			query.addJoin(ACL_CLASS, JoinType.JOIN, ACL_CLASS.ID.eq(ACL_OBJECT_IDENTITY.OBJECT_ID_CLASS));
			query.addJoin(ACL_ENTRY, JoinType.JOIN, ACL_ENTRY.ACL_OBJECT_IDENTITY.eq(ACL_OBJECT_IDENTITY.ID));
		}

		@Override
		protected Field<Long> idField() {
			return WIDGET.ID;
		}
	},

	USER_FILTER_TARGET(UserFilter.class,
			Arrays.asList(new CriteriaHolderBuilder().newBuilder(CRITERIA_ID, FILTER.ID, Long.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_NAME, FILTER.NAME, String.class).get(),
					new CriteriaHolderBuilder().newBuilder(CRITERIA_SHARED, SHAREABLE_ENTITY.SHARED, Boolean.class)
							.withAggregateCriteria(DSL.boolAnd(SHAREABLE_ENTITY.SHARED).toString())
							.get(),

					new CriteriaHolderBuilder().newBuilder(CRITERIA_PROJECT_ID, SHAREABLE_ENTITY.PROJECT_ID, Long.class)
							.withAggregateCriteria(DSL.max(SHAREABLE_ENTITY.PROJECT_ID).toString())
							.get(),

					new CriteriaHolderBuilder().newBuilder(CRITERIA_OWNER, SHAREABLE_ENTITY.OWNER, String.class)
							.withAggregateCriteria(DSL.max(SHAREABLE_ENTITY.OWNER).toString())
							.get()
			)
	) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(FILTER.ID,
					FILTER.NAME,
					FILTER.TARGET,
					FILTER.DESCRIPTION,
					FILTER_CONDITION.SEARCH_CRITERIA,
					FILTER_CONDITION.CONDITION,
					FILTER_CONDITION.VALUE,
					FILTER_CONDITION.NEGATIVE,
					FILTER_SORT.FIELD,
					FILTER_SORT.DIRECTION,
					SHAREABLE_ENTITY.SHARED,
					SHAREABLE_ENTITY.PROJECT_ID,
					SHAREABLE_ENTITY.OWNER
			);
		}

		@Override
		protected void addFrom(SelectQuery<? extends Record> query) {
			query.addFrom(FILTER);
		}

		@Override
		protected void joinTables(QuerySupplier query) {
			query.addJoin(SHAREABLE_ENTITY, JoinType.JOIN, FILTER.ID.eq(SHAREABLE_ENTITY.ID));
			query.addJoin(FILTER_CONDITION, JoinType.LEFT_OUTER_JOIN, FILTER.ID.eq(FILTER_CONDITION.FILTER_ID));
			query.addJoin(FILTER_SORT, JoinType.LEFT_OUTER_JOIN, FILTER.ID.eq(FILTER_SORT.FILTER_ID));
			query.addJoin(ACL_OBJECT_IDENTITY, JoinType.JOIN, FILTER.ID.cast(String.class).eq(ACL_OBJECT_IDENTITY.OBJECT_ID_IDENTITY));
			query.addJoin(ACL_CLASS, JoinType.JOIN, ACL_CLASS.ID.eq(ACL_OBJECT_IDENTITY.OBJECT_ID_CLASS));
			query.addJoin(ACL_ENTRY, JoinType.JOIN, ACL_ENTRY.ACL_OBJECT_IDENTITY.eq(ACL_OBJECT_IDENTITY.ID));
		}

		@Override
		protected Field<Long> idField() {
			return FILTER.ID;
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

	public QuerySupplier getQuery() {
		SelectQuery<? extends Record> query = DSL.select(idField().as(FILTERED_ID)).getQuery();
		addFrom(query);
		query.addGroupBy(idField());

		QuerySupplier querySupplier = new QuerySupplier(query);
		joinTablesForFilter(querySupplier);
		return querySupplier;
	}

	protected abstract Collection<? extends SelectField> selectFields();

	protected abstract void addFrom(SelectQuery<? extends Record> query);

	protected abstract void joinTables(QuerySupplier query);

	protected void joinTablesForFilter(QuerySupplier query) {
		joinTables(query);
	}

	protected abstract Field<Long> idField();

	public QuerySupplier wrapQuery(SelectQuery<? extends Record> query) {
		SelectQuery<Record> wrappedQuery = DSL.with(FILTERED_QUERY).as(query).select(selectFields()).getQuery();
		addFrom(wrappedQuery);
		QuerySupplier querySupplier = new QuerySupplier(wrappedQuery);
		querySupplier.addJoin(DSL.table(DSL.name(FILTERED_QUERY)),
				JoinType.JOIN,
				idField().eq(field(DSL.name(FILTERED_QUERY, FILTERED_ID), Long.class))
		);
		joinTables(querySupplier);
		return querySupplier;
	}

	public QuerySupplier wrapQuery(SelectQuery<? extends Record> query, String... excluding) {
		List<String> excludingFields = Lists.newArrayList(excluding);
		List<? extends SelectField> fields = selectFields().stream()
				.filter(it -> !excludingFields.contains(it.getName()))
				.collect(Collectors.toList());
		SelectQuery<Record> wrappedQuery = DSL.with(FILTERED_QUERY).as(query).select(fields).getQuery();
		addFrom(wrappedQuery);
		QuerySupplier querySupplier = new QuerySupplier(wrappedQuery);
		querySupplier.addJoin(DSL.table(DSL.name(FILTERED_QUERY)),
				JoinType.JOIN,
				idField().eq(field(DSL.name(FILTERED_QUERY, FILTERED_ID), Long.class))
		);
		joinTables(querySupplier);
		return querySupplier;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public List<CriteriaHolder> getCriteriaHolders() {
		return criteriaHolders;
	}

	public Optional<CriteriaHolder> getCriteriaByFilter(String filterCriteria) {
		/*
			creates criteria holder for statistics search criteria cause there
			can be custom statistics so we can't know it till this moment
		*/
		if (filterCriteria != null && filterCriteria.startsWith(STATISTICS_KEY)) {
			return Optional.of(new CriteriaHolderBuilder().newBuilder(filterCriteria,
					DSL.coalesce(DSL.max(STATISTICS.S_COUNTER).filterWhere(STATISTICS_FIELD.NAME.eq(filterCriteria)), 0).toString(),
					Long.class
			).get());
		}
		return criteriaHolders.stream().filter(holder -> holder.getFilterCriteria().equals(filterCriteria)).findAny();
	}

	public static FilterTarget findByClass(Class<?> clazz) {
		return Arrays.stream(values())
				.filter(val -> val.clazz.equals(clazz))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException(String.format("No target query builder for clazz %s", clazz)));
	}
}
