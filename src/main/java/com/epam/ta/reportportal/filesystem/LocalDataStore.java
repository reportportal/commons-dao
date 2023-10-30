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
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.filesystem.distributed.s3.StoredFile;
import com.epam.ta.reportportal.util.FeatureFlagHandler;
import com.epam.ta.reportportal.ws.model.ErrorType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.domain.Blob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dzianis_Shybeka
 */
public class LocalDataStore implements DataStore {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocalDataStore.class);
  private final BlobStore blobStore;

  private final FeatureFlagHandler featureFlagHandler;

  private static final String SINGLE_BUCKET_NAME = "store";

  private static final String PLUGINS = "plugins";

  public LocalDataStore(BlobStore blobStore, FeatureFlagHandler featureFlagHandler) {
    this.blobStore = blobStore;
    this.featureFlagHandler = featureFlagHandler;
  }

  @Override
  public String save(String filePath, InputStream inputStream) {
    StoredFile storedFile = getStoredFile(filePath);
    try {
      if (!blobStore.containerExists(storedFile.getBucket())) {
        blobStore.createContainerInLocation(null, storedFile.getBucket());
      }
      Blob objectBlob = blobStore.blobBuilder(storedFile.getFilePath()).payload(inputStream)
          .contentDisposition(storedFile.getFilePath()).contentLength(inputStream.available())
          .build();
      blobStore.putBlob(storedFile.getBucket(), objectBlob);
      return filePath;
    } catch (IOException e) {
      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to save file", e);
    }
  }

  @Override
  public InputStream load(String filePath) {
    StoredFile storedFile = getStoredFile(filePath);
    Blob fileBlob = blobStore.getBlob(storedFile.getBucket(), storedFile.getFilePath());
    if (fileBlob != null) {
      try {
        return fileBlob.getPayload().openStream();
      } catch (IOException e) {
        throw new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, e.getMessage(), e);
      }
    }
    throw new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, "Unable to find file");
  }

  @Override
  public boolean exists(String filePath) {
    StoredFile storedFile = getStoredFile(filePath);
    if (blobStore.containerExists(storedFile.getBucket())) {
      return blobStore.blobExists(storedFile.getBucket(), storedFile.getFilePath());
    } else {
      LOGGER.warn("Container '{}' does not exist", storedFile.getBucket());
      return false;
    }
  }

  @Override
  public void delete(String filePath) {
    StoredFile storedFile = getStoredFile(filePath);
    try {
      blobStore.removeBlob(storedFile.getBucket(), storedFile.getFilePath());
    } catch (Exception e) {
      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to delete file", e);
    }
  }

  @Override
  public void deleteAll(List<String> filePaths, String bucketName) {
    if (!featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)) {
      blobStore.removeBlobs(bucketName, filePaths);
    } else {
      blobStore.removeBlobs(SINGLE_BUCKET_NAME, filePaths);
    }
  }

  @Override
  public void deleteContainer(String bucketName) {
    blobStore.deleteContainer(bucketName);
  }

  private StoredFile getStoredFile(String filePath) {
    Path targetPath = Paths.get(filePath);
    if (featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)) {
      return new StoredFile(SINGLE_BUCKET_NAME, filePath);
    } else {
      int nameCount = targetPath.getNameCount();
      if (nameCount > 1) {
        String bucketName = targetPath.getName(0).toString();
        String newFilePath = targetPath.subpath(1, nameCount).toString();
        return new StoredFile(bucketName, newFilePath);
      } else {
        return new StoredFile(PLUGINS, filePath);
      }
    }
  }
}

