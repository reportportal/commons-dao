package com.epam.ta.reportportal.dao;

import static com.epam.ta.reportportal.dao.util.RecordMappers.ASSIGNMENT_DETAILS_MAPPER;
import static com.epam.ta.reportportal.jooq.Tables.ORGANIZATION;
import static com.epam.ta.reportportal.jooq.Tables.ORGANIZATION_USER;
import static com.epam.ta.reportportal.jooq.Tables.PROJECT;
import static com.epam.ta.reportportal.jooq.Tables.PROJECT_USER;

import com.epam.ta.reportportal.commons.UserAssignmentDetails;
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
  public Optional<UserAssignmentDetails> findDetailsByUserIdAndProjectKey(Long userId,
      String projectKey) {
    return dsl.select(
            PROJECT_USER.PROJECT_ID,
            PROJECT_USER.PROJECT_ROLE,
            PROJECT.NAME,
            PROJECT.KEY,
            PROJECT.ORGANIZATION_ID,
            ORGANIZATION_USER.ORGANIZATION_ROLE,
            ORGANIZATION.NAME)
        .from(PROJECT_USER)
        .join(PROJECT).on(PROJECT_USER.PROJECT_ID.eq(PROJECT.ID))
        .join(ORGANIZATION).on(PROJECT.ORGANIZATION_ID.eq(ORGANIZATION.ID))
        .join(ORGANIZATION_USER).on(ORGANIZATION_USER.USER_ID.eq(ORGANIZATION_USER.ORGANIZATION_ID))
        .where(PROJECT_USER.USER_ID.eq(userId))
        .and(ORGANIZATION_USER.USER_ID.eq(userId))
        .and(PROJECT.KEY.eq(projectKey))
        .fetchOptional(ASSIGNMENT_DETAILS_MAPPER);
  }

  @Override
  public Optional<UserAssignmentDetails> findAdminDetailsProjectKey(String projectKey) {
    return dsl.select(
            PROJECT.ID.as(PROJECT_USER.PROJECT_ID),
            DSL.val("MANAGER").as(ORGANIZATION_USER.ORGANIZATION_ROLE),
            PROJECT.NAME,
            PROJECT.KEY,
            PROJECT.ORGANIZATION_ID,
            ORGANIZATION.NAME)
        .from(PROJECT)
        .join(ORGANIZATION).on(PROJECT.ORGANIZATION_ID.eq(ORGANIZATION.ID))
        .where(PROJECT.KEY.eq(projectKey))
        .fetchOptional(ASSIGNMENT_DETAILS_MAPPER);
  }
}
