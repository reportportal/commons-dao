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

import com.epam.ta.reportportal.commons.querygen.ProjectFilter;
import com.epam.ta.reportportal.entity.ShareableEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public interface ShareableRepository<T extends ShareableEntity> {

	/**
	 * Get all permitted objects for specified user
	 */
	Page<T> getPermitted(ProjectFilter filter, Pageable pageable, String userName);

	/**
	 * Get only objects for which the user is owner
	 */
	Page<T> getOwn(ProjectFilter filter, Pageable pageable, String userName);

	/**
	 * Get all shared objects user without own objects
	 */
	Page<T> getShared(ProjectFilter filter, Pageable pageable, String userName);

	/**
	 * Update share flag for provided shareable entities ids.
	 */
	void updateSharingFlag(List<Long> ids, boolean isShared);

}
