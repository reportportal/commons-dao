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
import org.springframework.data.mongodb.core.DocumentCallbackHandler;

import java.util.*;

/**
 * Implementation of
 * {@link org.springframework.data.mongodb.core.DocumentCallbackHandler} which
 * should be used for loading content for overall statistics panel
 * 
 * @author Dzmitry_Kavalets
 */
public class OverallStatisticsDocumentHandler implements DocumentCallbackHandler {

	private static final Integer INITIAL_VALUE = 0;
	private Map<String, Integer> temporaryData;
	private List<String> contentFields;

	public OverallStatisticsDocumentHandler(List<String> contentFields) {
		this.contentFields = new ArrayList<>();
		this.temporaryData = new HashMap<>();
		if (null != contentFields) {
			this.contentFields.addAll(contentFields);
		}
	}

	@Override
	public void processDocument(DBObject dbObject) {
		throw new UnsupportedOperationException();
//		for (String fieldName : contentFields) {
//			Integer value = temporaryData.get(fieldName);
//			Integer previousValue = null == value ? INITIAL_VALUE : value;
//			temporaryData.put(fieldName, previousValue + Integer.parseInt(getValue(dbObject, fieldName)));
//		}
	}

	public Map<String, Integer> getResult() {
		return temporaryData;
	}

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
//		if (innerObject == null)
//			return null;
//		else {
//			if (innerObject.get(innerKeys[currentIndex]) instanceof Integer) {
//				return String.valueOf(innerObject.get(innerKeys[currentIndex]));
//			} else {
//				Map<String, Integer> seria = (Map<String, Integer>) innerObject.get(innerKeys[currentIndex]);
//				String key = innerKeys.length == 4 ? innerKeys[3] : IssueCounter.GROUP_TOTAL;
//				return seria != null && seria.keySet().size() > 0 ? String.valueOf(seria.get(key)) : "0";
//			}
//		}
		return null;
	}
}