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

package com.epam.ta.reportportal.filesystem.distributed.minio;

import com.google.common.collect.Maps;
import io.minio.MinioClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
class MinioDataStoreTest {

	private static final String FILE_PATH = "someFile";
	private static final String BUCKET_PREFIX = "prj-";
	private static final String DEFAULT_BUCKET_NAME = "rp-bucket";

	private final MinioClient minioClient = mock(MinioClient.class);
	private final InputStream inputStream = mock(InputStream.class);

	private final MinioDataStore minioDataStore = new MinioDataStore(minioClient, BUCKET_PREFIX, DEFAULT_BUCKET_NAME);

	@Test
	void save() throws Exception {

		when(minioClient.bucketExists(any(String.class))).thenReturn(true);
		when(inputStream.available()).thenReturn(0);

		minioDataStore.save(FILE_PATH, inputStream);

		verify(minioClient, times(1)).putObject(DEFAULT_BUCKET_NAME, FILE_PATH, inputStream, inputStream.available(), Maps.newHashMap());
	}

	@Test
	void load() throws Exception {

		when(minioClient.getObject(DEFAULT_BUCKET_NAME, FILE_PATH)).thenReturn(inputStream);
		InputStream loaded = minioDataStore.load(FILE_PATH);

		Assertions.assertEquals(inputStream, loaded);
	}

	@Test
	void delete() throws Exception {

		minioDataStore.delete(FILE_PATH);

		verify(minioClient, times(1)).removeObject(DEFAULT_BUCKET_NAME, FILE_PATH);
	}
}