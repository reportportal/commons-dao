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

import com.epam.ta.reportportal.database.entity.favorite.FavoriteResource;

/**
 * 
 * @author Dzmitry_Kavalets
 */
public interface FavoriteResourceRepositoryCustom {

	/**
	 * Find favorite resources of specified user
	 * 
	 * @param userName
	 * @return
	 */
	List<FavoriteResource> findFavoriteResourcesByUser(String userName);

	/**
	 * Remove favorite resources of specified user
	 * 
	 * @param userName
	 */
	void removeFavoriteResources(String userName);

	/**
	 * Remove favorite resources by user and project
	 * 
	 * @param userName
	 * @param projectName
	 */
	void removeFavoriteResources(String userName, String projectName);

}