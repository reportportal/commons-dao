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

import com.epam.ta.reportportal.commons.ReportPortalUser;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.entity.activity.Activity;
import com.epam.ta.reportportal.entity.dashboard.Dashboard;
import com.epam.ta.reportportal.entity.filter.FilterSort;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.item.*;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.log.Log;
import com.epam.ta.reportportal.entity.pattern.PatternTemplateTestItem;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectAttribute;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.entity.user.ProjectUser;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.epam.ta.reportportal.entity.widget.Widget;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.jooq.tables.JTestItem;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.ID;
import static com.epam.ta.reportportal.dao.util.RecordMappers.*;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static com.epam.ta.reportportal.jooq.tables.JProject.PROJECT;
import static com.epam.ta.reportportal.jooq.tables.JProjectUser.PROJECT_USER;
import static com.epam.ta.reportportal.jooq.tables.JTestItem.TEST_ITEM;
import static com.epam.ta.reportportal.jooq.tables.JUsers.USERS;
import static java.util.Optional.ofNullable;

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
			ofNullable(record.field(PROJECT_USER.PROJECT_ROLE)).ifPresent(f -> ofNullable(record.get(f)).ifPresent(field -> {
				Set<ProjectUser> projectUsers = ofNullable(project.getUsers()).orElseGet(Sets::newHashSet);
				projectUsers.add(PROJECT_USER_MAPPER.map(record));
				project.setUsers(projectUsers);
			}));

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
			ITEM_ATTRIBUTE_MAPPER.apply(record).ifPresent(it -> launch.getAttributes().add(it));
			launch.getStatistics().add(RecordMappers.STATISTICS_RECORD_MAPPER.map(record));
			launches.put(id, launch);
		});
		return new ArrayList<>(launches.values());
	};

	public static final BiConsumer<List<TestItem>, Result<? extends Record>> RETRIES_FETCHER = (testItems, retries) -> {
		Map<Long, TestItem> retriesMap = Maps.newLinkedHashMap();
		retries.forEach(record -> {
			Long id = record.get(TEST_ITEM.ITEM_ID);
			TestItem testItem;
			if (!retriesMap.containsKey(id)) {
				testItem = record.into(TestItem.class);
				testItem.setItemId(id);
				testItem.setName(record.get(TEST_ITEM.NAME));
				testItem.setItemResults(record.into(TestItemResults.class));
			} else {
				testItem = retriesMap.get(id);
			}
			ITEM_ATTRIBUTE_MAPPER.apply(record).ifPresent(it -> testItem.getAttributes().add(it));
			Optional.ofNullable(record.get(PARAMETER.ITEM_ID)).ifPresent(tag -> {
				testItem.getParameters().add(record.into(Parameter.class));
			});
			retriesMap.put(id, testItem);
		});

		Map<Long, TestItem> items = testItems.stream()
				.collect(HashMap::new, (map, item) -> map.put(item.getItemId(), item), HashMap::putAll);
		retriesMap.values()
				.stream()
				.collect(Collectors.groupingBy(TestItem::getRetryOf, Collectors.toCollection(LinkedHashSet::new)))
				.forEach((key, value) -> items.get(key).setRetries(value));
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
			ITEM_ATTRIBUTE_MAPPER.apply(record).ifPresent(it -> testItem.getAttributes().add(it));
			ofNullable(record.get(PARAMETER.ITEM_ID)).ifPresent(it -> testItem.getParameters().add(record.into(Parameter.class)));
			testItem.getItemResults().getStatistics().add(RecordMappers.STATISTICS_RECORD_MAPPER.map(record));
			PATTERN_TEMPLATE_NAME_RECORD_MAPPER.apply(record)
					.ifPresent(patternTemplate -> testItem.getPatternTemplateTestItems()
							.add(new PatternTemplateTestItem(patternTemplate, testItem)));
			if (testItem.getItemResults().getIssue() != null) {
				TICKET_MAPPER.apply(record).ifPresent(ticket -> testItem.getItemResults().getIssue().getTickets().add(ticket));
			}
			testItems.put(id, testItem);
		});
		return new ArrayList<>(testItems.values());
	};

	public static final Function<Result<? extends Record>, Map<Long, PathName>> PATH_NAMES_FETCHER = result -> {
		Map<Long, PathName> content = Maps.newHashMap();
		JTestItem parentItem = TEST_ITEM.as("parent");
		JTestItem childItem = TEST_ITEM.as("child");
		result.forEach(record -> {
			Long childItemId = record.get(childItem.ITEM_ID);
			PathName pathName = content.computeIfAbsent(childItemId, k -> {
				LaunchPathName launchPathName = new LaunchPathName(record.get(LAUNCH.NAME), record.get(LAUNCH.NUMBER));
				return new PathName(launchPathName, Lists.newArrayList());
			});

			ofNullable(record.get(parentItem.ITEM_ID)).ifPresent(parentItemId -> {
				String parentName = record.get(parentItem.NAME);
				pathName.getItemPaths().add(new ItemPathName(parentItemId, parentName));
			});
		});

		return content;
	};

	/**
	 * Fetches records from db results into list of {@link com.epam.ta.reportportal.entity.log.Log} objects.
	 */
	public static final Function<Result<? extends Record>, List<Log>> LOG_FETCHER = records -> {
		Map<Long, Log> logs = Maps.newLinkedHashMap();
		records.forEach(record -> {
			Long id = record.get(LOG.ID);
			if (!logs.containsKey(id)) {
				logs.put(id, LOG_MAPPER.map(record));
			}
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
			if (ofNullable(record.get(PROJECT_USER.PROJECT_ROLE)).isPresent()) {
				user.getProjects().add(PROJECT_USER_MAPPER.map(record));
			}

			users.put(id, user);
		});
		return new ArrayList<>(users.values());
	};

	public static final Function<Result<? extends Record>, List<UserFilter>> USER_FILTER_FETCHER = result -> {
		Map<Long, UserFilter> userFilterMap = Maps.newLinkedHashMap();
		result.forEach(r -> {
			Long userFilterID = r.get(ID, Long.class);
			UserFilter userFilter;
			if (userFilterMap.containsKey(userFilterID)) {
				userFilter = userFilterMap.get(userFilterID);
			} else {
				userFilter = r.into(UserFilter.class);
				userFilter.setOwner(r.get(SHAREABLE_ENTITY.OWNER));
				userFilter.setShared(r.get(SHAREABLE_ENTITY.SHARED));
				Project project = new Project();
				project.setId(r.get(SHAREABLE_ENTITY.PROJECT_ID, Long.class));
				userFilter.setProject(project);
			}
			userFilter.getFilterCondition().add(r.into(FilterCondition.class));
			userFilter.getFilterSorts().add(r.into(FilterSort.class));
			userFilterMap.put(userFilterID, userFilter);
		});
		return Lists.newArrayList(userFilterMap.values());
	};

	public static final Function<Result<? extends Record>, List<Dashboard>> DASHBOARD_FETCHER = result -> {
		Map<Long, Dashboard> dashboardMap = Maps.newLinkedHashMap();
		result.forEach(r -> {
			Long dashboardId = r.get(ID, Long.class);
			Dashboard dashboard;
			if (dashboardMap.containsKey(dashboardId)) {
				dashboard = dashboardMap.get(dashboardId);
			} else {
				dashboard = r.into(Dashboard.class);
				dashboard.setOwner(r.get(SHAREABLE_ENTITY.OWNER));
				dashboard.setShared(r.get(SHAREABLE_ENTITY.SHARED));
				Project project = new Project();
				project.setId(r.get(SHAREABLE_ENTITY.PROJECT_ID, Long.class));
				dashboard.setProject(project);
			}
			DASHBOARD_WIDGET_MAPPER.apply(r).ifPresent(it -> dashboard.getDashboardWidgets().add(it));
			dashboardMap.put(dashboardId, dashboard);
		});
		return Lists.newArrayList(dashboardMap.values());
	};

	public static final Function<Result<? extends Record>, List<Widget>> WIDGET_FETCHER = result -> {
		Map<Long, Widget> widgetMap = Maps.newLinkedHashMap();
		result.forEach(r -> {
			Long widgetId = r.get(ID, Long.class);
			Widget widget;
			if (widgetMap.containsKey(widgetId)) {
				widget = widgetMap.get(widgetId);
			} else {
				widget = r.into(Widget.class);
				widget.setOwner(r.get(SHAREABLE_ENTITY.OWNER));
				widget.setShared(r.get(SHAREABLE_ENTITY.SHARED));
				Project project = new Project();
				project.setId(r.get(SHAREABLE_ENTITY.PROJECT_ID, Long.class));
				widget.setProject(project);
			}
			widgetMap.put(widgetId, widget);
		});
		return Lists.newArrayList(widgetMap.values());
	};

	public static final Function<Result<? extends Record>, List<NestedItem>> NESTED_ITEM_FETCHER = result -> {
		List<NestedItem> nestedItems = Lists.newArrayListWithExpectedSize(result.size());
		result.forEach(record -> nestedItems.add(new NestedItem(record.get("id", Long.class), record.get("type", String.class))));
		return nestedItems;
	};

	public static final Function<Result<? extends Record>, ReportPortalUser> REPORTPORTAL_USER_FETCHER = records -> {
		if (!CollectionUtils.isEmpty(records)) {
			ReportPortalUser user = ReportPortalUser.userBuilder()
					.withUserName(records.get(0).get(USERS.LOGIN))
					.withPassword(ofNullable(records.get(0).get(USERS.PASSWORD)).orElse(""))
					.withAuthorities(Collections.emptyList())
					.withUserId(records.get(0).get(USERS.ID))
					.withUserRole(UserRole.findByName(records.get(0).get(USERS.ROLE))
							.orElseThrow(() -> new ReportPortalException(ErrorType.UNCLASSIFIED_REPORT_PORTAL_ERROR)))
					.withProjectDetails(new HashMap<>(records.size()))
					.withEmail(records.get(0).get(USERS.EMAIL))
					.build();
			records.forEach(record -> {
				ReportPortalUser.ProjectDetails projectDetails = new ReportPortalUser.ProjectDetails(record.get(PROJECT_USER.PROJECT_ID,
						Long.class
				),
						record.get(PROJECT.NAME, String.class),
						ProjectRole.forName(record.get(PROJECT_USER.PROJECT_ROLE, String.class))
								.orElseThrow(() -> new ReportPortalException(ErrorType.ROLE_NOT_FOUND,
										record.get(PROJECT_USER.PROJECT_ROLE, String.class)
								))
				);
				user.getProjectDetails().put(record.get(PROJECT.NAME), projectDetails);
			});
			return user;
		}
		return null;
	};

}
