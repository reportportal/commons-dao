package com.epam.ta.reportportal.database.dao;

import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.ta.reportportal.BaseDaoTest;
import com.epam.ta.reportportal.database.entity.Launch;

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

	public List<Launch> findByProjectIdsData() {
		final Launch launch1 = new Launch();
		launch1.setProjectRef("project1");
		final Launch launch2 = new Launch();
		launch2.setProjectRef("project2");
		final Launch launch3 = new Launch();
		launch3.setProjectRef("project2");
		final Launch launch4 = new Launch();
		return launchRepository.save(asList(launch1, launch2, launch3, launch4));
	}

	@After
	public void cleanup() {
		launchRepository.deleteAll();
	}
}