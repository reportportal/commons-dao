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

package com.epam.ta.reportportal.dao;

import static com.epam.ta.reportportal.commons.querygen.constant.ActivityCriteriaConstant.CRITERIA_CREATED_AT;
import static com.epam.ta.reportportal.commons.querygen.constant.ActivityCriteriaConstant.CRITERIA_OBJECT_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.ActivityCriteriaConstant.CRITERIA_OBJECT_NAME;
import static com.epam.ta.reportportal.commons.querygen.constant.ActivityCriteriaConstant.CRITERIA_OBJECT_TYPE;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_PROJECT_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.CRITERIA_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.entity.activity.Activity;
import com.epam.ta.reportportal.entity.activity.ActivityDetails;
import com.epam.ta.reportportal.entity.activity.EventAction;
import com.epam.ta.reportportal.entity.activity.EventObject;
import com.epam.ta.reportportal.entity.activity.EventPriority;
import com.epam.ta.reportportal.entity.activity.EventSubject;
import com.epam.ta.reportportal.entity.activity.HistoryField;
import com.google.common.collect.Comparators;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.commons.compress.utils.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

@Sql("/db/fill/activity/activities-fill.sql")
class ActivityRepositoryTest extends BaseTest {

	private static final int ACTIVITIES_COUNT = 7;

	@Autowired
	private ActivityRepository repository;

	//	JPA

	@Test
	void findByIdTest() {
		final Optional<Activity> activityOptional = repository.findById(1L);

		assertTrue(activityOptional.isPresent());
		assertEquals(1L, (long) activityOptional.get().getId());
	}

	@Test
	void findAllTest() {
		final List<Activity> activities = repository.findAll();

		assertFalse(activities.isEmpty());
		assertEquals(ACTIVITIES_COUNT, activities.size());
	}

