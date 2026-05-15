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

import com.epam.reportportal.rules.exception.ErrorType;
import com.epam.reportportal.rules.exception.ReportPortalException;
import com.epam.ta.reportportal.entity.enums.FeatureFlag;
import com.epam.ta.reportportal.filesystem.distributed.s3.StoredFile;
import com.epam.ta.reportportal.util.FeatureFlagHandler;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import org.apache.opendal.OpenDALException;
import org.apache.opendal.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dzianis_Shybeka
 */
public class LocalDataStore implements DataStore {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocalDataStore.class);

  private final Operator operator;
  private final FeatureFlagHandler featureFlagHandler;
  private final String bucketPrefix;
  private final String bucketPostfix;
  private final String defaultBucketName;

  public LocalDataStore(Operator operator, FeatureFlagHandler featureFlagHandler,
      String bucketPrefix, String bucketPostfix, String defaultBucketName) {
    this.operator = operator;
    this.featureFlagHandler = featureFlagHandler;
    this.bucketPrefix = bucketPrefix;
    this.bucketPostfix = Objects.requireNonNullElse(bucketPostfix, "");
    this.defaultBucketName = defaultBucketName;
  }

  @Override
  public String save(String filePath, InputStream inputStream) {
    if (filePath == null) {
      return "";
    }
    StoredFile storedFile = getStoredFile(filePath);
    String fullPath = storedFile.getBucket() + "/" + storedFile.getFilePath();
    try {
      operator.write(fullPath, inputStream.readAllBytes());
      return filePath;
    } catch (IOException e) {
      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to save file", e);
    }
  }

  @Override
  public InputStream load(String filePath) {
    if (filePath == null) {
      throw new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, "Unable to find file");
    }
    StoredFile storedFile = getStoredFile(filePath);
    String fullPath = storedFile.getBucket() + "/" + storedFile.getFilePath();
    try {
      return new ByteArrayInputStream(operator.read(fullPath));
    } catch (OpenDALException e) {
      throw new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, "Unable to find file");
    }
  }

  @Override
  public boolean exists(String filePath) {
    if (filePath == null) {
      return false;
    }
    StoredFile storedFile = getStoredFile(filePath);
    String fullPath = storedFile.getBucket() + "/" + storedFile.getFilePath();
    try {
      operator.stat(fullPath);
      return true;
    } catch (OpenDALException e) {
      if (e.getCode() != OpenDALException.Code.NotFound) {
        LOGGER.warn("Error checking existence of '{}'", filePath, e);
      }
      return false;
    }
  }

  @Override
  public void delete(String filePath) {
    if (filePath == null) {
      return;
    }
    StoredFile storedFile = getStoredFile(filePath);
    String fullPath = storedFile.getBucket() + "/" + storedFile.getFilePath();
    try {
      operator.delete(fullPath);
    } catch (OpenDALException e) {
      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to delete file", e);
    }
  }

  @Override
  public void deleteAll(List<String> filePaths, String bucketName) {
    String bucket = featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)
        ? bucketName : bucketPrefix + bucketName + bucketPostfix;
    filePaths.forEach(fp -> {
      try {
        operator.delete(bucket + "/" + fp);
      } catch (OpenDALException e) {
        LOGGER.warn("Unable to delete '{}' from '{}'", fp, bucket, e);
      }
    });
  }

  @Override
  public void deleteContainer(String bucketName) {
    try {
      operator.removeAll(bucketName + "/");
    } catch (OpenDALException e) {
      LOGGER.error("Unable to delete container '{}'", bucketName, e);
    }
  }

  private StoredFile getStoredFile(String filePath) {
    if (featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)) {
      return new StoredFile(defaultBucketName, filePath);
    }
    Path targetPath = Paths.get(filePath);
    int nameCount = targetPath.getNameCount();
    if (nameCount > 1) {
      String bucketName = bucketPrefix + retrievePath(targetPath, 0, 1) + bucketPostfix;
      return new StoredFile(bucketName, retrievePath(targetPath, 1, nameCount));
    } else {
      return new StoredFile(defaultBucketName, retrievePath(targetPath, 0, 1));
    }
  }

  private String retrievePath(Path path, int beginIndex, int endIndex) {
    return String.valueOf(path.subpath(beginIndex, endIndex));
  }
}
