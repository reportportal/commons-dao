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

import static java.lang.String.valueOf;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;

import com.epam.ta.reportportal.ws.model.widget.ChartObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * Implementation of
 * {@link org.springframework.data.mongodb.core.DocumentCallbackHandler} which
 * should be used for loading content for Filter results widget
 * 
 * @author Dzmitry_Kavalets
 */
public class LaunchesTableDocumentHandler implements DocumentCallbackHandler {
	private static final String NAME = "name";
	private static final String NUMBER = "number";
	private static final String START_TIME = "start_time";
	private static final String ID = "_id";

	private List<String> contentFields;
	private Map<String, List<ChartObject>> result;

	private List<ChartObject> list;

	public LaunchesTableDocumentHandler(List<String> contentFields) {
		this.contentFields = contentFields;
		this.result = new LinkedHashMap<>();
		this.list = new ArrayList<>();
	}

	@Override
	public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
		if (contentFields == null) {
			return;
		}

		ChartObject chartObject = new ChartObject();
		Map<String, String> values = new HashMap<>();
		for (String contentField : contentFields) {
			String[] split = contentField.split("\\.");
			Object object;
			if (split.length == 1) {
				object = dbObject.get(contentField);
			} else {
				object = getValue(dbObject, split);
			}

			chartObject.setId(dbObject.get(ID).toString());

			if (contentField.equalsIgnoreCase(NAME))
				chartObject.setName(object.toString());
			else if (contentField.equalsIgnoreCase(NUMBER))
				chartObject.setNumber(object.toString());
			else if (contentField.equalsIgnoreCase(START_TIME))
				chartObject.setStartTime(valueOf(((Date) object).getTime()));
			else {
				if (object instanceof Date) {
					values.put(contentField, valueOf(((Date) object).getTime()));
				} else if (object instanceof List) {
					values.put(contentField, ((List<?>) object).stream().map(Object::toString).collect(Collectors.joining(",")));
				} else {
					values.put(contentField, null != object ? object.toString() : null);
				}
			}
		}
		chartObject.setValues(values);
		list.add(chartObject);
	}

	public Map<String, List<ChartObject>> getResult() {
		result.put("result", list);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object getValue(DBObject dbObject, String[] path) {
		boolean isExist = true;
		int counter = 1;
		Map innerDbObject = (Map) dbObject.get(path[0]);
		while (isExist) {
			if (counter >= path.length) {
				Map<String, Integer> convert = innerDbObject;
				// find total of custom defect types
				// statistics.issueCounter.productBug.total = 3
				// statistics.issueCounter.productBug.pb1 = 1
				// statistics.issueCounter.productBug.pb2 = 2
				// statistics.issueCounter.productBug.pb3 = 0
				return convert.keySet().size() > 0 ? valueOf(convert.values().stream().mapToInt(Integer::intValue).max().orElse(0)) : "0";
			}
			if (innerDbObject.containsKey(path[counter])) {
				Object innerValue = innerDbObject.get(path[counter]);
				if (innerValue instanceof Map) {
					innerDbObject = (Map) innerValue;
					counter++;
				} else {
					return innerValue;
				}
			} else {
				// return 0 if subtype is absent
				return 0;
			}
		}
		return innerDbObject;
	}
}