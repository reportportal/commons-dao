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

package com.epam.ta.reportportal.dao.constant;

/**
 * @author Ivan Budaev
 */
public class WidgetContentRepositoryConstants {

	public static final String EXECUTIONS_TOTAL = "statistics$executions$total";
	public static final String EXECUTIONS_FAILED = "statistics$executions$failed";
	public static final String EXECUTIONS_SKIPPED = "statistics$executions$skipped";
	public static final String EXECUTIONS_PASSED = "statistics$executions$passed";

	public static final String DEFECTS_AUTOMATION_BUG_TOTAL = "statistics$defects$automation_bug$total";
	public static final String DEFECTS_PRODUCT_BUG_TOTAL = "statistics$defects$product_bug$total";
	public static final String DEFECTS_NO_DEFECT_TOTAL = "statistics$defects$no_defect$total";
	public static final String DEFECTS_SYSTEM_ISSUE_TOTAL = "statistics$defects$system_issue$total";
	public static final String DEFECTS_TO_INVESTIGATE_TOTAL = "statistics$defects$to_investigate$total";

	public static final String EXECUTION_STATS = "execution_stats";
	public static final String TOTAL = "total";
	public static final String EXECUTIONS_KEY = "executions";
	public static final String DEFECTS_KEY = "defects";
	public static final String TABLE_COLUMN_KEY = "columns";
	public static final String ISSUE_GROUP_KEY = "groups";

	public static final String STATISTICS_TABLE = "statistics_table";
	public static final String 	STATISTICS_COUNTER = "s_counter";
	public static final String SF_NAME = "sf_name";

	/*Constants for result query mapping*/
	public static final Double PERCENTAGE_MULTIPLIER = 100d;
	public static final Integer ZERO_QUERY_VALUE = 0;
	public static final String LAUNCH_ID = "launch_id";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String TARGET = "target";
	public static final String ID = "id";
	public static final String NUMBER = "number";
	public static final String START_TIME = "start_time";
	public static final String END_TIME = "end_time";

	/* Most failed widget constants */
	public static final String HISTORY = "history";
	public static final String CRITERIA = "criteria";
	public static final String STATUS_HISTORY = "status_history";
	public static final String START_TIME_HISTORY = "start_time_history";

	/* Overall statistics widget constants */
	public static final String LAUNCHES = "launches";

	/*Flaky test table widget constants*/
	public static final String FLAKY_TABLE_RESULTS = "flaky";

	/*Activity table widget constants*/
	public static final String ACTIVITIES = "activities";
	public static final String ACTION_TYPE = "action_type";
	public static final String ENTITY = "entity";
	public static final String LAST_MODIFIED = "last_modified";
	public static final String USER_LOGIN = "user_login";
	public static final String PROJECT_ID = "project_id";
	public static final String PROJECT_NAME = "project_name";
	public static final String ACTIVITY_TYPE = "activity_type";

	/*Investigation widget constants*/
	public static final String INVESTIGATED = "investigated";
	public static final String TO_INVESTIGATE = "to_investigate";

	/*Launch pass widget constants*/
	public static final String PASSED = "passed";

	/*Cases trend widget constants*/
	public static final String DELTA = "delta";

	/*Launches comparison widget constants*/
	public static final String ISSUE_PERCENTAGE = "issuePercentage";

	/*Launches duration widget constants*/
	public static final String ITEMS = "items";
	public static final String DURATION = "duration";

	/*Not passed cases widget constants*/
	public static final String PERCENTAGE = "percentage";
	public static final String NOT_PASSED_STATISTICS_KEY = "% (Failed+Skipped)/Total";

	/*Unique bugs table widget constants*/
	public static final String TICKET_ID = "ticketId";
	public static final String SUBMIT_DATE = "submitDate";
	public static final String URL = "url";
	public static final String TEST_ITEM_ID = "testItemId";
	public static final String TEST_ITEM_NAME = "testItemName";
	public static final String SUBMITTER = "submitter";

	/*Flaky cases table widget constants*/
	public static final String UNIQUE_ID = "unique_id";
	public static final String ITEM_NAME = "itemName";
	public static final String STATUSES = "statuses";
	public static final String SWITCH_FLAG = "switch_flag";
	public static final String FLAKY_COUNT = "flakyCount";

	/*Cumulative trend widget constants*/
	public static final Integer LAUNCHES_COUNT = 100;
	public static final String LAUNCHES_SUB_QUERY = "launches_sub_query";
	public static final String LIKE_CONDITION_SYMBOL = "%";

	/*Product status widget constants*/
	public static final String TAG_VALUE = "tag_value";
	public static final String FILTER_NAME = "filter_name";
	public static final String TAG_TABLE = "tag_table";
	public static final String TAG_VALUES = "tag_values";
	public static final String PASSING_RATE = "passing_rate";
	public static final String SUM = "sum";
	public static final String AVERAGE_PASSING_RATE = "average_passing_rate";

	/*Sub-query fields*/
	public static final String SUBQUERY_LAUNCH_ID = "l_id";
	public static final String SUBQUERY_USER_ID = "u_id";
	public static final String SUBQUERY_TEST_ITEM_ID = "ti_id";

}
