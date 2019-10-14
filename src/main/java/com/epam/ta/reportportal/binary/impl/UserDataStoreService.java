package com.epam.ta.reportportal.binary.impl;

import com.epam.reportportal.commons.Thumbnailator;
import com.epam.ta.reportportal.filesystem.DataEncoder;
import com.epam.ta.reportportal.filesystem.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Service("userDataStoreService")
public class UserDataStoreService extends CommonDataStoreService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDataStoreService.class);

	private final Thumbnailator thumbnailator;

	@Autowired
	public UserDataStoreService(DataStore dataStore, DataEncoder dataEncoder,
			@Qualifier("userPhotoThumbnailator") Thumbnailator thumbnailator) {
		super(dataStore, dataEncoder);
		this.thumbnailator = thumbnailator;
	}

	@Override
	public String saveThumbnail(String fileName, InputStream data) {
		try {
			return dataEncoder.encode(dataStore.save(fileName, thumbnailator.createThumbnail(data)));
		} catch (IOException e) {
			LOGGER.error("Thumbnail is not created for file [{}]. Error:\n{}", fileName, e);
		}
		return null;
	}
}
