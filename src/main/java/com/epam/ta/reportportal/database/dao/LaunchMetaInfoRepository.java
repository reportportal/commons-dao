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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Map;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

/**
 * Launch Counter. Calculates number of launch with specified name
 *
 * @author Andrei_Varabyeu
 * @author Andrei_Ramanchuk
 */
public interface LaunchMetaInfoRepository {

	String LAUNCH_META_INFO = "launchMetaInfo";

	/**
	 * Calculates number of launch with specified name
	 *
	 * @param launchName  Launch name
	 * @param projectName Project name
	 * @return Number of launch
	 */
	long getLaunchNumber(String launchName, String projectName);

	/**
	 * Implementation of launch counter
	 *
	 * @author Andrei_Varabyeu
	 * @author Andrei_Ramanchuk
	 */
	class LaunchMetaInfoRepositoryImpl implements LaunchMetaInfoRepository {

		@Autowired
		private MongoOperations mongoOperations;

		@Override
		public long getLaunchNumber(String launchName, String projectName) {
			/*
			 * Project name should be validated on controller level
			 */
			Query query = Query.query(Criteria.where("_id").is(launchName));
			LaunchMetaInfo counter = mongoOperations
					.findAndModify(query, new Update().inc("projects." + projectName, 1), options().returnNew(true).upsert(true),
							LaunchMetaInfo.class);

			return counter.getProjects().get(projectName);
		}

	}

	/**
	 * Launch Meta Information entity
	 *
	 * @author Andrei Varabyeu
	 * @author Andrei_Ramanchuk
	 */
	@Document
	class LaunchMetaInfo {

		@Id
		private String id;
		private Map<String, Integer> projects;

		public String getId() {
			return id;
		}

		public Map<String, Integer> getProjects() {
			return projects;
		}
	}
}