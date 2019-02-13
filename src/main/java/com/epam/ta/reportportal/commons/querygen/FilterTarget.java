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

package com.epam.ta.reportportal.commons.querygen;

import com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant;
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
import static com.epam.ta.reportportal.commons.querygen.constant.ItemAttributeConstant.CRITERIA_ITEM_ATTRIBUTE_KEY;
import static com.epam.ta.reportportal.commons.querygen.constant.ItemAttributeConstant.CRITERIA_ITEM_ATTRIBUTE_VALUE;
import static com.epam.ta.reportportal.commons.querygen.constant.LaunchCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.LogCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.ProjectCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.CRITERIA_TYPE;
import static com.epam.ta.reportportal.entity.project.ProjectInfo.*;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static org.jooq.impl.DSL.choose;
import static org.jooq.impl.DSL.field;

public enum FilterTarget {

	PROJECT_TARGET(Project.class, Arrays.asList(

			new CriteriaHolder(CRITERIA_PROJECT_NAME, PROJECT.NAME.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_PROJECT_TYPE, PROJECT.PROJECT_TYPE.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_PROJECT_ATTRIBUTE_NAME, ATTRIBUTE.NAME.getQualifiedName().toString(), String.class),
			new CriteriaHolder(USERS_QUANTITY, USERS_QUANTITY, DSL.countDistinct(PROJECT_USER.USER_ID).toString(), Long.class),
			new CriteriaHolder(LAUNCHES_QUANTITY,
					LAUNCHES_QUANTITY,
					DSL.countDistinct(choose().when(LAUNCH.MODE.eq(JLaunchModeEnum.DEFAULT).and(LAUNCH.STATUS.ne(JStatusEnum.IN_PROGRESS)),
							LAUNCH.ID
					)).toString(),
					Long.class
			)
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
			query.addJoin(LAUNCH, JoinType.LEFT_OUTER_JOIN, PROJECT.ID.eq(LAUNCH.PROJECT_ID));
		}

		@Override
		protected Field<Long> idField() {
			return PROJECT.ID;
		}
	},

	PROJECT_INFO(ProjectInfo.class,
			Arrays.asList(new CriteriaHolder(CRITERIA_PROJECT_NAME, PROJECT.NAME.getQualifiedName().toString(), String.class),
					new CriteriaHolder(CRITERIA_PROJECT_TYPE, PROJECT.PROJECT_TYPE.getQualifiedName().toString(), String.class),
					new CriteriaHolder(CRITERIA_PROJECT_ORGANIZATION, PROJECT.PROJECT_TYPE.getQualifiedName().toString(), String.class),
					new CriteriaHolder(CRITERIA_PROJECT_CREATION_DATE,
							PROJECT.CREATION_DATE.getQualifiedName().toString(),
							Timestamp.class
					),
					new CriteriaHolder(USERS_QUANTITY, USERS_QUANTITY, DSL.countDistinct(PROJECT_USER.USER_ID).toString(), Long.class),
					new CriteriaHolder(LAST_RUN, LAST_RUN, DSL.max(LAUNCH.START_TIME).toString(), Long.class),
					new CriteriaHolder(LAUNCHES_QUANTITY,
							LAUNCHES_QUANTITY,
							DSL.countDistinct(choose().when(LAUNCH.MODE.eq(JLaunchModeEnum.DEFAULT)
											.and(LAUNCH.STATUS.ne(JStatusEnum.IN_PROGRESS)),
									LAUNCH.ID
							)).toString(),
							Long.class
					)
			)
	) {
		@Override
		public SelectQuery<? extends Record> getQuery() {
			SelectQuery<? extends Record> query = DSL.select(selectFields()).getQuery();
			joinTables(query);
			query.addGroupBy(PROJECT.ID, PROJECT.CREATION_DATE, PROJECT.NAME, PROJECT.PROJECT_TYPE);
			return query;
		}

		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(DSL.countDistinct(PROJECT_USER.USER_ID).as(USERS_QUANTITY),
					DSL.countDistinct(choose().when(LAUNCH.MODE.eq(JLaunchModeEnum.DEFAULT).and(LAUNCH.STATUS.ne(JStatusEnum.IN_PROGRESS)),
							LAUNCH.ID
					))
							.as(LAUNCHES_QUANTITY),
					DSL.max(LAUNCH.START_TIME).as(LAST_RUN),
					PROJECT.ID,
					PROJECT.CREATION_DATE,
					PROJECT.NAME,
					PROJECT.PROJECT_TYPE
			);
		}

