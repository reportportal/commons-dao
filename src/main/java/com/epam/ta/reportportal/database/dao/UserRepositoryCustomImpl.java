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

import com.epam.ta.reportportal.commons.Constants;
import com.epam.ta.reportportal.database.BinaryData;
import com.epam.ta.reportportal.database.DataStorage;
import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.UserRoleDetails;
import com.epam.ta.reportportal.database.entity.user.User;
import com.epam.ta.reportportal.database.entity.user.UserType;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.mongodb.BasicDBObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static com.epam.ta.reportportal.commons.EntityUtils.normalizeId;
import static com.epam.ta.reportportal.config.CacheConfiguration.USERS_CACHE;
import static com.epam.ta.reportportal.database.dao.UserUtils.photoFilename;
import static com.epam.ta.reportportal.database.entity.user.User.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Default Implementation of {@link UserRepositoryCustom}
 *
 * @author Andrei Varabyeu
 */
class UserRepositoryCustomImpl implements UserRepositoryCustom {

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private DataStorage dataStorage;

	@Override
	public void expireUsersLoggedOlderThan(Date lastLogin) {
		mongoOperations.updateMulti(query(where(MetaInfo.LAST_LOGIN_PATH).lt(lastLogin)), update(IS_EXPIRED, true), User.class);
	}

	@Override
	public Page<User> findByTypeAndLastSynchronizedBefore(UserType type, Date lastSynchronized, Pageable pageable) {
		Query q = query(where("type").is(type).and(MetaInfo.SYNCHRONIZATION_DATE).lt(lastSynchronized)).with(pageable);
		long count = mongoOperations.count(q, User.class);
		return new PageImpl<>(mongoOperations.find(q, User.class), pageable, count);

	}

	@Override
	public String uploadUserPhoto(String login, BinaryData binaryData) {
		String photoFilename = photoFilename(login);
		/* make sure no junks here */
		if (!login.equalsIgnoreCase(Constants.NONAME_USER.toString())) {
			dataStorage.deleteByFilename(photoFilename);
		}

		return dataStorage.saveData(binaryData, photoFilename);
	}

	@CacheEvict(key = "#p0", value = USERS_CACHE, beforeInvocation = true)
	@Override
	public String replaceUserPhoto(String login, BinaryData binaryData) {
		Query q = query(where(User.LOGIN).is(login));
		q.fields().include(User.LOGIN).include("_id").include(User.PHOTO_ID);

		User user = mongoOperations.findOne(q, User.class);
		if (null == user) {
			throw new ReportPortalException("User with name '" + login + "' not found");
		}
		return replaceUserPhoto(user, binaryData);
	}

	@Override
	public String replaceUserPhoto(User user, BinaryData binaryData) {
		/*
		 * Clean out-dated user photo (if exists) and create newest one
		 */
		if (!StringUtils.isEmpty(user.getPhotoId())) {
			/* make sure this is nothing associated with user */
			dataStorage.deleteData(user.getPhotoId());
		}

		String dataId = uploadUserPhoto(user.getLogin(), binaryData);
		user.setPhotoId(dataId);
		mongoOperations.updateFirst(query(where(User.LOGIN).is(user.getId())), update(User.PHOTO_ID, dataId), User.class);
		return dataId;
	}

	@Override
	public void deleteUserPhotoById(String photoId) {
		if (!StringUtils.isEmpty(photoId)) {
			dataStorage.deleteData(photoId);
		}
	}

	@Override
	public BinaryData findUserPhoto(String login) {
		BinaryData photo = null;
		Query q = query(where(User.LOGIN).is(login));
		q.fields().include(User.PHOTO_ID);
		User user = mongoOperations.findOne(q, User.class);
		if (user != null && user.getPhotoId() != null)
			photo = dataStorage.fetchData(user.getPhotoId());
		if (null == photo) {
			// Get default photo avatar (batman)
			photo = dataStorage.findByFilename(photoFilename(Constants.NONAME_USER.toString())).get(0);
		}
		return photo;
	}

	@Override
	public User findByEmail(String email) {
		final Query query = query(where(User.EMAIL).is(normalizeId(email)));
		return mongoOperations.findOne(query, User.class);
	}

	@Override
	public Page<User> searchForUser(String term, Pageable pageable) {
		Query query = buildSearchUserQuery(term, pageable);
		List<User> users = mongoOperations.find(query, User.class);
		return new PageImpl<>(users, pageable, mongoOperations.count(query, User.class));
	}

	@Override
	public Page<User> searchForUserLogin(String term, Pageable pageable) {
		Query query = buildSearchUserQuery(term, pageable);
		query.fields().include(LOGIN);
		query.fields().include(FULLNAME_DB_FIELD);
		query.fields().include(TYPE);
		List<User> users = mongoOperations.find(query, User.class);
		return new PageImpl<>(users, pageable, mongoOperations.count(query, User.class));
	}

	@Override
	public void updateLastLoginDate(String user, Date date) {
		mongoOperations.updateFirst(query(where("_id").is(user)), update("metaInfo.lastLogin", date), User.class);

	}

	public UserRoleDetails aggregateUserProjects(String login) {
		//		db.project.aggregate( [
		//
		//				{ $unwind : "$users"},
		//		{ $match: { "users.login": "default" } },
		//		{ $group : { _id : "$users.login", projects: { $push: {"id" : "$_id", "proposedRole" : "$users.proposedRole", "projectRole" : "$users.projectRole" }} }},
		//		{ $lookup:
		//		{
		//			from: "user",
		//					localField: "_id",
		//				foreignField: "_id",
		//				as: "user"
		//		}
		//		},
		//		{ $unwind : "$user"}
		//
		//		] )
		//@formatter:off
		Aggregation a = newAggregation(
							unwind("$users"),
							match(where("users.login").is(login)),
							group("users.login")
									.push(new BasicDBObject
                   						("project", "$_id")
										.append("projectRole", "$users.projectRole"))
										.as("projects"),
							lookup("user", "_id", "_id", "user")
						);
		//@formatter:on
		return mongoOperations.aggregate(a, mongoOperations.getCollectionName(Project.class), UserRoleDetails.class).getUniqueMappedResult();
	}

	private Query buildSearchUserQuery(String term, Pageable pageable) {
		final String regex = "(?i).*" + Pattern.quote(term.toLowerCase()) + ".*";
		Criteria login = where(LOGIN).regex(regex);
		Criteria fullName = where(FULLNAME_DB_FIELD).regex(regex);
		Criteria email = where(User.EMAIL).regex(regex);
		Criteria criteria = new Criteria().orOperator(email, login, fullName);
		return query(criteria).with(pageable);
	}

}
