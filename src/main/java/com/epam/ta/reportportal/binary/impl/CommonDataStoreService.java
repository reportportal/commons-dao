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

import com.epam.ta.reportportal.binary.DataStoreService;
import com.epam.ta.reportportal.filesystem.DataEncoder;
import com.epam.ta.reportportal.filesystem.DataStore;

import java.io.InputStream;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public abstract class CommonDataStoreService implements DataStoreService {

	protected DataStore dataStore;

	protected DataEncoder dataEncoder;

	CommonDataStoreService(DataStore dataStore, DataEncoder dataEncoder) {
		this.dataStore = dataStore;
		this.dataEncoder = dataEncoder;
	}

	@Override
	public String save(String fileName, InputStream data) {
		return dataEncoder.encode(dataStore.save(fileName, data));
	}

	@Override
	public abstract String saveThumbnail(String fileName, InputStream data);

	@Override
	public void delete(String fileId) {
		dataStore.delete(dataEncoder.decode(fileId));
	}

	@Override
	public Optional<InputStream> load(String fileId) {
		return ofNullable(dataStore.load(dataEncoder.decode(fileId)));
	}
}
