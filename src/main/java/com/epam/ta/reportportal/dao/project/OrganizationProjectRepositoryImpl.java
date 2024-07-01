package com.epam.ta.reportportal.dao.project;

import static com.epam.ta.reportportal.dao.util.ResultFetchers.ORGANIZATION_PROJECT_LIST_FETCHER;

import com.epam.ta.reportportal.commons.querygen.FilterTarget;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.api.model.ProjectProfile;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationProjectRepositoryImpl implements OrganizationProjectRepository {

  private DSLContext dsl;

  @Autowired
  public void setDsl(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public Page<ProjectProfile> getProjectProfileListByFilter(Queryable filter, Pageable pageable) {
    return PageableExecutionUtils.getPage(ORGANIZATION_PROJECT_LIST_FETCHER.apply(
        dsl.fetch(QueryBuilder.newBuilder(filter).with(pageable).build())),
        pageable,
        () -> dsl.fetchCount(QueryBuilder.newBuilder(FilterTarget.PROJECT_PROFILE).build()));
  }

}
