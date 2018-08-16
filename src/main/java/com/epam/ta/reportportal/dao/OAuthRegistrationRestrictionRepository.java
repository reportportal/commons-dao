package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.oauth.OAuthRegistrationRestriction;

import java.util.List;

/**
 * @author Anton Machulski
 */
public interface OAuthRegistrationRestrictionRepository extends ReportPortalRepository<OAuthRegistrationRestriction, Long>, OAuthRegistrationRestrictionRepositoryCustom {
	List<OAuthRegistrationRestriction> findByRegistrationId(String id);
}