	@Test
	void createTest() {
		final Activity entity = generateActivity();
		final Activity saved = repository.save(entity);
		entity.setId(saved.getId());
		final List<Activity> all = repository.findAll();

		assertEquals(saved, entity);
		assertEquals(ACTIVITIES_COUNT + 1, all.size());
		assertTrue(all.contains(entity));
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	@Test
	void updateTest() {
		Activity activity = repository.findById(1L).get();
		final LocalDateTime now = LocalDateTime.now();
		final ActivityDetails details = generateDetails();
		final EventAction action = EventAction.CREATE;

		activity.setCreatedAt(now);
		activity.setAction(action);
		activity.setDetails(details);

		final Activity updated = repository.save(activity);

		assertEquals(now, updated.getCreatedAt());
		assertThat(updated.getDetails()).isEqualToIgnoringGivenFields(details, "mapper");
		assertEquals(action, updated.getAction());
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	@Test
	void deleteTest() {
		final Activity activity = repository.findById(1L).get();
		repository.delete(activity);

		assertEquals(ACTIVITIES_COUNT - 1, repository.findAll().size());
	}

	@Test
	void deleteById() {
		repository.deleteById(1L);
		assertEquals(ACTIVITIES_COUNT - 1, repository.findAll().size());
	}

	@Test
	void existsTest() {
		assertTrue(repository.existsById(1L));
		assertFalse(repository.existsById(100L));
		assertTrue(repository.exists(defaultFilter()));
	}

	//	Custom Repositories

	@Test
	void deleteModifiedLaterAgo() {
		Duration period = Duration.ofDays(10);
		LocalDateTime bound = LocalDateTime.now().minus(period);

		repository.deleteModifiedLaterAgo(1L, period);
		List<Activity> all = repository.findAll();
		all.stream().filter(a -> a.getProjectId().equals(1L))
				.forEach(a -> assertTrue(a.getCreatedAt().isAfter(bound)));
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	@Test
	void findByFilterWithSortingAndLimit() {
		List<Activity> activities = repository.findByFilterWithSortingAndLimit(defaultFilter(),
				Sort.by(Sort.Direction.DESC, CRITERIA_CREATED_AT),
				2
		);

		assertEquals(2, activities.size());
		final LocalDateTime first = activities.get(0).getCreatedAt();
		final LocalDateTime second = activities.get(1).getCreatedAt();
		assertTrue(first.isBefore(second) || first.isEqual(second));
	}

	@Test
	void findByFilter() {
		List<Activity> activities = repository.findByFilter(filterById(1));

		assertEquals(1, activities.size());
		assertNotNull(activities.get(0));
	}

	@Test
	void findByFilterPageable() {
		Page<Activity> page = repository.findByFilter(filterById(1), PageRequest.of(0, 10));
		ArrayList<Object> activities = Lists.newArrayList();
		page.forEach(activities::add);

		assertEquals(1, activities.size());
		assertNotNull(activities.get(0));
	}

	@Test
	void findByProjectId() {
		final List<Activity> activities = repository.findByFilter(new Filter(Activity.class,
				Condition.EQUALS,
				false,
				String.valueOf(1),
				CRITERIA_PROJECT_ID
		));
		assertNotNull(activities);
		assertFalse(activities.isEmpty());
		activities.forEach(it -> assertEquals(1L, (long) it.getProjectId()));
	}

	@Test
	void findByEntityType() {
		final List<Activity> activities = repository.findByFilter(new Filter(Activity.class,
				Condition.EQUALS,
				false,
				"LAUNCH",
				CRITERIA_OBJECT_TYPE
		));
		assertNotNull(activities);
		assertFalse(activities.isEmpty());
		activities.forEach(it -> assertEquals(EventObject.LAUNCH,
				it.getObjectType()));
	}

	@Test
	void findByCreationDate() {
		LocalDateTime to = LocalDateTime.now();
		LocalDateTime from = to.minusDays(7);
		final List<Activity> activities = repository.findByFilter(new Filter(Activity.class,
				Condition.BETWEEN,
				false,
				Timestamp.valueOf(from).getTime() + "," + Timestamp.valueOf(to).getTime(),
				CRITERIA_CREATED_AT
		));
		assertNotNull(activities);
		assertFalse(activities.isEmpty());
		activities.forEach(
				it -> assertTrue(it.getCreatedAt().isBefore(to) && it.getCreatedAt().isAfter(from)));
	}

	@Test
	void findByUserLogin() {
		final List<Activity> activities = repository.findByFilter(new Filter(Activity.class,
				Condition.EQUALS,
				false,
				"superadmin",
				CRITERIA_USER
		));
		assertNotNull(activities);
		assertFalse(activities.isEmpty());
		activities.forEach(it -> assertEquals(1L, (long) it.getSubjectId()));
	}

	@Test
	void findByObjectIdTest() {
		final List<Activity> activities = repository.findByFilter(new Filter(Activity.class,
				Condition.EQUALS,
				false,
				"4",
				CRITERIA_OBJECT_ID
		));
		assertNotNull(activities);
		assertFalse(activities.isEmpty());
		activities.forEach(it -> assertEquals(4L, (long) it.getObjectId()));
	}

	@Test
	void objectNameCriteriaTest() {
		String term = "filter";

		List<Activity> activities = repository.findByFilter(Filter.builder()
				.withTarget(Activity.class)
				.withCondition(FilterCondition.builder()
						.withCondition(Condition.CONTAINS)
						.withSearchCriteria(CRITERIA_OBJECT_NAME)
						.withValue(term)
						.build())
				.build());

		assertFalse(activities.isEmpty());
		activities.forEach(it -> assertTrue(it.getObjectName().contains(term)));
	}

	private Activity generateActivity() {
		Activity activity = new Activity();
		activity.setAction(EventAction.CREATE);
		activity.setEventName("createDefect");
		activity.setCreatedAt(LocalDateTime.now());
		activity.setDetails(new ActivityDetails());
		activity.setObjectId(11L);
		activity.setObjectName("test defect name");
		activity.setObjectType(EventObject.DEFECT_TYPE);
		activity.setPriority(EventPriority.MEDIUM);
		activity.setProjectId(1L);
		activity.setSubjectId(1L);
		activity.setSubjectName("subject_name1");
		activity.setSubjectType(EventSubject.USER);
		return activity;
	}

	private ActivityDetails generateDetails() {
		ActivityDetails details = new ActivityDetails();
		details.setHistory((Arrays.asList(HistoryField.of("test field", "old", "new"),
				HistoryField.of("test field 2", "old", "new"))));
		return details;
	}

	@Test
	void sortingByJoinedColumnTest() {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, CRITERIA_USER));
		Page<Activity> activitiesPage = repository.findByFilter(defaultFilter(), pageRequest);

		assertTrue(Comparators.isInOrder(activitiesPage.getContent(),
				Comparator.comparing(Activity::getSubjectName).reversed()));
	}

	private Filter filterById(long id) {
		return new Filter(Activity.class, Condition.EQUALS, false, String.valueOf(id), "id");
	}

	private Filter defaultFilter() {
		return new Filter(Activity.class, Condition.LOWER_THAN, false, "100", CRITERIA_ID);
	}
}