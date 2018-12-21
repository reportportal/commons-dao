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

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class WidgetRepositoryTest extends BaseTest {

	@Autowired
	private WidgetRepository widgetRepository;

	@Test
	public void existsByNameAndOwnerAndProjectId() {

		Assert.assertTrue(widgetRepository.existsByNameAndOwnerAndProjectId("LAUNCH TABLE", "superadmin", 1L));
		Assert.assertFalse(widgetRepository.existsByNameAndOwnerAndProjectId("LAUNCH TABLE", "yahoo", 1L));
		Assert.assertFalse(widgetRepository.existsByNameAndOwnerAndProjectId("LAUNCH TABLE", "superadmin", 2L));
	}
}