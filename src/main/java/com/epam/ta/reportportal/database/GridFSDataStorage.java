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

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import com.epam.ta.reportportal.database.search.ModifiableQueryBuilder;
import com.google.common.base.Preconditions;
import com.mongodb.gridfs.GridFSDBFile;

/**
 * Stores file in GridFS
 * 
 * @author Andrei Varabyeu
 * 
 */
public class GridFSDataStorage implements DataStorage {

	private static final String ID_FIELD = "_id";

	private final GridFsOperations gridFs;

	public GridFSDataStorage(GridFsOperations gridFs) {
		this.gridFs = Preconditions.checkNotNull(gridFs, "GridFS Template shouldn't be null");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.epam.ta.reportportal.database.DataStorage#saveData(com.epam.ta.
	 * reportportal.database.BinaryData, java.lang.String)
	 */
	@Override
	public String saveData(BinaryData binaryData, String filename) {
		return gridFs.store(binaryData.getInputStream(), filename, binaryData.getContentType()).getId().toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.epam.ta.reportportal.database.DataStorage#saveData(com.epam.ta.
	 * reportportal.database.BinaryData, java.lang.String, java.lang.Object)
	 */
	@Override
	public String saveData(BinaryData binaryData, String filename, Map<String, String> metainfo) {
		return gridFs.store(binaryData.getInputStream(), filename, binaryData.getContentType(), metainfo).getId().toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.epam.ta.reportportal.database.DataStorage#findModifiedLaterAgo(
	 * com.epam.ta.reportportal.util.Time, java.lang.String)
	 */
	@Override
	public List<GridFSDBFile> findModifiedLaterAgo(Time period, String project) {
		return gridFs.find(ModifiableQueryBuilder.findModifiedLaterThanPeriod(period, project));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.epam.ta.reportportal.database.DataStorage#fetchData(java.lang.String)
	 */
	@Override
	public BinaryData fetchData(String dataId) {
		GridFSDBFile file = gridFs.findOne(findByIdQuery(new ObjectId(dataId)));
		return null == file ? null : new BinaryData(file.getContentType(), file.getLength(), file.getInputStream());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.epam.ta.reportportal.database.DataStorage#findByFilename(java.lang
	 * .String)
	 */
	@Override
	public List<BinaryData> findByFilename(String filename) {
		return gridFs.find(findByFilenameQuery(filename)).stream()
				.map(file -> new BinaryData(file.getContentType(), file.getLength(), file.getInputStream()))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.epam.ta.reportportal.database.DataStorage#deleteData(java.lang.String
	 * )
	 */
	@Override
	public void deleteData(String dataId) {
		gridFs.delete(findByIdQuery(new ObjectId(dataId)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.epam.ta.reportportal.database.DataStorage#deleteAll()
	 */
	@Override
	public void deleteAll() {
		gridFs.delete(queryForAll());
	}

	@Override
	public void delete(List<String> ids) {
		gridFs.delete(query(where("_id").in(ids)));
	}

	@Override
	public void deleteByFilename(String filename) {
		gridFs.delete(findByFilenameQuery(filename));
	}

	/**
	 * Returns empty query which should find all objects
	 * 
	 * @return Mongo's Query
	 */
	private Query queryForAll() {
		return new Query();
	}

	/**
	 * Find by ID query
	 * 
	 * @param id Object ID
	 * @return Mongo's Query
	 */
	private Query findByIdQuery(ObjectId id) {
		return query(where(ID_FIELD).is(id));
	}

	/**
	 * Find by name query
	 * 
	 * @param filename Filename
	 * @return Mongo's Query
	 */
	private Query findByFilenameQuery(String filename) {
		return query(whereFilename().is(filename));
	}
}