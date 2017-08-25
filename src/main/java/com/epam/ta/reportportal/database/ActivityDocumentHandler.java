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

import com.epam.ta.reportportal.database.entity.Modifiable;
import com.epam.ta.reportportal.ws.model.widget.ChartObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;

import java.lang.reflect.Field;
import java.util.*;

import static com.epam.ta.reportportal.database.entity.item.Activity.FieldValues.*;

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
				Map<String, String> historyProps = transformHistory(dbObject, "history");
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

	private Map<String, String> transformHistory(DBObject dbObject, String dbField) {
		@SuppressWarnings("unchecked")
        List<Map<String, String>> tempHistory = (List<Map<String, String>>) dbObject.get(dbField);
		if (null == tempHistory) {
            return null;
        }
		Map<String, String> chartObjectValues = new HashMap<>();
        tempHistory.forEach(historyObject -> {
            String key = historyObject.get(FIELD);
            chartObjectValues.put(key + "$" + OLD_VALUE, historyObject.get(OLD_VALUE));
            chartObjectValues.put(key + "$" + NEW_VALUE, historyObject.get(NEW_VALUE));
        });
		return chartObjectValues;
	}

	private String getDbRepresentation(Field field) {
		return field.isAnnotationPresent(org.springframework.data.mongodb.core.mapping.Field.class)
				? field.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class).value() : field.getName();
	}
}