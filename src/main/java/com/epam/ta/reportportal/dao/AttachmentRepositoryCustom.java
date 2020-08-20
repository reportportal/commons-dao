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

import com.epam.ta.reportportal.entity.attachment.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public interface AttachmentRepositoryCustom {

	Page<Long> findIdsByProjectId(Long projectId, Pageable pageable);

	Page<Long> findIdsByLaunchId(Long launchId, Pageable pageable);

	Page<Long> findIdsByTestItemId(Collection<Long> itemIds, Pageable pageable);

	int deleteAllByIds(Collection<Long> ids);

	List<Attachment> findByItemIdsAndLogTimeBefore(Collection<Long> itemIds, LocalDateTime before);

	List<Attachment> findByLaunchIdsAndLogTimeBefore(Collection<Long> launchIds, LocalDateTime before);
}
