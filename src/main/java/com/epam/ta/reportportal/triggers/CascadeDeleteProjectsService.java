package com.epam.ta.reportportal.triggers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.ta.reportportal.commons.Constants;
import com.epam.ta.reportportal.database.dao.LaunchRepository;
import com.epam.ta.reportportal.database.dao.ProjectRepository;
import com.epam.ta.reportportal.database.dao.ShareableRepository;
import com.epam.ta.reportportal.database.dao.UserRepository;
import com.epam.ta.reportportal.database.entity.Dashboard;
import com.epam.ta.reportportal.database.entity.filter.UserFilter;
import com.epam.ta.reportportal.database.entity.sharing.Shareable;
import com.epam.ta.reportportal.database.entity.user.User;
import com.epam.ta.reportportal.database.entity.widget.Widget;
import com.epam.ta.reportportal.database.support.RepositoryProvider;

@Service
public class CascadeDeleteProjectsService {

	private LaunchRepository launchRepository;
	private ProjectRepository projectRepository;
	private CascadeDeleteLaunchesService cascadeDeleteLaunchesService;
	private UserRepository userRepository;
	private RepositoryProvider repositoryProvider;

	@Autowired
	public CascadeDeleteProjectsService(CascadeDeleteLaunchesService cascadeDeleteLaunchesService, LaunchRepository launchRepository,
			ProjectRepository projectRepository, UserRepository userRepository, RepositoryProvider repositoryProvider) {
		this.cascadeDeleteLaunchesService = cascadeDeleteLaunchesService;
		this.launchRepository = launchRepository;
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
		this.repositoryProvider = repositoryProvider;
	}

	public void delete(List<String> ids) {
		ids.forEach(it -> {
			updateDefaultProject(it);
			removeProjectShareable(it);
		});

		List<String> launchesToDelete = launchRepository.findLaunchIdsByProjectIds(ids);
		projectRepository.delete(ids);
		cascadeDeleteLaunchesService.delete(launchesToDelete);
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
			user.setDefaultProject(Constants.DEFAULT_PROJECT.toString());
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
