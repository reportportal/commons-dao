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

import java.util.*;

import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;

import com.epam.ta.reportportal.database.entity.statistics.IssueCounter;
import com.epam.ta.reportportal.ws.model.widget.ChartObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * Implementation of {@link DocumentCallbackHandler} which should be used during
 * <br>
 * loading chart content for widgets. This callback allow transform selected
 * Launch's<br>
 * or TestItem's {@link DBObject}s to arrays. Specified fields should be added
 * to result array.
 * 
 * @author Aliaksei_Makayed
 * @author Andrei_Ramanchuk
 */
public class StatisticsDocumentHandler implements DocumentCallbackHandler {
	private static final String NAME = "name";
	private static final String NUMBER = "number";
	private static final String START_TIME = "start_time";

	private List<String> fieldsForChart;
	private List<String> fieldXAxis;

	/*
	 * List of processed objects from BD for charts
	 */
	private List<ChartObject> result;

	public StatisticsDocumentHandler(List<String> fieldsChart, List<String> xFields) {
		this.fieldsForChart = fieldsChart;
		result = new ArrayList<>();
		this.fieldXAxis = xFields;
	}

	@Override
	public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
		if (fieldsForChart == null) {
			return;
		}

		ChartObject chartObject = new ChartObject();
		if (fieldXAxis.contains(NAME))
			chartObject.setName(getValue(dbObject, fieldXAxis.get(fieldXAxis.indexOf(NAME))));
		if (fieldXAxis.contains(NUMBER))
			chartObject.setNumber(getValue(dbObject, fieldXAxis.get(fieldXAxis.indexOf(NUMBER))));
		if (fieldXAxis.contains(START_TIME))
			chartObject.setStartTime(getValue(dbObject, fieldXAxis.get(fieldXAxis.indexOf(START_TIME))));
		chartObject.setId(dbObject.get("_id").toString());

		Map<String, String> values = new HashMap<>();
		fieldsForChart.stream().forEach(field -> values.put(field, getValue(dbObject, field)));
		chartObject.setValues(values);
		result.add(chartObject);
	}

	public List<ChartObject> getResult() {
		return result;
	}

	/**
	 * Iterate thru inner DBObjects and return field value, throw
	 * ReportPortalException if value isn't integer.
	 * 
	 * @param dbObject
	 * @param field
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	private String getValue(DBObject dbObject, String field) {
		String[] innerKeys = field.split("\\.");
		DBObject innerObject = dbObject;
		int currentIndex = 0;
		int itterationCount = innerKeys.length - 1;
		while (currentIndex < itterationCount) {
			innerObject = (DBObject) innerObject.get(innerKeys[currentIndex]);
			if (innerObject == null) {
				break;
			}
			currentIndex++;
		}
		if ((innerObject != null) && (innerObject.get(innerKeys[currentIndex]) instanceof Date)) {
			return String.valueOf(((Date) innerObject.get(innerKeys[currentIndex])).getTime());
		}

		// TODO Should be refactored after new UI implementation for issue
		// statistics!
		if (innerObject == null)
			return null;
		else {
			if ((innerObject.get(innerKeys[currentIndex]) instanceof Integer)
					|| (innerObject.get(innerKeys[currentIndex]) instanceof String)
					|| (innerObject.get(innerKeys[currentIndex]) instanceof Long)) {
				return String.valueOf(innerObject.get(innerKeys[currentIndex]));
			} else {
				Map<String, Integer> seria = (Map<String, Integer>) innerObject.get(innerKeys[currentIndex]);
				String key = innerKeys.length == 4 ? innerKeys[3] : IssueCounter.GROUP_TOTAL;
				return seria != null && seria.keySet().size() > 0 ? String.valueOf(seria.get(key)) : "0";
			}
		}
	}
}