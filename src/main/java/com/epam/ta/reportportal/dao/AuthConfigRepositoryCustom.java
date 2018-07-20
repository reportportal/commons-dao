/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-authorization
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
package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.ldap.ActiveDirectoryConfig;
import com.epam.ta.reportportal.entity.ldap.AuthConfig;
import com.epam.ta.reportportal.entity.ldap.LdapConfig;

import java.util.Optional;

/**
 * AuthConfig repository
 *
 * @author Andrei Varabyeu
 */
public interface AuthConfigRepositoryCustom {

	String DEFAULT_PROFILE = "default";

	void deleteSettings(Integration integration);

	void updatePartially(AuthConfig authConfig);

	void updateLdap(LdapConfig ldapConfig);

	void updateActiveDirectory(ActiveDirectoryConfig adConfig);

	Optional<LdapConfig> findLdap(boolean enabled);

	Optional<ActiveDirectoryConfig> findActiveDirectory(boolean enabled);
}
