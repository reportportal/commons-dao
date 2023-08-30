package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.ReportPortalUser;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.epam.ta.reportportal.dao.util.RecordMappers.PROJECT_DETAILS_MAPPER;
import static com.epam.ta.reportportal.jooq.Tables.PROJECT;
import static com.epam.ta.reportportal.jooq.Tables.PROJECT_USER;

@Repository
public class ProjectUserRepositoryCustomImpl implements ProjectUserRepositoryCustom {

	private final DSLContext dsl;

	@Autowired
	public ProjectUserRepositoryCustomImpl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public Optional<ReportPortalUser.ProjectDetails> findDetailsByUserIdAndProjectName(Long userId, String projectName) {
		return dsl.select(PROJECT_USER.PROJECT_ID, PROJECT_USER.PROJECT_ROLE, PROJECT.NAME)
				.from(PROJECT_USER)
				.join(PROJECT)
				.on(PROJECT_USER.PROJECT_ID.eq(PROJECT.ID))
				.where(PROJECT_USER.USER_ID.eq(userId))
				.and(PROJECT.NAME.eq(projectName))
				.fetchOptional(PROJECT_DETAILS_MAPPER);
	}
}
