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

import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.entity.ItemAttribute;
import com.epam.ta.reportportal.entity.item.ItemAttributePojo;
import com.epam.ta.reportportal.entity.launch.Launch;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public interface ItemAttributeRepositoryCustom {

	/**
	 * Retrieves {@link com.epam.ta.reportportal.entity.launch.Launch} and {@link com.epam.ta.reportportal.entity.item.TestItem}
	 * {@link ItemAttribute#getKey()} by project id and part of the {@link ItemAttribute#getKey()}.
	 *
	 * @param launchFilter   {@link Queryable} with {@link com.epam.ta.reportportal.commons.querygen.FilterTarget#LAUNCH_TARGET}
	 * @param launchPageable {@link Pageable} for launches sorting and limitation
	 * @param isLatest       Flag defines whether all or latest launches launches will be included in the query condition
	 * @param keyPart        Part of the {@link ItemAttribute#getKey()}
	 * @param isSystem       {@link ItemAttribute#isSystem()}
	 * @return {@link List} of matched attribute keys
	 */
	List<String> findAllKeysByLaunchFilter(Queryable launchFilter, Pageable launchPageable, boolean isLatest, String keyPart,
			boolean isSystem);

	/**
	 * Retrieves launch attribute keys by project and part of value.
	 * Used for autocompletion functionality
	 *
	 * @param projectId Id of project
	 * @param value     part of key
	 * @return List of matched attribute keys
	 */
	List<String> findLaunchAttributeKeys(Long projectId, String value, boolean system);

	/**
	 * Retrieves launch attribute values by project, specified key and part of value.
	 * Used for autocompletion functionality
	 *
	 * @param projectId Id of project
	 * @param key       Specified key
	 * @param value     Part of value
	 * @return List of matched attribute values
	 */
	List<String> findLaunchAttributeValues(Long projectId, String key, String value, boolean system);

	/**
	 * Retrieves test item attribute keys by launch and part of value.
	 * Used for autocompletion functionality
	 *
	 * @param launchId Id of launch
	 * @param value    part of key
	 * @return List of matched attribute keys
	 */
	List<String> findTestItemAttributeKeys(Long launchId, String value, boolean system);

	/**
	 * Retrieves test item attribute values by launch, specified key and part of value.
	 * Used for autocompletion functionality
	 *
	 * @param launchId Id of launch
	 * @param key      Specified key
	 * @param value    Part of value
	 * @return List of matched attribute values
	 */
	List<String> findTestItemAttributeValues(Long launchId, String key, String value, boolean system);

	/**
	 * Retrieves test item attribute keys by project id and part of value.
	 * Used for autocompletion functionality
	 *
	 * @param projectId  Id of {@link com.epam.ta.reportportal.entity.project.Project} which items' attribute keys should be found
	 * @param launchName {@link Launch#getName()} which items' attributes should be found
	 * @param keyPart    part of key
	 * @return List of matched attribute keys
	 */
	List<String> findTestItemKeysByProjectIdAndLaunchName(Long projectId, String launchName, String keyPart, boolean system);

	/**
	 * Retrieves test item attribute values by project id, specified key and part of value.
	 * Used for autocompletion functionality
	 *
	 * @param projectId  Id of {@link com.epam.ta.reportportal.entity.project.Project} which items' attribute values should be found
	 * @param launchName {@link Launch#getName()} which items' attributes should be found
	 * @param key        Specified key
	 * @param valuePart  Part of value
	 * @return List of matched attribute values
	 */
	List<String> findTestItemValuesByProjectIdAndLaunchName(Long projectId, String launchName, String key, String valuePart,
			boolean system);

	/**
	 * Save item attribute by {@link com.epam.ta.reportportal.entity.item.TestItem#itemId}
	 *
	 * @param itemId   {@link ItemAttribute#testItem} ID
	 * @param key      {@link ItemAttribute#key}
	 * @param value    {@link ItemAttribute#value}
	 * @param isSystem {@link ItemAttribute#system}
	 * @return 1 if inserted, otherwise 0
	 */
	int saveByItemId(Long itemId, String key, String value, boolean isSystem);

	/**
	 * Save item attribute by {@link Launch#getId()}
	 *
	 * @return 1 if inserted, otherwise 0
	 */
	int saveByLaunchId(Long launchId, String key, String value, boolean isSystem);

	/**
	 * Method for batch inserting of the {@link ItemAttribute}. Used for performance improvement
	 *
	 * @param itemAttributes The {@link List} of the {@link ItemAttributePojo}
	 * @return Number of inserted records
	 */
	int saveMultiple(List<ItemAttributePojo> itemAttributes);
}
