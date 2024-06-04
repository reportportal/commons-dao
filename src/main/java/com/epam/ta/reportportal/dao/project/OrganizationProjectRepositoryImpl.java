package com.epam.ta.reportportal.dao.project;

import static com.epam.ta.reportportal.dao.util.ResultFetchers.ORGANIZATION_PROJECT_LIST_FETCHER;

import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.model.ProjectProfile;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationProjectRepositoryImpl implements
    OrganizationProjectRepository {

  private DSLContext dsl;

  @Autowired
  public void setDsl(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public List<ProjectProfile> getProjectListByFilter(Queryable filter, Pageable pageable) {
    return ORGANIZATION_PROJECT_LIST_FETCHER.apply(
        dsl.fetch(QueryBuilder.newBuilder(filter).with(pageable).build()));
  }

}
