package com.epam.ta.reportportal.dao.organization;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.epam.ta.reportportal.BaseTest;
import com.epam.ta.reportportal.api.model.OrganizationUserProfile;
import com.epam.ta.reportportal.commons.querygen.Condition;
import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.organization.OrganizationFilter;
import com.epam.ta.reportportal.entity.organization.OrganizationUserFilter;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
}
