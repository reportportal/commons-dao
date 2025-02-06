package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.group.Group;
import java.util.Optional;

public interface GroupRepository extends ReportPortalRepository<Group, Long> {

  Optional<Group> findBySlug(String slug);
}