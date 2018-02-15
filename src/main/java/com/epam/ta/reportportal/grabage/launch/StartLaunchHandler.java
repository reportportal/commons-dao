/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
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

package com.epam.ta.reportportal.grabage.launch;

import com.epam.ta.reportportal.database.dao.LaunchRepository;
import com.epam.ta.reportportal.database.dao.LaunchTagRepository;
import com.epam.ta.reportportal.database.dao.ProjectRepository;
import com.epam.ta.reportportal.database.entity.enums.ProjectRoleEnum;
import com.epam.ta.reportportal.database.entity.enums.StatusEnum;
import com.epam.ta.reportportal.database.entity.launch.Launch;
import com.epam.ta.reportportal.database.entity.launch.LaunchTag;
import com.epam.ta.reportportal.database.entity.project.ProjectUser;
import com.epam.ta.reportportal.grabage.LaunchBuilder;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Default implementation of {@link IStartLaunchHandler}
 *
 * @author Andrei Varabyeu
 */
@Service
class StartLaunchHandler implements IStartLaunchHandler {

	private final LaunchRepository launchRepository;
	private final LaunchTagRepository tagRepository;
	private final ProjectRepository projectRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Autowired
	public StartLaunchHandler(LaunchRepository launchRepository, LaunchTagRepository tagRepository, ProjectRepository projectRepository,
			ApplicationEventPublisher eventPublisher) {
		this.launchRepository = launchRepository;
		this.tagRepository = tagRepository;
		this.projectRepository = projectRepository;
		this.eventPublisher = eventPublisher;
	}

	/*
		 * (non-Javadoc)
		 *
		 * @see
		 * com.epam.ta.reportportal.ws.IStartLaunchHandler#startLaunch(java.lang
		 * .String, com.epam.ta.reportportal.ws.model.StartLaunchRQ)
		 */
	@Override
	public StartLaunchRS startLaunch(String username, String projectName, StartLaunchRQ startLaunchRQ) {

		ProjectUser projectUser = projectRepository.selectProjectUser(projectName, username);
		if (startLaunchRQ.getMode() == Mode.DEBUG) {
			if (projectUser.getProjectRole() == ProjectRoleEnum.CUSTOMER) {
				startLaunchRQ.setMode(Mode.DEFAULT);
			}
		}

		// userName and projectName validations here is redundant, user name and
		// projectName have already validated by spring security in controller
		Launch launch = new LaunchBuilder().addStartRQ(startLaunchRQ)
				.addProject(projectUser.getProject().getId())
				.addUser(projectUser.getUser().getId())
				.get();

		Optional<Launch> lastLaunch = launchRepository.getLastLaunch(launch.getName(), projectName);

		int nextLaunchNumber = lastLaunch.map(Launch::getNumber).orElse(0) + 1;
		launch.setNumber(nextLaunchNumber);
		launch.setStatus(StatusEnum.IN_PROGRESS);

		//launch.setApproximateDuration(calculateApproximateDuration(projectName, startLaunchRQ.getName(), 5));

		Launch save = launchRepository.save(launch);
		List<LaunchTag> launchTags = startLaunchRQ.getTags().stream().map(it -> {
			LaunchTag launchTag = new LaunchTag();
			launchTag.setLaunchId(save.getId());
			launchTag.setValue(it);
			return launchTag;
		}).collect(toList());
		tagRepository.saveAll(launchTags);
		//eventPublisher.publishEvent(new LaunchStartedEvent(launch));
		return new StartLaunchRS(launch.getId().toString(), launch.getNumber().longValue());
	}

	private double calculateApproximateDuration(String projectName, String launchName, int limit) {
		//		Set<FilterCondition> conditions = ImmutableSet.<FilterCondition>builder().add(new FilterCondition(EQUALS, false, launchName, NAME))
		//				.add(new FilterCondition(EQUALS, false, projectName, PROJECT))
		//				.add(new FilterCondition(IN, true, STOPPED.name() + "," + INTERRUPTED.name() + "," + IN_PROGRESS.name(), STATUS))
		//				.add(new FilterCondition(EQUALS, false, DEFAULT.name(), MODE_CRITERIA))
		//				.build();
		//		Filter filter = new Filter(Launch.class, conditions);
		//		Sort sort = new Sort(new Sort.Order(DESC, "start_time"));
		//		List<Launch> launches = launchRepository.findByFilterWithSortingAndLimit(filter, sort, limit);
		//		return launches.stream().mapToLong(it -> it.getEndTime().getTime() - it.getStartTime().getTime()).average().orElse(0);
		return 0;
	}
}