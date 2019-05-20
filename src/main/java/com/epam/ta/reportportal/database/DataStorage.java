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

package com.epam.ta.reportportal.database;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Binary data storage
 *
 * @author Andrei Varabyeu
 */
public interface DataStorage {

	/**
	 * Saves data into storage
	 *
	 * @param binaryData {@link BinaryData}
	 * @param filename   Filename
	 * @return id of the data
	 */
	String saveData(BinaryData binaryData, String filename);

	/**
	 * Save data into storage with metadata (project name, at least)
	 *
	 * @param binaryData {@link BinaryData}
	 * @param filename   Filename
	 * @param metaInfo   Metadata
	 * @return String
	 */
	String saveData(BinaryData binaryData, String filename, Map<String, String> metaInfo);

	/**
	 * Obtains data from storage
	 *
	 * @param dataId Data ID
	 * @return BinaryData
	 */
	BinaryData fetchData(String dataId);

	/**
	 * Obtains data from storage
	 *
	 * @param filename Filename
	 * @return BinaryData
	 */
	List<BinaryData> findByFilename(String filename);

	/**
	 * Get project out-dated files from storage except photos
	 *
	 * @param period  Time period
	 * @param project Project name
	 * @return List of {@link GridFSDBFile}
	 */
	Page<DBObject> findModifiedLaterAgo(Duration period, String project, Pageable pageable);

	/**
	 * Deletes some data from storage
	 *
	 * @param dataId Data ID
	 */
	void deleteData(String dataId);

	/**
	 * Deletes all data from storage. Full clean up
	 */
	void deleteAll();

	/**
	 * Deletes data with specified IDs
	 *
	 * @param id IDs to delete
	 */
	void delete(List<String> id);

	/**
	 * Deletes data matching provided filename
	 *
	 * @param filename Filenames
	 */
	void deleteByFilename(String filename);
}