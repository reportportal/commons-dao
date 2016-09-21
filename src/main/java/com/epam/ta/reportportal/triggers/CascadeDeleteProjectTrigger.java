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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

import com.epam.ta.reportportal.commons.Constants;
import com.epam.ta.reportportal.database.dao.*;
import com.epam.ta.reportportal.database.entity.Dashboard;
import com.epam.ta.reportportal.database.entity.Launch;
import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.filter.UserFilter;
import com.epam.ta.reportportal.database.entity.sharing.Shareable;
import com.epam.ta.reportportal.database.entity.user.User;
import com.epam.ta.reportportal.database.entity.widget.Widget;
import com.epam.ta.reportportal.database.support.RepositoryProvider;

/**
 * Deletes all related to project stuff
 *
 * @author Andrei Varabyeu
 */
@Component
public class CascadeDeleteProjectTrigger extends AbstractMongoEventListener<Project> {

	private final LaunchRepository launchRepository;
	private final RepositoryProvider repositoryProvider;
	private final UserRepository userRepository;
	private final ProjectRepository projectRepository;
	private final TestItemRepository testItemRepository;

	@Autowired
	public CascadeDeleteProjectTrigger(LaunchRepository launchRepository, RepositoryProvider repositoryProvider,
			UserRepository userRepository, ProjectRepository projectRepository, TestItemRepository testItemRepository) {
		this.launchRepository = launchRepository;
		this.repositoryProvider = repositoryProvider;
		this.userRepository = userRepository;
		this.projectRepository = projectRepository;
		this.testItemRepository = testItemRepository;
	}

	@Override
	public void onBeforeDelete(BeforeDeleteEvent<Project> event) {
		Object name = event.getDBObject().get("name");
		if (name == null) {
			return;
		}
		Project project = projectRepository.findOne(name.toString());
		if (project != null) {
			List<Launch> launches = launchRepository.findLaunchIdsByProject(project);
			testItemRepository.delete(testItemRepository.findIdsByLaunch(launches));
			removeProjectShareable(project.getName());

			updateDefaultProject(project.getId());
		}
	}

	/**
	 * Update default project for users which have this project as default
	 */
	private void updateDefaultProject(String projectName) {
		if (null == projectName) {
			return;
		}
		List<User> usersForUpdate = userRepository.findByDefaultProject(projectName);
		if (null == usersForUpdate) {
			return;
		}
		for (User user : usersForUpdate) {
			user.setDefaultProject(Constants.DEFAULT_PROJECT.toString());
		}
		userRepository.save(usersForUpdate);
	}

	/**
	 * Remove all sharable objects for which current user is owner. If userName
	 * is null all sharable objects will be deleted
	 *
	 * @param projectName
	 *            Project name
	 */
	private void removeProjectShareable(String projectName) {
		// Set<Class<? extends Shareable>> shareableTypes =
		// ClasspathUtils.findSubclassesOf(Shareable.class);
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