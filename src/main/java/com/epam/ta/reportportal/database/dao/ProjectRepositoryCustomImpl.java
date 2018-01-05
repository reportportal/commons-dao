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

import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.Project.UserConfig;
import com.epam.ta.reportportal.database.entity.ProjectRole;
import com.epam.ta.reportportal.database.entity.project.EntryType;
import com.google.common.collect.Streams;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.ta.reportportal.database.personal.PersonalProjectService.PERSONAL_PROJECT_POSTFIX;

/**
 * Project repository routines custom implementation
 *
 * @author Andrei_Ramanchuk
 */
class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

	private static final String PROJECT_ID = "_id";
	private static final String PROJECT_TYPE = "configuration.entryType";
	private static final String USER_LOGIN = "users.login";

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public boolean isAssignedToProject(String project, String login) {
		return mongoTemplate.exists(Query.query(Criteria.where("name").is(project.toLowerCase()))
				.addCriteria(userExists(login)), Project.class);
	}

	@Override
	public List<Project> findUserProjects(String login) {
		return mongoTemplate.find(Query.query(userExists(login)), Project.class);
	}

	@Override
	public List<String> findProjectUsers(String projectName, String value) {
		Query query = projectById(projectName);
		query.fields().include("users");
		Project p = mongoTemplate.findOne(query, Project.class);
		return p == null ? null : p.getUsers().stream().map(UserConfig::getLogin)
				.filter(userNameContains(value)).collect(Collectors.toList());
	}

	@Override
	public List<Project> findUserProjects(String login, String projectType) {
		Query query = Query.query(userExists(login)).addCriteria(Criteria.where(PROJECT_TYPE).is(projectType));
		return mongoTemplate.find(query, Project.class);
	}

	@Override
	public void removeUserFromProjects(String userId) {
		Query query = Query.query(Criteria.where(USER_LOGIN).is(userId));
		mongoTemplate.updateMulti(query, new Update()
				.pull("users", new BasicDBObject("login", userId)), Project.class);
	}

	@Override
	public void addUsers(String projectId, List<UserConfig> users) {
		Update update = new Update();
		users.forEach(user -> update.addToSet("users", user));
		mongoTemplate.updateFirst(projectById(projectId), update, Project.class);
	}

	@Override
	public List<String> findAllProjectNames() {
		Query query = new Query();
		query.fields().include("_id");
		return mongoTemplate.find(query, Project.class).stream().map(Project::getId).collect(Collectors.toList());
	}

	@Override
	public synchronized void clearExternalSystems(String projectId) {
		Project first = mongoTemplate.findOne(projectById(projectId), Project.class);
		first.getConfiguration().setExternalSystem(Collections.emptyList());
		mongoTemplate.save(first);
	}

	@Override
	public Map<String, ProjectRole> findProjectRoles(String login) {
		final Query q = Query.query(userExists(login));
		q.fields().include("users");
		return mongoTemplate.find(q, Project.class).stream().collect(Collectors.
				toMap(Project::getName, p -> p.getUsers().stream()
						.filter(it -> login.equals(it.getLogin())).findFirst().get()
						.getProjectRole()));
	}

	@Override
	public void addDemoDataPostfix(String project, String postfix) {
		mongoTemplate.updateFirst(projectById(project), new Update()
				.push("metadata.demoDataPostfix", postfix), Project.class);
	}

	@Override
	public Stream<Project> streamWithConfiguration(String configurationField) {
		String field = "configuration." + configurationField;
		Query query = Query.query(Criteria.where(field).ne("forever"));
		query.fields().include("id").include(field);
		return Streams.stream(mongoTemplate.stream(query, Project.class));
	}

	@Override
	public Optional<String> findPersonalProjectName(String user) {
		Query query = Query.query(userExists(user))
				.addCriteria(Criteria.where(PROJECT_TYPE).is(EntryType.PERSONAL))
				.addCriteria(Criteria.where(PROJECT_ID).regex("^" + user + PERSONAL_PROJECT_POSTFIX));
		query.fields().include("name");
        return Optional.ofNullable(mongoTemplate.findOne(query, Project.class)).map(Project::getName);
	}

	private Criteria userExists(String login) {
		return Criteria.where(USER_LOGIN).is(login);
	}

	/**
	 * Users with usernames contains specified value
	 *
	 * @param value Username to check
	 * @return Predicate
	 */
	private Predicate<String> userNameContains(final String value) {
		return input -> input.contains(value);
	}

	private Query projectById(String id) {
		return Query.query(Criteria.where(PROJECT_ID).is(id));
	}
}
