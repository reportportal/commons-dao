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

import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.entity.attribute.Attribute;
import com.epam.ta.reportportal.entity.enums.ProjectType;
import com.epam.ta.reportportal.entity.project.Project;
import com.epam.ta.reportportal.entity.project.ProjectAttribute;
import com.epam.ta.reportportal.entity.project.ProjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Pavel Bortnik
 */
public interface ProjectRepositoryCustom extends FilterableRepository<Project> {

	/**
	 * Find project entity without fetching related entities
	 *
	 * @param name Project name to search
	 * @return {@link Optional} with {@link Project}
	 */
	Optional<Project> findRawByName(String name);

	/**
	 * Find projects info by filter
	 *
	 * @param filter Filter
	 * @return List of project info objects
	 */
	List<ProjectInfo> findProjectInfoByFilter(Queryable filter);

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

	List<Project> findAllByUserLogin(String login);

	/**
	 * Get {@link Page} of {@link Project#getId()} with attributes
	 *
	 * @param pageable {@link Pageable}
	 * @return {@link Page} of {@link Project}s that contain only
	 * {@link Project#getId()}, {@link Attribute#getName()}
	 * and {@link ProjectAttribute#getValue()}
	 */
	Page<Project> findAllIdsAndProjectAttributes(Pageable pageable);

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
