package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.item.issue.IssueType;

public interface IssueTypeRepository extends ReportPortalRepository<IssueType, Long> {

	IssueType findByLocator(String locator);
}
