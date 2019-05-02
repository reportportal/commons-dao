package com.epam.ta.reportportal.dao;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.epam.ta.reportportal.jooq.tables.JUserCreationBid.USER_CREATION_BID;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Repository
public class UserCreationBidRepositoryCustomImpl implements UserCreationBidRepositoryCustom {

	@Autowired
	private DSLContext dsl;

	@Override
	public int deleteAllByEmail(String email) {
		return dsl.deleteFrom(USER_CREATION_BID).where(USER_CREATION_BID.EMAIL.eq(email)).execute();
	}
}
