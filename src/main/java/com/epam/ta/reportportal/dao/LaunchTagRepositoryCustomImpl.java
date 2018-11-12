package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.jooq.tables.JItemAttribute;
import com.epam.ta.reportportal.jooq.tables.JLaunch;
import com.epam.ta.reportportal.jooq.tables.JProject;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.epam.ta.reportportal.jooq.Tables.*;

/**
 * @author Yauheni_Martynau
 */
@Repository
public class LaunchTagRepositoryCustomImpl implements LaunchTagRepositoryCustom {

	private final DSLContext dslContext;

	@Autowired
	public LaunchTagRepositoryCustomImpl(DSLContext dslContext) {

		this.dslContext = dslContext;
	}

	@Override
	public List<String> getTags(Long projectId, String value) {

		JLaunch l = LAUNCH.as("l");
		JProject p = PROJECT.as("p");
		JItemAttribute lt = JItemAttribute.ITEM_ATTRIBUTE.as("lt");

		return dslContext.select()
				.from(lt)
				.leftJoin(l).on(lt.LAUNCH_ID.eq(l.ID))
				.leftJoin(p).on(l.PROJECT_ID.eq(p.ID))
				.where(p.ID.eq(projectId))
				.and(lt.VALUE.like("%" + value + "%")).fetch(ITEM_ATTRIBUTE.VALUE);
	}
}
