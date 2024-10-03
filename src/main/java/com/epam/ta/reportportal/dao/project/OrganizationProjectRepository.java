package com.epam.ta.reportportal.dao.project;

import com.epam.reportportal.api.model.ProjectInfo;
import com.epam.ta.reportportal.commons.querygen.Queryable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizationProjectRepository {

  Page<ProjectInfo> getProjectProfileListByFilter(Queryable filter, Pageable pageable);

}
