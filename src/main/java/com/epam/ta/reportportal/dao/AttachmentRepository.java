/*
 * Copyright 2018 EPAM Systems
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

import com.epam.ta.reportportal.entity.attachment.Attachment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.stream.Stream;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public interface AttachmentRepository extends ReportPortalRepository<Attachment, Long> {

	@QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "1"))
	@Query(value = "SELECT a.id FROM Attachment a WHERE a.projectId = :projectId")
	Stream<Long> streamIdsByProjectId(@Param("projectId") Long projectId);

	@QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "1"))
	@Query(value = "SELECT a.id FROM Attachment a WHERE a.launchId = :launchId")
	Stream<Long> streamIdsByLaunchId(@Param("launchId") Long launchId);

	@QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "1"))
	@Query(value = "SELECT a.id FROM Attachment a WHERE a.itemId = :itemId")
	Stream<Long> streamIdsByItemId(@Param("itemId") Long itemId);

}
