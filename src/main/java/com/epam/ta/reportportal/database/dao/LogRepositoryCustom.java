/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
 * 
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.Log;
import com.epam.ta.reportportal.database.entity.item.TestItem;

import java.time.Duration;
import java.util.Date;
import java.util.List;

/**
 * Custom repository implementation. Is not used for know to avoid
 * misunderstanding. Adds possibility to extend current implementation with
 * several new methods
 * 
 * @author Andrei Varabyeu
 * 
 */
public interface LogRepositoryCustom {

    /**
     * Removes binary content field for specified fileId
     *
     * @param fileId
     * @return true if successfully removed
     */
    void removeBinaryContent(String fileId);

	/**
	 * Finds logs for specified test steps
	 * 
	 * @param testStep
	 * @return
	 */
	List<Log> findLogsByTestItem(Iterable<TestItem> testStep);

	/**
	 * Finds entities modified later than provided time
	 * 
	 * @param date
	 * @return
	 */
	List<Log> findModifiedBeforeThan(Date date);

	/**
	 * Finds entities modified later than period
	 * 
	 * @param time
	 * @return
	 */
	List<Log> findModifiedLaterAgo(Duration time);

	/**
	 * Finds entities modified later than period
	 * 
	 * @param time
	 * @return
	 */
	List<Log> findModifiedLaterAgo(Duration time, Iterable<TestItem> testItems);

	/**
	 * Get number of logs which attached to the specified {@link TestItem}
	 * 
	 * @param testStep
	 * @return
	 */
	long getNumberOfLogByTestItem(TestItem testStep);

	/**
	 * Load specified number of last logs for specified test item. binaryData
	 * field will be loaded if it specified in appropriate input parameter, all
	 * other fields will be fully loaded.
	 * 
	 * @param itemRef
	 * @param limit
	 * @param isLoadBinaryData
	 * @return
	 */
	List<Log> findByTestItemRef(String itemRef, int limit, boolean isLoadBinaryData);

	/**
	 * Whether test item contains any logs added lately
	 * 
	 * @param period
	 * @param testItem
	 * @return
	 */
	boolean hasLogsAddedLately(Duration period, TestItem testItem);

	/**
	 * Get list of log items with specified file id
	 * 
	 * @param fileId
	 * @return
	 */
	List<Log> findLogsByFileId(String fileId);

	/**
	 * Find all {@link Log} records with log_level<br>
	 * more than 40000 (ERROR and FATAL)
	 * 
	 * @param testItemId
	 * @return
	 */
	List<Log> findTestItemErrorLogs(String testItemId, int limit);

	List<String> findLogIdsByItemRefs(List<String> ids);

	List<String> findBinaryIdsByLogRefs(List<String> ids);

	List<String> findBinaryIdsByItemRefs(List<String> ids);

	void deleteByItemRef(List<String> ids);

	/**
	 * Remove all items' {@link Log} records that modified before the time
	 * @param time
	 * @param itemRef
	 */
	void deleteByPeriodAndItemsRef(Duration time, List<String> itemRef);
}