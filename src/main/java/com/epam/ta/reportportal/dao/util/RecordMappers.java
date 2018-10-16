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

package com.epam.ta.reportportal.dao.util;

import com.epam.ta.reportportal.entity.Activity;
import com.epam.ta.reportportal.entity.JsonbObject;
import com.epam.ta.reportportal.entity.attribute.Attribute;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.item.TestItemResults;
import com.epam.ta.reportportal.entity.item.issue.IssueEntity;
import com.epam.ta.reportportal.entity.item.issue.IssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueType;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.statistics.Statistics;
import com.epam.ta.reportportal.entity.user.User;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.epam.ta.reportportal.entity.user.UserType;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.LAUNCH_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.TestItemCriteriaConstant.PARENT_ID;
import static com.epam.ta.reportportal.dao.util.JooqFieldNameTransformer.fieldName;
import static com.epam.ta.reportportal.jooq.Tables.ISSUE;
import static com.epam.ta.reportportal.jooq.Tables.LAUNCH;
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

	private static final String STATISTICS = "statistics";

	private static ObjectMapper objectMapper;

	/**
	 * Maps record into {@link Attribute} object
	 */
	public static final RecordMapper<? super Record, Attribute> ATTRIBUTE_MAPPER = record -> record.into(Attribute.class);

	/**
	 * Maps crosstab results statistics into set of {@link Statistics} objects
	 */
	public static final RecordMapper<? super Record, Set<Statistics>> CROSSTAB_RECORD_STATISTICS_MAPPER = r -> Arrays.stream(r.fields())
			.filter(f -> f.getName().startsWith(STATISTICS))
			.map(f -> new Statistics(f.getName(), r.get(f.getName(), Integer.class)))
			.collect(Collectors.toSet());

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
		if (r.get(ISSUE.ISSUE_ID) != null) {
			IssueEntity issueEntity = r.into(IssueEntity.class);
			issueEntity.setIssueType(ISSUE_TYPE_RECORD_MAPPER.map(r));
			return issueEntity;
		}
		return null;
	};

	/**
	 * Maps record into {@link Project} object
	 */
	public static final RecordMapper<? super Record, Project> PROJECT_MAPPER = r -> r.into(Project.class);

	/**
	 * Maps record into {@link TestItemResults} object
	 */
	public static final RecordMapper<? super Record, TestItemResults> TEST_ITEM_RESULTS_RECORD_MAPPER = r -> {
		TestItemResults results = r.into(TestItemResults.class);
		results.setIssue(ISSUE_RECORD_MAPPER.map(r));
		results.setStatistics(CROSSTAB_RECORD_STATISTICS_MAPPER.map(r));
		return results;
	};

	/**
	 * Maps record with crosstab into {@link TestItem} object
	 */
	public static final RecordMapper<? super Record, TestItem> TEST_ITEM_RECORD_MAPPER = r -> {
		TestItem testItem = r.into(TestItem.class);
		testItem.setItemResults(TEST_ITEM_RESULTS_RECORD_MAPPER.map(r));
		testItem.setLaunch(new Launch(r.get(LAUNCH_ID, Long.class)));
		testItem.setParent(new TestItem(r.get(PARENT_ID, Long.class)));
		return testItem;
	};

	/**
	 * Maps record with crosstab into {@link Launch} object
	 */
	public static final RecordMapper<? super Record, Launch> LAUNCH_RECORD_MAPPER = r -> {
		Launch launch = r.into(Launch.class);
		launch.setUser(r.into(User.class));
		launch.setStatistics(CROSSTAB_RECORD_STATISTICS_MAPPER.map(r));
		return launch;
	};

	/**
	 * Maps result of records without crosstab into list of launches
	 */
	public static final Function<Result<? extends Record>, List<Launch>> LAUNCH_FETCHER = result -> new ArrayList<>(result.stream()
			.collect(Collectors.toMap(r -> r.get(LAUNCH.ID), r -> r.into(Launch.class)))
			.values());

	/**
	 * Maps record into {@link User} object
	 */
	public static final RecordMapper<? super Record, User> USER_RECORD_MAPPER = r -> {
		User user = new User();
		Project defaultProject = new Project();
		String metaDataString = r.get(fieldName(USERS.METADATA), String.class);
		ofNullable(metaDataString).ifPresent(md -> {
			try {
				JsonbObject metaData = objectMapper.readValue(metaDataString, JsonbObject.class);
				user.setMetadata(metaData);
			} catch (IOException e) {
				throw new ReportPortalException("Error during parsing user metadata");
			}
		});

		r = r.into(USERS.fields());
		defaultProject.setId(r.get(USERS.DEFAULT_PROJECT_ID));
		user.setId(r.get(USERS.ID));
		user.setAttachment(r.get(USERS.ATTACHMENT));
		user.setAttachmentThumbnail(r.get(USERS.ATTACHMENT_THUMBNAIL));
		user.setDefaultProject(defaultProject);
		user.setEmail(r.get(USERS.EMAIL));
		user.setExpired(r.get(USERS.EXPIRED));
		user.setFullName(r.get(USERS.FULL_NAME));
		user.setLogin(r.get(USERS.LOGIN));
		user.setPassword(r.get(USERS.PASSWORD));
		user.setRole(UserRole.findByName(r.get(USERS.ROLE)).orElseThrow(() -> new ReportPortalException(ErrorType.ROLE_NOT_FOUND)));
		user.setUserType(UserType.findByName(r.get(USERS.TYPE))
				.orElseThrow(() -> new ReportPortalException(ErrorType.INCORRECT_AUTHENTICATION_TYPE)));
		return user;
	};

	/**
	 * Maps result of records without crosstab into list of {@link User}
	 */
	public static final Function<Result<? extends Record>, List<User>> USER_FETCHER = result -> {
		Map<Long, User> userMap = Maps.newHashMap();
		result.forEach(res -> {
			Long userId = res.get(USERS.ID);
			if (!userMap.containsKey(userId)) {
				userMap.put(userId, USER_RECORD_MAPPER.map(res));
			}
		});

		return Lists.newArrayList(userMap.values());
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
				JsonbObject details = objectMapper.readValue(s, JsonbObject.class);
				activity.setDetails(details);
			} catch (IOException e) {
				throw new ReportPortalException(ErrorType.UNCLASSIFIED_REPORT_PORTAL_ERROR);
			}
		});
		return activity;
	};

	@Component
	private static class MapperInjector {

		@Autowired
		private ObjectMapper objectMapper;

		@PostConstruct
		private void injectMapper() {
			RecordMappers.objectMapper = objectMapper;
		}

	}
}
