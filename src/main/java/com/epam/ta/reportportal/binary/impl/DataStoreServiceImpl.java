/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.binary.impl;

import com.epam.reportportal.commons.Thumbnailator;
import com.epam.ta.reportportal.binary.DataStoreService;
import com.epam.ta.reportportal.filesystem.DataEncoder;
import com.epam.ta.reportportal.filesystem.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Service
public class DataStoreServiceImpl implements DataStoreService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataStoreServiceImpl.class);

	private DataStore dataStore;

	private DataEncoder dataEncoder;

	private Thumbnailator thumbnailator;

	@Autowired
	public DataStoreServiceImpl(DataStore dataStore, DataEncoder dataEncoder, Thumbnailator thumbnailator) {
		this.dataStore = dataStore;
		this.dataEncoder = dataEncoder;
		this.thumbnailator = thumbnailator;
	}

	@Override
	public String save(String fileName, InputStream data) {
		return dataEncoder.encode(dataStore.save(fileName, data));
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

	@Override
	public void delete(String fileId) {
		dataStore.delete(dataEncoder.decode(fileId));
	}

	@Override
	public Optional<InputStream> load(String fileId) {
		return ofNullable(dataStore.load(dataEncoder.decode(fileId)));
	}
}
