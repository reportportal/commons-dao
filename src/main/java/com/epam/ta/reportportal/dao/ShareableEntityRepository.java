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

import com.epam.ta.reportportal.entity.ShareableEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public interface ShareableEntityRepository extends ReportPortalRepository<ShareableEntity, Long> {

	/**
	 * Find all shareable entities on project with share status
	 *
	 * @param projectId Project id
	 * @param shared    Shared or not
	 * @return List of shareable entities
	 */
	@Query(value = "SELECT s.id, s.project_id, s.owner, s.shared, 0 as clazz_ FROM shareable_entity s WHERE s.project_id = :projectId AND s.shared = :shared", nativeQuery = true)
	List<ShareableEntity> findAllByProjectIdAndShared(@Param("projectId") Long projectId, @Param("shared") boolean shared);

}
