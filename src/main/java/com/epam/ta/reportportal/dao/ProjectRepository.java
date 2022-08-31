/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.project.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends ReportPortalRepository<Project, Long>, ProjectRepositoryCustom {

	Optional<Project> findByName(String name);

	@Query(value = "SELECT p.* FROM project p JOIN organization org on p.organization_id = org.id where org.slug = :slug and p.key = :key", nativeQuery = true)
	Optional<Project> findBySlugAndKey(@Param("slug") String slug, @Param("key") String key);

	boolean existsByName(String name);

	@Query(value = "SELECT p.* FROM project p JOIN project_user pu on p.id = pu.project_id JOIN users u on pu.user_id = u.id WHERE u.login = :login", nativeQuery = true)
	List<Project> findUserProjects(@Param("login") String login);

	@Query(value = "SELECT * FROM project p JOIN project_user pu on p.id = pu.project_id JOIN users u on pu.user_id = u.id WHERE u.login = :login AND p.project_type = :projectType", nativeQuery = true)
	List<Project> findUserProjects(@Param("login") String login, @Param("projectType") String projectType);

}
