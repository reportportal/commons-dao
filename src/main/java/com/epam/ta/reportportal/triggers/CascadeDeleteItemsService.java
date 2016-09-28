package com.epam.ta.reportportal.triggers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.ta.reportportal.database.dao.LogRepository;
import com.epam.ta.reportportal.database.dao.TestItemRepository;

@Service
public class CascadeDeleteItemsService {

	private CascadeDeleteLogsService cascadeDeleteLogsService;
	private LogRepository logRepository;
	private TestItemRepository testItemRepository;

	@Autowired
	public CascadeDeleteItemsService(CascadeDeleteLogsService cascadeDeleteLogsService, TestItemRepository testItemRepository, LogRepository logRepository) {
		this.cascadeDeleteLogsService = cascadeDeleteLogsService;
		this.testItemRepository = testItemRepository;
		this.logRepository = logRepository;
	}

	public void delete(List<String> ids) {
		List<String> toDelete = logRepository.findLogIdsByItemRefs(ids);
		testItemRepository.delete(ids);
		cascadeDeleteLogsService.delete(toDelete);
	}
}
