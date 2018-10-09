package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.config.TestConfiguration;
import com.epam.ta.reportportal.entity.Activity;
import org.apache.commons.compress.utils.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional("transactionManager")
@Sql("/test-activities-fill.sql")
public class ActivityRepositoryCustomImplTest {

	@Autowired
	private ActivityRepository repository;

	@Test
	public void findActivitiesByTestItemId() {
		List<Activity> activities = repository.findActivitiesByTestItemId(1L, defaultFilter(), PageRequest.of(0, 10));

		assertEquals(4, activities.size());
		activities.forEach(a -> assertEquals(1L, (long) a.getObjectId()));
	}

	@Test
	public void findActivitiesByProjectId() {
		List<Activity> activities = repository.findActivitiesByProjectId(1L, defaultFilter(), PageRequest.of(0, 10));
		assertEquals(3, activities.size());
		activities.forEach(a -> assertEquals(Long.valueOf(1L), a.getProjectId()));
	}

	@Test
	public void deleteModifiedLaterAgo() {
		Duration period = Duration.ofDays(10);
		LocalDateTime bound = LocalDateTime.now().minus(period);

		repository.deleteModifiedLaterAgo(1L, period);
		List<Activity> all = repository.findAll();
		all.forEach(a -> assertTrue(a.getCreatedAt().isAfter(bound)));
	}

	@Test
	public void findByFilterWithSortingAndLimit() {
		List<Activity> activities = repository.findByFilterWithSortingAndLimit(defaultFilter(),
				new Sort(Sort.Direction.DESC, "creation_date"),
				2
		);
		assertEquals(2, activities.size());
		activities.forEach(a -> assertTrue(a.getCreatedAt().toLocalDate().isEqual(LocalDate.of(2018, 10, 5))));
	}

	@Test
	public void findByFilter() {
		List<Activity> activities = repository.findByFilter(filterGetById(1));

		assertEquals(1, activities.size());
		assertNotNull(activities.get(0));
	}

	@Test
	public void findByFilterPageable() {
		Page<Activity> page = repository.findByFilter(filterGetById(1), PageRequest.of(0, 10));
		ArrayList<Object> activities = Lists.newArrayList();
		page.forEach(activities::add);

		assertEquals(1, activities.size());
		assertNotNull(activities.get(0));
	}

	private Filter filterGetById(long id) {
		return new Filter(Activity.class, Condition.EQUALS, false, String.valueOf(id), "id");
	}

	private Filter defaultFilter() {
		return new Filter(Activity.class, Condition.LOWER_THAN, false, "100", "id");
	}
}