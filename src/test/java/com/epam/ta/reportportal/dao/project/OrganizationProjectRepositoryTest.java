package com.epam.ta.reportportal.dao.project;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.model.ProjectProfile;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class OrganizationProjectRepositoryTest extends BaseTest {
  @Autowired
  private OrganizationProjectRepository organizationProjectRepository;


  @ParameterizedTest
  @CsvSource(value = {
      "1|1|Member",
      "2|1|Member",
  }, delimiter = '|')
  void findAllOrganizationProjects(Long userId, Long orgId, String role) {
    Filter filter = new Filter(ProjectProfile.class, Lists.newArrayList())
        .withCondition(new FilterCondition(Condition.EQUALS, false, orgId.toString(), "organization_id"));
    Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));

    List<ProjectProfile> projectsList = organizationProjectRepository.getProjectListByFilter(filter, pageable);
    assertTrue(isNotEmpty(projectsList));
  }

  @ParameterizedTest
  @CsvSource(value = {
      "1|1|Member",
      "2|1|Member",
  }, delimiter = '|')
  void findOrganizationProjectsAssignedToUser(Long userId, Long orgId, String role) {
    Filter filter = new Filter(ProjectProfile.class, Lists.newArrayList())
        .withCondition(new FilterCondition(Condition.EQUALS, false, orgId.toString(), "organization_id"))
        .withCondition(new FilterCondition(Condition.EQUALS, false, userId.toString(), "user_id"));
    Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));

    List<ProjectProfile> projectsList = organizationProjectRepository.getProjectListByFilter(filter, pageable);
    assertTrue(isNotEmpty(projectsList));
  }
}
