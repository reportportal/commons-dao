package com.epam.ta.reportportal.triggers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.ta.reportportal.database.DataStorage;
import com.epam.ta.reportportal.database.dao.LogRepository;
import com.epam.ta.reportportal.database.dao.TestItemRepository;

@Service
public class CascadeDeleteItemsService {

	private LogRepository logRepository;
	private TestItemRepository testItemRepository;
	private DataStorage dataStorage;

	@Autowired
	public CascadeDeleteItemsService(TestItemRepository testItemRepository, LogRepository logRepository, DataStorage dataStorage) {
		this.testItemRepository = testItemRepository;
		this.logRepository = logRepository;
		this.dataStorage = dataStorage;
	}

	public void delete(List<String> ids) {
		List<String> toDelete = logRepository.findBinaryIdsByItemRefs(ids);
		dataStorage.delete(toDelete);
		logRepository.deleteByItemRef(ids);
		testItemRepository.delete(ids);
	}
}
