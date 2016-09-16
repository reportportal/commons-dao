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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.epam.ta.reportportal.database.entity.favorite.FavoriteResource;

/**
 * Favorite resource custom implementation
 * 
 * @author Dzmitry_Kavalets
 */
public class FavoriteResourceRepositoryCustomImpl implements FavoriteResourceRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<FavoriteResource> findFavoriteResourcesByUser(String userName) {
		Query query = Query.query(Criteria.where(FavoriteResource.USERNAME_CRITERIA).is(userName));
		return mongoTemplate.find(query, FavoriteResource.class);
	}

	@Override
	public void removeFavoriteResources(String userName) {
		Query query = Query.query(Criteria.where(FavoriteResource.USERNAME_CRITERIA).is(userName));
		mongoTemplate.remove(query, FavoriteResource.class);
	}

	@Override
	public void removeFavoriteResources(String userName, String projectName) {
		Query query = Query.query(Criteria.where(FavoriteResource.USERNAME_CRITERIA).is(userName)
				.andOperator(Criteria.where(FavoriteResource.PROJECT_CRITERIA).is(projectName)));
		mongoTemplate.remove(query, FavoriteResource.class);
	}

}