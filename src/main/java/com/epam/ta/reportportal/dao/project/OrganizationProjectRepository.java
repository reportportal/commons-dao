package com.epam.ta.reportportal.dao.project;

import com.epam.ta.reportportal.commons.querygen.Queryable;
import com.epam.ta.reportportal.model.ProjectProfile;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrganizationProjectRepository {

  List<ProjectProfile> getProjectListByFilter(Queryable filter, Pageable pageable);

}
