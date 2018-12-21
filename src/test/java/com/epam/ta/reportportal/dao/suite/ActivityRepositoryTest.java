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

package com.epam.ta.reportportal.dao.suite;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.dao.ActivityRepository;
import com.epam.ta.reportportal.entity.Activity;
import com.epam.ta.reportportal.entity.ActivityDetails;
import com.epam.ta.reportportal.entity.HistoryField;
import org.apache.commons.compress.utils.Lists;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.ta.reportportal.commons.querygen.constant.ActivityCriteriaConstant.*;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_PROJECT_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.UserCriteriaConstant.CRITERIA_USER;
import static com.epam.ta.reportportal.dao.constant.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class ActivityRepositoryTest extends BaseTest {

	private static final String FILL_SCRIPT_PATH = "/db/fill/activity";

	private static final int ACTIVITIES_COUNT = 7;

	@Autowired
	private ActivityRepository repository;

	@FlywayTest(locationsForMigrate = { FILL_SCRIPT_PATH }, invokeCleanDB = false)
	@BeforeClass
	public static void before() {
	}

	//	JPA

	@Test
	public void findByIdTest() {
		final Optional<Activity> activityOptional = repository.findById(1L);

		assertTrue(activityOptional.isPresent());
		assertEquals(1L, (long) activityOptional.get().getId());
	}

	@Test
	public void findAllTest() {
		final List<Activity> activities = repository.findAll();

		assertTrue(!activities.isEmpty());
		assertEquals(ACTIVITIES_COUNT, activities.size());
	}

	@Test
	public void createTest() {
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
	public void updateTest() {
		Activity activity = repository.findById(1L).get();
		final LocalDateTime now = LocalDateTime.now();
		final ActivityDetails details = generateDetails();
		final String action = "test";

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
	public void deleteTest() {
		final Activity activity = repository.findById(1L).get();
		repository.delete(activity);

		assertEquals(ACTIVITIES_COUNT - 1, repository.findAll().size());
	}

	@Test
	public void deleteById() {
		repository.deleteById(1L);
		assertEquals(ACTIVITIES_COUNT - 1, repository.findAll().size());
	}

	@Test
	public void existsTest() {
		assertTrue(repository.existsById(1L));
		assertFalse(repository.existsById(100L));
		assertTrue(repository.exists(defaultFilter()));
	}

	//	Custom Repositories

	@Test
	public void deleteModifiedLaterAgo() {
		Duration period = Duration.ofDays(10);
		LocalDateTime bound = LocalDateTime.now().minus(period);

		repository.deleteModifiedLaterAgo(SUPERADMIN_PERSONAL_PROJECT_ID, period);
		List<Activity> all = repository.findAll();
		all.stream()
				.filter(a -> a.getProjectId().equals(SUPERADMIN_PERSONAL_PROJECT_ID))
				.forEach(a -> assertTrue(a.getCreatedAt().isAfter(bound)));
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	@Test
	public void findByFilterWithSortingAndLimit() {
		List<Activity> activities = repository.findByFilterWithSortingAndLimit(defaultFilter(),
				new Sort(Sort.Direction.DESC, CRITERIA_CREATION_DATE),
				2
		);

		assertEquals(2, activities.size());
		final LocalDateTime first = activities.get(0).getCreatedAt();
		final LocalDateTime second = activities.get(1).getCreatedAt();
		assertTrue(first.isBefore(second) || first.isEqual(second));
	}

	@Test
	public void findByFilter() {
		List<Activity> activities = repository.findByFilter(filterById(1));

		assertEquals(1, activities.size());
		assertNotNull(activities.get(0));
	}

	@Test
	public void findByFilterPageable() {
		Page<Activity> page = repository.findByFilter(filterById(1), PageRequest.of(0, 10));
		ArrayList<Object> activities = Lists.newArrayList();
		page.forEach(activities::add);

		assertEquals(1, activities.size());
		assertNotNull(activities.get(0));
	}

	@Test
	public void findByProjectId() {
		final List<Activity> activities = repository.findByFilter(new Filter(Activity.class,
				Condition.EQUALS,
				false,
				String.valueOf(SUPERADMIN_PERSONAL_PROJECT_ID),
				CRITERIA_PROJECT_ID
		));
		assertNotNull(activities);
		assertTrue(!activities.isEmpty());
		activities.forEach(it -> assertEquals(SUPERADMIN_PERSONAL_PROJECT_ID, it.getProjectId()));
	}

	@Test
	public void findByEntityType() {
		final List<Activity> activities = repository.findByFilter(new Filter(Activity.class,
				Condition.EQUALS,
				false,
				"LAUNCH",
				CRITERIA_ENTITY
		));
		assertNotNull(activities);
		assertTrue(!activities.isEmpty());
		activities.forEach(it -> assertEquals(Activity.ActivityEntityType.LAUNCH, it.getActivityEntityType()));
	}

	@Test
	public void findByCreationDate() {
		LocalDateTime to = LocalDateTime.now();
		LocalDateTime from = to.minusDays(7);
		final List<Activity> activities = repository.findByFilter(new Filter(Activity.class,
				Condition.BETWEEN,
				false,
				Timestamp.valueOf(from).getTime() + "," + Timestamp.valueOf(to).getTime(),
				CRITERIA_CREATION_DATE
		));
		assertNotNull(activities);
		assertTrue(!activities.isEmpty());
		activities.forEach(it -> assertTrue(it.getCreatedAt().isBefore(to) && it.getCreatedAt().isAfter(from)));
	}

	@Test
	public void findByUserLogin() {
		final List<Activity> activities = repository.findByFilter(new Filter(Activity.class,
				Condition.EQUALS,
				false,
				SUPERADMIN_LOGIN,
				CRITERIA_USER
		));
		assertNotNull(activities);
		assertTrue(!activities.isEmpty());
		activities.forEach(it -> assertEquals(SUPERADMIN_ID, it.getUserId()));
	}

	@Test
	public void findByObjectIdTest() {
		final List<Activity> activities = repository.findByFilter(new Filter(Activity.class,
				Condition.EQUALS,
				false,
				"4",
				CRITERIA_OBJECT_ID
		));
		assertNotNull(activities);
		assertTrue(!activities.isEmpty());
		activities.forEach(it -> assertEquals(4L, (long) it.getObjectId()));
	}

	private Activity generateActivity() {
		Activity activity = new Activity();
		activity.setActivityEntityType(Activity.ActivityEntityType.DEFECT_TYPE);
		activity.setAction("create_defect");
		activity.setObjectId(11L);
		activity.setCreatedAt(LocalDateTime.now());
		activity.setProjectId(SUPERADMIN_PERSONAL_PROJECT_ID);
		activity.setUserId(SUPERADMIN_ID);
		activity.setDetails(new ActivityDetails("test defect name"));
		return activity;
	}

	private ActivityDetails generateDetails() {
		ActivityDetails details = new ActivityDetails();
		details.setObjectName("test");
		details.setHistory((Arrays.asList(HistoryField.of("test field", "old", "new"), HistoryField.of("test field 2", "old", "new"))));
		return details;
	}

	private Filter filterById(long id) {
		return new Filter(Activity.class, Condition.EQUALS, false, String.valueOf(id), "id");
	}

	private Filter defaultFilter() {
		return new Filter(Activity.class, Condition.LOWER_THAN, false, "100", CRITERIA_ID);
	}
}