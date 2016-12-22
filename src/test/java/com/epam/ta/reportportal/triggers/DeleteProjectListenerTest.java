/*
 * Copyright 2016 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.epam.ta.reportportal.triggers;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.ta.reportportal.BaseDaoTest;
import com.epam.ta.reportportal.database.BinaryData;
import com.epam.ta.reportportal.database.DataStorage;
import com.epam.ta.reportportal.database.dao.*;
import com.epam.ta.reportportal.database.entity.*;
import com.epam.ta.reportportal.database.entity.item.Activity;
import com.epam.ta.reportportal.database.entity.item.TestItem;

/**
 * @author Dzmitry Kavalets
 */
public class DeleteProjectListenerTest extends BaseDaoTest {

	public static final String CONTENT_TYPE = "image/png";
	public static final String PROJECT_ID = "test_project";

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private LaunchRepository launchRepository;
	@Autowired
	private TestItemRepository testItemRepository;
	@Autowired
	private DataStorage dataStorage;
	@Autowired
	private LogRepository logRepository;
	@Autowired
	private ExternalSystemRepository externalSystemRepository;
	@Autowired
	private ActivityRepository activityRepository;

	@Test
	public void deleteProject() {
		final TestData testData = testData();
		final List<ExternalSystem> externalSystems = externalSystems();
		final List<Activity> activities = activities();
		projectRepository.delete(testData.projectId);
		assertFalse(projectRepository.exists(testData.projectId));
		assertFalse(launchRepository.exists(testData.launchId));
		assertFalse(testItemRepository.exists(testData.suiteId));
		assertFalse(testItemRepository.exists(testData.testId));
		assertFalse(testItemRepository.exists(testData.stepId));
		assertFalse(logRepository.exists(testData.logId));
		assertNull(dataStorage.fetchData(testData.fileName));
		assertNull(dataStorage.fetchData(testData.thumbnail));
		externalSystems.forEach(it -> assertFalse(externalSystemRepository.exists(it.getId())));
		activities.forEach(it -> assertFalse(activityRepository.exists(it.getId())));
	}

	private List<Activity> activities() {
		List<Activity> activities = new ArrayList<>();
		final Activity activity1 = new Activity();
		final Activity activity2 = new Activity();
		final Activity activity3 = new Activity();
		activity1.setProjectRef(PROJECT_ID);
		activity2.setProjectRef(PROJECT_ID);
		activity3.setProjectRef(PROJECT_ID);
		activities.add(activity1);
		activities.add(activity2);
		activities.add(activity3);
		activityRepository.save(activities);
		return activities;
	}

	private List<ExternalSystem> externalSystems() {
		List<ExternalSystem> externalSystems = new ArrayList<>();
		final ExternalSystem externalSystem1 = new ExternalSystem();
		externalSystem1.setProjectRef(PROJECT_ID);
		final ExternalSystem externalSystem2 = new ExternalSystem();
		externalSystem2.setProjectRef(PROJECT_ID);
		final ExternalSystem externalSystem3 = new ExternalSystem();
		externalSystem3.setProjectRef(PROJECT_ID);
		externalSystems.add(externalSystem1);
		externalSystems.add(externalSystem2);
		externalSystems.add(externalSystem3);
		externalSystemRepository.save(externalSystems);
		return externalSystems;
	}

	private TestData testData() {
		final Project project = new Project();
		project.setName(PROJECT_ID);
		projectRepository.save(project);

		final Launch launch = new Launch();
		launch.setProjectRef(project.getId());
		launchRepository.save(launch);

		final TestItem suite = new TestItem();
		suite.setLaunchRef(launch.getId());
		testItemRepository.save(suite);

		final TestItem test = new TestItem();
		test.setLaunchRef(launch.getId());
		test.setParent(suite.getId());
		test.setPath(singletonList(suite.getId()));
		testItemRepository.save(test);

		final TestItem step = new TestItem();
		step.setLaunchRef(launch.getId());
		step.setParent(test.getId());
		final List<String> stepPath = new ArrayList<>(test.getPath());
		stepPath.add(test.getId());
		step.setPath(stepPath);
		testItemRepository.save(step);

		final Log log = log(step.getId());

		final TestData testData = new TestData();
		testData.projectId = project.getId();
		testData.launchId = launch.getId();
		testData.suiteId = suite.getId();
		testData.testId = test.getId();
		testData.stepId = step.getId();
		testData.logId = log.getId();
		testData.fileName = log.getBinaryContent().getBinaryDataId();
		testData.thumbnail = log.getBinaryContent().getThumbnailId();
		return testData;

	}

	private Log log(String itemId) {
		final Log log = new Log();
		log.setTestItemRef(itemId);
		Map<String, String> metaInfo = new HashMap<>();
		metaInfo.put("project", "test_project");
		BinaryData binaryData = new BinaryData(CONTENT_TYPE, 64807L, this.getClass().getClassLoader().getResourceAsStream("meh.jpg"));
		String filename = dataStorage.saveData(binaryData, "filename", metaInfo);
		String thumbnail = dataStorage.saveData(binaryData, "thumbnail", metaInfo);
		BinaryContent binaryContent = new BinaryContent(filename, thumbnail, CONTENT_TYPE);
		log.setBinaryContent(binaryContent);
		logRepository.save(log);
		return log;
	}

	private static class TestData {
		String projectId;
		String launchId;
		String suiteId;
		String testId;
		String stepId;
		String logId;
		String fileName;
		String thumbnail;
	}
}
