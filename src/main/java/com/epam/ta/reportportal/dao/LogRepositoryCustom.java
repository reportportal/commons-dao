/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
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

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.log.Log;
import org.springframework.data.domain.Pageable;

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

	/**
	 * Get the specified log's page number
	 *
	 * @param id       ID of log page should be found of
	 * @param filter   Filter
	 * @param pageable Page details
	 * @return Page number log found using specified filter
	 */
	Integer getPageNumber(Long id, Filter filter, Pageable pageable);
}
