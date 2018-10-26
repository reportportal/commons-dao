package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.enums.TestItemIssueGroup;
import com.epam.ta.reportportal.entity.item.issue.IssueGroup;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public interface IssueGroupRepository extends ReportPortalRepository<IssueGroup, Long> {

	IssueGroup findByTestItemIssueGroup(TestItemIssueGroup testItemIssueGroup);
}
