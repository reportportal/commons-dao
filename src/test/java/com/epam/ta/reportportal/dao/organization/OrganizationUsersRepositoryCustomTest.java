package com.epam.ta.reportportal.dao.organization;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.CRITERIA_PROJECT_ID;
import static com.epam.ta.reportportal.commons.querygen.constant.OrganizationCriteriaConstant.CRITERIA_ORG_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.epam.ta.reportportal.BaseTest;
import com.epam.reportportal.api.model.OrganizationUserProfile;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.entity.organization.OrganizationUserFilter;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

class OrganizationUsersRepositoryCustomTest extends BaseTest {

  @Autowired
  OrganizationUsersRepositoryCustom organizationUsersRepositoryCustom;

  @Test
  void findOrganizationUsersByFilter() {
    Filter filter = new Filter(OrganizationUserFilter.class,
        Condition.EQUALS,
        false,
        "1",
        "organization_id");

    final List<OrganizationUserProfile> orgUsers = organizationUsersRepositoryCustom.findByFilter(
        filter);
    assertFalse(orgUsers.isEmpty());
  }

  @ParameterizedTest
  @CsvSource(value = {
      "0|1|fullName",
      "0|2|fullName",
  }, delimiter = '|')
  void findOrganizationUsersByFilterPageable(int offset, int limit, String sortField) {
    Filter filter = new Filter(OrganizationUserFilter.class,
        Condition.EQUALS,
        false,
        "1",
        CRITERIA_ORG_ID);
    filter.withCondition(
        new FilterCondition(Condition.IN, false, "1, 2", CRITERIA_PROJECT_ID));
    PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, sortField));

    final Page<OrganizationUserProfile> orgUsers = organizationUsersRepositoryCustom.findByFilter(
        filter, pageRequest);
    assertEquals(limit, orgUsers.getContent().size());
  }
}
