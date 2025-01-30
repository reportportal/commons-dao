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
	public Optional<ReportPortalUser.ProjectDetails> findDetailsByUserIdAndProjectName(Long userId, String projectName) {
  		return dsl.select(PROJECT.ID, PROJECT.NAME, PROJECT_USER.PROJECT_ROLE)
          .from(PROJECT)
          .leftJoin(PROJECT_USER)
          .on(PROJECT.ID.eq(PROJECT_USER.PROJECT_ID)
              .and(PROJECT_USER.USER_ID.eq(userId)))
          .where(PROJECT.NAME.eq(projectName))
          .fetchOptional(PROJECT_DETAILS_MAPPER);
	}

  @Override
  public Optional<ReportPortalUser.ProjectDetails> findAdminDetailsProjectName(String projectName) {
    return dsl.select(PROJECT.ID.as(PROJECT_USER.PROJECT_ID),
            DSL.val("PROJECT_MANAGER").as(PROJECT_USER.PROJECT_ROLE),
            PROJECT.NAME)
        .from(PROJECT)
        .where(PROJECT.NAME.eq(projectName))
        .fetchOptional(PROJECT_DETAILS_MAPPER);
  }
}
