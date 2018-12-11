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

import com.epam.ta.reportportal.entity.widget.Widget;

import java.util.List;

/**
 * @author Pavel Bortnik
 */
public interface WidgetRepository extends ReportPortalRepository<Widget, Long>, WidgetRepositoryCustom {

	/**
	 * @param projectId Id of the {@link com.epam.ta.reportportal.entity.project.Project} whose widgets will be extracted
	 * @return The {@link List} of the {@link Widget}
	 */
	List<Widget> findAllByProjectId(Long projectId);

	boolean existsByNameAndOwnerAndProjectId(String name, String owner, Long projectId);
}
