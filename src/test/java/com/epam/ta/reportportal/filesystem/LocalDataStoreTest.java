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

package com.epam.ta.reportportal.filesystem;

import com.epam.ta.reportportal.entity.enums.FeatureFlag;
import com.epam.ta.reportportal.util.FeatureFlagHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.when;

class LocalDataStoreTest {

  private LocalDataStore localDataStore;

  private FeatureFlagHandler featureFlagHandler;

  @TempDir
  Path tempDir;

  private static final String FILE_PATH = "someFile.txt";

  private static final String MULTI_BUCKET_NAME = "multiBucket";

  private static final String BUCKET_PREFIX = "prj-";

  private static final String BUCKET_POSTFIX = "-tiest";

  private static final String DEFAULT_BUCKET_NAME = "rp-bucket";

  private static final String MULTI_FILE_PATH = MULTI_BUCKET_NAME + "/" + FILE_PATH;

  @BeforeEach
  void setUp() {

    featureFlagHandler = Mockito.mock(FeatureFlagHandler.class);

    localDataStore = new LocalDataStore(featureFlagHandler, tempDir.toString(), BUCKET_PREFIX, BUCKET_POSTFIX,
        DEFAULT_BUCKET_NAME);
  }

  @Test
  void whenSave_andSingleBucketIsEnabled_thenSaveToSingleBucket() throws Exception {
    String content = "test content";
    InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(true);

    localDataStore.save(FILE_PATH, inputStream);

    Path expectedPath = tempDir.resolve(DEFAULT_BUCKET_NAME).resolve(FILE_PATH);
    Assertions.assertTrue(Files.exists(expectedPath));
    Assertions.assertEquals(content, Files.readString(expectedPath));
  }

  @Test
  void whenLoad_andSingleBucketIsEnabled_thenReturnFromSingleBucket() throws Exception {
    String content = "test content";
    Path filePath = tempDir.resolve(DEFAULT_BUCKET_NAME).resolve(FILE_PATH);
    Files.createDirectories(filePath.getParent());
    Files.writeString(filePath, content);

    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(true);

    InputStream loaded = localDataStore.load(FILE_PATH);

    Assertions.assertNotNull(loaded);
    String loadedContent = new String(loaded.readAllBytes(), StandardCharsets.UTF_8);
    Assertions.assertEquals(content, loadedContent);
  }

  @Test
  void whenDelete_andSingleBucketIsEnabled_thenDeleteFromSingleBucket() throws Exception {
    Path filePath = tempDir.resolve(DEFAULT_BUCKET_NAME).resolve(FILE_PATH);
    Files.createDirectories(filePath.getParent());
    Files.createFile(filePath);

    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(true);

    localDataStore.delete(FILE_PATH);

    Assertions.assertFalse(Files.exists(filePath));
  }

  @Test
  void whenSave_andSingleBucketIsDisabled_andBucketInName_thenSaveToThisBucket() throws Exception {
    String content = "test content";
    InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(false);

    localDataStore.save(MULTI_FILE_PATH, inputStream);

    Path expectedPath = tempDir.resolve(BUCKET_PREFIX + MULTI_BUCKET_NAME + BUCKET_POSTFIX).resolve(FILE_PATH);
    Assertions.assertTrue(Files.exists(expectedPath));
    Assertions.assertEquals(content, Files.readString(expectedPath));
  }

  @Test
  void whenLoad_andSingleBucketIsDisabled_andBucketInName_thenReturnFromThisBucket()
      throws Exception {
    String content = "test content";
    Path expectedPath = tempDir.resolve(BUCKET_PREFIX + MULTI_BUCKET_NAME + BUCKET_POSTFIX).resolve(FILE_PATH);
    Files.createDirectories(expectedPath.getParent());
    Files.writeString(expectedPath, content);

    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(false);

    InputStream loaded = localDataStore.load(MULTI_FILE_PATH);

    Assertions.assertNotNull(loaded);
    String loadedContent = new String(loaded.readAllBytes(), StandardCharsets.UTF_8);
    Assertions.assertEquals(content, loadedContent);
  }

  @Test
  void whenDelete_andSingleBucketIsDisabled_andBucketInName_thenReturnFromThisBucket()
      throws Exception {
    Path expectedPath = tempDir.resolve(BUCKET_PREFIX + MULTI_BUCKET_NAME + BUCKET_POSTFIX).resolve(FILE_PATH);
    Files.createDirectories(expectedPath.getParent());
    Files.createFile(expectedPath);

    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(false);

    localDataStore.delete(MULTI_FILE_PATH);

    Assertions.assertFalse(Files.exists(expectedPath));
  }
}
