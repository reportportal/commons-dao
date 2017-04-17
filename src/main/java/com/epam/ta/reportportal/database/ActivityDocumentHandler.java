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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;

import com.epam.ta.reportportal.commons.accessible.Accessible;
import com.epam.ta.reportportal.commons.accessible.AccessibleField;
import com.epam.ta.reportportal.database.entity.Modifiable;
import com.epam.ta.reportportal.database.entity.item.Activity;
import com.epam.ta.reportportal.ws.model.widget.ChartObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * @author Dzmitry_Kavalets
 */
public class ActivityDocumentHandler implements DocumentCallbackHandler {

	private static final String ID = "_id";
	private static final String HISTORY = "history";
	private final List<ChartObject> result;

	public ActivityDocumentHandler() {
		result = new ArrayList<>();
	}

	@Override
	public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
		Set<String> keySet = dbObject.keySet();
		ChartObject activity = new ChartObject();
		Map<String, String> objectValues = new HashMap<>();
		for (String key : keySet) {
			switch (key) {
			case HISTORY:
				Map<String, String> historyProps = transformHistoryMap(dbObject, "history");
				objectValues.putAll(historyProps);
				break;
			case Modifiable.LAST_MODIFIED:
				objectValues.put(key, String.valueOf(((Date) dbObject.get(key)).getTime()));
				break;
			case ID:
				activity.setId(dbObject.get(ID).toString());
				break;
			default:
				objectValues.put(key, dbObject.get(key).toString());
				break;
			}
		}
		activity.setValues(objectValues);
		result.add(activity);
	}

	public List<ChartObject> getResult() {
		return result;
	}

	private Map<String, String> transformHistoryMap(DBObject dbObject, String dbField) {
		@SuppressWarnings("unchecked")
		Map<String, Map<String, String>> tempHistory = (Map<String, Map<String, String>>) dbObject.get(dbField);
		if (null == tempHistory)
			return null;

		Map<String, String> chartObjectValues = new HashMap<>();

		for (Map.Entry<String, Map<String, String>> entry : tempHistory.entrySet()) {
			Activity.FieldValues fieldValues = new Activity.FieldValues();
			for (Field field : fieldValues.getClass().getDeclaredFields()) {
				String innerDbField = getDbRepresentation(field);
				AccessibleField innerField = Accessible.on(fieldValues).field(field);
				Map<String, String> values = entry.getValue();
				if ((values != null) && (null != values.get(innerDbField)))
					innerField.setValue(values.get(innerDbField));
			}
			chartObjectValues.put(entry.getKey() + "$" + Activity.FieldValues.OLD_VALUE, fieldValues.getOldValue());
			chartObjectValues.put(entry.getKey() + "$" + Activity.FieldValues.NEW_VALUE, fieldValues.getNewValue());
		}
		return chartObjectValues;
	}

	private String getDbRepresentation(Field field) {
		return field.isAnnotationPresent(org.springframework.data.mongodb.core.mapping.Field.class)
				? field.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class).value() : field.getName();
	}
}