package com.epam.ta.reportportal.triggers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.ta.reportportal.database.DataStorage;
import com.epam.ta.reportportal.database.dao.LogRepository;

@Service
public class CascadeDeleteLogsService {

	private DataStorage dataStorage;
	private LogRepository logRepository;

	@Autowired
	public CascadeDeleteLogsService(DataStorage dataStorage, LogRepository logRepository) {
		this.dataStorage = dataStorage;
		this.logRepository = logRepository;
	}

	public void delete(List<String> ids) {
		List<String> toDelete = logRepository.findBinaryIdsByLogRefs(ids);
		logRepository.delete(ids);
		dataStorage.delete(toDelete);
	}
}
