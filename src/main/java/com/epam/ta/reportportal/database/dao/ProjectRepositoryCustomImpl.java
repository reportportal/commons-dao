package com.epam.ta.reportportal.database.dao;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Pavel Bortnik
 */
@Repository
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

	private DSLContext dsl;

	@Autowired
	public void setDsl(DSLContext dsl) {
		this.dsl = dsl;
	}

}
