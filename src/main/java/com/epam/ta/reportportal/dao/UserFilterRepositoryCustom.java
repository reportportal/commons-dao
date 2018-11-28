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

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserFilterRepositoryCustom extends FilterableRepository<UserFilter> {

	/**
	 * Get all permitted filters for specified project and user
	 */
	Page<UserFilter> getPermittedFilters(Long projectId, Filter filter, Pageable pageable, String userName);

	/**
	 * Get only filters for which the user is owner
	 */
	Page<UserFilter> getOwnFilters(Long projectId, Filter filter, Pageable pageable, String userName);

	/**
	 * Get all shared filters  for specified project and user without own filters
	 */
	Page<UserFilter> getSharedFilters(Long projectId, Filter filter, Pageable pageable, String userName);
}
