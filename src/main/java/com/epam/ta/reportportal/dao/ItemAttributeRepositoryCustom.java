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

import java.util.List;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public interface ItemAttributeRepositoryCustom {

	/**
	 * Retrieves launch attribute keys by project and part of value.
	 * Used for autocompletion functionality
	 *
	 * @param projectId Id of project
	 * @param value     part of key
	 * @return List of matched attribute keys
	 */
	List<String> findKeysByProjectIdAndValue(Long projectId, String value);

	/**
	 * Retrieves launch attribute values by project, specified key and part of value.
	 * Used for autocompletion functionality
	 *
	 * @param projectId Id of project
	 * @param key       Specified key
	 * @param value     Part of value
	 * @return List of matched attribute values
	 */
	List<String> findValuesByProjectIdAndKeyLikeValue(Long projectId, String key, String value);

	/**
	 * Retrieves test item attribute keys by launch and part of value.
	 * Used for autocompletion functionality
	 *
	 * @param launchId Id of launch
	 * @param value    part of key
	 * @return List of matched attribute keys
	 */
	List<String> findKeysByLaunchIdAndValue(Long launchId, String value);

	/**
	 * Retrieves test item attribute values by launch, specified key and part of value.
	 * Used for autocompletion functionality
	 *
	 * @param launchId    Id of launch
	 * @param key       Specified key
	 * @param value     Part of value
	 * @return List of matched attribute values
	 */
	List<String> findValuesByLaunchIdAndKeyLikeValue(Long launchId, String key, String value);
}
