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

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.item.NestedItem;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.log.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

/**
 * @author Pavel Bortnik
 */
public interface LogRepositoryCustom extends FilterableRepository<Log> {

    /**
     * Checks if the test item has any logs.
     *
     * @param itemId Item id
     * @return true if logs were found
     */
    boolean hasLogs(Long itemId);

    /**
     * Load specified number of last logs for specified test item. binaryData
     * field will be loaded if it specified in appropriate input parameter, all
     * other fields will be fully loaded.
     *
     * @param limit  Max count of logs to be loaded
     * @param itemId Test Item log belongs to
     * @return Found logs
     */
    List<Log> findByTestItemId(Long itemId, int limit);

    /**
     * Load specified number of last logs for specified test item. binaryData
     * field will be loaded if it specified in appropriate input parameter, all
     * other fields will be fully loaded.
     *
     * @param itemId Test Item log belongs to
     * @return Found logs
     */
    List<Log> findByTestItemId(Long itemId);

    List<Long> findIdsByFilter(Queryable filter);

    List<Long> findIdsByTestItemId(Long testItemId);

    List<Long> findIdsUnderTestItemByLaunchIdAndTestItemIdsAndLogLevelGte(Long launchId, List<Long> itemIds, int logLevel);

    List<Long> findItemLogIdsByLaunchIdAndLogLevelGte(Long launchId, int logLevel);

    List<Long> findItemLogIdsByLaunchIdsAndLogLevelGte(List<Long> launchIds, int logLevel);

    /**
     * Load {@link Log} by {@link com.epam.ta.reportportal.entity.item.TestItem#itemId} referenced from {@link Log#testItem} and {@link Duration}
     *
     * @param itemId {@link com.epam.ta.reportportal.entity.item.TestItem#itemId}
     * @param period {@link Duration}
     * @return List of {@link Log} with {@link Log#id}, {@link Log#attachment} and {@link Log#attachment} that were modified before the specified time period
     */
    List<Log> findLogsWithThumbnailByTestItemIdAndPeriod(Long itemId, Duration period);

    /**
     * Get the specified log's page number
     *
     * @param id       ID of log page should be found of
     * @param filter   Filter
     * @param pageable Page details
     * @return Page number log found using specified filter
     */
    Integer getPageNumber(Long id, Filter filter, Pageable pageable);

    /**
     * True if the {@link com.epam.ta.reportportal.entity.item.TestItem} with matching 'status' and 'launchId'
     * has {@link Log}'s with {@link Log#lastModified} up to the current point of time minus provided 'period'
     *
     * @param period   {@link Duration}
     * @param launchId {@link com.epam.ta.reportportal.entity.launch.Launch#id}
     * @param statuses {@link StatusEnum}
     * @return true if logs(the log) exist(exists)
     */
    boolean hasLogsAddedLately(Duration period, Long launchId, StatusEnum... statuses);

    /**
     * @param period      {@link Duration}
     * @param testItemIds Collection of the {@link com.epam.ta.reportportal.entity.item.TestItem#itemId} referenced from {@link Log#testItem}
     * @return Count of removed logs
     */
    int deleteByPeriodAndTestItemIds(Duration period, Collection<Long> testItemIds);

    /**
     * Retrieve {@link Log} and {@link com.epam.ta.reportportal.entity.item.TestItem} entities' ids, differentiated by entity type
     * <p>
     * {@link Log} and {@link com.epam.ta.reportportal.entity.item.TestItem} entities filtered and sorted on the DB level
     * and returned as UNION parsed into the {@link NestedItem} entity
     *
     * @param parentId          {@link com.epam.ta.reportportal.entity.item.TestItem#itemId} of the parent item
     * @param filter            {@link Queryable}
     * @param excludeEmptySteps Exclude steps without content (logs and child items)
     * @param excludeLogs       Exclude logs selection
     * @param pageable          {@link Pageable}
     * @return {@link Page} with {@link NestedItem} as content
     */
    Page<NestedItem> findNestedItems(Long parentId, boolean excludeEmptySteps, boolean excludeLogs, Queryable filter, Pageable pageable);

    /**
     * Retrieves log message of specified test item with log level greather or equals than {@code level}
     *
     * @param itemId ID of {@link TestItem}
     * @param level  log level
     * @return {@link List} of {@link String} of log messages
     */
    List<String> findMessagesByItemIdAndLevelGte(Long itemId, Integer level);
}
