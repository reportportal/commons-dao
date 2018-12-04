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
import com.epam.ta.reportportal.entity.ActivityDetails;
import com.epam.ta.reportportal.entity.ItemAttribute;
import com.epam.ta.reportportal.entity.Metadata;
import com.epam.ta.reportportal.entity.attribute.Attribute;
import com.epam.ta.reportportal.entity.enums.ProjectType;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.item.TestItemResults;
import com.epam.ta.reportportal.entity.item.issue.IssueEntity;
import com.epam.ta.reportportal.entity.item.issue.IssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.entity.statistics.Statistics;
import com.epam.ta.reportportal.entity.statistics.StatisticsField;
import com.epam.ta.reportportal.entity.user.ProjectUser;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.widget.Widget;
import com.epam.ta.reportportal.entity.widget.WidgetOptions;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.jooq.Tables;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.epam.ta.reportportal.ws.model.SharedEntity;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.epam.ta.reportportal.jooq.Tables.*;
import static com.epam.ta.reportportal.jooq.tables.JActivity.ACTIVITY;
import static com.epam.ta.reportportal.jooq.tables.JUsers.USERS;
import static java.util.Optional.ofNullable;

/**
 * Set of record mappers that helps to convert the result of jooq queries into
 * Java objects
 *
 * @author Pavel Bortnik
 */
public class RecordMappers {

	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * Maps record into {@link Attribute} object
	 */
	public static final RecordMapper<? super Record, Attribute> ATTRIBUTE_MAPPER = record -> {
		Attribute attribute = new Attribute();
		ofNullable(record.field(ATTRIBUTE.ID)).ifPresent(f -> attribute.setId(record.get(f)));
		ofNullable(record.field(ATTRIBUTE.NAME)).ifPresent(f -> attribute.setName(record.get(f)));

		return attribute;
	};

	/**
	 * Maps record into {@link IssueType} object
	 */
	public static final RecordMapper<? super Record, IssueType> ISSUE_TYPE_RECORD_MAPPER = r -> {
		IssueType type = r.into(IssueType.class);
		type.setIssueGroup(r.into(IssueGroup.class));
		return type;
	};

	/**
	 * Maps record into {@link IssueEntity} object
	 */
	public static final RecordMapper<? super Record, IssueEntity> ISSUE_RECORD_MAPPER = r -> {
		IssueEntity issueEntity = r.into(IssueEntity.class);
		issueEntity.setIssueType(ISSUE_TYPE_RECORD_MAPPER.map(r));
		return issueEntity;
	};

	/**
	 * Maps record into {@link Project} object
	 */
	public static final RecordMapper<? super Record, Project> PROJECT_MAPPER = r -> {
		Project project = r.into(PROJECT.ID, PROJECT.NAME, PROJECT.CREATION_DATE, PROJECT.PROJECT_TYPE).into(Project.class);
		ofNullable(r.field(PROJECT.METADATA)).ifPresent(f -> {
			String metaDataString = r.get(f, String.class);
			ofNullable(metaDataString).ifPresent(md -> {
				try {
					Metadata metadata = objectMapper.readValue(metaDataString, Metadata.class);
					project.setMetadata(metadata);
				} catch (IOException e) {
					throw new ReportPortalException(ErrorType.UNCLASSIFIED_REPORT_PORTAL_ERROR, "Error during parsing user metadata");
				}
			});
		});

		return project;
	};

	/**
	 * Maps record into {@link TestItemResults} object
	 */
	public static final RecordMapper<? super Record, TestItemResults> TEST_ITEM_RESULTS_RECORD_MAPPER = r -> {
		TestItemResults results = r.into(TestItemResults.class);
		results.setIssue(ISSUE_RECORD_MAPPER.map(r));
		return results;
	};

