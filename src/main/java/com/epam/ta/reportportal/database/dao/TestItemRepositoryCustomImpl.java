package com.epam.ta.reportportal.database.dao;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Pavel Bortnik
 */
public class TestItemRepositoryCustomImpl implements TestItemRepositoryCustom {

	@Autowired
	private DSLContext dsl;


}
