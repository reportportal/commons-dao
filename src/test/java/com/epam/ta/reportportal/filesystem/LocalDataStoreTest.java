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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.epam.ta.reportportal.entity.enums.FeatureFlag;
import com.epam.ta.reportportal.util.FeatureFlagHandler;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import org.apache.opendal.Operator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalDataStoreTest {

  private static final String FILE_PATH = "someFile.txt";
  private static final String MULTI_BUCKET_NAME = "multiBucket";
  private static final String BUCKET_PREFIX = "prj-";
  private static final String BUCKET_POSTFIX = "-tiest";
  private static final String DEFAULT_BUCKET_NAME = "rp-bucket";
  private static final String MULTI_FILE_PATH = MULTI_BUCKET_NAME + "/" + FILE_PATH;
  private static final byte[] CONTENT = "hello".getBytes();

  private Operator operator;
  private FeatureFlagHandler featureFlagHandler;
  private LocalDataStore localDataStore;

  @BeforeEach
  void setUp() {
    operator = Operator.of("memory", Map.of());
    featureFlagHandler = mock(FeatureFlagHandler.class);
    localDataStore = new LocalDataStore(operator, featureFlagHandler, BUCKET_PREFIX, BUCKET_POSTFIX,
        DEFAULT_BUCKET_NAME);
  }

  @Test
  void whenSave_andSingleBucketIsEnabled_thenSaveToSingleBucket() throws Exception {
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(true);
    InputStream inputStream = new ByteArrayInputStream(CONTENT);

    localDataStore.save(FILE_PATH, inputStream);

    assertArrayEquals(CONTENT, operator.read(DEFAULT_BUCKET_NAME + "/" + FILE_PATH));
  }

  @Test
  void whenLoad_andSingleBucketIsEnabled_thenReturnFromSingleBucket() throws Exception {
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(true);
    operator.write(DEFAULT_BUCKET_NAME + "/" + FILE_PATH, CONTENT);

    InputStream loaded = localDataStore.load(FILE_PATH);

    assertArrayEquals(CONTENT, loaded.readAllBytes());
  }

  @Test
  void whenDelete_andSingleBucketIsEnabled_thenDeleteFromSingleBucket() throws Exception {
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(true);
    operator.write(DEFAULT_BUCKET_NAME + "/" + FILE_PATH, CONTENT);

    localDataStore.delete(FILE_PATH);

    assertFalse(localDataStore.exists(FILE_PATH));
  }

  @Test
  void whenSave_andSingleBucketIsDisabled_andBucketInName_thenSaveToThisBucket() throws Exception {
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(false);
    InputStream inputStream = new ByteArrayInputStream(CONTENT);

    localDataStore.save(MULTI_FILE_PATH, inputStream);

    String expectedPath = BUCKET_PREFIX + MULTI_BUCKET_NAME + BUCKET_POSTFIX + "/" + FILE_PATH;
    assertArrayEquals(CONTENT, operator.read(expectedPath));
  }

  @Test
  void whenLoad_andSingleBucketIsDisabled_andBucketInName_thenReturnFromThisBucket()
      throws Exception {
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(false);
    String storedPath = BUCKET_PREFIX + MULTI_BUCKET_NAME + BUCKET_POSTFIX + "/" + FILE_PATH;
    operator.write(storedPath, CONTENT);

    InputStream loaded = localDataStore.load(MULTI_FILE_PATH);

    assertArrayEquals(CONTENT, loaded.readAllBytes());
  }

  @Test
  void whenDelete_andSingleBucketIsDisabled_andBucketInName_thenDeleteFromThisBucket()
      throws Exception {
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(false);
    String storedPath = BUCKET_PREFIX + MULTI_BUCKET_NAME + BUCKET_POSTFIX + "/" + FILE_PATH;
    operator.write(storedPath, CONTENT);

    localDataStore.delete(MULTI_FILE_PATH);

    assertFalse(localDataStore.exists(MULTI_FILE_PATH));
  }

  @Test
  void whenExists_andFilePresent_thenReturnsTrue() throws Exception {
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(true);
    operator.write(DEFAULT_BUCKET_NAME + "/" + FILE_PATH, CONTENT);

    assertTrue(localDataStore.exists(FILE_PATH));
  }

  @Test
  void whenExists_andFileAbsent_thenReturnsFalse() {
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(true);

    assertFalse(localDataStore.exists(FILE_PATH));
  }
}
