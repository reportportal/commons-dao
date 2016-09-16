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
package com.epam.ta.reportportal.database.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;

import com.epam.ta.reportportal.database.entity.widget.Widget;

public class WidgetRepositoryCustomImpl implements WidgetRepositoryCustom {

	private static final String CONTENT_FIELDS = "contentOptions.contentFields";

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addContentField(String widgetId, String field) {
		mongoTemplate.updateFirst(query(where("_id").is(widgetId)), new Update().push(CONTENT_FIELDS, field), Widget.class);
	}

	@Override
	public void removeContentField(String widgetId, String field) {
		mongoTemplate.updateFirst(query(where("_id").is(widgetId)), new Update().pull(CONTENT_FIELDS, field), Widget.class);
	}
}