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

package com.epam.ta.reportportal.database.dao;

import com.epam.ta.reportportal.database.entity.UserPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author Dzmitry_Kavalets
 */
public class UserPreferenceRepositoryCustomImpl implements UserPreferenceRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void deleteByUserName(String userName) {
		Query query = Query.query(where("userRef").is(userName));
		mongoTemplate.remove(query, UserPreference.class);
	}

	public void deleteByUsernameAndProject(String username, String project) {
		Query query = Query.query(where("userRef").is(username)).addCriteria(where("projectRef").is(project));
		mongoTemplate.remove(query, UserPreference.class);
	}

	@Override
	public void deleteUnsharedFilters(String username, String project, String filterId) {
		Query query = Query.query(where("userRef").ne(username))
				.addCriteria(where("projectRef").is(project))
				.addCriteria(where("launchTabs.filters").is(filterId));

		Update update = new Update().pull("launchTabs.filters", filterId);
		mongoTemplate.updateMulti(query, update, UserPreference.class);

	}
}