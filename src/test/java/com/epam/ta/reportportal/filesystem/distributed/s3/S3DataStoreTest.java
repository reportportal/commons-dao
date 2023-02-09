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

package com.epam.ta.reportportal.filesystem.distributed.s3;

import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.domain.BlobBuilder;
import org.jclouds.io.Payload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
class S3DataStoreTest {

  private static final String FILE_PATH = "someFile";
  private static final String BUCKET_PREFIX = "prj-";

  private static final String BUCKET_POSTFIX = "-postfix";
  private static final String DEFAULT_BUCKET_NAME = "rp-bucket";
  private static final String REGION = "us-east-1";
  private static final int ZERO = 0;

  private final BlobStore blobStore = mock(BlobStore.class);
  private final InputStream inputStream = mock(InputStream.class);

  private final S3DataStore s3DataStore =
      new S3DataStore(blobStore, BUCKET_PREFIX, BUCKET_POSTFIX, DEFAULT_BUCKET_NAME, REGION);

  @Test
  void save() throws Exception {

    BlobBuilder blobBuilderMock = mock(BlobBuilder.class);
    BlobBuilder.PayloadBlobBuilder payloadBlobBuilderMock =
        mock(BlobBuilder.PayloadBlobBuilder.class);
    Blob blobMock = mock(Blob.class);

    when(inputStream.available()).thenReturn(ZERO);
    when(payloadBlobBuilderMock.contentDisposition(FILE_PATH)).thenReturn(payloadBlobBuilderMock);
    when(payloadBlobBuilderMock.contentLength(ZERO)).thenReturn(payloadBlobBuilderMock);
    when(payloadBlobBuilderMock.build()).thenReturn(blobMock);
    when(blobBuilderMock.payload(inputStream)).thenReturn(payloadBlobBuilderMock);

    when(blobStore.containerExists(any(String.class))).thenReturn(true);
    when(blobStore.blobBuilder(FILE_PATH)).thenReturn(blobBuilderMock);

    s3DataStore.save(FILE_PATH, inputStream);

    verify(blobStore, times(1)).putBlob(
        BUCKET_PREFIX + DEFAULT_BUCKET_NAME + BUCKET_POSTFIX, blobMock);
  }

  @Test
  void load() throws Exception {

    Blob mockBlob = mock(Blob.class);
    Payload mockPayload = mock(Payload.class);

    when(mockPayload.openStream()).thenReturn(inputStream);
    when(mockBlob.getPayload()).thenReturn(mockPayload);

    when(blobStore.getBlob(BUCKET_PREFIX + DEFAULT_BUCKET_NAME + BUCKET_POSTFIX,
        FILE_PATH
    )).thenReturn(mockBlob);
    InputStream loaded = s3DataStore.load(FILE_PATH);

    Assertions.assertEquals(inputStream, loaded);
  }

  @Test
  void delete() throws Exception {

    s3DataStore.delete(FILE_PATH);

    verify(blobStore, times(1)).removeBlob(
        BUCKET_PREFIX + DEFAULT_BUCKET_NAME + BUCKET_POSTFIX, FILE_PATH);
  }
}