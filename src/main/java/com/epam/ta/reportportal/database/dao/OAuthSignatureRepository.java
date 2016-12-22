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

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.epam.ta.reportportal.database.entity.OAuthSignature;

/**
 * Repository interface for {@link OAuthSignature} instances. Provides basic CRUD operations due to
 * the extension of {@link CrudRepository}
 * 
 * @author Andrei_Ramanchuk
 */
public interface OAuthSignatureRepository extends ReportPortalRepository<OAuthSignature, String> {

	@Query(value = "{ 'externalSystemUrl' : ?0 , 'userRef' : ?1 }")
	OAuthSignature findByUrlAndUsername(String url, String username);
}