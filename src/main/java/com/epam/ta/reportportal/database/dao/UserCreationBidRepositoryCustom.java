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
import com.epam.ta.reportportal.database.entity.user.UserCreationBid;

/**
 * Set of custom DAO methods for
 * {@link com.epam.ta.reportportal.database.entity.user.UserCreationBid} entity
 * 
 * @author Andrei_Ramanchuk
 */
public interface UserCreationBidRepositoryCustom {

	/**
	 * Expire(remove) bids older than specified date
	 * 
	 * @param lastLogin
	 */
	void expireBidsOlderThan(Date lastLogin);

	/**
	 * Find already requested user creation bid by email
	 * 
	 * @param email
	 * @return
	 */
	UserCreationBid findByEmail(String email);
}