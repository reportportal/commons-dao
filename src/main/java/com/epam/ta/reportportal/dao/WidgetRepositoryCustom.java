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

import com.epam.ta.reportportal.entity.widget.Widget;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public interface WidgetRepositoryCustom extends ShareableRepository<Widget> {

	/**
	 * Remove many to many relation between {@link com.epam.ta.reportportal.entity.filter.UserFilter} by specified
	 * {@link com.epam.ta.reportportal.entity.filter.UserFilter#id} and {@link Widget} entities,
	 * that are not owned by the {@link com.epam.ta.reportportal.entity.filter.UserFilter} owner
	 *
	 * @param filterId {@link com.epam.ta.reportportal.entity.filter.UserFilter#id}
	 * @param owner    {@link Widget#owner}
	 * @return count of removed {@link Widget} entities
	 */
	int deleteRelationByFilterIdAndNotOwner(Long filterId, String owner);

}
