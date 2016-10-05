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
package com.epam.ta.reportportal.triggers;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.epam.ta.reportportal.database.personal.PersonalProjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

import com.epam.ta.reportportal.commons.ClasspathUtils;
import com.epam.ta.reportportal.database.DataStorage;
import com.epam.ta.reportportal.database.dao.*;
import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.sharing.Shareable;
import com.epam.ta.reportportal.database.entity.user.User;
import com.epam.ta.reportportal.database.support.RepositoryProvider;

import static java.util.Collections.singletonList;

/**
 * Deletes user-related data before delete of user entity
 *
 * @author Andrei Varabyeu
 */
@Component
public class CascadeDeleteUserTrigger extends AbstractMongoEventListener<User> {

	private final DataStorage dataStorage;
	private final RepositoryProvider repositoryProvider;
	private final ProjectRepository projectRepository;
	private final FavoriteResourceRepository favoriteResourceRepository;
	private final UserPreferenceRepository userPreferenceRepository;
	private final UserRepository userRepository;
	private final CascadeDeleteProjectsService cascadeDeleteProjectsService;

	@Autowired
	public CascadeDeleteUserTrigger(DataStorage dataStorage, RepositoryProvider repositoryProvider, ProjectRepository projectRepository,
			FavoriteResourceRepository favoriteResourceRepository, UserPreferenceRepository userPreferenceRepository,
			UserRepository userRepository, CascadeDeleteProjectsService cascadeDeleteProjectsService) {
		this.dataStorage = dataStorage;
		this.repositoryProvider = repositoryProvider;
		this.projectRepository = projectRepository;
		this.favoriteResourceRepository = favoriteResourceRepository;
		this.userPreferenceRepository = userPreferenceRepository;
		this.userRepository = userRepository;
		this.cascadeDeleteProjectsService = cascadeDeleteProjectsService;
	}

	@Override
	public void onBeforeDelete(BeforeDeleteEvent<User> event) {
		// don't use here cacheable methods
		final Object loginObject = event.getDBObject().get("login");
		if (loginObject == null) {
			return;
		}
		String login = loginObject.toString();
		User user = userRepository.findPhotoIdByLogin(login);
		removeNonsharedItems(login);
		favoriteResourceRepository.removeFavoriteResources(login);
		userPreferenceRepository.deleteByUserName(login);

		if (!StringUtils.isEmpty(user.getPhotoId())) {
			dataStorage.deleteData(user.getPhotoId());
		}
		removePersonalProject(user.getId());
		clearProject(login);

	}

	/**
	 * Remove user references from projects for which user was assigned
	 *
	 * @param id
	 */
	private void clearProject(String id) {
		List<Project> projects = projectRepository.findUserProjects(id);
		for (Project project : projects) {
			project.getUsers().remove(id);
		}
		projectRepository.save(projects);
	}

	private void removePersonalProject(String user) {
		cascadeDeleteProjectsService.delete(singletonList(PersonalProjectUtils.personalProjectName(user)));
	}

	/**
	 * Remove all non shared objects for which current user is owner.
	 *
	 * @param ownerName
	 */
	private void removeNonsharedItems(String ownerName) {
		Set<Class<? extends Shareable>> shareableTypes = ClasspathUtils.findSubclassesOf(Shareable.class);
		for (Class<? extends Shareable> type : shareableTypes) {
			@SuppressWarnings("unchecked")
			ShareableRepository<Shareable, String> repository = (ShareableRepository<Shareable, String>) repositoryProvider
					.<Shareable> getRepositoryFor(type);
			List<? extends Shareable> entitiesForRemoving = repository.findNonSharedEntities(ownerName);
			repository.delete(entitiesForRemoving);
		}
	}
}