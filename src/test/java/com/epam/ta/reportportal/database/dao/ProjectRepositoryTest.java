/*
 * Copyright 2017 EPAM Systems
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
package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.BaseDaoTest;
import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.project.EntryType;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * @author Andrei Varabyeu
 */
public class ProjectRepositoryTest extends BaseDaoTest {

	@Autowired
	private ProjectRepository projectRepository;

	@Test
	public void testAddNewMetadataPostfix() {
		String projectName = "metadata_test";

		Project p = new Project();
		p.setName(projectName);

		projectRepository.save(p);

		projectRepository.addDemoDataPostfix(projectName, "metadataPostfix");

		List<String> postfixes = projectRepository.findOne(projectName).getMetadata().getDemoDataPostfix();
		Assert.assertThat("Exception during saving demo data postfix", postfixes, CoreMatchers.hasItem("metadataPostfix"));
	}

	@Test
	public void testFindPersonalProjectByUserName() {
		Project project = new Project();
		project.getConfiguration().setEntryType(EntryType.PERSONAL);
		project.setName("default_personal");
		projectRepository.save(project);
		Optional<String> defaultPersonal = projectRepository.findPersonalProjectName("default");
		Assert.assertTrue(defaultPersonal.isPresent());
	}

}