		@Override
		protected void joinTables(SelectQuery<? extends Record> query) {
			query.addFrom(PROJECT);
			query.addJoin(PROJECT_USER, JoinType.LEFT_OUTER_JOIN, PROJECT.ID.eq(PROJECT_USER.PROJECT_ID));
			query.addJoin(LAUNCH, JoinType.LEFT_OUTER_JOIN, PROJECT.ID.eq(LAUNCH.PROJECT_ID));
		}

		@Override
		public SelectQuery<? extends Record> wrapQuery(SelectQuery<? extends Record> query) {
			throw new UnsupportedOperationException("Doesn't supported for Project Info query");
		}

		@Override
		public SelectQuery<? extends Record> wrapQuery(SelectQuery<? extends Record> query, String... excluding) {
			throw new UnsupportedOperationException("Doesn't supported for Project Info query");
		}

		@Override
		protected Field<Long> idField() {
			return PROJECT.ID;
		}
	},

	USER_TARGET(User.class, Arrays.asList(

			new CriteriaHolder(CRITERIA_ID, USERS.ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder(CRITERIA_USER, USERS.LOGIN.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_EMAIL, USERS.EMAIL.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_FULL_NAME, USERS.FULL_NAME.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_ROLE, USERS.ROLE.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_TYPE, USERS.TYPE.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_EXPIRED, USERS.EXPIRED.getQualifiedName().toString(), Boolean.class),
			new CriteriaHolder(CRITERIA_PROJECT_ID, PROJECT_USER.PROJECT_ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder(CRITERIA_PROJECT,
					PROJECT.NAME.getQualifiedName().toString(),
					DSL.arrayAgg(PROJECT.NAME).toString(),
					List.class
			),
			new CriteriaHolder(CRITERIA_LAST_LOGIN,
					DSL.cast(DSL.field(USERS.METADATA.getQualifiedName().toString() + "-> 'metadata' ->> 'last_login'"), Timestamp.class)
							.toString(),
					Timestamp.class
			)

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

			new CriteriaHolder(CRITERIA_ID, LAUNCH.ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder(CRITERIA_NAME, LAUNCH.NAME.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_DESCRIPTION, LAUNCH.DESCRIPTION.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_LAUNCH_UUID, LAUNCH.UUID.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_START_TIME, LAUNCH.START_TIME.getQualifiedName().toString(), Timestamp.class),
			new CriteriaHolder(CRITERIA_END_TIME, LAUNCH.END_TIME.getQualifiedName().toString(), Timestamp.class),
			new CriteriaHolder(CRITERIA_PROJECT_ID, LAUNCH.PROJECT_ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder(CRITERIA_USER_ID, LAUNCH.USER_ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder(CRITERIA_LAUNCH_NUMBER, LAUNCH.NUMBER.getQualifiedName().toString(), Integer.class),
			new CriteriaHolder(CRITERIA_LAST_MODIFIED, LAUNCH.LAST_MODIFIED.getQualifiedName().toString(), Timestamp.class),
			new CriteriaHolder(CRITERIA_LAUNCH_MODE, LAUNCH.MODE.getQualifiedName().toString(), JLaunchModeEnum.class),
			new CriteriaHolder(CRITERIA_LAUNCH_STATUS, LAUNCH.STATUS.getQualifiedName().toString(), JStatusEnum.class),
			new CriteriaHolder(CRITERIA_HAS_RETRIES, LAUNCH.HAS_RETRIES.getQualifiedName().toString(), Boolean.class),
			new CriteriaHolder(CRITERIA_ITEM_ATTRIBUTE_KEY,
					ITEM_ATTRIBUTE.KEY.getQualifiedName().toString(),
					DSL.arrayAggDistinct(ITEM_ATTRIBUTE.KEY).toString(),
					List.class
			),
			new CriteriaHolder(CRITERIA_ITEM_ATTRIBUTE_VALUE,
					ITEM_ATTRIBUTE.VALUE.getQualifiedName().toString(),
					DSL.arrayAggDistinct(ITEM_ATTRIBUTE.VALUE).toString(),
					List.class
			),
			new CriteriaHolder(CRITERIA_USER, USERS.LOGIN.getQualifiedName().toString(), String.class)
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
			Arrays.asList(new CriteriaHolder(CRITERIA_PROJECT_ID, LAUNCH.PROJECT_ID.getQualifiedName().toString(), Long.class),
					new CriteriaHolder(CRITERIA_ID, TEST_ITEM.ITEM_ID.getQualifiedName().toString(), Long.class),
					new CriteriaHolder(CRITERIA_NAME, TEST_ITEM.NAME.getQualifiedName().toString(), String.class),
					new CriteriaHolder(TestItemCriteriaConstant.CRITERIA_TYPE,
							TEST_ITEM.TYPE.getQualifiedName().toString(),
							JTestItemTypeEnum.class
					),
					new CriteriaHolder(CRITERIA_START_TIME, TEST_ITEM.START_TIME.getQualifiedName().toString(), Timestamp.class),
					new CriteriaHolder(CRITERIA_DESCRIPTION, TEST_ITEM.DESCRIPTION.getQualifiedName().toString(), String.class),
					new CriteriaHolder(CRITERIA_LAST_MODIFIED, TEST_ITEM.LAST_MODIFIED.getQualifiedName().toString(), String.class),
					new CriteriaHolder(CRITERIA_PATH, TEST_ITEM.PATH.getQualifiedName().toString(), Long.class),
					new CriteriaHolder(CRITERIA_UNIQUE_ID, TEST_ITEM.UNIQUE_ID.getQualifiedName().toString(), String.class),
					new CriteriaHolder(CRITERIA_PARENT_ID, TEST_ITEM.PARENT_ID.getQualifiedName().toString(), Long.class),
					new CriteriaHolder(CRITERIA_HAS_CHILDREN, TEST_ITEM.HAS_CHILDREN.getQualifiedName().toString(), Boolean.class),
					new CriteriaHolder(CRITERIA_HAS_RETRIES, TEST_ITEM.HAS_RETRIES.getQualifiedName().toString(), Boolean.class),

					new CriteriaHolder(CRITERIA_STATUS, TEST_ITEM_RESULTS.STATUS.getQualifiedName().toString(), JStatusEnum.class),
					new CriteriaHolder(CRITERIA_END_TIME, TEST_ITEM_RESULTS.END_TIME.getQualifiedName().toString(), Timestamp.class),
					new CriteriaHolder(CRITERIA_DURATION, TEST_ITEM_RESULTS.DURATION.getQualifiedName().toString(), Long.class),

					new CriteriaHolder(CRITERIA_PARAMETER_KEY, PARAMETER.KEY.getQualifiedName().toString(), String.class),
					new CriteriaHolder(CRITERIA_PARAMETER_VALUE, PARAMETER.VALUE.getQualifiedName().toString(), String.class),
					new CriteriaHolder(CRITERIA_ISSUE_AUTO_ANALYZED, ISSUE.AUTO_ANALYZED.getQualifiedName().toString(), Boolean.class),
					new CriteriaHolder(CRITERIA_ISSUE_IGNORE_ANALYZER, ISSUE.IGNORE_ANALYZER.getQualifiedName().toString(), Boolean.class),
					new CriteriaHolder(CRITERIA_ISSUE_COMMENT, ISSUE.ISSUE_DESCRIPTION.getQualifiedName().toString(), String.class),
					new CriteriaHolder(CRITERIA_ISSUE_LOCATOR, ISSUE_TYPE.LOCATOR.getQualifiedName().toString(), String.class),

					new CriteriaHolder(CRITERIA_LAUNCH_ID, TEST_ITEM.LAUNCH_ID.getQualifiedName().toString(), Long.class),
					new CriteriaHolder(CRITERIA_LAUNCH_MODE, LAUNCH.MODE.getQualifiedName().toString(), JLaunchModeEnum.class),
					new CriteriaHolder(CRITERIA_PARENT_ID, TEST_ITEM.PARENT_ID.getQualifiedName().toString(), Long.class),
					new CriteriaHolder(CRITERIA_ITEM_ATTRIBUTE_KEY,
							ITEM_ATTRIBUTE.KEY.getQualifiedName().toString(),
							DSL.arrayAggDistinct(ITEM_ATTRIBUTE.KEY).toString(),
							List.class
					),
					new CriteriaHolder(CRITERIA_ITEM_ATTRIBUTE_VALUE,
							ITEM_ATTRIBUTE.VALUE.getQualifiedName().toString(),
							DSL.arrayAggDistinct(ITEM_ATTRIBUTE.VALUE).toString(),
							List.class
					),
					new CriteriaHolder(CRITERIA_ISSUE_TYPE, ISSUE_TYPE.LOCATOR.getQualifiedName().toString(), String.class)
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
					TEST_ITEM_RESULTS.DURATION,
					ITEM_ATTRIBUTE.KEY,
					ITEM_ATTRIBUTE.VALUE,
					ITEM_ATTRIBUTE.SYSTEM,
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
			query.addJoin(STATISTICS_FIELD, JoinType.LEFT_OUTER_JOIN, STATISTICS.STATISTICS_FIELD_ID.eq(STATISTICS_FIELD.SF_ID));
			query.addJoin(LAUNCH, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.LAUNCH_ID.eq(LAUNCH.ID));
			query.addJoin(TEST_ITEM_RESULTS, JoinType.LEFT_OUTER_JOIN, TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID));
			query.addJoin(ISSUE, JoinType.LEFT_OUTER_JOIN, TEST_ITEM_RESULTS.RESULT_ID.eq(ISSUE.ISSUE_ID));
			query.addJoin(ISSUE_TYPE, JoinType.LEFT_OUTER_JOIN, ISSUE.ISSUE_TYPE.eq(ISSUE_TYPE.ID));
			query.addJoin(ISSUE_GROUP, JoinType.LEFT_OUTER_JOIN, ISSUE_TYPE.ISSUE_GROUP_ID.eq(ISSUE_GROUP.ISSUE_GROUP_ID));

		}
	},

	LOG_TARGET(Log.class, Arrays.asList(

			new CriteriaHolder(CRITERIA_LOG_ID, LOG.ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder(CRITERIA_LOG_TIME, LOG.LOG_TIME.getQualifiedName().toString(), Timestamp.class),
			new CriteriaHolder(CRITERIA_LAST_MODIFIED, LOG.LAST_MODIFIED.getQualifiedName().toString(), Timestamp.class),
			new CriteriaHolder(CRITERIA_LOG_LEVEL, LOG.LOG_LEVEL.getQualifiedName().toString(), LogLevel.class),
			new CriteriaHolder(CRITERIA_LOG_MESSAGE, LOG.LOG_MESSAGE.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_LOG_BINARY_CONTENT, LOG.ATTACHMENT.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_TEST_ITEM_ID, LOG.ITEM_ID.getQualifiedName().toString(), Long.class)
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

			new CriteriaHolder(CRITERIA_ID, ACTIVITY.ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder(CRITERIA_PROJECT_ID, ACTIVITY.PROJECT_ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder(CRITERIA_PROJECT_NAME, PROJECT.NAME.getQualifiedName().toString(), Long.class),
			new CriteriaHolder(CRITERIA_USER_ID, ACTIVITY.USER_ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder(CRITERIA_ENTITY, ACTIVITY.ENTITY.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_ACTION, ACTIVITY.ACTION.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_CREATION_DATE, ACTIVITY.CREATION_DATE.getQualifiedName().toString(), Timestamp.class),
			new CriteriaHolder(CRITERIA_OBJECT_ID, ACTIVITY.OBJECT_ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder(CRITERIA_USER, USERS.LOGIN.getQualifiedName().toString(), String.class)
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

			new CriteriaHolder(CRITERIA_PROJECT_ID, INTEGRATION.PROJECT_ID.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_INTEGRATION_TYPE,
					INTEGRATION_TYPE.GROUP_TYPE.getQualifiedName().toString(),
					JIntegrationGroupEnum.class
			),
			new CriteriaHolder(CRITERIA_NAME, INTEGRATION_TYPE.NAME.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_PROJECT_NAME, PROJECT.NAME.getQualifiedName().toString(), String.class)
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

	DASHBOARD_TARGET(Dashboard.class, Arrays.asList(

			new CriteriaHolder(CRITERIA_ID, DASHBOARD.ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder(CRITERIA_NAME, DASHBOARD.NAME.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_SHARED,
					SHAREABLE_ENTITY.SHARED.getQualifiedName().toString(),
					DSL.boolAnd(SHAREABLE_ENTITY.SHARED).toString(),
					Boolean.class
			),
			new CriteriaHolder(CRITERIA_PROJECT_ID,
					SHAREABLE_ENTITY.PROJECT_ID.getQualifiedName().toString(),
					DSL.max(SHAREABLE_ENTITY.PROJECT_ID).toString(),
					Long.class
			),
			new CriteriaHolder(CRITERIA_OWNER,
					SHAREABLE_ENTITY.OWNER.getQualifiedName().toString(),
					DSL.max(SHAREABLE_ENTITY.OWNER).toString(),
					String.class
			)
	)) {
		@Override
		protected Collection<? extends SelectField> selectFields() {
			return Lists.newArrayList(DASHBOARD.ID,
					DASHBOARD.NAME,
					DASHBOARD.DESCRIPTION,
					DASHBOARD.CREATION_DATE,
					DASHBOARD_WIDGET.WIDGET_ID,
					DASHBOARD_WIDGET.WIDGET_HEIGHT,
					DASHBOARD_WIDGET.WIDGET_WIDTH,
					DASHBOARD_WIDGET.WIDGET_POSITION_X,
					DASHBOARD_WIDGET.WIDGET_POSITION_Y,
					SHAREABLE_ENTITY.SHARED,
					SHAREABLE_ENTITY.PROJECT_ID,
					SHAREABLE_ENTITY.OWNER
			);
		}

		@Override
		protected void joinTables(SelectQuery<? extends Record> query) {
			query.addFrom(DASHBOARD);
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

			new CriteriaHolder(CRITERIA_ID, WIDGET.ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder(CRITERIA_NAME, WIDGET.NAME.getQualifiedName().toString(), DSL.max(WIDGET.NAME).toString(), String.class),
			new CriteriaHolder(CRITERIA_DESCRIPTION, WIDGET.DESCRIPTION.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_SHARED,
					SHAREABLE_ENTITY.SHARED.getQualifiedName().toString(),
					DSL.boolAnd(SHAREABLE_ENTITY.SHARED).toString(),
					Boolean.class
			),
			new CriteriaHolder(CRITERIA_PROJECT_ID, SHAREABLE_ENTITY.PROJECT_ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder(CRITERIA_OWNER,
					SHAREABLE_ENTITY.OWNER.getQualifiedName().toString(),
					DSL.max(SHAREABLE_ENTITY.OWNER).toString(),
					String.class
			)

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
		protected void joinTables(SelectQuery<? extends Record> query) {
			query.addFrom(WIDGET);
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

	USER_FILTER_TARGET(UserFilter.class, Arrays.asList(

			new CriteriaHolder(CRITERIA_ID, FILTER.ID.getQualifiedName().toString(), Long.class),
			new CriteriaHolder(CRITERIA_NAME, FILTER.NAME.getQualifiedName().toString(), String.class),
			new CriteriaHolder(CRITERIA_SHARED,
					SHAREABLE_ENTITY.SHARED.getQualifiedName().toString(),
					DSL.boolAnd(SHAREABLE_ENTITY.SHARED).toString(),
					Boolean.class
			),
			new CriteriaHolder(CRITERIA_PROJECT_ID,
					SHAREABLE_ENTITY.PROJECT_ID.getQualifiedName().toString(),
					DSL.max(SHAREABLE_ENTITY.PROJECT_ID).toString(),
					Long.class
			),
			new CriteriaHolder(CRITERIA_OWNER,
					SHAREABLE_ENTITY.OWNER.getQualifiedName().toString(),
					DSL.max(SHAREABLE_ENTITY.OWNER).toString(),
					String.class
			)
	)) {
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
		protected void joinTables(SelectQuery<? extends Record> query) {
			query.addFrom(FILTER);
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
		/*
			creates criteria holder for statistics search criteria cause there
			can be custom statistics so we can't know it till this moment
		*/
		if (filterCriteria != null && filterCriteria.startsWith(STATISTICS_KEY)) {
			return Optional.of(new CriteriaHolder(filterCriteria,
					DSL.coalesce(DSL.max(STATISTICS.S_COUNTER).filterWhere(STATISTICS_FIELD.NAME.eq(filterCriteria)), 0).toString(),
					Long.class
			));
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
