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

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.epam.ta.reportportal.database.entity.project.EntryType;
import com.epam.ta.reportportal.database.entity.user.User;
import com.epam.ta.reportportal.database.entity.user.UserRole;

/**
 * Repository interface for {@link User} instances. Provides basic CRUD
 * operations due to the extension of {@link CrudRepository}
 * 
 * @author Henadzi_Vrubleuski
 * @author Andrei_Ramanchuk
 * @author Andrei Varabyeu
 */
public interface UserRepository extends ReportPortalRepository<User, String>, UserRepositoryCustom {

	List<User> findByRole(UserRole role);

	List<User> findByEmailIn(Iterable<String> mails);

	Page<User> findByTypeAndIsExpired(EntryType type, boolean expired, Pageable pageable);

	List<User> findByLoginIn(Set<String> ids);

	List<User> findByDefaultProject(String defaultProject);

	@Query(fields = "{'photoId' : 1}")
	User findPhotoIdByLogin(String login);

}