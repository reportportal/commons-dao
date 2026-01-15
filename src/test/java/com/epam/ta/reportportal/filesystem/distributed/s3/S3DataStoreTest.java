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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ta.reportportal.entity.enums.FeatureFlag;
import com.epam.ta.reportportal.util.FeatureFlagHandler;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
class S3DataStoreTest {

  private static final String FILE_NAME = "someFile";
  private static final String BUCKET_PREFIX = "prj-";
  private static final String BUCKET_POSTFIX = "-postfix";
  private static final String DEFAULT_BUCKET_NAME = "rp-bucket";
  private static final int ZERO = 0;

  private final S3Client s3Client = mock(S3Client.class);
  private final InputStream inputStream = mock(InputStream.class);

  private final FeatureFlagHandler featureFlagHandler = mock(FeatureFlagHandler.class);

  private final S3DataStore s3DataStore = new S3DataStore(s3Client, BUCKET_PREFIX, BUCKET_POSTFIX, DEFAULT_BUCKET_NAME,
      featureFlagHandler);

  @Test
  void save() throws Exception {

    String filePath = DEFAULT_BUCKET_NAME + "/" + FILE_NAME;

    when(inputStream.available()).thenReturn(ZERO);
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(false);

    // Mock ensureBucketExists check which calls headBucket
    when(s3Client.headBucket(any(HeadBucketRequest.class)))
        .thenReturn(any(software.amazon.awssdk.services.s3.model.HeadBucketResponse.class));

    s3DataStore.save(filePath, inputStream);

    verify(s3Client, times(1)).putObject(any(PutObjectRequest.class), any(RequestBody.class));
  }

  @Test
  void load() throws Exception {

    String filePath = DEFAULT_BUCKET_NAME + "/" + FILE_NAME;

    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(false);

    when(s3Client.getObject(any(GetObjectRequest.class), any(ResponseTransformer.class))).thenReturn(inputStream);

    InputStream loaded = s3DataStore.load(filePath);

    Assertions.assertEquals(inputStream, loaded);
  }

  @Test
  void delete() throws Exception {

    String filePath = DEFAULT_BUCKET_NAME + "/" + FILE_NAME;
    when(featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)).thenReturn(false);

    s3DataStore.delete(filePath);

    verify(s3Client, times(1)).deleteObject(any(DeleteObjectRequest.class));
  }
}