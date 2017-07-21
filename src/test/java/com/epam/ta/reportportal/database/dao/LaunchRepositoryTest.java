package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.BaseDaoTest;
import com.epam.ta.reportportal.database.entity.Launch;
import com.epam.ta.reportportal.database.search.Filter;
import com.epam.ta.reportportal.database.search.FilterCondition;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class LaunchRepositoryTest extends BaseDaoTest {

	@Autowired
	private LaunchRepository launchRepository;

	@Test
	public void findByProjectIdsTest() {
		final List<Launch> data = findByProjectIdsData();
		final List<String> launchIdsByProjectIds = launchRepository.findLaunchIdsByProjectIds(asList("project1", "project2"));
		sort(launchIdsByProjectIds);
		assertThat(launchIdsByProjectIds).size().isEqualTo(3);
		final List<String> expected = data.stream().limit(3).map(Launch::getId).sorted().collect(toList());
		assertThat(expected).hasSameElementsAs(launchIdsByProjectIds);
	}

	@Test
	public void findByWithExcludeTest() {
		findByProjectIdsData();
		final Page<Launch> foundLaunches = launchRepository
				.findByFilterExcluding(
						Filter.builder().withTarget(Launch.class)
								.withCondition(
										FilterCondition.builder().withCondition(com.epam.ta.reportportal.database.search.Condition.IN)
												.withValue("project1,project2").withSearchCriteria("project").build())
								.build(),
						null, "name");

		boolean nonNullName = StreamSupport.stream(foundLaunches.spliterator(), false).anyMatch(l -> l.getName() != null);
		assertThat(nonNullName).isFalse();
	}

	@Test
	public void deleteByProjectRef() {
		findByProjectIdsData();
		launchRepository.deleteByProjectRef("project1");
		Assert.assertTrue(launchRepository.findLaunchIdsByProjectId("project1").isEmpty());
	}

	public List<Launch> findByProjectIdsData() {
		final Launch launch1 = new Launch();
		launch1.setProjectRef("project1");
		launch1.setName("launch1");
		final Launch launch2 = new Launch();
		launch2.setProjectRef("project2");
		launch2.setName("launch2");
		final Launch launch3 = new Launch();
		launch3.setProjectRef("project2");
		launch3.setName("launch3");
		final Launch launch4 = new Launch();
		return launchRepository.save(asList(launch1, launch2, launch3, launch4));
	}

	@After
	public void cleanup() {
		launchRepository.deleteAll();
	}
}