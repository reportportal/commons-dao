/*
 * Copyright 2023 EPAM Systems
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

package com.epam.ta.reportportal.filesystem.distributed.s3;

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
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.apache.opendal.Operator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
class S3DataStoreTest {

  private static final String FILE_NAME = "someFile";
  private static final String BUCKET_PREFIX = "prj-";
  private static final String BUCKET_POSTFIX = "-postfix";
  private static final String DEFAULT_BUCKET_NAME = "rp-bucket";
  private static final byte[] CONTENT = "data".getBytes();

  private final ConcurrentHashMap<String, Operator> operatorCache = new ConcurrentHashMap<>();
  private final Function<String, Operator> operatorFactory =
      bucket -> operatorCache.computeIfAbsent(bucket, b -> Operator.of("memory", Map.of()));

  private final FeatureFlagHandler featureFlagHandler = mock(FeatureFlagHandler.class);

  private S3DataStore s3DataStore;

  @BeforeEach
  void setUp() {
    operatorCache.clear();
    s3DataStore = new S3DataStore(operatorFactory, BUCKET_PREFIX, BUCKET_POSTFIX,
        DEFAULT_BUCKET_NAME, featureFlagHandler);
  }

  @Test
  void save() throws Exception {
    String filePath = DEFAULT_BUCKET_NAME + "/" + FILE_NAME;
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(false);

    s3DataStore.save(filePath, new ByteArrayInputStream(CONTENT));

    String expectedBucket = BUCKET_PREFIX + DEFAULT_BUCKET_NAME + BUCKET_POSTFIX;
    assertArrayEquals(CONTENT, operatorCache.get(expectedBucket).read(FILE_NAME));
  }

  @Test
  void load() throws Exception {
    String filePath = DEFAULT_BUCKET_NAME + "/" + FILE_NAME;
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(false);
    String bucket = BUCKET_PREFIX + DEFAULT_BUCKET_NAME + BUCKET_POSTFIX;
    operatorFactory.apply(bucket).write(FILE_NAME, CONTENT);

    InputStream loaded = s3DataStore.load(filePath);

    assertArrayEquals(CONTENT, loaded.readAllBytes());
  }

  @Test
  void delete() throws Exception {
    String filePath = DEFAULT_BUCKET_NAME + "/" + FILE_NAME;
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(false);
    String bucket = BUCKET_PREFIX + DEFAULT_BUCKET_NAME + BUCKET_POSTFIX;
    operatorFactory.apply(bucket).write(FILE_NAME, CONTENT);

    s3DataStore.delete(filePath);

    assertFalse(s3DataStore.exists(filePath));
  }

  @Test
  void exists_whenFilePresent_returnsTrue() throws Exception {
    String filePath = DEFAULT_BUCKET_NAME + "/" + FILE_NAME;
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(false);
    String bucket = BUCKET_PREFIX + DEFAULT_BUCKET_NAME + BUCKET_POSTFIX;
    operatorFactory.apply(bucket).write(FILE_NAME, CONTENT);

    assertTrue(s3DataStore.exists(filePath));
  }

  @Test
  void exists_whenFileAbsent_returnsFalse() {
    String filePath = DEFAULT_BUCKET_NAME + "/" + FILE_NAME;
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(false);

    assertFalse(s3DataStore.exists(filePath));
  }

  @Test
  void save_singleBucketMode_usesDefaultBucket() throws Exception {
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(true);

    s3DataStore.save(FILE_NAME, new ByteArrayInputStream(CONTENT));

    assertArrayEquals(CONTENT, operatorCache.get(DEFAULT_BUCKET_NAME).read(FILE_NAME));
  }
}