	public static final RecordMapper<? super Record, Statistics> STATISTICS_RECORD_MAPPER = r -> {
		Statistics statistics = new Statistics();

		StatisticsField statisticsField = new StatisticsField();
		statisticsField.setName(r.get(STATISTICS_FIELD.NAME));

		statistics.setStatisticsField(statisticsField);
		statistics.setCounter(ofNullable(r.get(Tables.STATISTICS.S_COUNTER)).orElse(0));
		return statistics;
	};

	/**
	 * Maps record into {@link TestItem} object
	 */
	public static final RecordMapper<? super Record, TestItem> TEST_ITEM_RECORD_MAPPER = r -> {
		TestItem testItem = r.into(TestItem.class);
		testItem.setName(r.get(TEST_ITEM.NAME));
		testItem.setItemResults(TEST_ITEM_RESULTS_RECORD_MAPPER.map(r));
		testItem.setLaunch(new Launch(r.get(TEST_ITEM.LAUNCH_ID)));
		testItem.setParent(new TestItem(r.get(TEST_ITEM.PARENT_ID)));
		return testItem;
	};

	/**
	 * Maps record into {@link Launch} object
	 */
	public static final RecordMapper<? super Record, Launch> LAUNCH_RECORD_MAPPER = r -> {
		Launch launch = r.into(Launch.class);
		launch.setId(r.get(LAUNCH.ID));
		launch.setName(r.get(LAUNCH.NAME));
		launch.setUser(r.into(User.class));
		return launch;
	};

	public static final RecordMapper<Record, User> USER_MAPPER = r -> {
		User user = r.into(USERS.fieldStream()
				.filter(f -> !USERS.METADATA.getQualifiedName().toString().equalsIgnoreCase(f.getQualifiedName().toString())
						|| !USERS.METADATA.getName().equalsIgnoreCase(f.getName()))
				.toArray(Field[]::new)).into(User.class);
		String metaDataString = r.get(USERS.METADATA, String.class);
		ofNullable(metaDataString).ifPresent(md -> {
			try {
				Metadata metadata = objectMapper.readValue(metaDataString, Metadata.class);
				user.setMetadata(metadata);
			} catch (IOException e) {
				throw new ReportPortalException("Error during parsing user metadata");
			}
		});
		Project project = new Project();
		project.setId(r.get(USERS.DEFAULT_PROJECT_ID));
		user.setDefaultProject(project);
		return user;
	};

	public static final RecordMapper<Record, ProjectUser> PROJECT_USER_MAPPER = r -> {
		ProjectUser projectUser = new ProjectUser();
		projectUser.setProjectRole(r.into(PROJECT_USER.PROJECT_ROLE).into(ProjectRole.class));

		Project project = new Project();
		project.setId(r.get(PROJECT_USER.PROJECT_ID));
		project.setName(r.get(PROJECT.NAME));
		project.setProjectType(ProjectType.valueOf(r.get(PROJECT.PROJECT_TYPE)));

		User user = new User();
		user.setLogin(r.get(USERS.LOGIN));
		user.setId(r.get(PROJECT_USER.PROJECT_ID));

		projectUser.setProject(project);
		projectUser.setUser(user);
		return projectUser;
	};

	public static final RecordMapper<? super Record, Activity> ACTIVITY_MAPPER = r -> {
		Activity activity = new Activity();
		activity.setId(r.get(ACTIVITY.ID));
		activity.setUserId(r.get(ACTIVITY.USER_ID));
		activity.setProjectId(r.get(ACTIVITY.PROJECT_ID));
		activity.setAction(r.get(ACTIVITY.ACTION));
		activity.setActivityEntityType(r.get(ACTIVITY.ENTITY, Activity.ActivityEntityType.class));
		activity.setCreatedAt(r.get(ACTIVITY.CREATION_DATE, LocalDateTime.class));
		activity.setObjectId(r.get(ACTIVITY.OBJECT_ID));
		String detailsJson = r.get(ACTIVITY.DETAILS, String.class);
		ofNullable(detailsJson).ifPresent(s -> {
			try {
				ActivityDetails details = objectMapper.readValue(s, ActivityDetails.class);
				activity.setDetails(details);
			} catch (IOException e) {
				throw new ReportPortalException(ErrorType.UNCLASSIFIED_REPORT_PORTAL_ERROR);
			}
		});
		return activity;
	};

