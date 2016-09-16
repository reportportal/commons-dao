package com.epam.ta.reportportal.database.dao;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.ta.reportportal.BaseDaoTest;
import com.epam.ta.reportportal.database.BinaryData;
import com.epam.ta.reportportal.database.DataStorage;
import com.epam.ta.reportportal.database.entity.BinaryContent;
import com.epam.ta.reportportal.database.entity.Log;

public class LogRepositoryTest extends BaseDaoTest {

	public static final String CONTENT_TYPE = "image/png";

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private DataStorage dataStorage;

	private Log saved;
	private String filename;
	private String thumbnail;

	@Before
	public void addLogWithBinaryData() {
		Map<String, String> metaInfo = new HashMap<>();
		metaInfo.put("project", "EPMRPP");
		BinaryData binaryData = new BinaryData(CONTENT_TYPE, 64807L,
				this.getClass().getClassLoader().getResourceAsStream("meh.jpg"));
		filename = dataStorage.saveData(binaryData, "filename", metaInfo);
		thumbnail = dataStorage.saveData(binaryData, "thumbnail", metaInfo);
		BinaryContent binaryContent = new BinaryContent(filename, thumbnail, CONTENT_TYPE);
		Log log = new Log();
		log.setBinaryContent(binaryContent);
		saved = logRepository.save(log);
	}

	@Test
	public void deleteLogTriggerTest() {
		Assert.assertTrue(logRepository.exists(saved.getId()));
		Assert.assertNotNull(dataStorage.fetchData(thumbnail));
		Assert.assertNotNull(dataStorage.fetchData(filename));
		logRepository.delete(saved.getId());
		Assert.assertFalse(logRepository.exists(saved.getId()));
		Assert.assertNull(dataStorage.fetchData(thumbnail));
		Assert.assertNull(dataStorage.fetchData(filename));

	}
}
