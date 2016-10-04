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
import com.epam.ta.reportportal.database.entity.project.EntryType;
import com.epam.ta.reportportal.database.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Set of custom DAO methods for {@link com.epam.ta.reportportal.database.entity.user.User} entity
 *
 * @author Andrei Varabyeu
 */
interface UserRepositoryCustom {

	/**
	 * Expires set of users which are logged in last time before provided date
	 *
	 * @param lastLogin
	 */
	void expireUsersLoggedOlderThan(Date lastLogin);

	/**
	 * Finds users by specified type and last synchronized before specified date
	 *
	 * @param type
	 * @param lastSynchronized
	 * @param pageable
	 * @return
	 */
	Page<User> findByTypeAndLastSynchronizedBefore(EntryType type, Date lastSynchronized, Pageable pageable);

	/**
	 * Saves provided binary data as user's photo
	 *
	 * @param login
	 * @param binaryData
	 * @return
	 */
	String saveUserPhoto(String login, BinaryData binaryData);

	/**
	 * Finds user's photo
	 *
	 * @param login
	 * @return
	 */
	BinaryData findUserPhoto(String login);

	/**
	 * Deletes user photo
	 *
	 * @param login
	 * @return
	 */
	void deleteUserPhoto(String login);

	/**
	 * Deletes user photo by provided photo ID
	 *
	 * @param photoId Photo ID
	 */
	void deleteUserPhotoById(String photoId);

	/**
	 * Finds photo IDs, id fields(login) of all users
	 *
	 * @return
	 */
	List<User> findAllPhotos();

	/**
	 * Fetch users by Email
	 *
	 * @param email Email
	 * @return Found user or NULL
	 */
	User findByEmail(String email);

	Page<User> findByLoginNameOrEmail(String term, Pageable pageable);

	void updateLastLoginDate(String user, Date date);
}