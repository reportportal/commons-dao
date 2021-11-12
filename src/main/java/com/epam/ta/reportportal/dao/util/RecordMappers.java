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
import com.epam.ta.reportportal.entity.ItemAttribute;
import com.epam.ta.reportportal.entity.Metadata;
import com.epam.ta.reportportal.entity.activity.Activity;
import com.epam.ta.reportportal.entity.activity.ActivityDetails;
import com.epam.ta.reportportal.entity.attachment.Attachment;
import com.epam.ta.reportportal.entity.attribute.Attribute;
import com.epam.ta.reportportal.entity.bts.Ticket;
import com.epam.ta.reportportal.entity.dashboard.DashboardWidget;
import com.epam.ta.reportportal.entity.dashboard.DashboardWidgetId;
import com.epam.ta.reportportal.entity.enums.*;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.integration.IntegrationParams;
import com.epam.ta.reportportal.entity.integration.IntegrationType;
import com.epam.ta.reportportal.entity.integration.IntegrationTypeDetails;
import com.epam.ta.reportportal.entity.item.NestedStep;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.item.TestItemResults;
import com.epam.ta.reportportal.entity.item.issue.IssueEntity;
import com.epam.ta.reportportal.entity.item.issue.IssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.log.Log;
import com.epam.ta.reportportal.entity.pattern.PatternTemplate;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.entity.statistics.Statistics;
import com.epam.ta.reportportal.entity.statistics.StatisticsField;
import com.epam.ta.reportportal.entity.user.ProjectUser;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.epam.ta.reportportal.entity.widget.Widget;
import com.epam.ta.reportportal.entity.widget.WidgetOptions;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.jooq.Tables;
import com.epam.ta.reportportal.jooq.tables.JLog;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.epam.ta.reportportal.ws.model.SharedEntity;
import com.epam.ta.reportportal.ws.model.analyzer.IndexLaunch;
import com.epam.ta.reportportal.ws.model.analyzer.IndexLog;
import com.epam.ta.reportportal.ws.model.analyzer.IndexTestItem;
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
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.epam.ta.reportportal.dao.LogRepositoryCustomImpl.ROOT_ITEM_ID;
import static com.epam.ta.reportportal.dao.constant.TestItemRepositoryConstants.ATTACHMENTS_COUNT;
import static com.epam.ta.reportportal.dao.constant.TestItemRepositoryConstants.HAS_CONTENT;
import static com.epam.ta.reportportal.dao.util.RecordMapperUtils.fieldExcludingPredicate;
import static com.epam.ta.reportportal.jooq.Tables.*;
import static com.epam.ta.reportportal.jooq.tables.JActivity.ACTIVITY;
import static com.epam.ta.reportportal.jooq.tables.JAttachment.ATTACHMENT;
import static com.epam.ta.reportportal.jooq.tables.JUsers.USERS;
import static java.util.Optional.empty;
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
		Project project = r.into(PROJECT.ID, PROJECT.NAME, PROJECT.ORGANIZATION, PROJECT.CREATION_DATE, PROJECT.PROJECT_TYPE)
				.into(Project.class);
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

	public static final RecordMapper<? super Record, Attachment> ATTACHMENT_MAPPER = r -> ofNullable(r.get(ATTACHMENT.ID)).map(id -> {
		Attachment attachment = new Attachment();
		attachment.setId(id);
		attachment.setFileId(r.get(ATTACHMENT.FILE_ID));
		attachment.setThumbnailId(r.get(ATTACHMENT.THUMBNAIL_ID));
		attachment.setContentType(r.get(ATTACHMENT.CONTENT_TYPE));
		attachment.setFileSize(r.get(ATTACHMENT.FILE_SIZE));
		attachment.setProjectId(r.get(ATTACHMENT.PROJECT_ID));
		attachment.setLaunchId(r.get(ATTACHMENT.LAUNCH_ID));
		attachment.setItemId(r.get(ATTACHMENT.ITEM_ID));

		return attachment;
	}).orElse(null);

	private static final RecordMapper<? super Record, Log> COMMON_LOG_RECORD_MAPPER = result -> {
		Log log = new Log();
		log.setId(result.get(LOG.ID, Long.class));
		log.setLogTime(result.get(LOG.LOG_TIME, LocalDateTime.class));
		log.setLogMessage(result.get(LOG.LOG_MESSAGE, String.class));
		log.setLastModified(result.get(LOG.LAST_MODIFIED, LocalDateTime.class));
		log.setLogLevel(result.get(JLog.LOG.LOG_LEVEL, Integer.class));
		ofNullable(result.get(LOG.LAUNCH_ID)).map(Launch::new).ifPresent(log::setLaunch);
		return log;
	};

	public static final RecordMapper<? super Record, Log> LOG_RECORD_MAPPER = result -> {
		Log log = COMMON_LOG_RECORD_MAPPER.map(result);
		ofNullable(result.get(LOG.ITEM_ID)).map(TestItem::new).ifPresent(log::setTestItem);
		return log;
	};

	public static final RecordMapper<? super Record, Log> LOG_UNDER_RECORD_MAPPER = result -> {
		Log log = COMMON_LOG_RECORD_MAPPER.map(result);
		ofNullable(result.get(ROOT_ITEM_ID, Long.class)).map(TestItem::new).ifPresent(log::setTestItem);
		log.setClusterId(result.get(LOG.CLUSTER_ID, Long.class));
		return log;
	};

	public static final Function<Result<? extends Record>, Map<Long, List<IndexLog>>> INDEX_LOG_FETCHER = result -> {
		final Map<Long, List<IndexLog>> indexLogMapping = new HashMap<>();
		result.forEach(r -> {
			final Long itemId = r.get(ROOT_ITEM_ID, Long.class);

			final IndexLog indexLog = new IndexLog();
			indexLog.setLogId(r.get(LOG.ID, Long.class));
			indexLog.setMessage(r.get(LOG.LOG_MESSAGE, String.class));
			indexLog.setLogLevel(r.get(JLog.LOG.LOG_LEVEL, Integer.class));
			indexLog.setClusterId(r.get(CLUSTERS.INDEX_ID));

			ofNullable(indexLogMapping.get(itemId)).ifPresentOrElse(indexLogs -> indexLogs.add(indexLog), () -> {
				final List<IndexLog> indexLogs = new ArrayList<>();
				indexLogs.add(indexLog);
				indexLogMapping.put(itemId, indexLogs);
			});
		});
		return indexLogMapping;
	};

	public static final BiFunction<? super Record, RecordMapper<? super Record, Attachment>, Log> LOG_MAPPER = (result, attachmentMapper) -> {
		Log log = LOG_RECORD_MAPPER.map(result);
		log.setAttachment(attachmentMapper.map(result));
		return log;
	};

	public static final BiFunction<? super Record, RecordMapper<? super Record, Attachment>, Log> LOG_UNDER_MAPPER = (result, attachmentMapper) -> {
		Log log = LOG_UNDER_RECORD_MAPPER.map(result);
		log.setAttachment(attachmentMapper.map(result));
		return log;
	};

	/**
	 * Maps record into {@link TestItem} object
	 */
	public static final RecordMapper<? super Record, TestItem> TEST_ITEM_RECORD_MAPPER = r -> {
		TestItem testItem = r.into(TestItem.class);
		testItem.setItemId(r.get(TEST_ITEM.ITEM_ID));
		testItem.setName(r.get(TEST_ITEM.NAME));
		testItem.setCodeRef(r.get(TEST_ITEM.CODE_REF));
		testItem.setItemResults(TEST_ITEM_RESULTS_RECORD_MAPPER.map(r));
		ofNullable(r.get(TEST_ITEM.LAUNCH_ID)).ifPresent(testItem::setLaunchId);
		ofNullable(r.get(TEST_ITEM.PARENT_ID)).ifPresent(testItem::setParentId);
		return testItem;
	};

	public static final RecordMapper<? super Record, IndexTestItem> INDEX_TEST_ITEM_RECORD_MAPPER = record -> {
		final IndexTestItem indexTestItem = new IndexTestItem();
		indexTestItem.setTestItemId(record.get(TEST_ITEM.ITEM_ID));
		indexTestItem.setTestItemName(record.get(TEST_ITEM.NAME));
		indexTestItem.setStartTime(record.get(TEST_ITEM.START_TIME).toLocalDateTime());
		indexTestItem.setUniqueId(record.get(TEST_ITEM.UNIQUE_ID));
		indexTestItem.setTestCaseHash(record.get(TEST_ITEM.TEST_CASE_HASH));
		indexTestItem.setAutoAnalyzed(record.get(ISSUE.AUTO_ANALYZED));
		indexTestItem.setIssueTypeLocator(record.get(ISSUE_TYPE.LOCATOR));
		return indexTestItem;
	};

	public static final RecordMapper<? super Record, NestedStep> NESTED_STEP_RECORD_MAPPER = r -> new NestedStep(r.get(TEST_ITEM.ITEM_ID),
			r.get(TEST_ITEM.NAME),
			r.get(TEST_ITEM.UUID),
			TestItemTypeEnum.valueOf(r.get(TEST_ITEM.TYPE).getLiteral()),
			r.get(HAS_CONTENT, Boolean.class),
			r.get(ATTACHMENTS_COUNT, Integer.class),
			StatusEnum.valueOf(r.get(TEST_ITEM_RESULTS.STATUS).getLiteral()),
			r.get(TEST_ITEM.START_TIME, LocalDateTime.class),
			r.get(TEST_ITEM_RESULTS.END_TIME, LocalDateTime.class),
			r.get(TEST_ITEM_RESULTS.DURATION)
	);

	/**
	 * Maps record into {@link PatternTemplate} object (only {@link PatternTemplate#id} and {@link PatternTemplate#name} fields)
	 */
	public static final Function<? super Record, Optional<PatternTemplate>> PATTERN_TEMPLATE_NAME_RECORD_MAPPER = r -> ofNullable(r.get(
			PATTERN_TEMPLATE.NAME)).map(name -> {
		PatternTemplate patternTemplate = new PatternTemplate();
		patternTemplate.setId(r.get(PATTERN_TEMPLATE.ID));
		patternTemplate.setName(name);
		return patternTemplate;
	});

	/**
	 * Maps record into {@link Launch} object
	 */
	public static final RecordMapper<? super Record, Launch> LAUNCH_RECORD_MAPPER = r -> {
		Launch launch = r.into(Launch.class);
		launch.setId(r.get(LAUNCH.ID));
		launch.setName(r.get(LAUNCH.NAME));
		launch.setUserId(r.get(LAUNCH.USER_ID));
		return launch;
	};

	public static final RecordMapper<? super Record, IndexLaunch> INDEX_LAUNCH_RECORD_MAPPER = record -> {
		final IndexLaunch indexLaunch = new IndexLaunch();
		indexLaunch.setLaunchId(record.get(LAUNCH.ID));
		indexLaunch.setLaunchName(record.get(LAUNCH.NAME));
		indexLaunch.setProjectId(record.get(LAUNCH.PROJECT_ID));
		return indexLaunch;
	};

	public static final RecordMapper<Record, ReportPortalUser> REPORT_PORTAL_USER_MAPPER = r -> ReportPortalUser.userBuilder()
			.withUserName(r.get(USERS.LOGIN))
			.withPassword(ofNullable(r.get(USERS.PASSWORD)).orElse(""))
			.withAuthorities(Collections.emptyList())
			.withUserId(r.get(USERS.ID))
			.withUserRole(UserRole.findByName(r.get(USERS.ROLE))
					.orElseThrow(() -> new ReportPortalException(ErrorType.UNCLASSIFIED_REPORT_PORTAL_ERROR)))
			.withEmail(r.get(USERS.EMAIL))
			.build();

	public static final RecordMapper<Record, User> USER_MAPPER = r -> {
		User user = r.into(USERS.fieldStream().filter(f -> fieldExcludingPredicate(USERS.METADATA).test(f)).toArray(Field[]::new))
				.into(User.class);
		String metaDataString = r.get(USERS.METADATA, String.class);
		ofNullable(metaDataString).ifPresent(md -> {
			try {
				Metadata metadata = objectMapper.readValue(metaDataString, Metadata.class);
				user.setMetadata(metadata);
			} catch (IOException e) {
				throw new ReportPortalException("Error during parsing user metadata");
			}
		});
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

	public static final RecordMapper<Record, ReportPortalUser.ProjectDetails> PROJECT_DETAILS_MAPPER = r -> {
		final Long projectId = r.get(PROJECT_USER.PROJECT_ID);
		final String projectName = r.get(PROJECT.NAME);
		final ProjectRole projectRole = r.into(PROJECT_USER.PROJECT_ROLE).into(ProjectRole.class);
		return new ReportPortalUser.ProjectDetails(projectId, projectName, projectRole);
	};

	public static final RecordMapper<? super Record, Activity> ACTIVITY_MAPPER = r -> {
		Activity activity = new Activity();
		activity.setId(r.get(ACTIVITY.ID));
		activity.setUserId(r.get(ACTIVITY.USER_ID));
		activity.setProjectId(r.get(ACTIVITY.PROJECT_ID));
		activity.setAction(r.get(ACTIVITY.ACTION));
		activity.setUsername(ofNullable(r.get(USERS.LOGIN)).orElse(r.get(ACTIVITY.USERNAME)));
		activity.setActivityEntityType(r.get(ACTIVITY.ENTITY, String.class));
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

	private static final BiConsumer<Widget, ? super Record> WIDGET_USER_FILTER_MAPPER = (widget, res) -> ofNullable(res.get(FILTER.ID)).ifPresent(
			id -> {
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

	private static final BiConsumer<Widget, ? super Record> WIDGET_CONTENT_FIELD_MAPPER = (widget, res) -> ofNullable(res.get(CONTENT_FIELD.FIELD)).ifPresent(
			field -> {
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

	public static final Function<? super Record, Optional<Ticket>> TICKET_MAPPER = r -> {
		String ticketId = r.get(TICKET.TICKET_ID);
		if (ticketId != null) {
			return Optional.of(r.into(Ticket.class));
		}
		return Optional.empty();
	};

	public static final Function<? super Record, Optional<DashboardWidget>> DASHBOARD_WIDGET_MAPPER = r -> {
		Long widgetId = r.get(DASHBOARD_WIDGET.WIDGET_ID);
		if (widgetId == null) {
			return empty();
		}
		DashboardWidget dashboardWidget = new DashboardWidget();
		dashboardWidget.setId(new DashboardWidgetId(r.get(DASHBOARD.ID), widgetId));
		dashboardWidget.setPositionX(r.get(DASHBOARD_WIDGET.WIDGET_POSITION_X));
		dashboardWidget.setPositionY(r.get(DASHBOARD_WIDGET.WIDGET_POSITION_Y));
		dashboardWidget.setHeight(r.get(DASHBOARD_WIDGET.WIDGET_HEIGHT));
		dashboardWidget.setWidth(r.get(DASHBOARD_WIDGET.WIDGET_WIDTH));
		dashboardWidget.setCreatedOn(r.get(DASHBOARD_WIDGET.IS_CREATED_ON));
		dashboardWidget.setWidgetOwner(r.get(DASHBOARD_WIDGET.WIDGET_OWNER));
		dashboardWidget.setWidgetName(r.get(DASHBOARD_WIDGET.WIDGET_NAME));
		dashboardWidget.setWidgetType(r.get(DASHBOARD_WIDGET.WIDGET_TYPE));
		dashboardWidget.setShare(r.get(DASHBOARD_WIDGET.SHARE));
		return Optional.of(dashboardWidget);
	};

	public static final RecordMapper<? super Record, IntegrationType> INTEGRATION_TYPE_MAPPER = r -> {
		IntegrationType integrationType = new IntegrationType();
		integrationType.setId(r.get(INTEGRATION_TYPE.ID, Long.class));
		integrationType.setEnabled(r.get(INTEGRATION_TYPE.ENABLED));
		integrationType.setCreationDate(r.get(INTEGRATION_TYPE.CREATION_DATE).toLocalDateTime());
		ofNullable(r.get(INTEGRATION_TYPE.AUTH_FLOW)).ifPresent(af -> {
			integrationType.setAuthFlow(IntegrationAuthFlowEnum.findByName(af.getLiteral())
					.orElseThrow(() -> new ReportPortalException(ErrorType.INCORRECT_AUTHENTICATION_TYPE)));
		});

		integrationType.setName(r.get(INTEGRATION_TYPE.NAME));
		integrationType.setIntegrationGroup(IntegrationGroupEnum.findByName(r.get(INTEGRATION_TYPE.GROUP_TYPE).getLiteral())
				.orElseThrow(() -> new ReportPortalException(ErrorType.INCORRECT_AUTHENTICATION_TYPE)));

		String detailsJson = r.get(INTEGRATION_TYPE.DETAILS, String.class);
		ofNullable(detailsJson).ifPresent(s -> {
			try {
				IntegrationTypeDetails details = objectMapper.readValue(s, IntegrationTypeDetails.class);
				integrationType.setDetails(details);
			} catch (IOException e) {
				throw new ReportPortalException("Error during parsing integration type details");
			}
		});

		return integrationType;
	};

	public static final BiConsumer<? super Integration, ? super Record> INTEGRATION_PARAMS_MAPPER = (i, r) -> {
		String paramsJson = r.get(INTEGRATION.PARAMS, String.class);
		ofNullable(paramsJson).ifPresent(s -> {
			try {
				IntegrationParams params = objectMapper.readValue(s, IntegrationParams.class);
				i.setParams(params);
			} catch (IOException e) {
				throw new ReportPortalException("Error during parsing integration params");
			}
		});
	};

	public static final RecordMapper<? super Record, Integration> GLOBAL_INTEGRATION_RECORD_MAPPER = r -> {

		Integration integration = new Integration();
		integration.setId(r.get(INTEGRATION.ID, Long.class));
		integration.setName(r.get(INTEGRATION.NAME));
		integration.setType(INTEGRATION_TYPE_MAPPER.map(r));
		integration.setCreator(r.get(INTEGRATION.CREATOR));
		integration.setCreationDate(r.get(INTEGRATION.CREATION_DATE).toLocalDateTime());
		integration.setEnabled(r.get(INTEGRATION.ENABLED));
		INTEGRATION_PARAMS_MAPPER.accept(integration, r);

		return integration;
	};

	public static final RecordMapper<? super Record, Integration> PROJECT_INTEGRATION_RECORD_MAPPER = r -> {

		Integration integration = GLOBAL_INTEGRATION_RECORD_MAPPER.map(r);

		Project project = new Project();
		project.setId(r.get(INTEGRATION.PROJECT_ID));

		integration.setProject(project);

		return integration;
	};
}
