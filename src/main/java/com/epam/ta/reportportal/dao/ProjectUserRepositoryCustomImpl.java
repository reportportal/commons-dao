package com.epam.ta.reportportal.dao;

import static com.epam.ta.reportportal.dao.util.RecordMappers.ASSIGNMENT_DETAILS_MAPPER;
import static com.epam.ta.reportportal.jooq.Tables.ORGANIZATION;
import static com.epam.ta.reportportal.jooq.Tables.ORGANIZATION_USER;
import static com.epam.ta.reportportal.jooq.Tables.PROJECT;
import static com.epam.ta.reportportal.jooq.Tables.PROJECT_USER;

import com.epam.ta.reportportal.entity.organization.MembershipDetails;
import com.epam.ta.reportportal.jooq.enums.JOrganizationRoleEnum;
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
  public Optional<MembershipDetails> findDetailsByUserIdAndProjectKey(Long userId, String projectKey) {

    return dsl.select(
            PROJECT.ID,
            PROJECT_USER.PROJECT_ROLE,
            PROJECT.NAME,
            PROJECT.KEY,
            PROJECT.ORGANIZATION_ID,
            ORGANIZATION_USER.ORGANIZATION_ROLE,
            ORGANIZATION.NAME)
        .from(PROJECT)
        .join(ORGANIZATION).on(PROJECT.ORGANIZATION_ID.eq(ORGANIZATION.ID))
        .fullJoin(PROJECT_USER)
          .on(PROJECT_USER.PROJECT_ID.eq(PROJECT.ID)
              .and(PROJECT_USER.USER_ID.eq(userId)))
        .join(ORGANIZATION_USER)
          .on(ORGANIZATION_USER.ORGANIZATION_ID.eq(PROJECT.ORGANIZATION_ID)
              .and(ORGANIZATION_USER.USER_ID.eq(userId)))
        .where(PROJECT.KEY.eq(projectKey))
        .fetchOptional(ASSIGNMENT_DETAILS_MAPPER);
  }

  @Override
  public Optional<MembershipDetails> findAdminDetailsProjectKey(String projectKey) {
    return dsl.select(
            PROJECT.ID,
            DSL.val(JOrganizationRoleEnum.MANAGER).as(ORGANIZATION_USER.ORGANIZATION_ROLE),
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