	public static final RecordMapper<? super Record, SharedEntity> SHARED_ENTITY_MAPPER = r -> r.into(SharedEntity.class);

	private static final BiConsumer<Widget, ? super Record> WIDGET_USER_FILTER_MAPPER = (widget, res) -> ofNullable(res.get(USER_FILTER.ID))
			.ifPresent(id -> {
				Set<UserFilter> filters = ofNullable(widget.getFilters()).orElseGet(Sets::newLinkedHashSet);
				UserFilter filter = new UserFilter();
				filter.setId(id);
				filters.add(filter);
				widget.setFilters(filters);
			});

	private static final BiConsumer<Widget, ? super Record> WIDGET_OPTIONS_MAPPER = (widget, res) -> {
		ofNullable(res.get(WIDGET.WIDGET_OPTIONS, String.class)).ifPresent(wo -> {
			try {
				WidgetOptions widgetOptions = objectMapper.readValue(wo, WidgetOptions.class);
				widget.setWidgetOptions(widgetOptions);
			} catch (IOException e) {
				throw new ReportPortalException("Error during parsing widget options");
			}
		});
	};

	private static final BiConsumer<Widget, ? super Record> WIDGET_CONTENT_FIELD_MAPPER = (widget, res) -> ofNullable(res.get(CONTENT_FIELD.FIELD))
			.ifPresent(field -> {
				Set<String> contentFields = ofNullable(widget.getContentFields()).orElseGet(Sets::newLinkedHashSet);
				contentFields.add(field);
				widget.setContentFields(contentFields);
			});

	public static final RecordMapper<? super Record, Widget> WIDGET_RECORD_MAPPER = r -> {
		Widget widget = new Widget();
		widget.setDescription(r.get(WIDGET.DESCRIPTION));
		widget.setId(r.get(WIDGET.ID));
		widget.setName(r.get(WIDGET.NAME));
		widget.setItemsCount(r.get(WIDGET.ITEMS_COUNT));
		widget.setWidgetType(r.get(WIDGET.WIDGET_TYPE));

		WIDGET_USER_FILTER_MAPPER.accept(widget, r);
		WIDGET_OPTIONS_MAPPER.accept(widget, r);
		WIDGET_CONTENT_FIELD_MAPPER.accept(widget, r);

		return widget;
	};

	public static final Function<Result<? extends Record>, List<Widget>> WIDGET_FETCHER = result -> {
		Map<Long, Widget> widgetMap = Maps.newLinkedHashMap();
		result.forEach(res -> {
			Long widgetId = res.get(WIDGET.ID);
			Widget widget = widgetMap.get(widgetId);
			if (ofNullable(widget).isPresent()) {
				WIDGET_USER_FILTER_MAPPER.accept(widget, res);
				WIDGET_OPTIONS_MAPPER.accept(widget, res);
				WIDGET_CONTENT_FIELD_MAPPER.accept(widget, res);
			} else {
				widgetMap.put(widgetId, WIDGET_RECORD_MAPPER.map(res));
			}
		});

		return Lists.newArrayList(widgetMap.values());
	};

	public static final Function<? super Record, Optional<ItemAttribute>> ITEM_ATTRIBUTE_MAPPER = r -> {
		String key = r.get(ITEM_ATTRIBUTE.KEY);
		String value = r.get(ITEM_ATTRIBUTE.VALUE);
		Boolean system = r.get(ITEM_ATTRIBUTE.SYSTEM);
		if (key != null || value != null) {
			return Optional.of(new ItemAttribute(key, value, system));
		} else {
			return Optional.empty();
		}
	};
}
