package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.oauth.OAuthRegistrationRestriction;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.epam.ta.reportportal.jooq.Tables.OAUTH_REGISTRATION_RESTRICTION;

/**
 * @author Anton Machulski
 */
@Repository
public class OAuthRegistrationRestrictionRepositoryCustomImpl implements OAuthRegistrationRestrictionRepositoryCustom {
	private DSLContext dsl;

	@Autowired
	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public List<OAuthRegistrationRestriction> findByRegistrationId(String registrationId) {
		return dsl.select()
				.from(OAUTH_REGISTRATION_RESTRICTION)
				.where(OAUTH_REGISTRATION_RESTRICTION.OAUTH_REGISTRATION_FK.eq(registrationId))
				.fetch().into(OAuthRegistrationRestriction.class);
	}
}
