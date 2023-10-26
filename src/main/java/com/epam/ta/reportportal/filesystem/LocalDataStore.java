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

import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.domain.Blob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dzianis_Shybeka
 */
public class LocalDataStore implements DataStore {

  private static final Logger logger = LoggerFactory.getLogger(LocalDataStore.class);

  private final BlobStore blobStore;
  private final String containerName;

  public LocalDataStore(BlobStore blobStore, String containerName) {
    this.blobStore = blobStore;
    this.containerName = containerName;

    if (!blobStore.containerExists(containerName)) {
      blobStore.createContainerInLocation(null, containerName);
    }
  }

  @Override
  public String save(String filePath, InputStream inputStream) {
    try {
      byte[] bytes = IOUtils.toByteArray(inputStream);
      Blob blob = blobStore.blobBuilder(filePath)
          .payload(bytes)
          .contentLength(bytes.length)
          .build();
      blobStore.putBlob(containerName, blob);

      return filePath;
    } catch (IOException e) {
      logger.error("Unable to save log file '{}'", filePath, e);
      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to save log file");
    }
  }

  @Override
  public InputStream load(String filePath) {
    Blob blob = blobStore.getBlob(containerName, filePath);
    if (blob == null) {
      logger.error("Unable to find file '{}'", filePath);
      throw new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, "Unable to find file");
    }

    try {
      return blob.getPayload().openStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  @Override
  public boolean exists(String filePath) {
    return blobStore.blobExists(containerName, filePath);
  }

  @Override
  public void delete(String filePath) {
    blobStore.removeBlob(containerName, filePath);
  }

  @Override
  public void deleteAll(List<String> filePaths, String bucketName) {
    for (String filePath : filePaths) {
      delete(filePath);
    }
  }

  @Override
  public void deleteContainer(String bucketName) {
    blobStore.deleteContainer(bucketName);
  }
}
