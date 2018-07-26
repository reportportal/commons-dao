package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.oauth.OAuthRegistrationRestriction;


/**
 * @author Anton Machulski
 */
public interface OAuthRegistrationRestrictionRepository extends ReportPortalRepository<OAuthRegistrationRestriction, Long>, OAuthRegistrationRestrictionRepositoryCustom {

//	List<OAuthRegistrationRestriction> findAllByRegistration(OAuthRegistration oAuthRegistration);

}
