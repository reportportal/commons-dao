/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
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

import java.util.List;
import java.util.Map;

import com.mongodb.gridfs.GridFSDBFile;

/**
 * Binary data storage
 * 
 * @author Andrei Varabyeu
 * 
 */
public interface DataStorage {

	/**
	 * Saves data into storage
	 * 
	 * @param binaryData
	 * @param filename
	 * @return id of the data
	 */
	String saveData(BinaryData binaryData, String filename);

	/**
	 * Save data into storage with metadata (project name, at least)
	 * 
	 * @param binaryData
	 * @param filename
	 * @param metainfo
	 * @return String
	 */
	String saveData(BinaryData binaryData, String filename, Map<String, String> metainfo);

	/**
	 * Obtains data from storage
	 * 
	 * @param dataId
	 * @return BinaryData
	 */
	BinaryData fetchData(String dataId);

	/**
	 * Obtains data from storage
	 * 
	 * @param filename
	 * @return BinaryData
	 */
	BinaryData findByFilename(String filename);

	/**
	 * Obtain database object from GridFS.files collection by filename
	 * 
	 * @param filename
	 * @return GridFSDBFile
	 */
	GridFSDBFile findGridFSFileByFilename(String filename);

	/**
	 * Get project out-dated files from storage
	 * 
	 * @param period
	 * @param project
	 * @return List<GridFSDBFile>
	 */
	List<GridFSDBFile> findModifiedLaterAgo(Time period, String project);

	/**
	 * Deletes some data from storage
	 * 
	 * @param dataId
	 */
	void deleteData(String dataId);

	/**
	 * Deletes all data from storage. Full clean up
	 */
	void deleteAll();

	void delete(List<String> id);
}