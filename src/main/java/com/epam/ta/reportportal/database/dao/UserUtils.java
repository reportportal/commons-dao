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

/**
 * Some useful methods for working with Users
 * 
 * @author Andrei Varabyeu
 * 
 */
public class UserUtils {

	private UserUtils() {

	}

	public static final String PHOTO_FILENAME_PREFIX = "photo_";

	/**
	 * Generates photo filename for specified user
	 * 
	 * @param username
	 * @return
	 */
	public static String photoFilename(String username) {
		return PHOTO_FILENAME_PREFIX.concat(username.toLowerCase());
	}
}