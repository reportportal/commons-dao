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

import com.epam.ta.reportportal.BinaryData;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @author Pavel Bortnik
 */
public interface UserRepositoryCustom extends FilterableRepository<User> {

	String uploadUserPhoto(String username, BinaryData binaryData);

	String replaceUserPhoto(String username, BinaryData binaryData);

	String replaceUserPhoto(User user, BinaryData binaryData);

	void deleteUserPhoto(String path);

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

}
