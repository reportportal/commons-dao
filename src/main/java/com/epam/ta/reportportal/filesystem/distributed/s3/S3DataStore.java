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

import com.epam.reportportal.rules.exception.ErrorType;
import com.epam.reportportal.rules.exception.ReportPortalException;
import com.epam.ta.reportportal.entity.enums.FeatureFlag;
import com.epam.ta.reportportal.filesystem.DataStore;
import com.epam.ta.reportportal.util.FeatureFlagHandler;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.apache.opendal.OpenDALException;
import org.apache.opendal.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of basic operations with blob storages.
 *
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class S3DataStore implements DataStore {

  private static final Logger LOGGER = LoggerFactory.getLogger(S3DataStore.class);

  private final Function<String, Operator> operatorFactory;
  private final String bucketPrefix;
  private final String bucketPostfix;
  private final String defaultBucketName;
  private final FeatureFlagHandler featureFlagHandler;

  /**
   * @param operatorFactory    returns a per-bucket {@link Operator} (callers should cache)
   * @param bucketPrefix       prefix applied to bucket names in multi-bucket mode
   * @param bucketPostfix      postfix applied to bucket names in multi-bucket mode
   * @param defaultBucketName  bucket used in single-bucket mode
   * @param featureFlagHandler feature flag access
   */
  public S3DataStore(Function<String, Operator> operatorFactory, String bucketPrefix,
      String bucketPostfix, String defaultBucketName, FeatureFlagHandler featureFlagHandler) {
    this.operatorFactory = operatorFactory;
    this.bucketPrefix = bucketPrefix;
    this.bucketPostfix = Objects.requireNonNullElse(bucketPostfix, "");
    this.defaultBucketName = defaultBucketName;
    this.featureFlagHandler = featureFlagHandler;
  }

  @Override
  public String save(String filePath, InputStream inputStream) {
    if (filePath == null) {
      return "";
    }
    StoredFile storedFile = getStoredFile(filePath);
    try {
      operatorFactory.apply(storedFile.getBucket())
          .write(storedFile.getFilePath(), inputStream.readAllBytes());
      return Paths.get(filePath).toString();
    } catch (IOException e) {
      LOGGER.error("Unable to save file '{}'", filePath, e);
      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to save file");
    }
  }

  @Override
  public InputStream load(String filePath) {
    if (filePath == null) {
      LOGGER.error("Unable to find file");
      throw new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, "Unable to find file");
    }
    StoredFile storedFile = getStoredFile(filePath);
    try {
      byte[] bytes = operatorFactory.apply(storedFile.getBucket()).read(storedFile.getFilePath());
      return new ByteArrayInputStream(bytes);
    } catch (OpenDALException e) {
      LOGGER.error("Unable to find file '{}'", filePath, e);
      throw new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, "Unable to find file");
    }
  }

  @Override
  public boolean exists(String filePath) {
    if (filePath == null) {
      return false;
    }
    StoredFile storedFile = getStoredFile(filePath);
    try {
      operatorFactory.apply(storedFile.getBucket()).stat(storedFile.getFilePath());
      return true;
    } catch (OpenDALException e) {
      return false;
    }
  }

  @Override
  public void delete(String filePath) {
    if (filePath == null) {
      return;
    }
    StoredFile storedFile = getStoredFile(filePath);
    try {
      operatorFactory.apply(storedFile.getBucket()).delete(storedFile.getFilePath());
    } catch (OpenDALException e) {
      LOGGER.error("Unable to delete file '{}'", filePath, e);
      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to delete file");
    }
  }

  @Override
  public void deleteAll(List<String> filePaths, String bucketName) {
    try {
      filePaths.forEach(filePath -> {
        StoredFile storedFile = getStoredFile(filePath);
        operatorFactory.apply(storedFile.getBucket()).delete(storedFile.getFilePath());
      });
    } catch (OpenDALException e) {
      LOGGER.error("Unable to delete files from bucket '{}'", bucketName, e);
      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to delete files");
    }
  }

  @Override
  public void deleteContainer(String bucketName) {
    String resolvedBucket = featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)
        ? bucketName : bucketPrefix + bucketName + bucketPostfix;
    try {
      operatorFactory.apply(resolvedBucket).removeAll("");
    } catch (OpenDALException e) {
      LOGGER.error("Unable to delete container '{}'", resolvedBucket, e);
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
