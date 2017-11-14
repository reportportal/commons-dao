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

import com.epam.ta.reportportal.database.entity.sharing.AclPermissions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Provide utilities for working with sharable repositories
 *
 * @author Aliaksei_Makayed
 */
class ShareableRepositoryUtils {

	private ShareableRepositoryUtils() {
	}

	/**
	 * Create {@link Query} for loading shared to specified project and not owned by specified user
	 * entities.
	 *
	 * @param projectName Project name
	 * @return Query
	 */
	public static Query createSharedEntityQuery(String projectName) {
		return Query.query(Criteria.where("acl.entries.projectId").is(projectName))
				.addCriteria(Criteria.where("acl.entries.permissions").is(AclPermissions.READ.name()));
	}

	/**
	 * Create {@link Query} for loading entities owned by specified user.
	 *
	 * @param owner Widget owner
	 * @return Query
	 */
	public static Query createOwnedEntityQuery(String owner) {
		return Query.query(Criteria.where("acl.ownerUserId").is(owner));
	}

	/**
	 * Create {@link org.springframework.data.mongodb.core.query.Query} for loading non shared
	 * entities owned by specified user
	 *
	 * @param owner Widget owner
	 * @return Query
	 */
	public static Query createUnsharedEntityQuery(String owner) {
		return Query.query(Criteria.where("acl.ownerUserId").is(owner)).addCriteria(Criteria.where("acl.entries").size(0));
	}

}