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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;

import com.epam.ta.reportportal.ws.model.widget.ChartObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * Implementation of
 * {@link org.springframework.data.mongodb.core.DocumentCallbackHandler} which
 * should be used for loading content for Launches duration chart widget
 *
 * @author Dzmitry_Kavalets
 */
public class LaunchesDurationDocumentHandler implements DocumentCallbackHandler {

	private static final String START_TIME = "start_time";
	private static final String END_TIME = "end_time";
	private static final String DURATION = "duration";
	private static final String NAME = "name";
	private static final String NUMBER = "number";
	private static final String ID = "_id";
	private static final String STATUS = "status";
	private List<ChartObject> result;

	public LaunchesDurationDocumentHandler() {
		this.result = new ArrayList<>();
	}

	@Override
	public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
		ChartObject chartObject = new ChartObject();
		Map<String, String> values = new HashMap<>();
		chartObject.setId(dbObject.get(ID).toString());
		if (dbObject.containsField(START_TIME) && dbObject.containsField(END_TIME)) {
			Long startTime = ((Date) dbObject.get(START_TIME)).getTime();
			Long endTime = ((Date) dbObject.get(END_TIME)).getTime();
			long duration = endTime - startTime;
			values.put(START_TIME, String.valueOf(startTime));
			values.put(END_TIME, String.valueOf(endTime));
			values.put(DURATION, String.valueOf(duration));
		}

		if (dbObject.containsField(NAME)) {
			chartObject.setName(dbObject.get(NAME).toString());
		}

		if (dbObject.containsField(NUMBER)) {
			chartObject.setNumber(dbObject.get(NUMBER).toString());
		}
		if (dbObject.containsField(STATUS)) {
			values.put(STATUS, dbObject.get(STATUS).toString());
		}
		chartObject.setValues(values);
		result.add(chartObject);
	}

	public List<ChartObject> getResult() {
		return result;
	}
}