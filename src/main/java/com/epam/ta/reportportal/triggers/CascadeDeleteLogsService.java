package com.epam.ta.reportportal.triggers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.ta.reportportal.database.dao.LogRepository;

@Service
public class CascadeDeleteLogsService {

	private CascadeDeleteBinariesService cascadeDeleteBinariesService;
	private LogRepository logRepository;

	@Autowired
	public CascadeDeleteLogsService(CascadeDeleteBinariesService cascadeDeleteBinariesService, LogRepository logRepository) {
		this.cascadeDeleteBinariesService = cascadeDeleteBinariesService;
		this.logRepository = logRepository;
	}

	public void delete(List<String> ids) {
		List<String> toDelete = logRepository.findBinaryIdsByLogRefs(ids);
		logRepository.delete(ids);
		cascadeDeleteBinariesService.delete(toDelete);
	}
}
