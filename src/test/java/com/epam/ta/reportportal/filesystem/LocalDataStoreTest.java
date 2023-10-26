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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.domain.BlobBuilder;
import org.jclouds.io.Payload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LocalDataStoreTest {

  private static final String CONTAINER_NAME = "container";

  private static final String FILE_PATH = "someFile.txt";

  private LocalDataStore localDataStore;

  private BlobStore blobStore;

  private final InputStream inputStream = mock(InputStream.class);

  private static final int ZERO = 0;

  @BeforeEach
  void setUp() {

    blobStore = Mockito.mock(BlobStore.class);

    localDataStore = new LocalDataStore(blobStore, CONTAINER_NAME);
  }

  @Test
  void save() throws Exception {

    BlobBuilder blobBuilderMock = mock(BlobBuilder.class);
    BlobBuilder.PayloadBlobBuilder payloadBlobBuilderMock =
        mock(BlobBuilder.PayloadBlobBuilder.class);
    Blob blobMock = mock(Blob.class);

    when(inputStream.available()).thenReturn(ZERO);
    when(payloadBlobBuilderMock.contentLength(ZERO)).thenReturn(payloadBlobBuilderMock);
    when(payloadBlobBuilderMock.build()).thenReturn(blobMock);
    when(blobBuilderMock.payload(inputStream)).thenReturn(payloadBlobBuilderMock);

    when(blobStore.blobBuilder(FILE_PATH)).thenReturn(blobBuilderMock);

    localDataStore.save(FILE_PATH, inputStream);

    verify(blobStore, times(1)).putBlob(CONTAINER_NAME, blobMock);
  }

  @Test
  void load() throws Exception {

    Blob mockBlob = mock(Blob.class);
    Payload mockPayload = mock(Payload.class);

    when(mockPayload.openStream()).thenReturn(inputStream);
    when(mockBlob.getPayload()).thenReturn(mockPayload);

    when(blobStore.getBlob(CONTAINER_NAME, FILE_PATH)).thenReturn(mockBlob);
    InputStream loaded = localDataStore.load(FILE_PATH);

    Assertions.assertEquals(inputStream, loaded);
  }

  @Test
  void delete() throws Exception {

    localDataStore.delete(FILE_PATH);

    verify(blobStore, times(1)).removeBlob(CONTAINER_NAME, FILE_PATH);
  }
}
