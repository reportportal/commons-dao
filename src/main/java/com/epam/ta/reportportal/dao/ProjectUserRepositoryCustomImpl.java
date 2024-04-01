package com.epam.ta.reportportal.dao;

import static com.epam.ta.reportportal.dao.util.RecordMappers.PROJECT_DETAILS_MAPPER;
import static com.epam.ta.reportportal.jooq.Tables.PROJECT;
import static com.epam.ta.reportportal.jooq.Tables.PROJECT_USER;

import com.epam.ta.reportportal.commons.ReportPortalUser;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectUserRepositoryCustomImpl implements ProjectUserRepositoryCustom {

  private final DSLContext dsl;

  @Autowired
  public ProjectUserRepositoryCustomImpl(DSLContext dsl) {
    this.dsl = dsl;
  }

	@Override
	public Optional<ReportPortalUser.ProjectDetails> findDetailsByUserIdAndProjectKey(Long userId, String projectKey) {
    return dsl.select(
            PROJECT_USER.PROJECT_ID,
            PROJECT_USER.PROJECT_ROLE,
            PROJECT.NAME,
            PROJECT.KEY,
            PROJECT.ORGANIZATION_ID)
        .from(PROJECT_USER)
        .join(PROJECT)
        .on(PROJECT_USER.PROJECT_ID.eq(PROJECT.ID))
        .where(PROJECT_USER.USER_ID.eq(userId))
        .and(PROJECT.KEY.eq(projectKey))
        .fetchOptional(PROJECT_DETAILS_MAPPER);
  }

  @Override
  public Optional<ReportPortalUser.ProjectDetails> findAdminDetailsProjectKey(String projectKey) {
    return dsl.select(
            PROJECT.ID.as(PROJECT_USER.PROJECT_ID),
            DSL.val("PROJECT_MANAGER").as(PROJECT_USER.PROJECT_ROLE),
            PROJECT.NAME,
            PROJECT.KEY,
            PROJECT.ORGANIZATION_ID)
        .from(PROJECT)
        .where(PROJECT.KEY.eq(projectKey))
        .fetchOptional(PROJECT_DETAILS_MAPPER);
  }
}
