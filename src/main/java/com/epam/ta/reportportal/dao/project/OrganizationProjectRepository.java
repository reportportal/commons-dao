package com.epam.ta.reportportal.dao.project;

import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.entity.project.ProjectProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizationProjectRepository {

  Page<ProjectProfile> getProjectProfileListByFilter(Queryable filter, Pageable pageable);

}
