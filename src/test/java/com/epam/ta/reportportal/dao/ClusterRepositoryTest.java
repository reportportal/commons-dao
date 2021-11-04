/*
 * Copyright 2021 EPAM Systems
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

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.entity.cluster.Cluster;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_ID;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
@Sql("/db/fill/launch/launch-fill.sql")
class ClusterRepositoryTest extends BaseTest {

	private static final long CLUSTER_ID_START_VALUE = 1L;
	private static final long CLUSTER_ID_END_VALUE = 4L;
	private static final long PROJECT_ID = 1L;
	private static final long LAUNCH_ID = 1L;

	private final List<Long> savedIds = new ArrayList<>();

	@Autowired
	private ClusterRepository clusterRepository;

	@BeforeEach
	void insertClusters() {
		final List<Cluster> clusters = LongStream.range(CLUSTER_ID_START_VALUE, CLUSTER_ID_END_VALUE).mapToObj(id -> {
			final Cluster cluster = new Cluster();
			cluster.setIndexId(id);
			cluster.setProjectId(PROJECT_ID);
			cluster.setLaunchId(LAUNCH_ID);
			cluster.setMessage("Message");
			return cluster;
		}).collect(Collectors.toList());
		clusterRepository.saveAll(clusters);
		clusters.stream().map(Cluster::getId).forEach(savedIds::add);
	}

	@AfterEach
	void removeClusters() {
		savedIds.stream().map(clusterRepository::findById).forEach(c -> c.ifPresent(clusterRepository::delete));
		savedIds.clear();
	}

	@Test
	void shouldFindAllByLaunchId() {
		final List<Cluster> clusters = clusterRepository.findAllByLaunchId(LAUNCH_ID);
		assertFalse(clusters.isEmpty());
		assertEquals(3, clusters.size());
		clusters.forEach(cluster -> assertEquals(LAUNCH_ID, cluster.getLaunchId()));
	}

	@Test
	void shouldFindByLaunchId() {

		final Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Order.by(CRITERIA_ID)));
		final Page<Cluster> clusters = clusterRepository.findAllByLaunchId(LAUNCH_ID, pageable);
		assertFalse(clusters.isEmpty());
		assertEquals(3, clusters.getContent().size());
		clusters.getContent().forEach(cluster -> assertEquals(LAUNCH_ID, cluster.getLaunchId()));
	}

	@Test
	void shouldDeleteByLaunchId() {
		final int removed = clusterRepository.deleteAllByLaunchId(LAUNCH_ID);
		assertEquals(3, removed);

		final Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Order.by(CRITERIA_ID)));
		final Page<Cluster> clusters = clusterRepository.findAllByLaunchId(LAUNCH_ID, pageable);
		assertTrue(clusters.isEmpty());
	}

	@Test
	void shouldDeleteByProjectId() {
		final int removed = clusterRepository.deleteAllByProjectId(PROJECT_ID);
		assertEquals(3, removed);

		final Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Order.by(CRITERIA_ID)));
		final Page<Cluster> clusters = clusterRepository.findAllByLaunchId(LAUNCH_ID, pageable);
		assertTrue(clusters.isEmpty());
	}

}