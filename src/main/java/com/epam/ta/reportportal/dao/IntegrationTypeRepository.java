package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.integration.IntegrationType;

import java.util.Optional;

/**
 * Repository for {@link com.epam.ta.reportportal.entity.integration.IntegrationType} entity
 *
 * @author Yauheni_Martynau
 */
public interface IntegrationTypeRepository extends ReportPortalRepository<IntegrationType, Long> {

	/**
	 * Searches for an integration
	 *
	 * @param name Integration name
	 * @return Optional of integration
	 */
	Optional<IntegrationType> findByName(String name);

	/**
	 * Searches for an integration by name and group
	 *
	 * @param name      Integration name
	 * @param groupType Integration group
	 * @return Optional of integration
	 */
	Optional<IntegrationType> findByNameAndGroupType(String name, String groupType);
}
