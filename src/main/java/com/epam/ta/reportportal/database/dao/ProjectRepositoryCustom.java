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

import com.epam.ta.reportportal.config.CacheConfiguration;
import com.epam.ta.reportportal.database.entity.Project;
import com.epam.ta.reportportal.database.entity.Project.UserConfig;
import com.epam.ta.reportportal.database.entity.ProjectRole;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

/**
 * Some custom queries defined for project repository
 *
 * @author Andrei Varabyeu
 */
public interface ProjectRepositoryCustom {

    /**
     * Whether user assigned to project
     *
     * @param project
     * @param login
     * @return
     */
    @Cacheable(value = CacheConfiguration.ASSIGNED_USERS_CACHE, key = "#p0 + #p1")
    boolean isAssignedToProject(String project, String login);

    /**
     * Finds projects contain specified user
     *
     * @param login
     * @return
     */
    List<Project> findUserProjects(String login);

    /**
     * Finds projects contains specified user and has specified projectType
     *
     * @param login       User login
     * @param projectType projectType
     * @return
     */
    List<Project> findUserProjects(String login, String projectType);

    /**
     * Get project usernames which contains specified value (for auto-complete on UI)
     *
     * @param projectName
     * @param value
     * @return
     */
    List<String> findProjectUsers(String projectName, String value);

    /**
     * Remove user from all projects
     *
     * @param userId
     */
    void removeUserFromProjects(String userId);

    /**
     * Add users to project
     *
     * @param projectId
     * @param users
     */
    void addUsers(String projectId, Map<String, UserConfig> users);

    /**
     * Find all project names
     *
     * @return Project Names
     */
    List<String> findAllProjectNames();

    void clearExternalSystems(String projectId);

    /**
     * Returns map Project->ProjectRole
     *
     * @param login username
     * @return Project->ProjectRole map
     */
    Map<String, ProjectRole> findProjectRoles(String login);

}
