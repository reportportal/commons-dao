package com.epam.ta.reportportal.triggers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.ta.reportportal.database.dao.LaunchRepository;
import com.epam.ta.reportportal.database.dao.TestItemRepository;

@Service
public class CascadeDeleteLaunchesService {

	private CascadeDeleteItemsService cascadeDeleteItemsService;
	private LaunchRepository launchRepository;
	private TestItemRepository testItemRepository;

	@Autowired
	public CascadeDeleteLaunchesService(CascadeDeleteItemsService cascadeDeleteItemsService, LaunchRepository launchRepository,
			TestItemRepository testItemRepository) {
		this.cascadeDeleteItemsService = cascadeDeleteItemsService;
		this.launchRepository = launchRepository;
		this.testItemRepository = testItemRepository;
	}

	public void delete(List<String> ids) {
		List<String> itemIdsByLaunchRef = testItemRepository.findItemIdsByLaunchRef(ids);
		launchRepository.delete(ids);
		cascadeDeleteItemsService.delete(itemIdsByLaunchRef);
	}
}
