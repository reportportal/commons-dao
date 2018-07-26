package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.ldap.ActiveDirectoryConfig;
import com.epam.ta.reportportal.entity.ldap.LdapConfig;

import java.util.Optional;

/**
 * @author Yauheni_Martynau
 */
public interface IntegrationRepositoryCustom extends FilterableRepository<Integration>{

	void deleteSettings(Integration integration);

	void updateLdap(LdapConfig ldapConfig);

	void updateActiveDirectory(ActiveDirectoryConfig adConfig);

	Optional<LdapConfig> findLdap(boolean enabled);

	Optional<ActiveDirectoryConfig> findActiveDirectory(boolean enabled);

}
