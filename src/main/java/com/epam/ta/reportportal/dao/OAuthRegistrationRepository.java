package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.oauth.OAuthRegistration;

public interface OAuthRegistrationRepository extends ReportPortalRepository<OAuthRegistration, String>, OAuthRegistrationRepositoryCustom {
}