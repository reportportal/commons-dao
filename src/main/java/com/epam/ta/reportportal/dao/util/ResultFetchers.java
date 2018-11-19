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

import com.epam.ta.reportportal.entity.Activity;
import com.epam.ta.reportportal.entity.ItemAttribute;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.item.Parameter;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.log.Log;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectAttribute;
import com.epam.ta.reportportal.entity.user.User;
import com.google.common.collect.Maps;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.epam.ta.reportportal.dao.util.RecordMappers.*;
import static com.epam.ta.reportportal.jooq.Tables.*;

/**
 * Fetches results from db by JOOQ queries into Java objects.
 *
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public class ResultFetchers {

	private ResultFetchers() {
		//static only
	}

	/**
	 * Fetches records from db results into list of {@link Project} objects.
	 */
	public static final Function<Result<? extends Record>, List<Project>> PROJECT_FETCHER = records -> {
		Map<Long, Project> projects = Maps.newLinkedHashMap();
		records.forEach(record -> {
			Long id = record.get(PROJECT.ID);
			Project project;
			if (!projects.containsKey(id)) {
				project = RecordMappers.PROJECT_MAPPER.map(record);
			} else {
				project = projects.get(id);
			}
			project.getProjectAttributes()
					.add(new ProjectAttribute().withProject(project)
							.withAttribute(ATTRIBUTE_MAPPER.map(record))
							.withValue(record.get(PROJECT_ATTRIBUTE.VALUE)));
			project.getUsers().add(PROJECT_USER_MAPPER.map(record));
			projects.put(id, project);
		});
		return new ArrayList<>(projects.values());
	};

	/**
	 * Fetches records from db results into list of {@link Launch} objects.
	 */
	public static final Function<Result<? extends Record>, List<Launch>> LAUNCH_FETCHER = records -> {
		Map<Long, Launch> launches = Maps.newLinkedHashMap();
		records.forEach(record -> {
			Long id = record.get(LAUNCH.ID);
			Launch launch;
			if (!launches.containsKey(id)) {
				launch = RecordMappers.LAUNCH_RECORD_MAPPER.map(record);
			} else {
				launch = launches.get(id);
			}
			launch.getTags().add(record.into(ItemAttribute.class));
			launch.getStatistics().add(RecordMappers.STATISTICS_RECORD_MAPPER.map(record));
			launches.put(id, launch);
		});
		return new ArrayList<>(launches.values());
	};

	/**
	 * Fetches records from db results into list of {@link TestItem} objects.
	 */
	public static final Function<Result<? extends Record>, List<TestItem>> TEST_ITEM_FETCHER = records -> {
		Map<Long, TestItem> testItems = Maps.newLinkedHashMap();
		records.forEach(record -> {
			Long id = record.get(TEST_ITEM.ITEM_ID);
			TestItem testItem;
			if (!testItems.containsKey(id)) {
				testItem = RecordMappers.TEST_ITEM_RECORD_MAPPER.map(record);
			} else {
				testItem = testItems.get(id);
			}
			testItem.getTags().add(record.into(ItemAttribute.class));
			testItem.getParameters().add(record.into(Parameter.class));
			testItem.getItemResults().getStatistics().add(RecordMappers.STATISTICS_RECORD_MAPPER.map(record));
			testItems.put(id, testItem);
		});
		return new ArrayList<>(testItems.values());
	};

	/**
	 * Fetches records from db results into list of {@link com.epam.ta.reportportal.entity.log.Log} objects.
	 */
	public static final Function<Result<? extends Record>, List<Log>> LOG_FETCHER = records -> {
		Map<Long, Log> logs = Maps.newLinkedHashMap();
		records.forEach(record -> {
			Long id = record.get(LOG.ID);
			Log log;
			if (!logs.containsKey(id)) {
				log = record.into(Log.class);
			} else {
				log = logs.get(id);
			}
			logs.put(id, log);
		});
		return new ArrayList<>(logs.values());
	};

	/**
	 * Fetches records from db results into list of {@link Activity} objects.
	 */
	public static final Function<Result<? extends Record>, List<Activity>> ACTIVITY_FETCHER = records -> {
		Map<Long, Activity> activities = Maps.newLinkedHashMap();
		records.forEach(record -> {
			Long id = record.get(ACTIVITY.ID);
			Activity activity;
			if (!activities.containsKey(id)) {
				activity = RecordMappers.ACTIVITY_MAPPER.map(record);
			} else {
				activity = activities.get(id);
			}
			activities.put(id, activity);
		});
		return new ArrayList<>(activities.values());
	};

	/**
	 * Fetches records from db results into list of {@link com.epam.ta.reportportal.entity.integration.Integration} objects.
	 */
	public static final Function<Result<? extends Record>, List<Integration>> INTEGRATION_FETCHER = records -> {
		Map<Integer, Integration> integrations = Maps.newLinkedHashMap();
		records.forEach(record -> {
			Integer id = record.get(INTEGRATION.ID);
			Integration integration;
			if (!integrations.containsKey(id)) {
				integration = record.into(Integration.class);
			} else {
				integration = integrations.get(id);
			}
			integrations.put(id, integration);
		});
		return new ArrayList<>(integrations.values());
	};

	public static final Function<Result<? extends Record>, List<User>> USER_FETCHER = records -> {
		Map<Long, User> users = Maps.newLinkedHashMap();
		records.forEach(record -> {
			Long id = record.get(USERS.ID);
			User user;
			if (!users.containsKey(id)) {
				user = record.map(USER_MAPPER);
			} else {
				user = users.get(id);
			}
			user.getProjects().add(PROJECT_USER_MAPPER.map(record));
			users.put(id, user);
		});
		return new ArrayList<>(users.values());
	};

}
