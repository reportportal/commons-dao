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

import com.epam.ta.reportportal.database.BinaryData;
import com.epam.ta.reportportal.database.entity.user.User;
import com.epam.ta.reportportal.database.entity.user.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

/**
 * Set of custom DAO methods for {@link com.epam.ta.reportportal.database.entity.user.User} entity
 *
 * @author Andrei Varabyeu
 */
interface UserRepositoryCustom {

	/**
	 * Expires set of users which are logged in last time before provided date
	 *
	 * @param lastLogin Last login date
	 */
	void expireUsersLoggedOlderThan(Date lastLogin);

	/**
	 * Finds users by specified type and last synchronized before specified date
	 *
	 * @param type             Type of User
	 * @param lastSynchronized Last synchronization date
	 * @param pageable         Pageable
	 * @return List of found Users
	 */
	Page<User> findByTypeAndLastSynchronizedBefore(UserType type, Date lastSynchronized, Pageable pageable);

	/**
	 * Saves provided binary data as user's photo
	 *
	 * @param login      User Login
	 * @param binaryData Replacement
	 * @return ID of new photo
	 */
	String replaceUserPhoto(String login, BinaryData binaryData);

	/**
	 * Saves provided binary data as user's photo
	 *
	 * @param user      User
	 * @param binaryData Replacement
	 * @return ID of new photo
	 */
	String replaceUserPhoto(User user, BinaryData binaryData);

	/**
	 * Finds user's photo
	 *
	 * @param login Username
	 * @return User's Photo
	 */
	BinaryData findUserPhoto(String login);

	/**
	 * Deletes user photo by provided photo ID
	 *
	 * @param photoId Photo ID
	 */
	void deleteUserPhotoById(String photoId);

	/**
	 * Fetch users by Email
	 *
	 * @param email Email
	 * @return Found user or NULL
	 */
	User findByEmail(String email);

	/**
	 * Finds users by login or email
	 *
	 * @param term     login OR email
	 * @param pageable Pageable
	 * @return Found users
	 */
	Page<User> findByLoginNameOrEmail(String term, Pageable pageable);

	/**
	 * Updates last login date field of user entity
	 *
	 * @param user User
	 * @param date Date
	 */
	void updateLastLoginDate(String user, Date date);
}