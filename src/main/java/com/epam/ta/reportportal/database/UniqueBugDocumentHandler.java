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

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;

import com.epam.ta.reportportal.ws.model.widget.ChartObject;
import com.google.common.collect.Lists;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * Implementation of
 * {@link org.springframework.data.mongodb.core.DocumentCallbackHandler} which
 * should be used for loading content for unique bug table widget
 *
 * @author Dzmitry_Kavalets
 * @author Andrei_Ramanchuk
 */
public class UniqueBugDocumentHandler implements DocumentCallbackHandler {

	private final static String LAUNCH_REF = "launchRef";
	private final static String ISSUE = "issue";
	private final static String SUBMIT_DATE = "submitDate";
	private final static String SUBMITTER = "submitter";
	private final static String ID = "_id";
	private final static String TICKET_ID = "ticketId";
	private final static String EXTERNAL_SYSTEM_ISSUES = "externalSystemIssues";
	private Map<String, List<ChartObject>> result;

	public UniqueBugDocumentHandler() {
		result = new LinkedHashMap<>();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
		if (dbObject.containsField(ISSUE) && ((DBObject) dbObject.get(ISSUE)).containsField(EXTERNAL_SYSTEM_ISSUES)) {
			List<DBObject> innerDbObjects = (List<DBObject>) ((DBObject) dbObject.get(ISSUE)).get(EXTERNAL_SYSTEM_ISSUES);
			for (DBObject innerDbObject : innerDbObjects) {
				ChartObject axisObject = new ChartObject();
				Map<String, String> objectValues = new HashMap<>();
				String ticketId;
				if (dbObject.containsField(LAUNCH_REF)) {
					objectValues.put(LAUNCH_REF, dbObject.get(LAUNCH_REF).toString());
					axisObject.setValues(objectValues);
				}
				if (dbObject.containsField(ID)) {
					/* ID of test-item containing external system issue */
					axisObject.setId(dbObject.get(ID).toString());
				}
				if (innerDbObject.containsField(SUBMIT_DATE)) {
					axisObject.setStartTime(innerDbObject.get(SUBMIT_DATE).toString());
				}
				if (innerDbObject.containsField(SUBMITTER)) {
					axisObject.setName(innerDbObject.get(SUBMITTER).toString());
				}
				if (innerDbObject.containsField(TICKET_ID)) {
					ticketId = innerDbObject.get(TICKET_ID).toString();
					if (result.containsKey(ticketId)) {
						List<ChartObject> items = result.get(ticketId);
						items.add(axisObject);
					} else
						result.put(ticketId, Lists.newArrayList(axisObject));
				}
			}
		}
	}

	public Map<String, List<ChartObject>> getResult() {
		// Sorting
		for (Map.Entry<String, List<ChartObject>> entry : result.entrySet()) {
			Collections.sort(entry.getValue(), (o1, o2) -> {
				DateTime one = new DateTime(Long.valueOf(o1.getStartTime()));
				DateTime next = new DateTime(Long.valueOf(o2.getStartTime()));
				return one.compareTo(next);
			});
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}