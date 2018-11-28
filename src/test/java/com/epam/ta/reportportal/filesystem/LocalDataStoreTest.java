/*
 * Copyright (C) 2018 EPAM Systems
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

package com.epam.ta.reportportal.filesystem;

import com.epam.ta.reportportal.exception.ReportPortalException;
import com.google.common.base.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class LocalDataStoreTest {

	private static final String ROOT_PATH = System.getProperty("java.io.tmpdir");
	private static final String TEST_FILE = "test-file.txt";

	private LocalDataStore localDataStore;

	private FilePathGenerator fileNameGenerator;

	@Before
	public void setUp() {

		fileNameGenerator = Mockito.mock(FilePathGenerator.class);

		localDataStore = new LocalDataStore(ROOT_PATH);
	}

	@Test
	public void save_load_delete() throws Exception {

		//  given:
		String generatedDirectory = "/test";
		when(fileNameGenerator.generate()).thenReturn(generatedDirectory);
		FileUtils.deleteDirectory(new File(Paths.get(ROOT_PATH, generatedDirectory).toUri()));

		//  when: save new file
		String savedFilePath = localDataStore.save(TEST_FILE, new ByteArrayInputStream("test text".getBytes(Charsets.UTF_8)));

		//		and: load it back
		InputStream loaded = localDataStore.load(savedFilePath);

		//		then: saved and loaded files should be the same
		byte[] bytes = IOUtils.toByteArray(loaded);
		String result = new String(bytes, Charsets.UTF_8);
		assertEquals("saved and loaded files should be the same", "test text", result);

		//		when: delete saved file
		localDataStore.delete(savedFilePath);

		//		and: load file again
		boolean isNotFound = false;
		try {

			localDataStore.load(savedFilePath);
		} catch (ReportPortalException e) {

			isNotFound = true;
		}

		//		then: deleted file should not be found
		assertTrue("deleted file should not be found", isNotFound);
	}
}
