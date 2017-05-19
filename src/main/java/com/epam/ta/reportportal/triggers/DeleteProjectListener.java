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
package com.epam.ta.reportportal.triggers;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.QueryMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

import com.epam.ta.reportportal.database.dao.*;
import com.epam.ta.reportportal.database.entity.Dashboard;
import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.filter.UserFilter;
import com.epam.ta.reportportal.database.entity.sharing.Shareable;
import com.epam.ta.reportportal.database.entity.user.User;
import com.epam.ta.reportportal.database.entity.widget.Widget;
import com.epam.ta.reportportal.database.support.RepositoryProvider;
import com.mongodb.DBObject;

/**
 * @author Dzmitry Kavalets
 */
@Component
public class DeleteProjectListener extends AbstractMongoEventListener<Project> {

	private final MongoTemplate mongoTemplate;
	private final UserRepository userRepository;
	private final ProjectRepository projectRepository;
	private final LaunchRepository launchRepository;
	private final ActivityRepository activityRepository;
	private final ExternalSystemRepository externalSystemRepository;
	private final RepositoryProvider repositoryProvider;
	private final MongoMappingContext context;
	private final QueryMapper queryMapper;

	@Autowired
	public DeleteProjectListener(MongoTemplate mongoTemplate, UserRepository userRepository, ProjectRepository projectRepository,
			LaunchRepository launchRepository,
			RepositoryProvider repositoryProvider, MongoMappingContext context, ExternalSystemRepository externalSystemRepository,
			ActivityRepository activityRepository) {
		this.mongoTemplate = mongoTemplate;
		this.userRepository = userRepository;
		this.projectRepository = projectRepository;
		this.launchRepository = launchRepository;
		this.repositoryProvider = repositoryProvider;
		this.activityRepository = activityRepository;
		this.externalSystemRepository = externalSystemRepository;
		this.context = context;
		this.queryMapper = new QueryMapper(mongoTemplate.getConverter());
	}

	@Override
	public void onBeforeDelete(BeforeDeleteEvent<Project> event) {
		for (DBObject dbObject : mongoTemplate.getCollection(event.getCollectionName())
				.find(queryMapper.getMappedObject(event.getDBObject(), context.getPersistentEntity(Project.class)))) {
			final String id = dbObject.get("_id").toString();
			updateDefaultProject(id);
			removeProjectShareable(id);
			externalSystemRepository.deleteByProjectRef(id);
			activityRepository.deleteByProjectRef(id);
			launchRepository.deleteByProjectRef(id);
		}
	}

	private void updateDefaultProject(String projectName) {
		if (null == projectName) {
			return;
		}
		List<User> usersForUpdate = userRepository.findByDefaultProject(projectName);
		if (null == usersForUpdate) {
			return;
		}
		for (User user : usersForUpdate) {
			user.setDefaultProject(projectRepository.findPersonalProjectName(user.getId()));
		}
		userRepository.save(usersForUpdate);
	}

	private void removeProjectShareable(String projectName) {
		Set<Class<? extends Shareable>> shareableTypes = new HashSet<>();
		shareableTypes.add(Dashboard.class);
		shareableTypes.add(UserFilter.class);
		shareableTypes.add(Widget.class);
		for (Class<? extends Shareable> type : shareableTypes) {
			@SuppressWarnings("unchecked")
			ShareableRepository<Shareable, String> repository = (ShareableRepository<Shareable, String>) repositoryProvider
					.<Shareable> getRepositoryFor(type);
			if (null == projectName) {
				repository.deleteAll();
			} else {
				List<? extends Shareable> entitiesForRemoving = repository.findByProject(projectName);
				repository.delete(entitiesForRemoving);
			}
		}
	}
}
