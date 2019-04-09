/*
 * Copyright 2019 EPAM Systems
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

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.entity.enums.ProjectType;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Pavel Bortnik
 */
public interface ProjectRepositoryCustom extends FilterableRepository<Project> {

	/**
	 * Find projects info by filter with paging
	 *
	 * @param filter   Filter
	 * @param pageable Paging
	 * @return Page of project info objects
	 */
	Page<ProjectInfo> findProjectInfoByFilter(Queryable filter, Pageable pageable);

	/**
	 * Find all project names
	 *
	 * @return List of project names
	 */
	List<String> findAllProjectNames();

	/**
	 * Find all project names, which contain provided term
	 *
	 * @return List of project names
	 */
	List<String> findAllProjectNamesByTerm(String term);

	/**
	 * Get {@link Page} of {@link Project#id} with attributes
	 *
	 * @param filter   {@link Filter}
	 * @param pageable {@link Pageable}
	 * @return {@link Page} of {@link Project}s that contain only
	 * {@link Project#id}, {@link com.epam.ta.reportportal.entity.attribute.Attribute#name}
	 * and {@link com.epam.ta.reportportal.entity.project.ProjectAttribute#value}
	 */
	Page<Project> findAllIdsAndProjectAttributes(Queryable filter, Pageable pageable);

	/**
	 * Delete {@code limit} project with specified {@code projectType} and last launch run before {@code bound}
	 *
	 * @param projectType
	 * @param bound
	 * @param limit
	 * @return number of deleted projects
	 */
	int deleteByTypeAndLastLaunchRunBefore(ProjectType projectType, LocalDateTime bound, int limit);
}
