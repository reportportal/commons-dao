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

package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.entity.attribute.Attribute;
import com.epam.ta.reportportal.entity.enums.ProjectAttributeEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class ProjectUtilsTest {

	private Project project;

	private Set<Attribute> defaultAttributes;

	@Before
	public void setUp() throws Exception {
		project = new Project();
		defaultAttributes = Arrays.stream(ProjectAttributeEnum.values()).map(it -> {
			final Attribute attribute = new Attribute();
			attribute.setName(it.getAttribute());
			attribute.setId(new Random().nextLong());
			return attribute;
		}).collect(Collectors.toSet());
	}

	@Test
	public void defaultProjectAttributes() {
		final Set<ProjectAttribute> projectAttributes = ProjectUtils.defaultProjectAttributes(project, defaultAttributes);
		projectAttributes.forEach(it -> {
			assertEquals(project, it.getProject());
			assertThat(it.getAttribute()).isIn(defaultAttributes);
			assertEquals(it.getValue(), ProjectAttributeEnum.findByAttributeName(it.getAttribute().getName()).get().getDefaultValue());
		});
	}

	@Test
	public void defaultIssueTypes() {
	}

	@Test
	public void doesHaveUser() {
	}

	@Test
	public void findUserConfigByLogin() {
	}

	@Test
	public void getConfigParameters() {
	}

	@Test
	public void getOwner() {
	}
}