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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * AuthConfig repository custom
 *
 * @author Andrei Varabyeu
 */
@Repository
public class AuthConfigRepositoryCustomImpl implements AuthConfigRepositoryCustom {

	//	private final DSLContext dslContext;

	private final EntityManager entityManager;

	public AuthConfigRepositoryCustomImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
		//		createDefaultProfileIfAbsent();
	}

	@Transactional
	@Override
	public void deleteSettings(Integration integration) {
		entityManager.remove(integration);
	}


	@Transactional
	@Override
	public void updatePartially(AuthConfig authConfig) {
		entityManager.merge(authConfig);
	}

	@Override
	public void updateLdap(LdapConfig ldapConfig) {
		entityManager.merge(ldapConfig);

	}

	@Transactional
	@Override
	public void updateActiveDirectory(ActiveDirectoryConfig adConfig) {
		entityManager.merge(adConfig);
	}

	@Override
	public Optional<LdapConfig> findLdap(boolean enabled) {
		TypedQuery<AuthConfig> authConfigTypedQuery = entityManager.createQuery(
				"SELECT a FROM AuthConfig a JOIN a.ldap l JOIN Integration i ON l.id = i.id WHERE a.id = :id AND i.enabled = :enabled",
				AuthConfig.class
		);

		return retrieveAuthConfig(authConfigTypedQuery, enabled).flatMap(cfg -> ofNullable(cfg.getLdap()));
	}

	@Override
	public Optional<ActiveDirectoryConfig> findActiveDirectory(boolean enabled) {
		TypedQuery<AuthConfig> authConfigTypedQuery = entityManager.createQuery(
				"SELECT a FROM AuthConfig a JOIN a.activeDirectory ad JOIN Integration i ON ad.id = i.id WHERE a.id = :id AND i.enabled = :enabled",
				AuthConfig.class
		);

		return retrieveAuthConfig(authConfigTypedQuery, enabled).flatMap(cfg -> ofNullable(cfg.getActiveDirectory()));
	}

	private Optional<AuthConfig> retrieveAuthConfig(TypedQuery<AuthConfig> authConfigTypedQuery, boolean enabled) {
		authConfigTypedQuery.setParameter("id", DEFAULT_PROFILE);
		authConfigTypedQuery.setParameter("enabled", enabled);

		return authConfigTypedQuery.getResultList().stream().findFirst();
	}

	//    private Update updateExisting(Object object) {
	//        try {
	//            Update update = new Update();
	//            PropertyUtils.describe(object).entrySet().stream().filter(e -> null != e.getValue())
	//                    .forEach(it -> update.set(it.getKey(), it.getValue()));
	//            return update;
	//        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
	//            throw new ReportPortalException("Error during auth config update", e);
	//        }
	//    }

}
