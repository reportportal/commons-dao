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

import com.epam.ta.reportportal.database.entity.filter.UserFilter;
import com.epam.ta.reportportal.database.entity.sharing.Shareable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;

import static com.epam.ta.reportportal.database.dao.ShareableRepositoryUtils.createOwnedEntityQuery;
import static com.epam.ta.reportportal.database.dao.ShareableRepositoryUtils.createSharedEntityQuery;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class UserFilterRepositoryCustomImpl implements UserFilterRepositoryCustom {

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String ACL = "acl";
	public static final String OWNER = "acl.ownerUserId";
	public static final String ENTRIES = "acl.entries";
	public static final String TARGET = "filter.target";
	public static final String PROJECT = "projectName";
	public static final String CONDITIONS = "filter.filterConditions";
	public static final String OPTIONS = "selectionOptions";
	public static final String LINK = "isLink";

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public UserFilter findOneByName(String userName, String name, String projectName) {
		Query query = createOwnedEntityQuery(userName).addCriteria(where(NAME).is(name))
				.addCriteria(where(UserFilter.PROJECT_NAME).is(projectName));
		query.fields().include(ID);
		return mongoTemplate.findOne(query, UserFilter.class);
	}

	@Override
	public List<UserFilter> findFilters(String userName, String projectName, Sort sort, boolean isShared) {
		Query query;
		if (isShared) {
			query = createSharedEntityQuery(projectName);
		} else {
			query = createOwnedEntityQuery(userName);
		}
		query.addCriteria(where("isLink").is(false))
				.addCriteria(where(UserFilter.PROJECT_NAME).is(projectName)).with(sort);
		query.with(sort);
		query.fields().include(ID);
		query.fields().include(NAME);
		query.fields().include(TARGET);
		query.fields().include(CONDITIONS);
		query.fields().include(OPTIONS);
		query.fields().include(Shareable.OWNER);
		query.fields().include(ENTRIES);
		query.fields().include(PROJECT);
		return mongoTemplate.find(query, UserFilter.class);
	}

	@Override
	public UserFilter findOneLoadACL(String userName, String id, String projectName) {
		Query query = Query.query(where(OWNER).is(userName)).addCriteria(where(ID).is(id))
				.addCriteria(where(PROJECT).is(projectName));
		Query shared = Query.query(where(ID).is(id)).addCriteria(where(ENTRIES).size(1))
				.addCriteria(where(PROJECT).is(projectName));
		query.fields().include(TARGET).include(ACL).include(LINK);
		shared.fields().include(TARGET).include(ACL).include(LINK);
		UserFilter filter = mongoTemplate.findOne(query, UserFilter.class);
		return filter == null ? mongoTemplate.findOne(shared, UserFilter.class) : filter;
	}

	@Override
	public List<UserFilter> findAvailableFilters(String projectName, String[] ids, String userName) {
		//where ID from provided array AND it's shared on project
		Query q = Query.query(where(ID).in(Arrays.asList(ids))
				.andOperator(new Criteria()
						.orOperator(
								where(OWNER).is(userName),
								where(PROJECT).is(projectName),
								where(ENTRIES).elemMatch(where("projectId").is(projectName)))));
		return mongoTemplate.find(q, UserFilter.class);
	}
}