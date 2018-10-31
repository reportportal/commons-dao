package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.item.issue.IssueEntity;

import java.util.List;

public interface IssueEntityRepository extends ReportPortalRepository<IssueEntity, Long> {

	List<IssueEntity> findAllByIssueTypeId(Long id);
}
