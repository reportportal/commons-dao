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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Dzianis_Shybeka
 */
public class LocalDataStore implements DataStore {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocalDataStore.class);

  private final FeatureFlagHandler featureFlagHandler;
  private final String storagePath;
  private final String bucketPrefix;
  private final String bucketPostfix;
  private final String defaultBucketName;

  public LocalDataStore(FeatureFlagHandler featureFlagHandler, String storagePath,
      String bucketPrefix, String bucketPostfix, String defaultBucketName) {
    this.featureFlagHandler = featureFlagHandler;
    this.storagePath = storagePath;
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
    try {
      Path path = Paths.get(storagePath, storedFile.bucket(), storedFile.filePath());
      Files.createDirectories(path.getParent());
      Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
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
    Path path = Paths.get(storagePath, storedFile.bucket(), storedFile.filePath());
    if (Files.exists(path)) {
      try {
        return Files.newInputStream(path);
      } catch (IOException e) {
        throw new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, e.getMessage(), e);
      }
    }
    throw new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, "Unable to find file");
  }

  @Override
  public boolean exists(String filePath) {
    if (filePath == null) {
      return false;
    }
    StoredFile storedFile = getStoredFile(filePath);
    Path path = Paths.get(storagePath, storedFile.bucket(), storedFile.filePath());
    return Files.exists(path);
  }

  @Override
  public void delete(String filePath) {
    if (filePath == null) {
      return;
    }
    StoredFile storedFile = getStoredFile(filePath);
    Path path = Paths.get(storagePath, storedFile.bucket(), storedFile.filePath());
    try {
      Files.deleteIfExists(path);
    } catch (IOException e) {
      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to delete file", e);
    }
  }

  @Override
  public void deleteAll(List<String> filePaths, String bucketName) {
    String resolvedBucketName;
    if (!featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)) {
      resolvedBucketName = bucketPrefix + bucketName + bucketPostfix;
    } else {
      resolvedBucketName = bucketName;
    }

    for (String filePath : filePaths) {
      Path path = Paths.get(storagePath, resolvedBucketName, filePath);
      try {
        Files.deleteIfExists(path);
      } catch (IOException e) {
        LOGGER.error("Unable to delete file '{}'", path, e);
      }
    }
  }

  @Override
  public void deleteContainer(String bucketName) {
    String resolvedBucketName;
    if (!featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)) {
      resolvedBucketName = bucketPrefix + bucketName + bucketPostfix;
    } else {
      resolvedBucketName = bucketName;
    }

    Path path = Paths.get(storagePath, resolvedBucketName);
    if (Files.exists(path)) {
      try (Stream<Path> walk = Files.walk(path)) {
        walk.sorted(Comparator.reverseOrder())
            .forEach(p -> {
              try {
                Files.delete(p);
              } catch (IOException e) {
                LOGGER.error("Unable to delete '{}'", p, e);
              }
            });
      } catch (IOException e) {
        LOGGER.error("Unable to delete container '{}'", bucketName, e);
      }
    }
  }

  private StoredFile getStoredFile(String filePath) {
    if (featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)) {
      return new StoredFile(defaultBucketName, filePath);
    }
    Path targetPath = Paths.get(filePath);
    int nameCount = targetPath.getNameCount();
    String bucketName;
    if (nameCount > 1) {
      bucketName = bucketPrefix + retrievePath(targetPath, 0, 1) + bucketPostfix;
      return new StoredFile(bucketName, retrievePath(targetPath, 1, nameCount));
    } else {
      bucketName = defaultBucketName;
      return new StoredFile(bucketName, retrievePath(targetPath, 0, 1));
    }
  }

  private String retrievePath(Path path, int beginIndex, int endIndex) {
    return String.valueOf(path.subpath(beginIndex, endIndex));
  }
}
