/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.widget.Widget;
import com.epam.ta.reportportal.ws.model.SharedEntity;
import com.epam.ta.reportportal.ws.model.widget.WidgetPreviewRQ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public interface WidgetRepositoryCustom {

	Page<SharedEntity> getSharedWidgetNames(String userName, Long projectId, Pageable pageable);

	/**
	 * Get list of shared widget for specified project
	 *
	 * @param userName
	 * @param projectName
	 * @return
	 */
	Page<Widget> getSharedWidgetsList(String username, Long projectId, Pageable pageable);

	/**
	 * Get list of widget names for specified user
	 *
	 * @param userName
	 * @param projectName
	 * @return
	 */
	List<String> getWidgetNames(String userName, Filter filter);

	/**
	 * Get content for building preview while creating widget
	 *
	 * @param previewRQ   Widget parameters
	 * @param projectName Project name
	 * @param userName    Username
	 * @return Widget content
	 */
	Map<String, ?> getWidgetPreview(String userName, Filter filter, WidgetPreviewRQ previewRQ);

	/**
	 * Get list of widgets that contains search criteria
	 *
	 * @param term        Search criteria
	 * @param projectName Project name
	 * @return List of widgets
	 */
	Page<Widget> searchSharedWidgets(String term, Filter filter, Pageable pageable);
}
