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

import com.epam.ta.reportportal.commons.ReportPortalUser;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

/**
 * @author Pavel Bortnik
 */
public interface UserRepositoryCustom extends FilterableRepository<User> {

	Page<User> findByFilterExcludingProjects(Queryable filter, Pageable pageable);

	Optional<User> findRawById(Long id);

	/**
	 * Finds entities list according provided filter
	 *
	 * @param filter   Filter - Query representation
	 * @param pageable Page Representation
	 * @param exclude  Fields to exclude from query
	 * @return Found Paged objects
	 */
	Page<User> findByFilterExcluding(Queryable filter, Pageable pageable, String... exclude);

	Map<String, ProjectRole> findUsernamesWithProjectRolesByProjectId(Long projectId);

	/**
	 * Finds details about user and his project by login.
	 *
	 * @param login Login to find
	 * @return User details
	 */
	Optional<ReportPortalUser> findUserDetails(String login);

	Optional<ReportPortalUser> findReportPortalUser(String login);
}
