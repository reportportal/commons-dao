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

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.epam.ta.reportportal.database.entity.Modifiable;
import com.epam.ta.reportportal.database.entity.user.UserCreationBid;

/**
 * Default implementation of {@link UserCreationBidRepositoryCustom}
 * 
 * @author Andrei_Ramanchuk
 */
public class UserCreationBidRepositoryCustomImpl implements UserCreationBidRepositoryCustom {

	private static final String EMAIL = "email";

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private MongoTemplate mongoTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.epam.ta.reportportal.database.dao.UserCreationBidCustom#
	 * expireBidsOlderThan(java.util.Date)
	 */
	@Override
	public void expireBidsOlderThan(Date lastLogin) {
		mongoOperations.remove(Query.query(Criteria.where(Modifiable.LAST_MODIFIED).lt(lastLogin)), UserCreationBid.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.epam.ta.reportportal.database.dao.UserCreationBidCustom#
	 * findByEmail(java.lang.String)
	 */
	@Override
	public UserCreationBid findByEmail(String email) {
		Query query = Query.query(Criteria.where(EMAIL).is(email));
		return mongoTemplate.findOne(query, UserCreationBid.class);
	}
}